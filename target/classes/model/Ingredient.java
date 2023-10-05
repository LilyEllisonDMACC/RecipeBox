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

@Entity
@Table(name = "ingredient")
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "QUANTITY")
	private String quantity;

	@Column(name = "UNIT")
	private String unit;

	// Define the relationship to Recipe
	@ManyToOne
	@JoinColumn(name = "RECIPE_ID")
	private Recipe recipe;

	// Default constructor
	public Ingredient() {
	}

	// Constructor with name, quantity, and unit
	public Ingredient(String name, String quantity, String unit) {
		this.name = name;
		this.quantity = quantity;
		this.unit = unit;
	}

	// Getter for id
	public int getId() {
		return id;
	}

	// Setter for id
	public void setId(int id) {
		this.id = id;
	}

	// Getter for name
	public String getName() {
		return name;
	}

	// Setter for name
	public void setName(String name) {
		this.name = name;
	}

	// Getter for quantity
	public String getQuantity() {
		return quantity;
	}

	// Setter for quantity
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	// Getter for unit
	public String getUnit() {
		return unit;
	}

	// Setter for unit
	public void setUnit(String unit) {
		this.unit = unit;
	}

	// Override toString() method
	@Override
	public String toString() {
		return "Ingredient [id=" + id + ", name=" + name + ", quantity=" + quantity + ", unit=" + unit + "]";
	}
}
