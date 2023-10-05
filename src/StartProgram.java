
/**
 * @author Lily Ellison - lbellison
 * CIS175 - Fall 2023
 * Oct 2, 2023
 *
 * @author Adam Reese - amreese3
 * CIS175 - Fall 2023
 * Oct 2, 2023
 */

import controller.RecipeHelper;
import exceptions.DatabaseAccessException;
import model.Category;
import model.Ingredient;
import model.Recipe;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Class to run the program
public class StartProgram {
	private static final Scanner scanner = new Scanner(System.in);
	private static RecipeHelper recipeHelper;
	private static EntityManager entityManager;

	// Constructor that takes an EntityManager as a parameter
	public StartProgram(EntityManager entityManager) {
		StartProgram.entityManager = entityManager;
		recipeHelper = new RecipeHelper(entityManager);
	}

	// Main method
	public static void main(String[] args) throws DatabaseAccessException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("RecipeBox");
		entityManager = emf.createEntityManager();
		new StartProgram(entityManager);
		runMenu();
		entityManager.close();
		emf.close();
	}

	// Main menu
	public static void runMenu() throws DatabaseAccessException {
		boolean continueRunning = true;
		System.out.println("--- Welcome to our awesome recipe box! ---");

		// Main menu loop
		while (continueRunning) {
			System.out.println("*  Select an option:"); // Display the menu options
			System.out.println("*  1 -- Add a recipe"); // Add a recipe
			System.out.println("*  2 -- Delete a recipe"); // Delete a recipe
			System.out.println("*  3 -- Search for recipes"); // Search for recipes
			System.out.println("*  4 -- View the list"); // View the list of recipes
			System.out.println("*  5 -- Exit the program"); // Exit the program
			System.out.print("*  Your selection: "); // Prompt the user for input

			// Get the user's selection
			String input = scanner.nextLine();

			try {
				int selection = Integer.parseInt(input);

				// Handle the user's selection
				switch (selection) {
				case 1:
					addRecipe();
					break;
				case 2:
					deleteRecipe();
					break;
				case 3:
					searchRecipes();
					break;
				case 4:
					viewRecipeList();
					break;
				case 5:
					recipeHelper.closeEntityManager();
					System.out.println("   Goodbye!   ");
					continueRunning = false;
					break;
				default:
					System.out.println("Invalid selection. Please try again.");
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a valid selection.");
			}
		}
	}

	// Sub-menu for adding a recipe
	private static void addRecipe() throws DatabaseAccessException {
		System.out.print("Enter a name: ");
		String name = scanner.nextLine();
		int servings = getPositiveIntegerInput("Enter the number of servings: ");
		int preparationTime = getPositiveIntegerInput("Enter the preparation time in minutes: ");
		System.out.print("Enter a category: ");
		String category = scanner.nextLine();

		// Collect ingredients and instructions
		List<Ingredient> ingredients = new ArrayList<>();
		List<String> instructions = new ArrayList<>();

		// Loop to add ingredients
		while (true) {
			System.out.println("Please add ingredients (or type 'done' to finish). Use plural form if needed.");
			String ingredientName = scanner.nextLine();

			if (ingredientName.equalsIgnoreCase("done")) {
				break; // Exit the loop if 'done' is entered
			}

			System.out.print("Enter the quantity: ");
			String quantity = scanner.nextLine();

			System.out.print("Enter the unit (e.g., cups, grams, etc.): ");
			String unit = scanner.nextLine();

			Ingredient ingredient = new Ingredient();
			ingredient.setName(ingredientName);
			ingredient.setQuantity(quantity);
			ingredient.setUnit(unit);

			ingredients.add(ingredient);
		}

		// Loop to add instructions
		while (true) {
			System.out.println("Add an instruction (or type 'done' to finish): ");
			String instruction = scanner.nextLine();

			if (instruction.equalsIgnoreCase("done")) {
				break; // Exit the loop if 'done' is entered
			}

			instructions.add(instruction);
		}

		// Check if the category already exists in the database and create it if needed
		TypedQuery<Category> categoryQuery = entityManager
				.createQuery("SELECT c FROM Category c WHERE c.name = :categoryName", Category.class);
		categoryQuery.setParameter("categoryName", category);
		Category foundCategory;

		List<Category> categoryList = categoryQuery.getResultList();
		if (!categoryList.isEmpty()) {
			foundCategory = categoryList.get(0);
		} else {
			foundCategory = new Category(category);
			entityManager.getTransaction().begin();
			entityManager.persist(foundCategory);
			entityManager.getTransaction().commit();
		}

		// Create a new recipe and insert it into the database
		Recipe newRecipe = new Recipe(name, servings, preparationTime, foundCategory, ingredients,
				String.join("\n", instructions));
		newRecipe.setIngredients(ingredients);
		newRecipe.setInstructions(String.join("\n", instructions));
		recipeHelper.insertRecipe(newRecipe);

		// Display a success message
		System.out.println("Recipe added successfully!");

		// Return to the previous menu
		handleSubMenu();
	}

	private static void deleteRecipe() throws DatabaseAccessException {
		List<Recipe> deleteOptions = recipeHelper.showAllRecipes(); // Display all recipes

		// If no recipes were found, return to the previous menu
		if (deleteOptions.isEmpty()) {
			System.out.println("No recipes found. Returning to the previous menu.");
			return;
		}

		// Display the list of recipes to delete
		System.out.println("Recipes available for deletion:");
		for (int i = 0; i < deleteOptions.size(); i++) {
			Recipe recipe = deleteOptions.get(i);
			System.out.println((i + 1) + ": " + recipe.getName());
		}

		// Get the user's selection
		int choice = -1;
		// Loop until the user enters a valid choice
		while (choice < 1 || choice > deleteOptions.size()) {
			System.out.print("Enter the number of the recipe you want to delete: ");
			try {
				choice = Integer.parseInt(scanner.nextLine());
				if (choice < 1 || choice > deleteOptions.size()) {
					System.out.println("Invalid choice. Please enter a valid number.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a valid number.");
			}
		}

		Recipe toDelete = deleteOptions.get(choice - 1);

		// Confirm the deletion
		if (confirmDelete(toDelete)) {
			recipeHelper.deleteRecipe(toDelete);
			System.out.println("Recipe deleted successfully!");
		} else {
			System.out.println("Deletion canceled. Returning to the previous menu.");
		}

		// Return to the previous menu
		handleSubMenu();
	}

	// Sub-menu for viewing the list of recipes
	private static void viewRecipeList() throws DatabaseAccessException {
		List<Recipe> allRecipes = recipeHelper.showAllRecipes();
		for (Recipe recipe : allRecipes) {
			System.out.println(recipe.toString());
		}

		// Return to the main menu
		return;
	}

	// Helper method to display a list of recipes
	private static void displayFoundRecipes(List<Recipe> recipes) {
		System.out.println("Found Recipes:");
		for (Recipe recipe : recipes) {
			System.out.println(recipe.toString());
		}

		// Return to the previous menu
		handleSubMenu();
	}

	// Helper method to confirm the deletion of a recipe
	private static boolean confirmDelete(Recipe toDelete) {
		System.out.println("Please confirm this is the recipe you would like to delete: ");
		System.out.println("Recipe #" + toDelete.getId() + " : " + toDelete.getName());
		System.out.println("1: Yes");
		System.out.println("2: No");

		// Get the user's confirmation
		int confirmationInt = scanner.nextInt();

		scanner.nextLine(); // Consume the newline character

		return confirmationInt == 1;
	}

	// Helper method to get a positive integer input from the user
	private static int getPositiveIntegerInput(String message) {
		int value = -1;
		// Loop until the user enters a positive integer
		while (value < 0) {
			System.out.print(message);
			try {
				value = Integer.parseInt(scanner.nextLine());
				if (value < 0) {
					System.out.println("Please enter a positive number.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a number.");
			}
		}
		// Return the user's input
		return value;
	}

	// Sub-menu for searching for recipes
	private static void searchRecipes() throws DatabaseAccessException {
		int searchBy = selectSearchMethod();

		switch (searchBy) {
		case 1:
			searchByName(); // Search by name
			break;
		case 2:
			searchByCategory(); // Search by category
			break;
		case 3:
			searchByIngredient(); // Search by ingredient
			break;
		case 4:
			searchByServingSize(); // Search by serving size
			break;
		default:
			System.out.println("Invalid choice. Please select a valid search method.");
			break;
		}

		// Return to the previous menu
		handleSubMenu();
	}

	// Helper method to select a search method
	private static int selectSearchMethod() {
		System.out.println("How would you like to search? ");
		System.out.println("1 : Search by Name");
		System.out.println("2 : Search by Category");
		System.out.println("3 : Search by Ingredient");
		System.out.println("4 : Search by Serving Size");
		int searchBy = scanner.nextInt();
		scanner.nextLine();
		return searchBy;
	}

	// Helper methods to search for recipes
	private static void searchByName() throws DatabaseAccessException {
		System.out.print("Enter the recipe's name: ");
		String recipeName = scanner.nextLine();
		List<Recipe> foundRecipes = recipeHelper.searchForRecipeByTitle(recipeName);

		// If recipes were found, display them. Otherwise, display an error message
		if (!foundRecipes.isEmpty()) {
			displayFoundRecipes(foundRecipes);
		} else {
			System.out.println("No recipes found with the specified name.");
		}
	}

	// Helper methods to search for recipes
	private static void searchByCategory() throws DatabaseAccessException {
		System.out.print("Enter the category: ");
		String category = scanner.nextLine();
		List<Recipe> foundRecipes = recipeHelper.searchForRecipeByCategory(category);

		// If recipes were found, display them. Otherwise, display an error message
		if (!foundRecipes.isEmpty()) {
			displayFoundRecipes(foundRecipes);
		} else {
			System.out.println("No recipes found in the specified category.");
		}
	}

	// Helper methods to search for recipes
	private static void searchByIngredient() throws DatabaseAccessException {
		System.out.print("Enter an ingredient: ");
		String ingredient = scanner.nextLine();
		List<Recipe> foundRecipes = recipeHelper.searchForRecipeByIngredient(ingredient);

		// If recipes were found, display them. Otherwise, display an error message
		if (!foundRecipes.isEmpty()) {
			displayFoundRecipes(foundRecipes);
		} else {
			System.out.println("No recipes found with the specified ingredient.");
		}
	}

	// Helper methods to search for recipes
	private static void searchByServingSize() throws DatabaseAccessException {
		System.out.print("Enter the serving size: ");
		int servingSize = scanner.nextInt();
		scanner.nextLine(); // Consume the newline character
		List<Recipe> foundRecipes = recipeHelper.searchForRecipeByServingSize(servingSize);

		// If recipes were found, display them. Otherwise, display an error message
		if (!foundRecipes.isEmpty()) {
			displayFoundRecipes(foundRecipes);
		} else {
			System.out.println("No recipes found with the specified serving size.");
		}
	}

	// Sub-menu for returning to the main menu or exiting the program
	private static int handleSubMenu() {
		System.out.println("*  1 -- Return to Main Menu");
		System.out.println("*  2 -- Exit the program");
		System.out.print("*  Your selection: ");
		int subSelection = scanner.nextInt();
		scanner.nextLine(); // Consume the newline character

		// Handle the user's selection
		if (subSelection == 1) {
			return 1; // Return to Main Menu
		} else if (subSelection == 2) {
			recipeHelper.closeEntityManager();
			System.out.println("   Goodbye!   ");
			System.exit(0);
		} else {
			System.out.println("Invalid selection. Please try again.");
			return handleSubMenu(); // Return to the sub-menu
		}

		// This line should never be reached
		return -1;
	}
}
