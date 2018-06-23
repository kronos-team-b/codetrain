package jp.keronos.dto;

public class LearningCourseDto {

    private int courseId;

    private int userNo;

    private int learnigType;

    private int passFlg;

    private String updateAt;

	private String courseName;
	
	private int isFreeFlg;
	
	private int categoryId;
	
	private int updateNumber;

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

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }
		
	public String getCourseName() {
	    return courseName;
	}
	
	public void setCourseName(String courseName) {
	    this.courseName = courseName;
	}
	
	public int getIsFreeFlg() {
	    return isFreeFlg;
	}
	
	public void setIsFreeFlg(int isFreeFlg) {
	    this.isFreeFlg = isFreeFlg;
	}
	
	public int getCategoryId() {
	    return categoryId;
	}
	
	public void setCategoryId(int categoryId) {
	    this.categoryId = categoryId;
	}
	
	public int getUpdateNumber() {
	    return updateNumber;
	}
	
	public void setUpdateNumber(int updateNumber) {
	    this.updateNumber = updateNumber;
	}
}
