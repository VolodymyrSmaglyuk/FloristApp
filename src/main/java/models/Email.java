package models;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Email {

    public static void SendMessage(String errorMessage,Exception exception) {

        final String username = "smaglukvolodia54@gmail.com";
        final String password = "nmwxsmqflgihuapt";
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("smaglukvolodia54@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("smaglukvoloda822@gmail.com")
            );
            message.setSubject("Помилка в роботі програми.");
            generateFileWithExeption(exception);
            compressLogFile();
            message.setText(errorMessage);
            MimeMultipart multipart = new MimeMultipart();
            MimeBodyPart attachment = new MimeBodyPart();
            attachment.attachFile(new File("C:\\LP\\ПП\\JavaFXtest\\ErrorDetail.txt"));
            MimeBodyPart attachment2 = new MimeBodyPart();
            attachment2.attachFile(new File("C:\\LP\\ПП\\JavaFXtest\\compressedLogFile.zip"));
            MimeBodyPart messagePart = new MimeBodyPart();
            messagePart.setText(errorMessage);
            multipart.addBodyPart(attachment);
            multipart.addBodyPart(attachment2);
            multipart.addBodyPart(messagePart);
            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Лист з інформацією про помилку надіслано на e-mail.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void generateFileWithExeption(Exception e) throws IOException {
        FileOutputStream fos = new FileOutputStream(
                "C:\\LP\\ПП\\Lab4-8\\ComplexLab\\ErrorDetail.txt",false);
        PrintStream ps = new PrintStream(fos);
        e.printStackTrace(ps);
        fos.close();
        ps.close();
    }
    private static void compressLogFile() throws IOException {
        String sourceFile = "C:\\LP\\ПП\\JavaFXtest\\Logger.log";
        FileOutputStream fos = new FileOutputStream("C:\\LP\\ПП\\JavaFXtest\\" +
                "compressedLogFile.zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File(sourceFile);
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        zipOut.close();
        fis.close();
        fos.close();
    }

}
