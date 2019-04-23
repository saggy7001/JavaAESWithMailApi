public class Consumer {
    public static void main(String[] args)
    {
        final String secretKey = "ssshhhhhhhhhhh!!!!";
        String msg = "howtodoinjava.com";

        String encryptedMsg = AES.encrypt(msg, secretKey) ;
        String decryptedString = AES.decrypt(encryptedMsg, secretKey) ;

        System.out.println(msg);
        System.out.println(encryptedMsg);
        System.out.println(decryptedString);

        String fromEmail = "sagardada101@gmail.com";
        String toEmail = "sagardada101@gmail.com";
        String subject = "Test Subject";
        String message = encryptedMsg;

        SendEmailSSL.sendEmail(fromEmail, toEmail, subject, message);

        //SendEmailSSL.check();
    }
}
