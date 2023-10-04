/**
 * @author Lily Ellison - lbellison
 * CIS175 - Fall 2023
 * Oct 2, 2023
 */

/**
 * @author Adam Reese - amreese3
 * CIS175 - Fall 2023
 * Oct 2, 2023
 */

package model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "recipes")
public class Recipe {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;
	@Column(name = "NAME")
	private String name;
	@Column(name = "SERVINGS")
	private int servings;
	@Column(name = "PREPTIME")
	private int preparationTime;

	@OneToMany
	@JoinColumn(name = "RECIPE_ID")
	private List<Ingredient> ingredients;

	@ManyToMany
	@JoinTable(name = "recipe_category", joinColumns = @JoinColumn(name = "recipe_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private List<Category> categories;

	public Recipe() {
		super();
	}

	// Constructor for creating a Recipe with a name, servings, preparation time,
	// and a single Category.
	public Recipe(String name, int servings, int preparationTime, Category category) {
		super();
		this.name = name;
		this.servings = servings;
		this.preparationTime = preparationTime;
		this.categories = List.of(category);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getServings() {
		return servings;
	}

	public void setServings(int servings) {
		this.servings = servings;
	}

	public int getPreparationTime() {
		return preparationTime;
	}

	public void setPreparationTime(int preparationTime) {
		this.preparationTime = preparationTime;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	@Override
	public String toString() {
		return "Recipe [id=" + id + ", name=" + name + ", servings=" + servings + ", preparationTime=" + preparationTime
				+ "]";
	}
}
