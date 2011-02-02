package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class Participation extends Model {

	public enum ParticipationStatus {
		WaitingPreRegistration, PreRegistration, Confirmed, OnWaitingList;

		public ParticipationStatus next() {
			switch (this) {
			case WaitingPreRegistration:
				return PreRegistration;
			case PreRegistration:
				return Confirmed;
			}
			return null;

		}
	}

	public ParticipationStatus status = ParticipationStatus.WaitingPreRegistration;

	public String code;

	@ManyToOne
	public User user;

	@ManyToOne
	public Event event;

	public void confirmed() {
		status = ParticipationStatus.Confirmed;
	}

	public void preRegistrated() {
		status = ParticipationStatus.PreRegistration;
	}

}
