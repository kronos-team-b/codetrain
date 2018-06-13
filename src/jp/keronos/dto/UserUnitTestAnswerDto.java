package jp.keronos.dto;

public class UserUnitTestAnswerDto {

	private int userCourseTestAnswerId;
	
	private int userId;
	
	private String answer;
	
	private int testID;
	
	private int courseId;

	public int getUserCourseTestAnswerId() {
		return userCourseTestAnswerId;
	}

	public void setUserCourseTestAnswerId(int userCourseTestAnswerId) {
		this.userCourseTestAnswerId = userCourseTestAnswerId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getTestID() {
		return testID;
	}

	public void setTestID(int testID) {
		this.testID = testID;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
}
