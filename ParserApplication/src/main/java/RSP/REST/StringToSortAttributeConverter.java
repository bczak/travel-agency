package RSP.REST;

import java.util.Locale;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import RSP.dto.SortAttribute;

@Component
public class StringToSortAttributeConverter implements Converter<String, SortAttribute> {

	@Override
	public SortAttribute convert(String string) {
		return SortAttribute.valueOf(string.toUpperCase(Locale.ROOT));
	}
}
