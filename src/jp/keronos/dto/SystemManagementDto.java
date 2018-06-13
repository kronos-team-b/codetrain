package jp.keronos.dto;

public class SystemManagementDto {

	private int manageId;
	
	private String password;
	
	private String lastName;
	
	private String firstName;
	
	private int superUserFlg;
	
	private int editCorporateFlg;
	
	private int editTextFlg;
	
	private int deleteFlg;

	public int getManageId() {
		return manageId;
	}

	public void setManageId(int manageId) {
		this.manageId = manageId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public int getSuperUserFlg() {
		return superUserFlg;
	}

	public void setSuperUserFlg(int superUserFlg) {
		this.superUserFlg = superUserFlg;
	}

	public int getEditCorporateFlg() {
		return editCorporateFlg;
	}

	public void setEditCorporateFlg(int editCorporateFlg) {
		this.editCorporateFlg = editCorporateFlg;
	}

	public int getEditTextFlg() {
		return editTextFlg;
	}

	public void setEditTextFlg(int editTextFlg) {
		this.editTextFlg = editTextFlg;
	}

	public int getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(int deleteFlg) {
		this.deleteFlg = deleteFlg;
	}
	
}
