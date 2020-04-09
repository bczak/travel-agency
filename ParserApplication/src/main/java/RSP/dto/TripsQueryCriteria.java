package RSP.dto;

public class TripsQueryCriteria {

	private SortAttribute sortBy;

	private SortOrder order;

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
