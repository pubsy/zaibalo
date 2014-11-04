package ua.com.zaibalo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
@NamedQueries({ @NamedQuery(name = "CATEGORY_MOST_POPULAR_LIST", 
query = "select p.categories from Post p right outer join p.categories c group by c.id order by count(p) desc") })
public class Category {

    @Id
	@GeneratedValue
	@Column
	private int id;

	@Column(unique = true)
	private String name;


    public Category() {
    }

    public Category(String name) {
        this.name = name;
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
