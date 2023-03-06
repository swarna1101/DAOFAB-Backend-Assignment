package com.assignment.daofab.model;

/**
 * 
 * @author Sanket Lathiya
 *
 */
public class ChildTransaction {
	long id;
	long parentId;
	long paidAmount;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getParentId() {
		return parentId;
	}
	
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	
	public long getPaidAmount() {
		return paidAmount;
	}
	
	public void setPaidAmount(long paidAmount) {
		this.paidAmount = paidAmount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (paidAmount ^ (paidAmount >>> 32));
		result = prime * result + (int) (parentId ^ (parentId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChildTransaction other = (ChildTransaction) obj;
		if (id != other.id)
			return false;
		if (paidAmount != other.paidAmount)
			return false;
		if (parentId != other.parentId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Child [id=" + id + ", parentId=" + parentId + ", paidAmount=" + paidAmount + "]";
	}
}
