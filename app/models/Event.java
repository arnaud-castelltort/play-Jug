package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.data.validation.MaxSize;
import play.db.jpa.JPABase;
import play.db.jpa.Model;

@Entity
public class Event extends Model {


	public String nouguier;
	
	public String title;

	public Date date;

	public String location;

	@MaxSize(2000)
	public String description;

	@MaxSize(5000)
	public String report;

	public String registrationURL;

	public int capacity;
	
	
	@OneToMany(mappedBy="event", cascade=CascadeType.ALL)
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

	public boolean registrationCloded() {
		return date.compareTo(new Date()) < 0;
	}

	public List<Speaker> speakers() {
		List<Speaker> speakers = new ArrayList<Speaker>();
		for (Talk talk : talks) {
			speakers.add(talk.speaker);
		}
		return speakers;
	}

}
