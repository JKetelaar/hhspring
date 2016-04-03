package edu.avans.hartigehap.domain.notifications;

import edu.avans.hartigehap.domain.NotificationAdapter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Includes a 'complicated' singleton
 *
 * @author JKetelaar
 */
public class EmailNotification extends NotificationAdapter {

    private static final String username = "ivh11b4@gmail.com";
    private static final String password = "myawesomepassword";
    private static final Properties properties = new Properties();
    private static EmailNotification instance;
    private Session session;

    /**
     * Constructing the email notification
     */
    public EmailNotification() {
        super(Type.EMAIL);

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        instance = this;
    }

    private static EmailNotification getInstance() {
        return instance == null ? instance = new EmailNotification() : instance;
    }

    /**
     * TODO: Well it might be inefficient to make #getSession also static, as you could call this from the instance too
     *
     * @return Saved session
     */
    public static Session getSession() {
        return getInstance().session == null ? getInstance().session = createSession() : getInstance().session;
    }

    /**
     * Creates the session for GMail, should only be called once, or if session is over
     *
     * @return Session
     */
    private static Session createSession() {
        return Session.getInstance(getInstance().properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                }
        );
    }

    @Override
    public boolean send(String receiver, String messageText) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from-email@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("to-email@gmail.com"));
            message.setSubject("Testing Subject");
            message.setText("This is a notification email for a successful login,"
                    + "\n\n " + messageText);

            Transport.send(message);

            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
