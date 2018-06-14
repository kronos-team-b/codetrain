package jp.keronos.dto;

public class LearningHistoryDto {

	private int learningHistoryId;
	
	private int unitId;
	
	private int courseId;
	
	private int userId;
	
	private int skipFlg;
	
	private int endFlg;
	
	private int unitTestPoint;
	
	private int testAt;
	
	public int getLearningHistoryId() {
		return learningHistoryId;
	}

	public void setLearningHistoryId(int learningHistoryId) {
		this.learningHistoryId = learningHistoryId;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getSkipFlg() {
		return skipFlg;
	}

	public void setSkipFlg(int skipFlg) {
		this.skipFlg = skipFlg;
	}

	public int getEndFlg() {
		return endFlg;
	}

	public void setEndFlg(int endFlg) {
		this.endFlg = endFlg;
	}

	public int getUnitTestPoint() {
		return unitTestPoint;
	}

	public void setUnitTestPoint(int unitTestPoint) {
		this.unitTestPoint = unitTestPoint;
	}

	public int getTestAt() {
		return testAt;
	}

	public void setTestAt(int testAt) {
		this.testAt = testAt;
	}
}
