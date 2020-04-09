package RSP.dto;

import java.util.Objects;

public class TripsQueryCriteria {

	private SortAttribute sortBy = SortAttribute.ID;

	private SortOrder order = SortOrder.ASCENDING;

	private Integer minPrice = null;

	private Integer maxPrice = null;

	public SortAttribute getSortBy() {
		return sortBy;
	}

	public void setSortBy(SortAttribute sortBy) {
		this.sortBy = Objects.requireNonNull(sortBy, "sortBy");
	}

	public SortOrder getOrder() {
		return order;
	}

	public void setOrder(SortOrder order) {
		this.order = Objects.requireNonNull(order, "order");
	}

	public Integer getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}

	public Integer getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Integer maxPrice) {
		this.maxPrice = maxPrice;
	}
}
