package jp.keronos.dto;

public class UnitDto {
	
	private int unitId;
	
	private String unitTitle;
	
	private String unitText;
	
	private int courseId;
	
	private int updateNumber;
	
	private String manageId;
	
	private int deleteFlg;

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public String getUnitTitle() {
		return unitTitle;
	}

	public void setUnitTitle(String unitTitle) {
		this.unitTitle = unitTitle;
	}

	public String getUnitText() {
		return unitText;
	}

	public void setUnitText(String unitText) {
		this.unitText = unitText;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getUpdateNumber() {
		return updateNumber;
	}

	public void setUpdateNumber(int updateNumber) {
		this.updateNumber = updateNumber;
	}

	public String getManageId() {
		return manageId;
	}

	public void setManageId(String manageId) {
		this.manageId = manageId;
	}

	public int getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(int deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

}
