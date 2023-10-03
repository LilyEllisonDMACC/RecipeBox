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
@Table(name="categories")
public class Category {
	@Id
	@GeneratedValue
	@Column(name="ID")
	private int id;
    @Column(name="NAME")
	private String name;
    
    public Category() {
    	super();
    }
    
    public Category(int id, String name) {
    	super();
        this.id = id;
        this.name = name;
    }
    
    public Category(String name) {
    	super();
    	this.name = name;
    }

}
