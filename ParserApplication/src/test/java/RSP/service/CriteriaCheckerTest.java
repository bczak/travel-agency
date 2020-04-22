package RSP.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import RSP.ParserApplicationBackend;
import RSP.dto.TripsQueryCriteria;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackageClasses = ParserApplicationBackend.class)
public class CriteriaCheckerTest {

	@Autowired
	private CriteriaChecker checker;

	@Test(expected = InvalidQueryException.class)
	public void checkNumberRejectsNegativeNumbers() throws InvalidQueryException {
		checker.checkNumber("number", -1);
	}

	@Test
	public void checkNumberAcceptsPositiveNumbers() throws InvalidQueryException {
		checker.checkNumber("number", 0);
		checker.checkNumber("number", 123);
	}

	@Test(expected = InvalidQueryException.class)
	public void checkIntervalRejectsNegativeLowerBound()
			throws InvalidQueryException, InconsistentQueryException {
		checker.checkInterval("min", -2, "max", 0);
	}

	@Test(expected = InconsistentQueryException.class)
	public void checkIntervalRejectsReversedInterval()
			throws InvalidQueryException, InconsistentQueryException {
		checker.checkInterval("min", 5, "max", 2);
	}

	@Test
	public void checkIntervalAcceptsValidInterval()
			throws InvalidQueryException, InconsistentQueryException {
		checker.checkInterval("min", 7, "max", 7);
		checker.checkInterval("min", 0, "max", 10);
	}

	@Test(expected = InconsistentQueryException.class)
	public void checkPriceRejectsInvalidFilter()
			throws InvalidQueryException, InconsistentQueryException {
		checker.checkPrice(100, 0);
	}

	@Test
	public void checkAcceptsValidPriceFilters()
			throws InvalidQueryException, InconsistentQueryException {

		TripsQueryCriteria criteria = new TripsQueryCriteria();

		criteria.setMinPrice(1);
		checker.check(criteria);

		criteria.setMaxPrice(10);
		checker.check(criteria);

		criteria.setMinPrice(null);
		checker.check(criteria);

		criteria.setMaxPrice(null);
		checker.check(criteria);
	}
}
