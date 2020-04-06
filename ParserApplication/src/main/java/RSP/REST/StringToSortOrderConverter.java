package RSP.REST;

import java.util.Locale;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import RSP.dto.SortOrder;

@Component
public class StringToSortOrderConverter implements Converter<String, SortOrder> {

	@Override
	public SortOrder convert(String source) {
		return SortOrder.valueOf(source.toUpperCase(Locale.ROOT));
	}
}
