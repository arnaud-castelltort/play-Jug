package controllers;

import models.Event;
import play.mvc.Before;
import play.mvc.Controller;

public class Filters extends Controller {

	@Before
	static void menu()
	{
		Event event = Event.next();
		if (event == null) {
			event = Event.last();
		}
		if (event != null) {
			renderArgs.put("nextEventId", event.id);
		}
	}

}
