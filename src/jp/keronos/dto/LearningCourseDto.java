package jp.keronos.dto;

import java.sql.Timestamp;

public class LearningCourseDto {

	private int courseId;
	
	private String userId;
	
	private int learnigType;
	
	private int passFlg;
	
	private Timestamp updateAt;

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getLearnigType() {
		return learnigType;
	}

	public void setLearnigType(int learnigType) {
		this.learnigType = learnigType;
	}

	public int getPassFlg() {
		return passFlg;
	}

	public void setPassFlg(int passFlg) {
		this.passFlg = passFlg;
	}

	public Timestamp getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Timestamp updateAt) {
		this.updateAt = updateAt;
	}
	
}