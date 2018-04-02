package  com.linkage.study.attend.data.db;

import com.google.zxing.common.StringUtils;
import com.linkage.study.attend.app.BaseApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClassRoom implements Serializable {

	private static final long serialVersionUID = -6144540780406960117L;

	private long id; //班级Id

	private String name;

	private String avatar;

	private long schoolId;

	private String schoolName;

	private String gradeName;

	private String classLeader;

	private String teacherName;

	private String gradeId;

	private int isCreate;//是否是自己创建的班级 0：不是，1：是

	private String classNo;//班级longid

	private int classNumber;

	private String classLevel;
	private String loginName;

	private int joinOrManage;//2已加入 1已管理的班级
	

	private long taskid;

	private int is_xxt;
	

	private int defaultClass;

	private int classroomType;//0.校讯通班级 1.互联网班级 2.培训机构 3.学校组织

	private int userRole;//0.班主任 1.任课老师 2.普通用户

	private String classroomNickName;//班级昵称

	private String classroomQrCode;//班级二维码

	private String createPersonName;//创建人

	private String createPersonID;//创建者ID

	private int UnDisturb = 0;//0:关闭 1:开启 是否开启免打扰

	private int validationState = 0;//0.关闭验证 1.开启验证 是否需要验证

	private int isProhibitIM = 1;//0:禁止 1:允许 是否允许班级群聊

	private int isJoined = 0;//是否加入此班级 0.没加入1.加入

	private int memCount =0;//成员数量

	private String joinClassUrl;

    private String platform;
    
	
	private List<ClassMemberBean> sclasses = new ArrayList<>();
	
    public List<ClassMemberBean> getSclasses() {
		return sclasses;
	}
	public void setSclasses(List<ClassMemberBean> sclasses) {
		this.sclasses = sclasses;
	}
	public String getPlatform()
    {
        if(id ==0)
        {
            return "-1"; 
        }
        return platform;
    }
    public void setPlatform(String platform)
    {
        this.platform = platform;
    }
	
	public int getIsCreate() {
		return isCreate;
	}
	public void setIsCreate(int isCreate) {
		this.isCreate = isCreate;
	}
	public String getJoinClassUrl() {
		return joinClassUrl;
	}
	public void setJoinClassUrl(String joinClassUrl) {
		this.joinClassUrl = joinClassUrl;
	}
	public int getMemCount() {
		return memCount;
	}
	public void setMemCount(int memCount) {
		this.memCount = memCount;
	}
	
	public String getClassLeader() {
		return classLeader;
	}
	public void setClassLeader(String classLeader) {
		this.classLeader = classLeader;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getGradeId() {
		return gradeId;
	}
	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}
	
	public int getIsJoined() {
		return isJoined;
	}

	public void setIsJoined(int isJoined) {
		this.isJoined = isJoined;
	}
	private boolean isCheck;
	
	private boolean isHafeCheck;
	
	public String getCreatePersonName() {
		return createPersonName;
	}

	public void setCreatePersonName(String createPersonName) {
		this.createPersonName = createPersonName;
	}
	
	public int getValidationState() {
		return validationState;
	}

	public void setValidationState(int validationState) {
		this.validationState = validationState;
	}
	
	public String getClassroomNickName() {
		if(classroomNickName==null || classroomNickName.equals("") || classroomNickName.equals("null"))
			return "";
		else
			return classroomNickName;
	}

	public void setClassroomNickName(String classroomNickName) {
		this.classroomNickName = classroomNickName;
	}
	public String getClassroomQrCode() {
		return classroomQrCode;
	}

	public void setClassroomQrCode(String classroomQrCode) {
		this.classroomQrCode = classroomQrCode;
	}
	public int getUserRole() {
		return userRole;
	}

	public void setUserRole(int userRole) {
		this.userRole = userRole;
	}
	
	public String getCreatePersonID() {
		return createPersonID;
	}

	public void setCreatePersonID(String createPersonID) {
		this.createPersonID = createPersonID;
	}
	
	public int getUnDisturb() {
		return UnDisturb;
	}

	public void setUnDisturb(int unDisturb) {
		UnDisturb = unDisturb;
	}
	
	public int getIsProhibitIM() {
		return isProhibitIM;
	}

	public void setIsProhibitIM(int isProhibitIM) {
		this.isProhibitIM = isProhibitIM;
	}
	
	private List<ClassMemberBean> cList = new ArrayList<>();
	
	public List<ClassMemberBean> getcList()
    {
        return cList;
    }

    public void setcList(List<ClassMemberBean> cList)
    {
        this.cList = cList;
    }

    public boolean isHafeCheck()
    {
        return isHafeCheck;
    }

    public void setHafeCheck(boolean isHafeCheck)
    {
        this.isHafeCheck = isHafeCheck;
    }

    public boolean isCheck()
    {
        return isCheck;
    }

    public void setCheck(boolean isCheck)
    {
        this.isCheck = isCheck;
    }

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLoginName() {
	   
        return loginName;
	}

	public void setLoginName(String loginName) {
	    this.loginName = (loginName);
       
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(long schoolId) {
		this.schoolId = schoolId;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	
	public String getClassNo() {
		return classNo;
	}
	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}
	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public int getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(int classNumber) {
		this.classNumber = classNumber;
	}

	public String getClassLevel() {
		return classLevel;
	}

	public void setClassLevel(String classLevel) {
		this.classLevel = classLevel;
	}

	public int getJoinOrManage() {
		return joinOrManage;
	}

	public void setJoinOrManage(int joinOrManage) {
		this.joinOrManage = joinOrManage;
	}

	public long getTaskid() {
		return taskid;
	}

	public void setTaskid(long taskid) {
		this.taskid = taskid;
	}

	public int getIs_xxt() {
		return is_xxt;
	}

	public void setIs_xxt(int is_xxt) {
		this.is_xxt = is_xxt;
	}

	public int getDefaultClass() {
		return defaultClass;
	}

	public void setDefaultClass(int defaultClass) {
		this.defaultClass = defaultClass;
	}
	
	public int getClassroomType() {
		return classroomType;
	}

	public void setClassroomType(int classroomType) {
		this.classroomType = classroomType;
	}
	
	public static ClassRoom parseJson(JSONObject jsonObj, int joinOrManage) throws JSONException {
		ClassRoom classRoom = new ClassRoom();
		classRoom.setId(jsonObj.optLong("classroomId"));
		classRoom.setLoginName("111");
		classRoom.setJoinOrManage(joinOrManage);

            classRoom.setName(jsonObj.optString("classroomName"));

		classRoom.setClassNumber(jsonObj.optInt("classNumber"));
		classRoom.setAvatar(jsonObj.optString("classroomAvatar"));
		classRoom.setClassLevel(jsonObj.optString("classLevel"));
		classRoom.setSchoolId(jsonObj.optLong("schoolId"));
		classRoom.setSchoolName(jsonObj.optString("schoolName"));
		classRoom.setClassroomQrCode(jsonObj.optString("classroomQrCode"));
		if(jsonObj.get("taskid") == null){
			classRoom.setTaskid(0);
		}else{
			classRoom.setTaskid(jsonObj.optLong("taskid"));
		}
		classRoom.setIs_xxt(jsonObj.optInt("is_xxt"));
		return classRoom;
	}

	public static List<ClassRoom> parseFromJson(JSONArray jsonArray, int joinOrManage) throws JSONException {
		List<ClassRoom> clazzs = new ArrayList<ClassRoom>();
		if(jsonArray != null && jsonArray.length() > 0) {
			for(int i=0;i<jsonArray.length();i++) {
				ClassRoom clazz = parseJson(jsonArray.optJSONObject(i), joinOrManage);
				if(clazz != null)clazzs.add(clazz);
			}
		}
		return clazzs;
	}
	
	
	   public static ClassRoom parseJsonForOff(JSONObject jsonObj) {
	        ClassRoom classRoom = new ClassRoom();
	        classRoom.setId(jsonObj.optLong("group_id"));
	        classRoom.setLoginName("11");
	        String sname = jsonObj.optString("group_name");
	        classRoom.setName(jsonObj.optString("group_name"));
//	        classRoom.setName("全选");
	        classRoom.setClassroomNickName(jsonObj.optString("group_name"));
	        classRoom.setClassNumber(jsonObj.optInt("group_type"));
	        JSONArray jsonArray = jsonObj.optJSONArray("group_members");
	        List<ClassMemberBean> cList = new ArrayList<>();
	        if(jsonArray != null && jsonArray.length() > 0)
	        {
	            for(int i = 0;i<jsonArray.length();i++)
	            {
	                ClassMemberBean cBean = new ClassMemberBean();
	                cBean.setNickName(jsonArray.optJSONObject(i).optString("name"));
                    cBean.setClassId(classRoom.getId());
                    cBean.setUserId(Long.parseLong(jsonArray.optJSONObject(i).optString("id")));
                    cBean.setPhone(sname);
                    cBean.setAvatar(jsonArray.optJSONObject(i).optString("head_image"));
                    cList.add(cBean);
	            }
	            classRoom.setcList(cList);
	        }
	        return classRoom;
	    }

	    public static List<ClassRoom> parseFromJsonOff(JSONArray jsonArray) {
	        List<ClassRoom> clazzs = new ArrayList<ClassRoom>();
	        if(jsonArray != null && jsonArray.length() > 0) {
	            for(int i=0;i<jsonArray.length();i++) {
	                ClassRoom clazz = parseJsonForOff(jsonArray.optJSONObject(i));
	                if(clazz != null)clazzs.add(clazz);
	            }
	        }
	        return clazzs;
	    }
	    
	    
		public static List<ClassRoom> parseFromJson(JSONArray jsonArray) {
			List<ClassRoom> clazzs = new ArrayList<ClassRoom>();
			if(jsonArray != null && jsonArray.length() > 0) {
				for(int i=0;i<jsonArray.length();i++) {
					ClassRoom clazz = parseJson(jsonArray.optJSONObject(i));
					if(clazz != null)clazzs.add(clazz);
				}
			}
			return clazzs;
		}


		private static ClassRoom parseJson(JSONObject optJSONObject) {
			ClassRoom grade=new ClassRoom();
		   grade.setId(optJSONObject.optLong("gid"));
			grade.setName(optJSONObject.optString("gname"));
			grade.setPlatform(optJSONObject.optLong("platform")==0?"1":"2");
			JSONArray jsonArray=optJSONObject.optJSONArray("data");
			List<ClassMemberBean> cList = new ArrayList<>();
			if(jsonArray != null && jsonArray.length() > 0){
				
				for (int i = 0; i < jsonArray.length(); i++) {
				ClassMemberBean sclass=	new ClassMemberBean();

					sclass.setClassId(grade.getId());
					sclass.setUserId(jsonArray.optJSONObject(i).optLong("cid"));
					sclass.setNickName(jsonArray.optJSONObject(i).optString("cname"));
					cList.add(sclass);
				}
				grade.setSclasses(cList);
			}
			return grade;
		}
}
