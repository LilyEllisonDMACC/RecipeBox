/**
 * @author tehli - lbellison
 * CIS175 - Fall 2023
 * Oct 3, 2023
 */
package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author LILY ELLISON - LBELLISON
 * CIS175 - FALL 2023
 * Oct 3, 2023
 */
@Entity
@Table(name="ingredients")
public class Ingredient {

	@Id
	@GeneratedValue
	@Column(name="ID")
	private int id;
	@Column(name="NAME")
    private String name;
	@Column(name="QUANTITY")
    private double quantity;
	@Column(name="UNIT")
    private String unit;
	
	public Ingredient() {
		super();
	}
    
    public Ingredient(int id, String name, double quantity, String unit) {
        super();
    	this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }
}
