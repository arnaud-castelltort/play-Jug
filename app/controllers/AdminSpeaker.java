package controllers;

import play.mvc.With;
import models.Speaker;

@CRUD.For(Speaker.class)
@With(Secure.class)
public class AdminSpeaker extends CRUD {

}

