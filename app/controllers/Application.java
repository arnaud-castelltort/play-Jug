package controllers;

import java.util.List;

import models.Event;
import models.Speaker;
import play.mvc.Controller;
import play.mvc.With;

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
		render(event);
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
		List<Speaker> members = Speaker.findAll();
		if (renderArgs.get("nextEventId") != null) {
			Event event = Event.findById(renderArgs.get("nextEventId"));
			render(members, event);
		} else {
			render(members);
		}
	}

}
