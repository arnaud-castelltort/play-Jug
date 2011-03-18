package models;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.Play;
import play.data.validation.MaxSize;
import play.db.jpa.Model;

@Entity
public class Event extends Model {

    public String title;

    public Date date;

    public String location;

    @MaxSize(2000)
    public String description;

    @MaxSize(5000)
    public String report;

    public String registrationURL;

    public int capacity;

    public boolean open;

    public String map;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    public List<Talk> talks;

    public static Event next() {
        return Event.find("date > ? order by date", new Date()).first();
    }

    public static List<Event> upComings() {
        return Event.find("date > ? order by date", new Date()).fetch();
    }

    public static List<Event> pasts() {
        return Event.find("date < ? order by date desc", new Date()).fetch();
    }

    public static List<Event> lasts() {
        return Event.find("date < ? order by date desc", new Date()).fetch(3);
    }

    public static Event last() {
        return Event.find("date < ? order by date desc", new Date()).first();
    }

    public boolean registrationCloded() {
        return !open || date.compareTo(new Date()) < 0;
    }

    public List<Talk> talks() {
        Collections.sort(talks, new Comparator<Talk>() {
            public int compare(Talk talk1, Talk talk2) {
                return talk1.orderInEvent - talk2.orderInEvent;
            };
        });
        return talks;
    }

    public List<Speaker> speakers() {
        List<Speaker> speakers = new ArrayList<Speaker>();
        for (Talk talk : talks) {
            speakers.add(talk.speaker);
        }
        return speakers;
    }

    public static String[] attachments(Long id) {
        File eventAttachments = getAttachment(id, "");
        return eventAttachments.list();
    }

    public static File getAttachment(Long id, String filename) {
        return Play.getFile("public/event" + id + "/" + filename);
    }

    @Override
    public String toString() {
        return title;
    }
}
