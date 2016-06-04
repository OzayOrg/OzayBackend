package com.ozay.backend.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by naofumiezaki on 6/5/16.
 */
public class HTMLUtility {
    public static String removeScriptTag(String html){

        if(html != null) {
            String re = "<script>(.*)</script>";

            Pattern pattern = Pattern.compile(re);
            Matcher matcher = pattern.matcher(html);
            if (matcher.find()) {
                return html.replace(matcher.group(1), "");
            }else {
                return html;
            }
        }
        return null;
    }
}
