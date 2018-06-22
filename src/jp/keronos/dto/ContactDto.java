package jp.keronos.dto;

import java.sql.Timestamp;

public class ContactDto {

    private int contactDetailId;

    private int contactId;

    private String contactDetail;

    private String contactAt;

    private int requestOrResponseFlg;

    private int manageNo;

    private int userNo;

    public int getContactDetailId() {
        return contactDetailId;
    }

    public void setContactDetailId(int contactDetailId) {
        this.contactDetailId = contactDetailId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getContactDetail() {
        return contactDetail;
    }

    public void setContactDetail(String contactDetail) {
        this.contactDetail = contactDetail;
    }

    public String getContactAt() {
        return contactAt;
    }

    public void setContactAt(String contactAt) {
        this.contactAt = contactAt;
    }

    public int getRequestOrResponseFlg() {
        return requestOrResponseFlg;
    }

    public void setRequestOrResponseFlg(int requestOrResponseFlg) {
        this.requestOrResponseFlg = requestOrResponseFlg;
    }

    public int getManageNo() {
        return manageNo;
    }

    public void setManageNo(int manageNo) {
        this.manageNo = manageNo;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }
}