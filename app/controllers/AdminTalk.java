package controllers;

import play.mvc.With;
import models.Talk;

@CRUD.For(Talk.class)
@With(Secure.class)
public class AdminTalk extends CRUD {

}

