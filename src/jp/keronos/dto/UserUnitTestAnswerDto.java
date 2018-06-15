package jp.keronos.dto;

public class UserUnitTestAnswerDto {

    private int userUnitTestAnswerId;

    private int userNo;

    private String userAnswer;

    private int testId;

    private int unitId;

    public int getUserUnitTestAnswerId() {
        return userUnitTestAnswerId;
    }

    public void setUserUnitTestAnswerId(int userUnitTestAnswerId) {
        this.userUnitTestAnswerId = userUnitTestAnswerId;
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

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }
}
