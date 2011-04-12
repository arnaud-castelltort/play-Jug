package notifiers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.SimpleEmail;

import models.Event;
import models.Participation;

import play.Play;
import play.jobs.Every;
import play.jobs.Job;
import play.jobs.On;
import play.libs.Mail;

/** Fire at 12:30pm (noon) every day **/
 @On("0 30 12 * * ?")
//@Every("1mn")
public class NotificationJob extends Job<String> {

    @Override
    public void doJob() throws Exception {
        List<Event> events = Event.findAll();
        for (Event event : events) {
            if(event.open && isSameDay(event.date, new Date())){
                StringBuffer stringBuffer = new StringBuffer();
                List<Participation> participations = Participation.find("event.id = ? ", event.id).fetch();
                for(Participation participation : participations){
                    Mails.confirmationMail(event, participation.user.email, "www.jug-montpellier.org", participation.code);
                    stringBuffer.append("Send mail to "+ participation.user.email + "\n");
                }
                SimpleEmail email = new SimpleEmail();
                email.setFrom(Play.configuration.getProperty("mail.smtp.user"));
                email.addTo("arnaud.castelltort@gmail.com");
                email.setSubject("Report");
                email.setMsg(stringBuffer.toString());
                Mail.send(email);
            }
        }
        super.doJob();
    }
    
    public static boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal1);
        
    }
    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }
}
