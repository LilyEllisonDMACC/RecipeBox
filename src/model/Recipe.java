/**
 * @author Lily Ellison - lbellison
 * CIS175 - Fall 2023
 * Oct 2, 2023
 * 
 * @author Adam Reese - amreese3
 * CIS175 - Fall 2023
 * Oct 2, 2023
 */

package model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Recipe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private int servings;
	private int preparationTime;

	@ManyToOne
	private Category category;

	// Modify this field to store a list of ingredients
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
	private List<Ingredient> ingredients;

	// Add a field to store instructions
	@Column(columnDefinition = "TEXT")
	private String instructions;

	public Recipe() {
		// Default constructor
	}

	/**
	 * Creates a new Recipe with the specified attributes.
	 *
	 * @param name            The name of the recipe.
	 * @param servings        The number of servings the recipe yields.
	 * @param preparationTime The preparation time in minutes.
	 * @param category        The category of the recipe.
	 * @param ingredients     The list of ingredients for the recipe.
	 * @param instructions    The cooking instructions for the recipe.
	 */
	public Recipe(String name, int servings, int preparationTime, Category category, List<Ingredient> ingredients,
			String instructions) {
		this.name = name;
		this.servings = servings;
		this.preparationTime = preparationTime;
		this.category = category;
		this.ingredients = ingredients;
		this.instructions = instructions;
	}

	// Getter and setter methods for id, name, servings, preparationTime, category,
	// ingredients, and instructions
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

	// Other methods and properties as needed

	// Displays the recipe details in a user-friendly way
	@Override
	public String toString() {
		StringBuilder ingredientList = new StringBuilder();
		for (Ingredient ingredient : ingredients) {
			ingredientList.append(ingredient.getName()).append(": ").append(ingredient.getQuantity()).append(" ")
					.append(ingredient.getUnit()).append("\n");
		}

		return "Recipe #" + id + "\nName: " + name + "\nServings: " + servings + "\nPreparation Time: "
				+ preparationTime + " minutes" + "\nCategory: " + category.getName() + "\nIngredients:\n"
				+ ingredientList + "\nInstructions:\n" + instructions + "\n-----------------------";
	}
}
