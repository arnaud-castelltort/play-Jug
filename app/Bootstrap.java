import java.util.Date;

import models.Event;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

/**
 * Bootstrap some init data to avoid some useless test for the application long life time
 * @author arnaud Castelltort
 *
 */
@OnApplicationStart
public class Bootstrap extends Job {

	
	@Override
	public void doJob() throws Exception {
		if(Event.count() == 0)
		{
			Fixtures.load("initial-data.yml");
		}
	}
}
