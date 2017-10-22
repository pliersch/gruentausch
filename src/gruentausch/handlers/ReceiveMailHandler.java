package gruentausch.handlers;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import org.eclipse.e4.core.di.annotations.Execute;

public class ReceiveMailHandler {

	@Execute
	public void execute() {
		
		String user = "patrick.liersch@gmx.de";
		String password = "Rias=rs2!g";
		String host = "pop.gmx.net";

		Properties properties = System.getProperties();

//		properties.put("mail.transport.protocol", "smtp");
//		properties.put("mail.smtp.auth", "true");
//		properties.put("mail.smtp.user", sender);
//		properties.put("mail.smtp.password", password);
		
		properties.put("mail.pop3.starttls.enable", "true");
		properties.put("mail.pop3.ssl.enable", "true");
		properties.put("mail.pop3.host", host);
		properties.put("mail.pop3.port", "995");
		try {
			// Set property values
			Session emailSessionObj = Session.getDefaultInstance(properties);
			// Create POP3 store object and connect with the server
			Store storeObj = emailSessionObj.getStore("pop3");
			storeObj.connect(host, user, password);
			// Create folder object and open it in read-only mode
			Folder emailFolderObj = storeObj.getFolder("INBOX");
			emailFolderObj.open(Folder.READ_ONLY);
			// Fetch messages from the folder and print in a loop
			Message[] messageobjs = emailFolderObj.getMessages();

			for (int i = 0, n = messageobjs.length; i < n; i++) {
				Message indvidualmsg = messageobjs[i];
				System.out.println("Printing individual messages");
				System.out.println("No# " + (i + 1));
				System.out.println("Email Subject: " + indvidualmsg.getSubject());
				System.out.println("Sender: " + indvidualmsg.getFrom()[0]);
				System.out.println("Content: " + indvidualmsg.getContent().toString());

			}
			// Now close all the objects
			emailFolderObj.close(false);
			storeObj.close();
		} catch (NoSuchProviderException exp) {
			exp.printStackTrace();
		} catch (MessagingException exp) {
			exp.printStackTrace();
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	

}
