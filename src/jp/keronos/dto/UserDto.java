package jp.keronos.dto;

import java.sql.Timestamp;

public class UserDto {

    private int userNo;

    private String userId;

    private String password;

    private String lastName;

    private String firstName;

    private int inactiveFlg;

    private int corporateNo;

    private Timestamp updateAt;
    
    private int updateNumber;

    private int deleteFlg;

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getInactiveFlg() {
        return inactiveFlg;
    }

    public void setInactiveFlg(int inactiveFlg) {
        this.inactiveFlg = inactiveFlg;
    }

    public int getCorporateNo() {
        return corporateNo;
    }

    public void setCorporateNo(int corporateNo) {
        this.corporateNo = corporateNo;
    }

    public int getUpdateNumber() {
        return updateNumber;
    }

    public void setUpdateNumber(int updateNumber) {
        this.updateNumber = updateNumber;
    }

    public int getDeleteFlg() {
        return deleteFlg;
    }

    public void setDeleteFlg(int deleteFlg) {
        this.deleteFlg = deleteFlg;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

}
