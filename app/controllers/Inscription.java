package controllers;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import notifiers.Mails;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.sun.xml.internal.ws.api.pipe.Codec;

import models.Event;
import models.Participation;
import models.User;

import play.cache.Cache;
import play.data.validation.Required;
import play.libs.Images;
import play.libs.Mail;
import play.mvc.Controller;
import play.mvc.With;

@With(Filters.class)
public class Inscription extends Controller{

	public static void show(Long id)
	{
		Event event = Event.findById(id);
		List<Participation> participations = Participation.find("event.id = ? ", id).fetch();
		// create a random id for cache system
		String randomId = play.libs.Codec.UUID();

		render(participations,event, randomId);
	}
	
	public static synchronized void postSubscription(
			Long id, 
			@Required(message="Un email est requis") String subscribeEmail,
			@Required(message="Vous devez remplir le captcha")String subscribeCaptcha, 
			String randomId)
	{
		//TODO remove synch method and create synch bloc
		//TODO add waiting list 

		//Check section
		
		Event event = Event.findById(id);
		if(event == null)
		{
			validation.addError("id", "L'évènement est introuvable", "id");
		}
		
		validation.equals(subscribeCaptcha, Cache.get(randomId)).message("Captcha invalide.");
		
		// check is the event is full
		if(event.capacity <= Participation.find("event.id = ?", id).fetch().size())
		{
			validation.addError("capacity", "Désolé mais la capacité d'accueil maximale est dépassée; Veuillez réessayer le jour même de l'évènement à partir de 15h00 (publication des désistements)", "capacity");
		}

		if(validation.hasErrors())
		{
			List<Participation> participations = Participation.find("event.id = ? ", id).fetch();
			render("Inscription/show.html",participations, event, randomId);
		}
		
		User user = User.find("byEmail", subscribeEmail).first();
		if (user == null)
		{
			user = new User();
			user.email = subscribeEmail;
			user.save();
		}
		
		Participation participation = new Participation();
		participation.event = event;
		participation.user = user;
		participation.code = UUID.randomUUID().toString();
		participation.save();
		Mails.preRegistrationMail(event, subscribeEmail, request.getBase(), participation.code);
		flash.success("Votre préréservation est enregistrée pour %s", event.title);
		Cache.delete(randomId);

		show(event.id);
	}

	/**
	 * Get code and acte in the workflow of participation
	 * @param code
	 */
	public static void validCode(String code){
		Participation participation = Participation.find("byCode", code).first();
		if(participation != null)
		{
			participation.status = participation.status.next();
			participation.code = UUID.randomUUID().toString();
			participation.save();
			Event event = participation.event;
			render("Inscription/show.html", event);
		}else
		{
			//TODO
//			flash("alerte", "Cannot valid participation");
		}
	}
	
	public static void captcha(String id) {
	    Images.Captcha captcha = Images.captcha();
	    String code = captcha.getText("#E4EAFD");
	    Cache.set(id, code, "10mn");
	    renderBinary(captcha);
	}

	
	private static void sendMail(String toEmail, String subject, String message) throws EmailException
	{
		SimpleEmail email = new SimpleEmail();
		email.setFrom("info@jug-montpellier.org");
		email.addTo(toEmail);
		email.setSubject(subject);
		email.setMsg(message);
		Mail.send(email); 
	}
	

}
