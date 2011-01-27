package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.MaxSize;
import play.db.jpa.Model;

@Entity
public class Talk extends Model {

	public String title;

	@MaxSize(5)
	public String time;

	@ManyToOne
	public Speaker speaker;

	public String tags;

	@MaxSize(1000)
	public String teaser;

	@ManyToOne
	public Event event;
}
