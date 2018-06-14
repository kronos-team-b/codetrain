package jp.keronos.dto;

import java.sql.Timestamp;

public class BillDto {

	/**/
	private int billingId;
	/**/
	private int corporateId;
	/**/
	private Timestamp billingData;
	/**/
	private int PriceId;
	/**/
	private int numberOfActiveAcount;
	/**/
	private int numberOfInactiveAcount;
	
	public int getBillingId() {
		return billingId;
	}
	public void setBillingId(int billingId) {
		this.billingId = billingId;
	}
	public int getCorporateId() {
		return corporateId;
	}
	public void setCorporateId(int corporateId) {
		this.corporateId = corporateId;
	}
	public Timestamp getBillingData() {
		return billingData;
	}
	public void setBillingData(Timestamp billingData) {
		this.billingData = billingData;
	}
	public int getPriceId() {
		return PriceId;
	}
	public void setPriceId(int priceId) {
		PriceId = priceId;
	}
	public int getNumberOfActiveAcount() {
		return numberOfActiveAcount;
	}
	public void setNumberOfActiveAcount(int numberOfActiveAcount) {
		this.numberOfActiveAcount = numberOfActiveAcount;
	}
}
