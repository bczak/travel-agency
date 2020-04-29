package RSP.dao;

import RSP.ParserApplicationBackend;
import RSP.dto.SortAttribute;
import RSP.dto.SortOrder;
import RSP.model.Trip;
import generator.Generator;
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

import java.text.ParseException;
import java.util.Date;
import java.util.List;

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
        trip.setName("Test");
        trip.setPrice(10000);
        trip.setDescription("asdsadd");
        tripDao.add(trip);

        assertNotNull(tripDao.get(trip.getId()));
    }

    @Test
    public void removeTrip(){
        final Trip trip = new Trip();

        trip.setStartDate(new Date());
        trip.setLength(15);
        trip.setName("Test");
        trip.setPrice(10000);

        tripDao.add(trip);

        tripDao.remove(trip);

        assertNull(tripDao.get(trip.getId()));
    }

    @Test
    public void getByNameTest() throws ParseException {
        Trip t = Generator.generateTrip();

        tripDao.add(t);

        Trip test = tripDao.get(t.getId());
        assertNotNull(test);

        tripDao.getByName(t.getName());
        assertEquals(test.getName(), t.getName());

    }

    @Deprecated
    @Test
    public void sortAllTest() throws ParseException {
        Trip t = Generator.generateTrip();
        t.setPrice(1000);
        tripDao.add(t);

        Trip t2 = Generator.generateTrip();
        t2.setPrice(1);
        tripDao.add(t2);

        Trip t3 = Generator.generateTrip();
        t3.setPrice(20);
        tripDao.add(t3);

        List<Trip> sortList = tripDao.getAllSorted(SortAttribute.PRICE, SortOrder.ASCENDING);

        assertNotEquals(tripDao.getAll().get(0).getPrice(), sortList.get(0).getPrice());
        assertEquals(1.0,sortList.get(0).getPrice(), 0);

    }

}
