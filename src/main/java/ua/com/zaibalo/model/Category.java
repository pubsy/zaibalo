package ua.com.zaibalo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="categories")
public class Category{
	
	public Category(){}
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="name", unique=true)
	private String name;
	
	@Column(name="type")
	private String type;
	
	public static final String CATEGORY_TYPE = "category";
	public static final String TAG_TYPE = "post_tag";
	
	public Category(int id, String name, CategoryType type) {
		this(name, type);
		this.id = id;		
	}
	
	public Category(String name, CategoryType type) {
		this.name = name;
		this.type = type.getType();
	}

	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
	public boolean equals(Object o){
		if(o!= null && o instanceof Category){
			if(((Category) o).getName().equals(this.getName())){
				return true;
			}
		}
		return false;
	}
	
	public int hashCode(){
		return this.getName().hashCode();
	}

	public enum CategoryType{
		CATEGORY(Category.CATEGORY_TYPE), TAG(Category.TAG_TYPE), BOTH("%%");
		
		private String type;
		
		CategoryType(String type){
			this.type = type;
		}
		
		public String getType(){
			return type;
		}
		
		public static CategoryType getCategoryTypeForString(String type){
			if(Category.CATEGORY_TYPE.equals(type)){
				return CategoryType.CATEGORY;
			}else if(Category.TAG_TYPE.equals(type)){
				return CategoryType.TAG;
			}else{
				throw new RuntimeException("Unknown type + " + type);
			}
		}
	}
}

