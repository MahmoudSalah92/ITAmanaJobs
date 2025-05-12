package sa.gov.amana.alriyadh.job.utils;

import org.apache.commons.lang3.StringUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class MailUtil {
    public MailUtil() {
        super();
    }


    public static boolean sendEmail(String from, String fromName, String to, String cc, String bcc, String subject, String body, int authType, String username, String password,
                                    String filePath, String fileName) {

        if(StringUtils.isBlank(to) || StringUtils.isBlank(from) || StringUtils.isBlank(body)) {
            return false;
        }

        String[] toArray = new String[0];
        String[] ccArray = new String[0];
        String[] bccArray = new String[0];
        if (!StringUtils.isBlank(to)) {
            toArray = to.split(";");
        }
        if (!StringUtils.isBlank(cc)) {
            ccArray = cc.split(";");
        }
        if (!StringUtils.isBlank(bcc)) {
            bccArray = bcc.split(";");
        }

        // RequestPojo's email ID needs to be mentioned.
        //String to = "mshehata@alriyadh.gov.sa";

        // Assuming you are sending email through relay.jangosmtp.net
        String host = "mail.alriyadh.gov.sa";

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "25");
        //props.put("mail.mime.charset", "UTF-8");
        props.put("Content-Type", "text/html");

        // Get the Session object.
        Session session = null;
        if (authType == 1) {
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
        } else {
            session = Session.getDefaultInstance(props);
        }

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            if (from.split(",").length > 1) {
                message.setFrom(new InternetAddress(from.split(",")[0], from.split(",")[1]));
            } else {
                message.setFrom(new InternetAddress(from, fromName));
            }

            // Set Email: RecipientType.To
            for (int i = 0; i < toArray.length; i++) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(toArray[i]));
            }

            // Set Email: RecipientType.CC
            for (int i = 0; i < ccArray.length; i++) {
                message.addRecipient(Message.RecipientType.CC, new InternetAddress(ccArray[i]));
            }

            // Set Email: RecipientType.BCC
            for (int i = 0; i < bccArray.length; i++) {
                message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bccArray[i]));
            }

            // Set Subject: header field
            message.setHeader("Content-Type", "text/html; charset=UTF-8");
            message.setSubject(subject, "UTF-8"); //message.setSubject("New user has been created");

            // Now set the actual message
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setHeader("Content-Type", "text/plain; charset=UTF-8");
            messageBodyPart.setContent(body.toString(), "text/html;charset=UTF-8");


            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            if (filePath != null) {
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(filePath);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(fileName);
                multipart.addBodyPart(messageBodyPart);
            }

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isValidEmailAddress(String email) {
        if (email != null && !email.isEmpty()) {
            String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
            java.util.regex.Matcher m = p.matcher(email);
            return m.matches();
        } else {
            return false;
        }
    }

    public static String validEmailAddress(String email) {
        StringBuilder emails = new StringBuilder();
        String[] toArray = new String[0];
        if (email != null) {
            toArray = email.split(";");
        }
        for (int i = 0; i < toArray.length; i++) {
            String emailAddress = toArray[i];
            if (emailAddress != null && !emailAddress.isEmpty()) {
                String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
                java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
                java.util.regex.Matcher m = p.matcher(emailAddress);
                if( m.matches()){
                    if(toArray.length >1){
                        emails.append(emailAddress).append(";");
                    }else{
                        emails = new StringBuilder(emailAddress);
                    }
                }
            }
        }
        return emails.toString();
    }

    public static boolean isEnternalEmailAddress(String email) {

        if (email.contains("alriyadh.gov.sa")) {
            return true;
        } else {
            return false;
        }
    }

//    public static void main(String[] args) {
//        String mail = "mshehata@alriyadh.gov.sa;msalah@alriyadh.gov.sa;yruryu@test";
//        String emails = validEmailAddress(mail);
//        System.out.println(emails);
//    }

}
