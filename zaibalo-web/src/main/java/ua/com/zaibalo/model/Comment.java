package ua.com.zaibalo.model;

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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

@Entity
@Table(name="comments")
@JsonIgnoreProperties({"post", "ratings", "postId", "postTitle", "authorId"})
public class Comment{
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;

	private Date date;
	
	@Type(type="text")
	private String content;
	
	//@ExcludeFromJson
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="author_id", referencedColumnName="id", insertable = false, updatable = false)
	private User author;
	
	//@ExcludeFromJson
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="post_id", referencedColumnName="id", insertable = false, updatable = false)
	private Post post;
	
	@Column(name="post_title")
	private String postTitle;
	
	@Column(name="rating_count")
	private int ratingCount;

	@Column(name="rating_sum")
	private int ratingSum;

	@Column(name="author_id")
	private int authorId;
	
	@Column(name="post_id")
	private int postId;
	
	//@ExcludeFromJson
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "comment", cascade=CascadeType.REMOVE)
	private List<CommentRating> ratings;
	
	public Comment() {
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content){
		this.content = content;
	}
	
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getPostId() {
		return postId;
	}
	
	public User getAuthor(){
		return this.author;
	}	
	
	public void setAuthor(User author){
		this.author = author;
	}
	
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	
	public String getPostTitle() {
		return postTitle;
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
