package jp.keronos.dto;

public class ContactDto {

    private int contactDetailId;

    private int contactId;

    private String contactDetail;

    private String contactAt;

    private String firstAt;

    private String lastAt;

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

    public String getFirstAt() {
        return firstAt;
    }

    public void setFirstAt(String firstAt) {
        this.firstAt = firstAt;
    }

    public String getLastAt() {
        return lastAt;
    }

    public void setLastAt(String lastAt) {
        this.lastAt = lastAt;
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

