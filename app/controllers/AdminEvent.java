package controllers;

import play.mvc.With;
import models.Event;


@CRUD.For(Event.class)
@With(Secure.class)
public class AdminEvent extends CRUD {

}

