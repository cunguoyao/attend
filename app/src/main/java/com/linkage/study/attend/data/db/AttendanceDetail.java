package com.linkage.study.attend.data.db;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDetail implements Serializable {
	private String totalDays;
	private String userId;
	private String comeNum;
	private String vacateNum;
	private String nocomeNum;
	private String attendanceRate;
	private String userName;
	private String picture;
	public String getTotalDays() {
		return totalDays;
	}
	public void setTotalDays(String totalDays) {
		this.totalDays = totalDays;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getComeNum() {
		return comeNum;
	}
	public void setComeNum(String comeNum) {
		this.comeNum = comeNum;
	}
	public String getVacateNum() {
		return vacateNum;
	}
	public void setVacateNum(String vacateNum) {
		this.vacateNum = vacateNum;
	}
	public String getNocomeNum() {
		return nocomeNum;
	}
	public void setNocomeNum(String nocomeNum) {
		this.nocomeNum = nocomeNum;
	}
	public String getAttendanceRate() {
		return attendanceRate;
	}
	public void setAttendanceRate(String attendanceRate) {
		this.attendanceRate = attendanceRate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public static List<AttendanceDetail> paserAll(JSONObject jsonObject){
		List<AttendanceDetail> attendanceNums=new ArrayList<>();
	JSONArray array=	jsonObject.optJSONArray("data");
	if(null!=array&&array.length()>0){
		for (int i = 0; i < array.length(); i++) {
			AttendanceDetail attendanceDetail=	new AttendanceDetail();
			JSONObject jb=array.optJSONObject(i);
			attendanceDetail.setAttendanceRate(jb.optString("attendanceRate"));			
			attendanceDetail.setUserId(jb.optString("userId"));
			attendanceDetail.setPicture(jb.optString("picture"));
			attendanceDetail.setComeNum(jb.optString("comeNum"));
			attendanceDetail.setUserName(jb.optString("userName"));
			attendanceDetail.setTotalDays(jb.optString("totalDays"));
			attendanceDetail.setVacateNum(jb.optString("vacateNum"));
			attendanceDetail.setNocomeNum(jb.optString("nocomeNum"));
			attendanceNums.add(attendanceDetail);
		}
		
	}
		
		return attendanceNums;
	}
      
   
}
