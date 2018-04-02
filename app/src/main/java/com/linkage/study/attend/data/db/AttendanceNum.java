package com.linkage.study.attend.data.db;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AttendanceNum implements Serializable {
	private String classId;//: 10005947,
	private String totalNum;
	private String schoolId;
	private String isCheck;
	private String comeNum;
	private String attendanceRateValue;
	private String attendanceRate;
	private String className;
	private String vacateNum;
	private String nocomeNum;
	private String schoolName;
	
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getNocomeNum() {
		return nocomeNum;
	}
	public void setNocomeNum(String nocomeNum) {
		this.nocomeNum = nocomeNum;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}
	public String getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
	public String getComeNum() {
		return comeNum;
	}
	public void setComeNum(String comeNum) {
		this.comeNum = comeNum;
	}
	public String getAttendanceRateValue() {
		return attendanceRateValue;
	}
	public void setAttendanceRateValue(String attendanceRateValue) {
		this.attendanceRateValue = attendanceRateValue;
	}
	public String getAttendanceRate() {
		return attendanceRate;
	}
	public void setAttendanceRate(String attendanceRate) {
		this.attendanceRate = attendanceRate;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getVacateNum() {
		return vacateNum;
	}
	public void setVacateNum(String vacateNum) {
		this.vacateNum = vacateNum;
	}

	public static List<AttendanceNum> paserAll(JSONObject jsonObject){
		List<AttendanceNum> attendanceNums=new ArrayList<>();
	JSONArray array=	jsonObject.optJSONArray("data");
	if(null!=array&&array.length()>0){
		for (int i = 0; i < array.length(); i++) {
			AttendanceNum attendanceNum=	new AttendanceNum();
			JSONObject jb=array.optJSONObject(i);
			attendanceNum.setAttendanceRate(jb.optString("attendanceRate"));
			attendanceNum.setAttendanceRateValue(jb.optString("attendanceRateValue"));
			attendanceNum.setClassId(jb.optString("classId"));
			attendanceNum.setClassName(jb.optString("className"));
			attendanceNum.setComeNum(jb.optString("comeNum"));
			attendanceNum.setIsCheck(jb.optString("isCheck"));
			attendanceNum.setTotalNum(jb.optString("totalNum"));
			attendanceNum.setVacateNum(jb.optString("vacateNum"));
			attendanceNum.setNocomeNum(jb.optString("nocomeNum"));
			attendanceNum.setSchoolName(jb.optString("schoolName"));
			attendanceNum.setSchoolId(jb.optString("schoolId"));
			attendanceNums.add(attendanceNum);
		}
		
	}
		
		return attendanceNums;
	}
      
}
