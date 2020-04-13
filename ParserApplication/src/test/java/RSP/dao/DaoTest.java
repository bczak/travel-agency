package RSP.dao;

import RSP.ParserApplicationBackend;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ComponentScan(basePackageClasses = ParserApplicationBackend.class)
public class DaoTest {
    @Autowired
    protected TripDao tripDao;
    @Autowired
    protected TagDao tagDao;
    @Autowired
    protected TripCriteriaDao tripCriteriaDao;
    @Autowired
    protected UserDao userDao;


    @Test
    public void testRepositories()
    {
        Assert.assertNotNull(tripDao);
        Assert.assertNotNull(tagDao);
        Assert.assertNotNull(tripCriteriaDao);
        Assert.assertNotNull(userDao);

    }
}
