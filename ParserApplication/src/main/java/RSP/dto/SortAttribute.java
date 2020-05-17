package RSP.dto;

public enum SortAttribute {
    ID("id", true),
    NAME("name", true),
    START("startDate", false),
    LENGTH("length", false),
    PRICE("price", false);

    private final String columnName;
    private final boolean total;

    SortAttribute(String columnName, boolean total) {
        this.columnName = columnName;
        this.total = total;
    }

    public String getColumnName() {
        return columnName;
    }

    public boolean isTotalOrdering() {
        return total;
    }
}
