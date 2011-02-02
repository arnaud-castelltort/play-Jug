import java.util.List;

import models.Participation;
import models.User;

import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class InscriptionTest extends UnitTest {

	@Test
	public void participantsTest()
	{
		Fixtures.load("data.yml");
		User user1 = User.findById(1L);
		Participation participation = Participation.findById(1L);
		List<Participation> participations = Participation.find("user.id = ?", 1L).fetch();
		System.out.println(Participation.find("user.id = ?", 1L).fetch().size());
		
	}
}
