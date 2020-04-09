package RSP.dto;

public class TripsQueryCriteria {

	private SortAttribute sortBy = SortAttribute.ID;

	private SortOrder order = SortOrder.ASCENDING;

	public SortAttribute getSortBy() {
		return sortBy;
	}

	public void setSortBy(SortAttribute sortBy) {
		this.sortBy = sortBy;
	}

	public SortOrder getOrder() {
		return order;
	}

	public void setOrder(SortOrder order) {
		this.order = order;
	}
}
