package jp.keronos.dto;

import java.sql.Timestamp;

public class LearningHistoryDto {

    private int learningHistoryId;

    private int unitNo;

    private int courseId;

    private int userId;

    private int skipFlg;

    private int endFlg;

    private int unitTestPoint;

    private Timestamp testAt;

    public int getLearningHistoryId() {
        return learningHistoryId;
    }

    public void setLearningHistoryId(int learningHistoryId) {
        this.learningHistoryId = learningHistoryId;
    }

    public int getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(int unitNo) {
        this.unitNo = unitNo;
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

    public Timestamp getTestAt() {
        return testAt;
    }

    public void setTestAt(Timestamp testAt) {
        this.testAt = testAt;
    }
}
