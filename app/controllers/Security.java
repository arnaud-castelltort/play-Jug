package controllers;

import play.Play;

public class Security extends Secure.Security {

	static boolean authenticate(String username, String password) {
		return username.equals(Play.configuration
				.getProperty("secure.admin.username"))
				&& password.equals(Play.configuration
						.getProperty("secure.admin.password"));
	}
}
