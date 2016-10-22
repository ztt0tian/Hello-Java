package studentmessage;
import java.io.File;
import java.io.Serializable;
/**
 * Student类，需要实现serializable接口
 */
public class Student implements Serializable {
	private File imagePic;
	private String number,name,sex,major,grade,birthday;
	public File getImagePic() {
		return imagePic;
	}
	public void setImagePic(File imagePic) {
		this.imagePic = imagePic;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	

}
