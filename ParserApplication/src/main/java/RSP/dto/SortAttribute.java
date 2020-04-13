package RSP.dto;

public enum SortAttribute {
	ID("id"),
	NAME("name"),
	START("startDate"),
	LENGTH("length"),
	PRICE("price");

	private final String columnName;

	SortAttribute(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnName() {
		return columnName;
	}
}
