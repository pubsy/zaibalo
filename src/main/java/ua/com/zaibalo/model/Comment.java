package ua.com.zaibalo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import ua.com.zaibalo.helper.gson.ExcludeFromJson;

@Entity
@Table(name="comments")
@JsonIgnoreProperties({"post", "ratings"})
public class Comment{
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@Type(type="text")
	private String content;
	
	@ExcludeFromJson
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="author_id", referencedColumnName="id", nullable = false)
	private User author;
	
	@ExcludeFromJson
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="post_id", referencedColumnName="id", nullable = false)
	private Post post;
	
	//TODO: Remove that field. Just calculate it every time
	@Column(name="rating_count")
	private int ratingCount = 0;

	//TODO: Remove that field. Just calculate it every time
	@Column(name="rating_sum")
	private int ratingSum = 0;
	
	@ExcludeFromJson
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "comment", cascade=CascadeType.REMOVE)
	private List<CommentRating> ratings = new ArrayList<CommentRating>();
	
	public Comment() {
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content){
		this.content = content;
	}
	
	public User getAuthor(){
		return this.author;
	}	
	
	public void setAuthor(User author){
		this.author = author;
	}

	public void setRatingCount(int ratingCount) {
		this.ratingCount = ratingCount;
	}

	public int getRatingCount() {
		return ratingCount;
	}

	public void setRatingSum(int ratingSum) {
		this.ratingSum = ratingSum;
	}

	public int getRatingSum() {
		return ratingSum;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Post getPost() {
		return post;
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

	public void setRatings(List<CommentRating> ratings) {
		this.ratings = ratings;
	}

	public List<CommentRating> getRatings() {
		return ratings;
	}

}
