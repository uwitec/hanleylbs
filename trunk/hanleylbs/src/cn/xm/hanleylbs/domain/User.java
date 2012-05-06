package cn.xm.hanleylbs.domain;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 229004153858642582L;
	private int userId;
	private int age;
	private int gender;
	private int online;
	private String tel;
	private String name;
	private String address;
	private String longitude;
	private String latitude;
	private String memo;
	
	public User(){
		
	}



	public int getUserId() {
		return userId;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}



	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("userId=");
		sb.append(userId);
		sb.append("|");
		sb.append("age=");
		sb.append(age);
		sb.append("|");
		sb.append("gender=");
		sb.append(gender);
		sb.append("|");
		sb.append("online=");
		sb.append(online);
		sb.append("|");
		sb.append("tel=");
		sb.append(tel);
		sb.append("|");
		sb.append("name=");
		sb.append(name);
		sb.append("|");
		sb.append("address=");
		sb.append(address);
		sb.append("|");
		sb.append("longitude=");
		sb.append(longitude);
		sb.append("|");
		sb.append("latitude=");
		sb.append(latitude);
		sb.append("|");
		sb.append("memo=");
		sb.append(memo);
		return sb.toString();
	}
	
	


}
