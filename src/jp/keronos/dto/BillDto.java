package jp.keronos.dto;

import java.sql.Date;

public class BillDto {

    /**/
    private int billingId;
    /**/
    private int corporateNo;
    /**/
    private Date billingDate;
    /**/
    private int PriceId;
    /**/
    private int numberOfActiveAccount;
    /**/
    private int numberOfInactiveAccount;

    private int price;

    private double tax;

    private int totalPrice;

    public int getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public double getTax() {
        return tax;
    }
    public void setTax(double tax) {
        this.tax = tax;
    }
    public int getBillingId() {
        return billingId;
    }
    public void setBillingId(int billingId) {
        this.billingId = billingId;
    }
    public int getCorporateNo() {
        return corporateNo;
    }
    public void setCorporateNo(int corporateNo) {
        this.corporateNo = corporateNo;
    }
    public Date getBillingDate() {
        return billingDate;
    }
    public void setBillingDate(Date billingDate) {
        this.billingDate = billingDate;
    }
    public int getPriceId() {
        return PriceId;
    }
    public void setPriceId(int priceId) {
        PriceId = priceId;
    }
    public int getNumberOfActiveAccount() {
        return numberOfActiveAccount;
    }
    public void setNumberOfActiveAccount(int numberOfActiveAccount) {
        this.numberOfActiveAccount = numberOfActiveAccount;
    }
    public int getNumberOfInactiveAccount() {
        return numberOfInactiveAccount;
    }
    public void setNumberOfInactiveAccount(int numberOfInactiveAccount) {
        this.numberOfInactiveAccount = numberOfInactiveAccount;
    }
}
