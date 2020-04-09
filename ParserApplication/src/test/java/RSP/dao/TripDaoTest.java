package RSP.dao;

import RSP.ParserApplicationBackend;
import RSP.model.Trip;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.*;

import java.util.Date;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ComponentScan(basePackageClasses = ParserApplicationBackend.class)
public class TripDaoTest {

    @Autowired
    private TripDao tripDao;

    @Test
    public void addTrip(){
        final Trip trip = new Trip();

        trip.setStartDate(new Date());
        trip.setLength(15);
        trip.setLocation("ABAS City");
        trip.setName("Test");
        trip.setPrice(10000);

        tripDao.add(trip);

        assertNotNull(tripDao.get(trip.getId()));
    }

    @Test
    public void removeTrip(){
        final Trip trip = new Trip();

        trip.setStartDate(new Date());
        trip.setLength(15);
        trip.setLocation("ABAS City");
        trip.setName("Test");
        trip.setPrice(10000);

        tripDao.add(trip);

        tripDao.remove(trip);

        assertNull(tripDao.get(trip.getId()));
    }

}
