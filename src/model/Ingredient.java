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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ingredients")
public class Ingredient {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "QUANTITY")
	private double quantity;

	@Column(name = "UNIT")
	private String unit;

	// Default constructor
	public Ingredient() {
		super();
	}

	// Constructor with all fields
	public Ingredient(int id, String name, double quantity, String unit) {
		super();
		this.id = id;
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
	public double getQuantity() {
		return quantity;
	}

	// Setter for quantity
	public void setQuantity(double quantity) {
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
