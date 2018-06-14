package jp.keronos.dto;

import java.sql.Timestamp;

public class contactDetailDto {

	private int contactDetailId;
	
	private int contactId;
	
	private String contactDetail;
	
	private Timestamp contactAt;
	
	private int requestOrResponseFlg;
	
	private int manageId;

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

	public Timestamp getContactAt() {
		return contactAt;
	}

	public void setContactAt(Timestamp contactAt) {
		this.contactAt = contactAt;
	}

	public int getRequestOrResponseFlg() {
		return requestOrResponseFlg;
	}

	public void setRequestOrResponseFlg(int requestOrResponseFlg) {
		this.requestOrResponseFlg = requestOrResponseFlg;
	}

	public int getManageId() {
		return manageId;
	}

	public void setManageId(int manageId) {
		this.manageId = manageId;
	}
	
	
	
}
