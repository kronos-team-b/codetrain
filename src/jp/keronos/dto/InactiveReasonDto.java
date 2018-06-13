package jp.keronos.dto;

public class InactiveReasonDto {

	private int inactiveReasonId;

	private int userId;
	
	private String reason;
	
	public int getInactiveReasonId() {
		return inactiveReasonId;
	}

	public void setInactiveReasonId(int inactiveReasonId) {
		this.inactiveReasonId = inactiveReasonId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
