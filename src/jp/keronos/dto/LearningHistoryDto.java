package jp.keronos.dto;

public class LearningHistoryDto {

    private int learningHistoryId;

    private int unitId;
    
    private String unitTitle;

    private int courseId;

    private int userNo;

    private int skipFlg;

    private int endFlg;

    private int unitTestPoint;

    private String testAt;

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
    
    public String getUnitTitle() {
        return unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
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

    public String getTestAt() {
        return testAt;
    }

    public void setTestAt(String testAt) {
        this.testAt = testAt;
    }
}

