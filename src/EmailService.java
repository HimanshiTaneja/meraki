import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailService {

	public void sendEmail(String secretSanta, String santaFor, String emailId,
			Session session) {

		String to = emailId;
		String from = "tanejahimanshi9@gmail.com";
		String text = "Hi " + secretSanta
				+ ",\n\nYou will be the Secret Santa for " + santaFor
				+ ".\n\nGreetings!\nMerry Christmas :)";

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));
			message.setSubject("Secret Santa");

			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setText(text);
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();

			String filename = "src/Merry Christmas.gif";
			DataSource source = new FileDataSource(filename);
			messageBodyPart2.setDataHandler(new DataHandler(source));
			messageBodyPart2.setFileName("Merry Christmas.gif");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			multipart.addBodyPart(messageBodyPart2);

			message.setContent(multipart);

			Transport.send(message);
			System.out.println("message sent successfully");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public void sendMails(Map<String, String> secretSantaPairs,
			Map<String, String> members) {

		String username = "tanejahimanshi9@gmail.com";
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Scanner s=new Scanner(System.in);
		System.out.println("Enter password");
		String password = s.next();
		s.close();
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		for (String secretSanta : secretSantaPairs.keySet()) {
			sendEmail(secretSanta, secretSantaPairs.get(secretSanta),
					members.get(secretSanta), session);
		}
		
		   //sendEmail("","","himanshi2579@gmail.com",session);

	}
}
