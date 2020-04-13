package RSP.service;

import org.springframework.stereotype.Component;

import RSP.dto.TripsQueryCriteria;

@Component
public class CriteriaChecker {

    public void check(TripsQueryCriteria criteria)
            throws InvalidQueryException, InconsistentQueryException {
        checkPrice(criteria.getMinPrice(), criteria.getMaxPrice());
    }

    void checkPrice(Integer min, Integer max)
            throws InvalidQueryException, InconsistentQueryException {
        checkInterval("minPrice", min, "maxPrice", max);
    }

    void checkInterval(
            String minName, Integer minValue, String maxName, Integer maxValue)
            throws InvalidQueryException, InconsistentQueryException {

        checkNumber(minName, minValue);
        checkNumber(maxName, maxValue);

        if (minValue != null && maxValue != null && minValue > maxValue) {
            throw new InconsistentQueryException(minName, minValue, maxName, maxValue);
        }
    }

    void checkNumber(String name, Integer value) throws InvalidQueryException {
        if (value != null && value < 0) {
            throw new InvalidQueryException(name, value);
        }
    }
}
