package jp.keronos.dto;

public class InactiveReasonDto {

    private int inactiveReasonId;

    private int userNo;

    private String reason;

    public int getInactiveReasonId() {
        return inactiveReasonId;
    }

    public void setInactiveReasonId(int inactiveReasonId) {
        this.inactiveReasonId = inactiveReasonId;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
