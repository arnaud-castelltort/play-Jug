package notifiers;

import org.apache.commons.mail.EmailAttachment;

import models.Event;
import play.Play;
import play.mvc.Http.Request;
import play.mvc.Mailer;

public class Mails extends Mailer {

	static public void preRegistrationMail(Event event, String email, String host, String code)
	{
		setSubject("JUG-Montpellier: "+event.title + ": PreRegistration");
		addRecipient(email);
		setFrom("info@jug-montpellier.org");
//		EmailAttachment cssAttachment = new EmailAttachment();
//	      cssAttachment.setDescription("CSS File");
//	      cssAttachment.setPath(Play.getFile("public/stylesheets/main.css").getPath());
//	      cssAttachment.setDisposition(EmailAttachment.INLINE);
//	      addAttachment(cssAttachment);
//	EmailAttachment logoAttachment = new EmailAttachment();
//	      logoAttachment.setDescription("Logo Document");
//	      logoAttachment.setPath(Play.getFile("public/images/logo.png").getPath());
//	      logoAttachment.setDisposition(EmailAttachment.INLINE);
//	      addAttachment(logoAttachment);
		send(event,host, code);
	}

}
