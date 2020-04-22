package RSP.dto;

import java.util.Date;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

public class TripsQueryCriteria {

	private SortAttribute sortBy = SortAttribute.ID;

	private SortOrder order = SortOrder.ASCENDING;

	private Integer minPrice = null;

	private Integer maxPrice = null;

	private Integer minLength = null;

	private Integer maxLength = null;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startAfter = null;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startBefore = null;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endAfter = null;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endBefore = null;

	private String inName = null;

	private String country = null;

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

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public Date getStartAfter() {
		return startAfter;
	}

	public void setStartAfter(Date startAfter) {
		this.startAfter = startAfter;
	}

	public Date getStartBefore() {
		return startBefore;
	}

	public void setStartBefore(Date startBefore) {
		this.startBefore = startBefore;
	}

	public Date getEndAfter() {
		return endAfter;
	}

	public void setEndAfter(Date endAfter) {
		this.endAfter = endAfter;
	}

	public Date getEndBefore() {
		return endBefore;
	}

	public void setEndBefore(Date endBefore) {
		this.endBefore = endBefore;
	}

	public String getInName() {
		return inName;
	}

	public void setInName(String inName) {
		this.inName = inName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "TripsQueryCriteria [maxPrice=" + maxPrice
				+ ", minPrice=" + minPrice
				+ ", maxLength=" + maxLength
				+ ", minLength=" + minLength
				+ ", startAfter=" + startAfter
				+ ", startBefore=" + startBefore
				+ ", endAfter=" + endAfter
				+ ", endBefore=" + endBefore
				+ ", inName=" + inName
				+ ", country=" + country
				+ ", order=" + order
				+ ", sortBy=" + sortBy + "]";
	}
}
