package RSP.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.stereotype.Component;

import RSP.dto.TripsQueryCriteria;

@Component
public class CriteriaChecker {

    private static final Date MIN_DATE
            = new GregorianCalendar(2020, Calendar.APRIL, 1).getTime();
    private static final Date MAX_DATE
            = new GregorianCalendar(2030, Calendar.APRIL, 1).getTime();

    public void check(TripsQueryCriteria criteria)
            throws InvalidQueryException, InconsistentQueryException {
        checkPrice(criteria.getMinPrice(), criteria.getMaxPrice());
        checkLength(criteria.getMinLength(), criteria.getMaxLength());
        checkDateInterval(
            criteria.getStartAfter(), criteria.getStartBefore(),
            criteria.getEndAfter(), criteria.getEndBefore());

        criteria.setInName(normalizeName(criteria.getInName()));
        criteria.setCountry(normalizeList(criteria.getCountry()));
        criteria.setTag(normalizeList(criteria.getTag()));

        checkPagination(criteria.getFrom(), criteria.getLimit());
    }

    void checkPagination(Integer from, Integer limit) throws InvalidQueryException {
        checkNumber("from", from);
        checkNumber("limit", limit);
    }

    void checkPrice(Integer min, Integer max)
            throws InvalidQueryException, InconsistentQueryException {
        checkInterval("minPrice", min, "maxPrice", max);
    }

    void checkLength(Integer min, Integer max)
            throws InvalidQueryException, InconsistentQueryException {
        checkInterval("minLength", min, "maxLength", max);
    }

    void checkDateInterval(
            Date startAfter, Date startBefore,
            Date endAfter, Date endBefore)
            throws InvalidQueryException, InconsistentQueryException {

        checkDate("startAfter", startAfter);
        checkDate("startBefore", startBefore);
        checkDate("endAfter", endAfter);
        checkDate("endBefore", endBefore);

        checkDates("startAfter", startAfter, "startBefore", startBefore);
        checkDates("endAfter", endAfter, "endBefore", endBefore);
        checkDates("startAfter", startAfter, "endBefore", endBefore);
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

    void checkDate(String name, Date value) throws InvalidQueryException {
        if (value != null && (MIN_DATE.after(value) || MAX_DATE.before(value))) {
            throw new InvalidQueryException(name, value);
        }
    }

    void checkDates(String minName, Date minValue, String maxName, Date maxValue)
            throws InconsistentQueryException {

        if (minValue != null && maxValue != null && minValue.after(maxValue)) {
            throw new InconsistentQueryException(minName, minValue, maxName, maxValue);
        }
    }

    String normalizeName(String name) {
        if (name != null) {
            String relevant = name.trim();
            if (!relevant.isEmpty()) {
                return relevant.toLowerCase(); // TODO what about locales?
            }
        }
        return null;
    }

    List<String> normalizeList(List<String> list) {
        if (list != null) {
            List<String> relevant = new ArrayList<>(list.size());
            for (String item : list) {
                if (item != null) {
                    String data = item.trim();
                    if (!data.isEmpty()) {
                        relevant.add(data);
                    }
                }
            }
            if (!relevant.isEmpty()) {
                return relevant;
            }
        }
        return null;
    }
}
