/**
 * @author Lily Ellison - lbellison
 * CIS175 - Fall 2023
 * Oct 6, 2023
 * @author Adam Reese - amreese3
 * CIS175 - Fall 2023
 * Oct 6, 2023
 */

package model;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category {

    // Primary key and auto-generated value
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;

    // Name of the category
    @Column(name = "NAME")
    private String name;

    // Default constructor
    public Category() {
        super();
    }

    // Constructor without id
    public Category(String name) {
        super();
        this.name = name;
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

    // Override toString() method
    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + "]";
    }
}