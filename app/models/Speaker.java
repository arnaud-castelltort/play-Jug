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
		List<Event> events = new ArrayList<Event>();
 		List<Talk> talks = Talk.find("bySpeaker", Speaker.findById(speakerId)).fetch();
 		for (Talk talk: talks) {
 			events.add(talk.event);
		}
 		return events;
	}
}
