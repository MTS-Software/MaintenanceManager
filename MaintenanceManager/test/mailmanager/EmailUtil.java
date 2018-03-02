package mailmanager;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailUtil {

	public static void main(String[] args) {

		EmailUtil mail = new EmailUtil();

		mail.test();

	}

	private void test() {

		String smtpHostServer = "10.176.199.45";
		String from = "mpt_ilz_sys_maplat@magna.com";

		String to = "markus.thaler@magna.com,markus.thaler@gmx.at";
		String betreff = "Betreff";
		String text = "Diese Nachricht wurde an folgende Adressen versendet: " + to;

		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtpHostServer);
		props.put("mail.smtp.auth", "false");

		Session session = Session.getInstance(props, null);
		session.setDebug(true);

		sendEmail(session, from, to, null, betreff, text, null);

	}

	/**
	 * Utility method to send simple HTML email
	 * 
	 * @param session
	 * @param to
	 * @param betreff
	 * @param text
	 */
	public static void sendEmail(Session session, String from, String to, String cc, String betreff, String text,
			File file) {
		try {
			MimeMessage msg = new MimeMessage(session);

			// set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress(from, from));

			// msg.setReplyTo(InternetAddress.parse("no_reply@journaldev.com", false));

			msg.setSubject(betreff, "UTF-8");

			msg.setText(text, "UTF-8");

			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, to);

			if (!cc.isEmpty())
				msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc, false));

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setText(text);

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			// messageBodyPart = new MimeBodyPart();
			// String filename = file.getAbsolutePath();
			// DataSource source = new FileDataSource(filename);
			// messageBodyPart.setDataHandler(new DataHandler(source));
			// messageBodyPart.setFileName(file.getName());
			// multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			msg.setContent(multipart);

			System.out.println("Nachricht ist bereit");

			Transport.send(msg);

			System.out.println("Nachricht wurde erfolgreich versendet");

		} catch (Exception e) {

			System.out.println("Fehler beim Senden der Nachricht");
			e.printStackTrace();
		}
	}

}
