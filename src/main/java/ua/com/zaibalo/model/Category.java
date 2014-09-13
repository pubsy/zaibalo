package ua.com.zaibalo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue
	@Column
	private int id;

	@Column(unique = true)
	private String name;

	@Column
	@Enumerated(EnumType.STRING)
	private CategoryType type;

	public enum CategoryType {
		CATEGORY, TAG;
	}

	public Category() {
	}

	public Category(String name, CategoryType type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setType(CategoryType type) {
		this.type = type;
	}

	public CategoryType getType() {
		return type;
	}

	public boolean equals(Object other) {
		if(other == null){
			return false;
		}
		if (other.getClass() != this.getClass()){
			return false;
		}
		Category otherCategory = (Category) other;
		return otherCategory.getName().equals(this.getName());
	}

	public int hashCode() {
		return this.getName().hashCode();
	}
}
