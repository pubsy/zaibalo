package ua.com.zaibalo.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import ua.com.zaibalo.helper.gson.ExcludeFromJson;

@Entity
@Table(name="posts")
public class Post {

	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	private String title;
	
	@Type(type="text")
	private String content;
	
	@ExcludeFromJson
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="author_id", referencedColumnName="id")
	private User author;
	
	//TODO: Remove that field. Just calculate it every time
	@Column(name="rating_count")
	private int ratingCount;
	//TODO: Remove that field. Just calculate it every time
	@Column(name="rating_sum")
	private int ratingSum;

	@ExcludeFromJson
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "post", cascade=CascadeType.REMOVE)
	private List<Comment> comments;
	
	@ExcludeFromJson
	@OneToMany(mappedBy = "post", cascade=CascadeType.REMOVE)
	private Set<PostRating> ratings;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany
    @JoinTable(name="post_category", 
                joinColumns={@JoinColumn(name="post_id")}, 
                inverseJoinColumns={@JoinColumn(name="category_id")})
	private Set<Category> categories;

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent(){
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public User getAuthor() {
		return this.author;
	}
	
	public void setAuthor(User author) {
		this.author = author;
	}
	
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
	
	public Set<Category> getCategories() {
		return categories;
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

	public enum PostOrder{
		ID("posts.id", "id"), RATING_SUM("posts.rating_sum", "ratingSum");
		
		PostOrder(String columnName, String propertyName){
			this.columnName = columnName;
			this.propertyName = propertyName;
		}
		
		private String columnName;
		private String propertyName;

		public String getColumnName() {
			return columnName;
		}
		
		public String getPropertyName(){
			return propertyName;
		}
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

	public void setRatings(Set<PostRating> ratings) {
		this.ratings = ratings;
	}

	public Set<PostRating> getRatings() {
		return ratings;
	}
}
