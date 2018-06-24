package jp.keronos.dto;

import java.sql.Timestamp;

public class InactiveReasonDto {

    private int inactiveReasonId;

    private int userNo;

    private String reason;
    
    private Timestamp inactiveAt;
    
    private Timestamp activeAt;

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
    
    public Timestamp getInactiveAt() {
		return inactiveAt;
	}

	public void setInactiveAt(Timestamp inactiveAt) {
		this.inactiveAt = inactiveAt;
	}

	public Timestamp getActiveAt() {
		return activeAt;
	}

	public void setActiveAt(Timestamp activeAt) {
		this.activeAt = activeAt;
	}

}
