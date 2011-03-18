package controllers;

import play.mvc.With;
import models.Member;

@CRUD.For(Member.class)
@With(Secure.class)
public class AdminMember extends CRUD {

}