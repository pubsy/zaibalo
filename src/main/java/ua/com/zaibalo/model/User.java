package ua.com.zaibalo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ua.com.zaibalo.helper.gson.ExcludeFromJson;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	private Date date;

	public static final String USERPHOTO_DIR_PATH = "/image/";
	public static final String DEFAULT_BIG_IMG_PATH = "default.jpg";
	public static final String DEFAULT_SMALL_IMG_PATH = "default.thumbnail.jpg";

	@Column(name = "login_name", unique = true)
	private String loginName;
	private String password;

	@Column(name = "display_name", unique = true)
	private String displayName;
	
	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "role")
	private int role;

	private String token;

	@Column(name = "small_img_path")
	private String smallImgPath;

	@Column(name = "big_img_path")
	private String bigImgPath;

	private String about;

	@Column(name = "notify_on_pm")
	private boolean notifyOnPM;

	@ExcludeFromJson
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
	private List<Comment> comments;

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginName() {
		return loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setSmallImgPath(String smallImgPath) {
		this.smallImgPath = smallImgPath;
	}

	public String getSmallImgPath() {
		if (smallImgPath == null) {
			return DEFAULT_SMALL_IMG_PATH;
		}
		return smallImgPath;
	}

	public void setBigImgPath(String bigImgPath) {
		this.bigImgPath = bigImgPath;
	}

	public String getBigImgPath() {
		if (bigImgPath == null) {
			return DEFAULT_BIG_IMG_PATH;
		}
		return bigImgPath;
	}

	public String getPartlyHiddenEmail() {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < email.length(); i++) {

			if (i % 2 == 0 || email.charAt(i) == '.' || i >= email.indexOf('@')) {
				buf.append(email.charAt(i));
			} else {
				buf.append('*');
			}
		}
		return buf.toString();
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getAbout() {
		return about;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;

		return this.getId() == ((User) obj).getId();
	}

	public int hashCode() {
		return this.getLoginName().hashCode();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getRole() {
		return role;
	}

	public void setNotifyOnPM(boolean notifyOnPM) {
		this.notifyOnPM = notifyOnPM;
	}

	public boolean isNotifyOnPM() {
		return notifyOnPM;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Comment> getComments() {
		return comments;
	}
}
