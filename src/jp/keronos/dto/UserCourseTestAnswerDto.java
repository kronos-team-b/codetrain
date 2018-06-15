package jp.keronos.dto;

public class UserCourseTestAnswerDto {

    private int userCourseTestAnswerId;

    private int userNo;

    private String userAnswer;

    private int testId;

    private int courseId;

    public int getUserCourseTestAnswerId() {
        return userCourseTestAnswerId;
    }

    public void setUserCourseTestAnswerId(int userCourseTestAnswerId) {
        this.userCourseTestAnswerId = userCourseTestAnswerId;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

}
