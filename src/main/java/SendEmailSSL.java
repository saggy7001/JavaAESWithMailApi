import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class SendEmailSSL {
    final static String username = "sagardada101@gmail.com";
    final static String password = "pierkjlhwbgmjjut";

    private static Session getSession(){
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        return session;
    }

    public static void sendEmail(String fromEmail, String toEmail, String subject, String msg){
        Session session = getSession();
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toEmail)
            );
            message.setSubject(subject);
            message.setText(msg
                    + "\n\n Please decrypt this email as this is secured !!!");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void check()
    {
        try {
            Session emailSession = getSession();

            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");

            store.connect("smtp.gmail.com", username, password);

            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            try {
                // retrieve latest 5 messages
                List<Message> messages = Arrays.asList(emailFolder.getMessages());
                List<Message> lastmsgs = messages.subList(messages.size()-6, messages.size()-1);

                List<Message> result = new ArrayList<>();
                for (Message msg : lastmsgs){
                    if (msg.getSubject().equals("Test Subject")){
                        result.add(msg);
                    }
                }
                System.out.println(result);
            }catch (MessagingException e) {
                e.printStackTrace();
            }

            System.out.println("messages.length---");

            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getSubject(Message msg) {
        try {
            return msg.getSubject();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
