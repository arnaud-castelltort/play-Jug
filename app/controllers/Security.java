package controllers;

import play.Play;

public class Security extends Secure.Security {

    static boolean authenticate(String username, String password) {
        return username.equals(Play.configuration.getProperty("secure.admin.username"))
                && password.equals(Play.configuration.getProperty("secure.admin.password"));
    }

    static boolean check(String profile) {
        if (profile.equals("Admin")) {
            return session.get("username").equals(Play.configuration.getProperty("secure.admin.username"));
        }
        return false;
    }
}
