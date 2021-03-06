package ua.com.zaibalo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ua.com.zaibalo.helper.gson.ExcludeFromJson;

@Entity
@Table(name = "users")
public class User {

	private static final String DEFAULT_JPG = "default.jpg";
	public static final String IMAGES_PATH_URL = "https://s3.eu-central-1.amazonaws.com/z-avatars/";

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@Column(name = "login_name", unique = true)
	private String loginName;
	
	@ExcludeFromJson
	private String password;

	@Column(name = "display_name", unique = true)
	private String displayName;
	
	@Column(name = "email", unique = true)
	private String email;

	@Column
	@ExcludeFromJson
	@Enumerated(EnumType.STRING)
	private Role role = Role.ROLE_USER;

	public enum Role {
		ROLE_ADMIN, ROLE_USER, ROLE_GUEST;
	}
	
	@ExcludeFromJson
	private String token;

	@Column(name = "photo")
	private String bigImgPath = IMAGES_PATH_URL + DEFAULT_JPG;

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

	public void setBigImgPath(String bigImgPath) {
		this.bigImgPath = bigImgPath;
	}

	public String getBigImgPath() {
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

	public void setRole(Role role) {
		this.role = role;
	}

	public Role getRole() {
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

	public boolean isGuest() {
		return role.equals(Role.ROLE_GUEST);
	}

	public boolean isUser() {
		return role.equals(Role.ROLE_USER);
	}

	public String getSmallImgPath() {
		int i = bigImgPath.lastIndexOf('.');
		if (i > 0) {
		    String extension = bigImgPath.substring(i+1);
		    return bigImgPath.substring(0, i).concat(".thumbnail.").concat(extension);
		}
		return bigImgPath;
	}

}
