package com.ozay.backend.service;

import com.ozay.backend.domain.User;
import com.ozay.backend.model.*;
import com.ozay.backend.repository.*;
import com.ozay.backend.web.rest.dto.OrganizationUserRoleDTO;
import com.ozay.backend.web.rest.form.RoleFormDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by naofumiezaki on 11/18/15.
 */
@Service
@Transactional
public class NotificationService {

    @Inject
    NotificationRepository notificationRepository;

    public void processNotification(Notification notification){

    }
}
