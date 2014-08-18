package org.swingBean.example.customizedform;

import java.util.Date;

public class Dog {

	private String name;
	private String race;
	private int kennelNumber;
	private Date birthDate;
	private boolean hasPedigree;
	private String phone;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public int getKennelNumber() {
		return kennelNumber;
	}
	public void setKennelNumber(int kennelNumber) {
		this.kennelNumber = kennelNumber;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public boolean isHasPedigree() {
		return hasPedigree;
	}
	public void setHasPedigree(boolean hasPedigree) {
		this.hasPedigree = hasPedigree;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
