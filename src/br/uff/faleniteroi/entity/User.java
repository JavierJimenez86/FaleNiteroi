package br.uff.faleniteroi.entity;

public class User {

	private String name;
	private String areaCode;
	private String phoneNumber;
	private String email;
	private boolean sendNotification;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isSendNotification() {
		return sendNotification;
	}
	public void setSendNotification(boolean sendNotification) {
		this.sendNotification = sendNotification;
	}
}
