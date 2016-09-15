package com.ozay.backend.service;

import com.ozay.backend.config.JHipsterProperties;
import com.ozay.backend.domain.User;
import com.ozay.backend.model.*;
import com.ozay.backend.repository.OrganizationRepository;
import com.ozay.backend.repository.BuildingRepository;
import com.ozay.backend.web.rest.dto.OrganizationUserDTO;
import com.ozay.backend.web.rest.form.NotificationFormDTO;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Version;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.CharEncoding;
import org.joda.time.DateTime;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;
import java.util.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Service for sending e-mails.
 * <p/>
 * <p>
 * We use the @Async annotation to send e-mails asynchronously.
 * </p>
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    @Inject
    private JHipsterProperties jHipsterProperties;

    @Inject
    private JavaMailSenderImpl javaMailSender;

    @Inject
    private MessageSource messageSource;

    @Inject
    private SpringTemplateEngine templateEngine;

    @Inject
    private OrganizationRepository organizationRepository;

    @Inject
    private BuildingRepository buildingRepository;

    /**
     * System default email address that sends the e-mails.
     */
    private String from;

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent e-mail to User '{}'", to);
        } catch (Exception e) {
            log.warn("E-mail could not be sent to user '{}', exception is: {}", to, e.getMessage());
        }
    }

    @Async
    public void sendActivationEmail(User user, String baseUrl) {
        log.debug("Sending activation e-mail to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable("user", user);
        context.setVariable("baseUrl", baseUrl);
        String content = templateEngine.process("activationEmail", context);
        String subject = messageSource.getMessage("email.activation.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendPasswordResetMail(User user, String baseUrl) {
        log.debug("Sending password reset e-mail to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable("user", user);
        context.setVariable("baseUrl", baseUrl);
        String content = templateEngine.process("passwordResetEmail", context);
        String subject = messageSource.getMessage("email.reset.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendNewOrganizationUserWelcomeEmail(OrganizationUserDTO organizationUserDTO, String baseUrl) {
        log.debug("Sending organization user welcome e-mail to '{}'", organizationUserDTO.getEmail());
        Locale locale = Locale.forLanguageTag("en");
        Context context = new Context(locale);
        context.setVariable("user", organizationUserDTO);
        context.setVariable("baseUrl", baseUrl);
        String content = templateEngine.process("organizationUserWelcomeEmail", context);
        String subject = messageSource.getMessage("email.organizationUserWelcome.title", null, locale);
        sendEmail(organizationUserDTO.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendOrganizationUserInvitationMail(OrganizationUserDTO organizationUserDTO, String baseUrl, String activationKey) {
        log.debug("Sending organization user invitation e-mail to '{}'", organizationUserDTO.getEmail());
        Locale locale = Locale.forLanguageTag("en");

        String name = organizationUserDTO.getFirstName() + " " + organizationUserDTO.getLastName();
        Organization organization = organizationRepository.findOne(organizationUserDTO.getOrganizationId());
        Context context = new Context(locale);
        context.setVariable("user", organizationUserDTO);
        context.setVariable("name", name);
        context.setVariable("organization", organization.getName());
        context.setVariable("activationKey", activationKey);
        context.setVariable("baseUrl", baseUrl);
        String content = templateEngine.process("organizationUserInvitationEmail", context);
        String subject = messageSource.getMessage("email.organizationUser.title", null, locale);
        sendEmail(organizationUserDTO.getEmail(), subject, content, false, true);
    }

    @Async
    public void inviteMember(Member member, Building building, InvitedMember invitedMember, String baseUrl){
        log.debug("Sending invitation e-mail to {}", member);
        Locale locale = Locale.forLanguageTag(invitedMember.getLangKey());
        Context context = new Context(locale);
        context.setVariable("name", member.getFirstName() + " " + member.getLastName());
        context.setVariable("building", building.getName());
        context.setVariable("activationKey", invitedMember.getActivationKey());
        context.setVariable("baseUrl", baseUrl);
        String content = templateEngine.process("memberInvitationEmail", context);
        String subject = messageSource.getMessage("email.member.subject", null, locale);
        sendEmail(member.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendTrackComplete(String email, boolean trackComplete,String Subject, DateTime createdDate, DateTime trackedDate){
        log.debug("Sending invitation e-mail to {}", email);
        //Locale locale = Locale.forLanguageTag(invitedMember.getLangKey());
        Locale locale = Locale.forLanguageTag("en");
        Context context = new Context(locale);
        //context.setVariable("name", member.getFirstName() + " " + member.getLastName());
        context.setVariable("building", email);
        //String content = templateEngine.process("memberInvitationEmail", context);
        //String subject = messageSource.getMessage("email.member.subject", null, locale);
        String status = null;
        if (trackComplete==true) {
            status = "COMPLETE" ; }
        if  (trackComplete==false) {
            status="INCOMPLETE";}
        Date dt = createdDate.toDate();

        Date input = new Date();
        LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        Date dt_track = trackedDate.toDate();

        sendEmail(email, status + ": " + Subject, "Task created on " + date + ". " + status + " on " + dt_track , false, true);
    }


    // Collaborate
    @Async
    public void sendCollaborateMail(Collaborate collaborate, List<Member> members){
        String[] emails = this.getEmailsFromListMembers(members);
        log.debug("Sending Collaborate e-mail to {}", emails);
        Locale locale = Locale.forLanguageTag("en");
        Context context = new Context(locale);
        List<String> optionsToSelect=new ArrayList<String>();
        String subject = "";
        String message = null;
        String responseType = "";
        String additional ="";
        String dated="";
        List<String> dates = new ArrayList<String>();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy hh:mm a");
        if(collaborate.getStatus() == Collaborate.STATUS_CREATED){
            subject = collaborate.getSubject();
            message += collaborate.getMessage();


            for(CollaborateField cd : collaborate.getCollaborateFields()){
                dates.add(formatter.print(new DateTime(cd.getIssueDate())));
                optionsToSelect.add(cd.getQuestion());
            }
            dated=dates.get(0);
            
        }
        else if(collaborate.getStatus() == Collaborate.STATUS_CANCELED){
            subject = "Collaborate Canceled";
            message += "<p>The following collaborate is canceled</p>";
            message += collaborate.getMessage();
        }
        else if(collaborate.getStatus() == Collaborate.STATUS_COMPLETED){
            subject = "Collaborate Completed";
            CollaborateField scheduledDate = null;
            for(CollaborateField cd : collaborate.getCollaborateFields()){
                if(cd.getId() == collaborate.getCollaborateFieldId()){
                    scheduledDate = cd;
                    break;
                }
            }

            message += "Scheduled Date is " + formatter.print(new DateTime(scheduledDate.getIssueDate()));
            
        }

        if(collaborate.getResponse() == Collaborate.RADIO){
            responseType = "Radio";
        } else if(collaborate.getResponse() == Collaborate.MULTIPLE_CHOICE){
            responseType = "Multiple Choice";
        }
        else if(collaborate.getResponse() == Collaborate.CALENDAR){
            responseType = "Calendar";
        }

        context.setVariable("collaborate", collaborate);
        context.setVariable("responseType", responseType);
        context.setVariable("optionsToSelect", optionsToSelect);
        context.setVariable("additional", additional);
        context.setVariable("dated", dated);
        context.setVariable("message", message);
        String content = templateEngine.process("collaborateMail", context);

        log.debug("About to collaborate email Status " + collaborate.getStatus());
        this.sendMultipleEmails(emails, subject, content, false, true);
    }

    @Async
    public void sendCollaborateUpdate(Collaborate collaborate, Member replyFrom, String creatorEmail){

        log.debug("Sending collabrate update e-mail to {}", creatorEmail);
        Locale locale = Locale.forLanguageTag("en");
        Context context = new Context(locale);
        context.setVariable("body", collaborate.getMessage());
        String content = templateEngine.process("collaborateUpdate", context);

        String subject = collaborate.getSubject();
        log.debug("About to send email");
        this.sendEmail(creatorEmail, subject, content, false, true);
    }

    @Async
    public void sendCollaborateCancel(Collaborate collaborate, List<Member> members){
        String[] emails = this.getEmailsFromListMembers(members);
        log.debug("Sending collaborate Cancel e-mail to {}", emails);
        Locale locale = Locale.forLanguageTag("en");
        Context context = new Context(locale);
        context.setVariable("body", collaborate.getMessage());
        String content = templateEngine.process("collaborateUpdate", context);

        String subject = "Collaborate updated";
        log.debug("About to send email");
        this.sendMultipleEmails(emails, subject, content, false, true);
    }

    @Async
    public void sendCollaborateComplete(Collaborate collaborate, List<Member> members, CollaborateField collaborateField){
        String[] emails = this.getEmailsFromListMembers(members);
        log.debug("Sending collaborate complete e-mail to {}", emails);
        Locale locale = Locale.forLanguageTag("en");
        Context context = new Context(locale);
        context.setVariable("body", collaborate.getMessage());
        String content = templateEngine.process("collaborateComplete", context);

        if(collaborate.getResponse() == Collaborate.CALENDAR && collaborateField != null){
            TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
            net.fortuna.ical4j.model.TimeZone timezone = registry.getTimeZone("America/New_York");

            java.util.Calendar cal = java.util.Calendar.getInstance(timezone);
            DateTime issueDate = new DateTime(collaborateField.getIssueDate());
            cal.set(java.util.Calendar.YEAR, issueDate.getYear());
            cal.set(java.util.Calendar.MONTH, issueDate.getMonthOfYear());
            cal.set(java.util.Calendar.DAY_OF_MONTH, issueDate.getDayOfMonth());
            cal.set(java.util.Calendar.HOUR_OF_DAY, issueDate.getHourOfDay());
            cal.set(Calendar.MINUTE, issueDate.getMinuteOfDay());
            cal.clear(java.util.Calendar.SECOND);

            net.fortuna.ical4j.model.DateTime dt = new net.fortuna.ical4j.model.DateTime(cal.getTime());
            dt.setTimeZone(timezone);
            VEvent collaborateIcal = new VEvent(dt, "Collaborate");
        } else {
            String subject = "Collaborate completed";
            log.debug("About to send email");
            this.sendMultipleEmails(emails, subject, content, false, true);
        }

    }

    // End collaborate

    private String[] getEmailsFromListMembers(List<Member> members){
        ArrayList<String> emails = new ArrayList<String>();
        for(Member m : members){
            emails.add(m.getEmail());
        }
        return emails.toArray(new String[emails.size()]);
    }

    @Async
    public void sendNotification(NotificationFormDTO notificationFormDTO, String[] to) {
        Notification notification = notificationFormDTO.getNotification();
        log.debug("Sending notification e-mail to {}", to);
        Locale locale = Locale.forLanguageTag("en");
        Context context = new Context(locale);
        context.setVariable("body", notification.getNotice());
        String content = templateEngine.process("notificationEmail", context);
        Building building = buildingRepository.findOne(notification.getBuildingId());
        String subject = building.getName()+" : "+notification.getSubject();
        log.debug("About to send email");
        this.sendMultipleEmails(to, subject, content, false, true);
    }

    @Async
    private void sendMultipleEmails(String[] to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);

            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);


            message.setText(content, isHtml);

            for(String emailAddress:to){
                message.setTo(emailAddress);
                javaMailSender.send(mimeMessage);
            }
        } catch (Exception e) {
            log.warn("E-mail could not be sent to users '{}', exception is: {}", to, e.getMessage());
        }
        log.debug("Sent e-mail to Emails '{}'", to);
    }
    @Async
    private void sendMultipleEmailsWithCalendarInvite(String[] to, String subject, String content, boolean isMultipart, boolean isHtml, VEvent event) {
        log.debug("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);

            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);


            // http://www.programcreek.com/java-api-examples/index.php?api=net.fortuna.ical4j.data.CalendarOutputter
            // http://ical4j.sourceforge.net/introduction.html
            // calendar invite
            net.fortuna.ical4j.model.Calendar c = new net.fortuna.ical4j.model.Calendar();
            c.getProperties().add(event);
            c.getProperties().add(Version.VERSION_2_0);
            CalendarOutputter outputter = new CalendarOutputter();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            outputter.setValidating(false);
            outputter.output(c, os);
            message.addAttachment("invite.ics", new ByteArrayResource(os.toByteArray()));


            message.setText(content, isHtml);


            for(String emailAddress:to){
                message.setTo(emailAddress);
                javaMailSender.send(mimeMessage);
            }
        } catch (Exception e) {
            log.warn("E-mail could not be sent to users '{}', exception is: {}", to, e.getMessage());
        }
        log.debug("Sent e-mail to Emails '{}'", to);
    }
}
