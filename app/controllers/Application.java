package controllers;

import models.Event;
import play.mvc.*;

@With(Filters.class)
public class Application extends Controller {

	public static void index() {
		Event event = Event.next();
		render(event);
	}

	public static void event(Long id) {
		Event event = Event.findById(id);
		render(event);
	}

	public static void about() {
		Event event = Event.findById(renderArgs.get("nextEventId"));
		render(event);
		render();
	}

	public static void view() {
		render();
	}

}
