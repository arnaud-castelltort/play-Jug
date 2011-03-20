package controllers;

import java.io.File;
import java.util.List;

import models.Event;
import models.Speaker;
import play.mvc.Controller;
import play.mvc.With;
import play.mvc.results.NotFound;

@With(Filters.class)
public class Application extends Controller {

	public static void index() {
		Event event = Event.next();
		if (event == null) {
			event = Event.last();
		}
		render(event);
	}

	public static void event(Long id) {
		Event event = Event.findById(id);
		String [] attachments = Event.attachments(id);
		render(event, attachments);
	}

	public static void about() {
		if (renderArgs.get("nextEventId") != null) {
			Event event = Event.findById(renderArgs.get("nextEventId"));
			render(event);
		} else {
			render();
		}
	}

	public static void view() {
		render();
	}

	public static void members() {
		List<Speaker> members = Speaker.getMembers();
		if (renderArgs.get("nextEventId") != null) {
			Event event = Event.findById(renderArgs.get("nextEventId"));
			render(members, event);
		} else {
			render(members);
		}
	}
	
	public static void member(Long id) {
		Speaker member = Speaker.findById(id);
		List<Event> memberEvent = Speaker.getSpeakerEvents(id);
		if (renderArgs.get("nextEventId") != null) {
			Event event = Event.findById(renderArgs.get("nextEventId"));
			render(member, memberEvent, event);
		} else {
			render(member, memberEvent);
		}
	}
	
	public static void downloadEventAttachment(Long eventId, String attachment) {
		File file = Event.getAttachment(eventId, attachment);
		if (file != null && file.exists()) {
			renderBinary(file);
		} else {
			notFound();
		}
	}

}
