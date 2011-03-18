package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Lob;

import play.data.validation.MaxSize;
import play.db.jpa.Model;

@Entity
public class Speaker extends Model {

    public String fullName;

    public String activity;

    public String company;

    public String url;

    @Lob
    @MaxSize(5000)
    public String description;

    public Boolean jugmember;

    public String memberFct;

    public String photoUrl;

    public static List<Speaker> getMembers() {
        return find("jugmember", Boolean.TRUE).fetch();
    }

    public static List<Event> getSpeakerEvents(Long speakerId) {
        return Talk.find(
                "select talk.event from Talk talk where talk.speaker.id=?",
                speakerId).fetch();
    }

    @Override
    public String toString() {
        return fullName;
    }
}
