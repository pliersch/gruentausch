package gruentausch.handlers;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.eclipse.e4.core.di.annotations.Execute;

public class SendMailHandler {

	// @CanExecute
	// public boolean canExecute(EPartService partService) {
	// if (partService != null) {
	// return !partService.getDirtyParts().isEmpty();
	// }
	// return false;
	// }

	// @Execute
	// public void execute() {
	//
	// String recipient = "hourby@gmail.com";
	// String subject = "Hallo zusammen ...";
	// String text = "... ich bin eine E-Mail : - )";
	//
	// try {
	// Mail.send(MailAccounts.GMX, recipient, subject, text);
	// } catch (MessagingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	@Execute
	public void execute() {

		String sender = "patrick.liersch@gmx.de";
		String password = "Rias=rs2!g";
		String receiver = "hourby@gmail.com";

		Properties properties = new Properties();

		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.host", "mail.gmx.net");
		properties.put("mail.smtp.port", "25");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.user", sender);
		properties.put("mail.smtp.password", password);
		properties.put("mail.smtp.starttls.enable", "true");

		Session mailSession = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(properties.getProperty("mail.smtp.user"),
						properties.getProperty("mail.smtp.password"));
			}
		});

		Message message = new MimeMessage(mailSession);
		InternetAddress addressTo;
		try {
			addressTo = new InternetAddress(receiver);
			try {
				message.setRecipient(Message.RecipientType.TO, addressTo);
				message.setFrom(new InternetAddress(sender));
				message.setSubject("The subject");
				message.setContent("This is the message content", "text/plain");
				Transport.send(message);
			} catch (MessagingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// @Execute
	// public void execute() {
	// //Declare recipient's & sender's e-mail id.
	// String destmailid = "destemail@eduonix.com";
	// String sendrmailid = "frmemail@eduonix.com";
	// //Mention user name and password as per your configuration
	// final String uname = "username";
	// final String pwd = "password";
	// //We are using relay.jangosmtp.net for sending emails
	// String smtphost = "relay.jangosmtp.net";
	// //Set properties and their values
	// Properties propvls = new Properties();
	// propvls.put("mail.smtp.auth", "true");
	// propvls.put("mail.smtp.starttls.enable", "true");
	// propvls.put("mail.smtp.host", smtphost);
	// propvls.put("mail.smtp.port", "25");
	// //Create a Session object & authenticate uid and pwd
	// Session sessionobj = Session.getInstance(propvls,
	// new Authenticator() {
	// protected PasswordAuthentication getPasswordAuthentication() {
	// return new PasswordAuthentication(uname, pwd);
	// }
	// });
	//
	// try {
	// //Create MimeMessage object & set values
	// Message messageobj = new MimeMessage(sessionobj);
	// messageobj.setFrom(new InternetAddress(sendrmailid));
	// messageobj.setRecipients(Message.RecipientType.TO,InternetAddress.parse(destmailid));
	// messageobj.setSubject("This is test Subject");
	// messageobj.setText("Checking sending emails by using JavaMail APIs");
	// //Now send the message
	// Transport.send(messageobj);
	// System.out.println("Your email sent successfully....");
	// } catch (MessagingException exp) {
	// throw new RuntimeException(exp);
	// }
	// }

}
