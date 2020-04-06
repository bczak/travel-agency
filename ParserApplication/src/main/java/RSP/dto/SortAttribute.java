package RSP.dto;

public enum SortAttribute {
	NAME("Name"),
	START("startDate"),
	LENGTH("length"),
	PRICE("Price");

	private final String columnName;

	SortAttribute(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnName() {
		return columnName;
	}
}
