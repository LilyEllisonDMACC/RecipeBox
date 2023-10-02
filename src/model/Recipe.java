/**
 * @author tehli - lbellison
 * CIS175 - Fall 2023
 * Oct 1, 2023
 */
package model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author LILY ELLISON - LBELLISON
 * CIS175 - FALL 2023
 * Oct 1, 2023
 */

@Entity
@Table(name="recipes")
public class Recipe {
	@Id
	@GeneratedValue
	@Column(name="ID")
	private int id;
	@Column(name="TITLE")
	private String title;
	@Column(name="TYPE")
	private String type;
	@Column(name="INGREDIENTS")
	private List<String> ingredients;
	@Column(name="INSTRUCTIONS")
	private List<String> instructions;
	
	public Recipe() {
		super();
	}
	
	public Recipe(String title, String type, List<String> ingredients, List<String> instructions) {
		super();
		this.title = title;
		this.type = type;
		this.ingredients = ingredients;
		this.instructions = instructions;
	}
	
	public Recipe(String title, String type) {
		super();
		this.title = title;
		this.type = type;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the ingredients
	 */
	public List<String> getIngredients() {
		return ingredients;
	}

	/**
	 * @param ingredients the ingredients to set
	 */
	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}

	/**
	 * @return the instructions
	 */
	public List<String> getInstructions() {
		return instructions;
	}

	/**
	 * @param instructions the instructions to set
	 */
	public void setInstructions(List<String> instructions) {
		this.instructions = instructions;
	}

	@Override
	public String toString() {
		return "Recipe [id=" + id + ", title=" + title + ", type=" + type + ", ingredients=" + ingredients
				+ ", instructions=" + instructions + "]";
	}

	/**
	 * @return
	 */
	public char[] returnRecipeDetails() {
		// TODO Auto-generated method stub
		return null;
	}

}
