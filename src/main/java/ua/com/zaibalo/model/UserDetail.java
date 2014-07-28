package ua.com.zaibalo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ua.com.zaibalo.helper.gson.ExcludeFromJson;

@Entity
@Table(name="user_details")
public class UserDetail {
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@ExcludeFromJson
	@ManyToOne(fetch=FetchType.LAZY)
	private User user;
	
	@Enumerated(EnumType.STRING)
	@Column(name="type")
	private DetailType detailType;

	private String value;
	
	public enum DetailType{
		IP
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public DetailType getDetailType() {
		return detailType;
	}

	public void setDetailType(DetailType detailType) {
		this.detailType = detailType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
