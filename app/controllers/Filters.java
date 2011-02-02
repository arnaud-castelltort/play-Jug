package controllers;

import models.Event;
import play.mvc.Before;
import play.mvc.Controller;

public class Filters extends Controller {

	@Before
	static void menu()
	{
			renderArgs.put("nextEventId", Event.next().id);
	}

}
