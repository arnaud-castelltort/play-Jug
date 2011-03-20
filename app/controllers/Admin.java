package controllers;

import java.io.File;
import java.io.IOException;

import models.Event;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import play.Play;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Admin extends Controller {

    @Check("Admin")
    public static void addEventAttachment(Long eventId, File attachment) {
        notFoundIfNull(attachment);
        File f = Event.getAttachment(eventId, StringUtils.deleteWhitespace(attachment.getName()));
        try {
            FileUtils.moveFile(attachment, f);
            flash.success(Messages.get("upload.success", f.getName()));
        } catch (IOException e) {
            validation.addError("attachment", Messages.get("upload.error"), "attachment");
            flash.error(Messages.get("upload.error"));
        }
        Application.event(eventId);
    }

    @Check("Admin")
    public static void rmEventAttachment(Long eventId, String filename) {
        if (filename != null && filename.trim().length() > 0
                && FileUtils.deleteQuietly(Event.getAttachment(eventId, filename))) {
            flash.success(Messages.get("remove.success", filename));
        } else {
            flash.error(Messages.get("remove.error", filename));
        }
        Application.event(eventId);
    }
}
