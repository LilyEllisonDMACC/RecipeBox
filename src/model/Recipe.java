/**
 * @author Lily Ellison - lbellison
 * CIS175 - Fall 2023
 * Oct 7, 2023
 * @author Adam Reese - amreese3
 * CIS175 - Fall 2023
 * Oct 7, 2023
 */

package model;

import util.DateConverter;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.List;

@Entity
public class Recipe {
	// Primary key and auto-generated value
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	// Attributes
	private String name;
	private int servings;
	private int preparationTime;

	// Many-to-one relationship with Category
	@ManyToOne(cascade = CascadeType.ALL)
	private Category category;

	// One-to-many relationship with Ingredient
	@Column(columnDefinition = "TEXT")
	private String instructions;

	// One-to-many relationship with Ingredient
	@OneToMany(cascade = CascadeType.ALL)
	private List<Ingredient> ingredients;

	@Convert(converter = DateConverter.class)
	private java.util.Date dateAdded;

	@Convert(converter = DateConverter.class)
	private java.util.Date lastModified;

	// No-argument constructor
	public Recipe() {
	}

	/**
	 * Creates a new Recipe with the specified attributes.
	 *
	 * @param name            The name of the recipe.
	 * @param servings        The number of servings the recipe yields.
	 * @param preparationTime The preparation time as an int.
	 * @param category        The category of the recipe.
	 * @param instructions    The cooking instructions for the recipe.
	 */

	// Constructor that takes all attributes
	public Recipe(String name, int servings, int preparationTime, Category category, String instructions) {
		this.name = name;
		this.servings = servings;
		this.preparationTime = preparationTime;
		this.category = category;
		this.instructions = instructions;
	}

	// Constructor that takes another Recipe object
	public Recipe(Recipe otherRecipe) {
		this.name = otherRecipe.name;
		this.servings = otherRecipe.servings;
		this.preparationTime = otherRecipe.preparationTime;
		this.category = otherRecipe.category;
		this.instructions = otherRecipe.instructions;
	}

	// Getter and setter methods
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public java.util.Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(java.util.Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public java.util.Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(java.util.Date lastModified) {
		this.lastModified = lastModified;
	}

	@PrePersist
	protected void onCreate() {
		dateAdded = new java.util.Date();
		lastModified = dateAdded;
	}

	@PreUpdate
	protected void onUpdate() {
		lastModified = new java.util.Date();
	}

	// Displays the recipe details in a user-friendly way
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		StringBuilder ingredientList = new StringBuilder();
		for (Ingredient ingredient : this.ingredients) { // Assuming 'ingredients' is your List<Ingredient>
			ingredientList.append(ingredient.getName()).append("\n");
		}

		return "\n---------------------------" + "\nRecipe: " + name + "\n---------------------------" + "\nCategory: "
				+ category.getName() + "\nServings: " + servings + "\nPreparation Time: " + preparationTime + " minutes"
				+ "\nDate Added: " + (dateAdded != null ? sdf.format(dateAdded) : "N/A") + "\nLast Modified: "
				+ (lastModified != null ? sdf.format(lastModified) : "N/A") + "\n---------------------------"
				+ "\nIngredients:\n" + ingredientList + "\n---------------------------" + "\nInstructions:\n"
				+ instructions + "\n---------------------------";
	}
}