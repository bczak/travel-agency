package RSP.service;

import java.time.Instant;
import java.util.Date;

import org.springframework.stereotype.Component;

import RSP.dto.TripsQueryCriteria;

@Component
public class CriteriaChecker {

    public void check(TripsQueryCriteria criteria)
            throws InvalidQueryException, InconsistentQueryException {
        checkPrice(criteria.getMinPrice(), criteria.getMaxPrice());
        checkLength(criteria.getMinLength(), criteria.getMaxLength());
        checkStart(criteria.getStartAfter(), criteria.getStartBefore());
    }

    void checkPrice(Integer min, Integer max)
            throws InvalidQueryException, InconsistentQueryException {
        checkInterval("minPrice", min, "maxPrice", max);
    }

    void checkLength(Integer min, Integer max)
            throws InvalidQueryException, InconsistentQueryException {
        checkInterval("minLength", min, "maxLength", max);
    }

    void checkStart(Date from, Date to) throws InconsistentQueryException {
        checkDates("startAfter", from, "startBefore", to);
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

    void checkDates(String minName, Date minValue, String maxName, Date maxValue)
            throws InconsistentQueryException {

        if (minValue != null && maxValue != null && minValue.after(maxValue)) {
            throw new InconsistentQueryException(minName, minValue, maxName, maxValue);
        }
    }
}
