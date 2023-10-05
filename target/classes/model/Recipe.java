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

import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	private int servings;
	private int preparationTime; // Changed to String

	@ManyToOne
	private Category category;

	@ElementCollection
	private List<String> ingredients;

	@Column(columnDefinition = "TEXT")
	private String instructions;

	// No-argument constructor
	public Recipe() {
	}

	/**
	 * Creates a new Recipe with the specified attributes.
	 *
	 * @param name            The name of the recipe.
	 * @param servings        The number of servings the recipe yields.
	 * @param preparationTime The preparation time as a String.
	 * @param category        The category of the recipe.
	 * @param ingredients     The list of ingredients as List<String>.
	 * @param instructions    The cooking instructions for the recipe.
	 */
	public Recipe(String name, int servings, int preparationTime, Category category, List<String> ingredients,
			String instructions) {
		this.name = name;
		this.servings = servings;
		this.preparationTime = preparationTime;
		this.category = category;
		this.ingredients = ingredients;
		this.instructions = instructions;
	}

	// Constructor that takes another Recipe object
	public Recipe(Recipe otherRecipe) {
		this.name = otherRecipe.name;
		this.servings = otherRecipe.servings;
		this.preparationTime = otherRecipe.preparationTime;
		this.category = otherRecipe.category;
		this.ingredients = new ArrayList<>(otherRecipe.ingredients);
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
		return preparationTime; // Changed return type to String
	}

	public void setPreparationTime(int preparationTime) { // Changed parameter type to String
		this.preparationTime = preparationTime;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<String> ingredients) { // Changed parameter type to List<String>
		this.ingredients = ingredients;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	// Displays the recipe details in a user-friendly way
	@Override
	public String toString() {
		StringBuilder ingredientList = new StringBuilder();
		for (String ingredient : ingredients) { // Iterating over List<String>
			ingredientList.append(ingredient).append("\n");
		}

		return "\nName: " + name + "\nServings: " + servings + "\nPreparation Time: " + preparationTime + " minutes"
				+ "\nCategory: " + category.getName() + "\nIngredients:\n" + ingredientList + "\nInstructions:\n"
				+ instructions + "\n---------------------------";
	}
}
