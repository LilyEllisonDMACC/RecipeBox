
/**
 * @author Lily Ellison - lbellison
 * CIS175 - Fall 2023
 * Oct 2, 2023
 *
 * @author Adam Reese - amreese3
 * CIS175 - Fall 2023
 * Oct 2, 2023
 */

import controller.CategoryHelper;
import controller.IngredientHelper;
import controller.RecipeHelper;
import exceptions.DatabaseAccessException;
import model.Category;
import model.Ingredient;
import model.Recipe;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Class to run the program
public final class StartProgram {
	private final static Scanner scanner = new Scanner(System.in);
	private static RecipeHelper recipeHelper = null;
	private static IngredientHelper ingredientHelper = null;
	private static CategoryHelper categoryHelper = null;

	private static EntityManager entityManager = null; // Add this line

	// Method to initialize the program
	public StartProgram(EntityManager entityManager) {
		recipeHelper = new RecipeHelper(entityManager);
		categoryHelper = new CategoryHelper(entityManager);
		ingredientHelper = new IngredientHelper(entityManager);
		StartProgram.entityManager = entityManager;
	}

	// Main method
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("RecipeBox");
		EntityManager entityManager = emf.createEntityManager();

		try {
			// Call the runMenu method directly using the class name
			StartProgram.runMenu();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (entityManager != null && entityManager.isOpen()) {
				entityManager.close();
			}
			if (emf.isOpen()) {
				emf.close();
			}
		}
	}

	// Helper method to handle the main menu
	public static void runMenu() {
		boolean continueRunning = true;
		System.out.println("--- Welcome to our awesome recipe box! ---");

		// Main menu loop
		while (continueRunning) {
			System.out.println("*  Select an option:");
			System.out.println("*  1 -- Add a recipe");
			System.out.println("*  2 -- Edit a recipe");
			System.out.println("*  3 -- Delete a recipe");
			System.out.println("*  4 -- Search for recipes");
			System.out.println("*  5 -- View the recipe list");
			System.out.println("*  6 -- Manage Ingredients");
			System.out.println("*  7 -- Manage Categories");
			System.out.println("*  8 -- Exit the program");
			System.out.print("*  Your selection: ");

			// Get the user's input
			String input = scanner.nextLine();

			// Handle the user's input
			try {
				int selection = Integer.parseInt(input);

				// Handle the user's selection
				switch (selection) {
				case 1:
					addRecipe(entityManager);
					break;
				case 2:
					editRecipe();
					break;
				case 3:
					deleteRecipe();
					break;
				case 4:
					searchRecipes();
					break;
				case 5:
					viewRecipeList();
					break;
				case 6:
					manageIngredients();
					break;
				case 7:
					manageCategories();
					break;
				case 8:
					recipeHelper.closeEntityManager();
					System.out.println("   Goodbye!   ");
					continueRunning = false;
					break;
				default:
					System.out.println("Invalid selection. Please try again.");
					break;
				}
				// Handle any exceptions that may occur
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a valid selection.");
			} catch (DatabaseAccessException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}

	// Sub-menu for adding a recipe
	private static void addRecipe(EntityManager em) throws DatabaseAccessException {
		System.out.print("Enter a name for the recipe: ");
		String name = scanner.nextLine();
		int servings = getPositiveIntegerInput("Enter the number of servings: ");
		int preparationTime = getPositiveIntegerInput("Enter the preparation time in minutes: ");

		// Collect category input
		System.out.print("Enter a category: ");
		String categoryName = scanner.nextLine();

		// Check if the category already exists in the database and create it if needed
		Category foundCategory;

		// Check if the category already exists in the database
		foundCategory = categoryHelper.getCategoryByName(categoryName);

		// If the category doesn't exist, create it
		if (foundCategory == null) {
			foundCategory = new Category(categoryName);
			categoryHelper.addCategory(foundCategory);
		}

		// Collect ingredients and instructions
		List<String> ingredients = new ArrayList<>();
		List<String> instructions = new ArrayList<>();

		// Loop to add ingredients
		while (true) {
			System.out.println("Please add ingredients (or type 'done' to finish). Use plural form if needed.");
			String ingredientName = scanner.nextLine();

			if (ingredientName.equalsIgnoreCase("done")) {
				break; // Exit the loop if 'done' is entered
			}

			// Check if the ingredient already exists in the list
			boolean ingredientExists = false;
			for (String existingIngredient : ingredients) {
				if (existingIngredient.equalsIgnoreCase(ingredientName)) {
					ingredientExists = true;
					break;
				}
			}
			// If the ingredient doesn't exist, add it to the list
			if (!ingredientExists) {
				System.out.println("Ingredient doesn't exist, creating it...");
				ingredients.add(ingredientName);
			}
		}

		// Loop to add instructions
		while (true) {
			System.out.println("Add an instruction (or type 'done' to finish): ");
			String instruction = scanner.nextLine();

			if (instruction.equalsIgnoreCase("done")) {
				break; // Exit the loop if 'done' is entered
			}

			// Add the instruction to the list
			instructions.add(instruction);
		}

		// Create a new recipe and insert it into the database
		Recipe newRecipe = new Recipe(name, servings, preparationTime, foundCategory, ingredients,
				String.join("\n", instructions));

		// Begin a transaction before inserting the recipe
		em.getTransaction().begin();

		// Insert the recipe into the database
		recipeHelper.insertRecipe(newRecipe);

		// Commit the transaction
		em.getTransaction().commit();

		System.out.println("Recipe added successfully!");

		// Return to the previous menu
		handleSubMenu();
	}

	// Edit an existing recipe
	private static void editRecipe() {
		try {
			// Display a list of recipes for the user to choose from
			List<Recipe> recipes = recipeHelper.showAllRecipes();
			System.out.println("Select a recipe to edit:");

			for (int i = 0; i < recipes.size(); i++) {
				System.out.println((i + 1) + ": " + recipes.get(i).getName());
			}

			// Get the user's selection
			int choice = getPositiveIntegerInput("Enter the number of the recipe to edit: ");

			// Check if the user's selection is valid
			if (choice >= 1 && choice <= recipes.size()) {
				Recipe recipeToEdit = recipes.get(choice - 1);

				// Display the recipe to edit
				while (true) {
					System.out.println("Enter the number for the aspect to edit:");
					System.out.println("1: Name");
					System.out.println("2: Servings");
					System.out.println("3: Preparation Time");
					System.out.println("4: Ingredients");
					System.out.println("5: Instructions");
					System.out.println("6: Done Editing");
					int editChoice = getPositiveIntegerInput("Your selection: ");

					switch (editChoice) {
					case 1:
						// Edit name
						System.out.println("Current Name: " + recipeToEdit.getName());
						System.out.print("Enter the new name: ");
						String newName = scanner.nextLine();
						recipeToEdit.setName(newName);
						System.out.println("Name updated to: " + newName);
						break;
					case 2:
						// Edit servings
						System.out.println("Current Servings: " + recipeToEdit.getServings());
						int newServings = getPositiveIntegerInput("Enter the new serving size: ");
						recipeToEdit.setServings(newServings);
						System.out.println("Servings updated to: " + newServings);
						break;
					case 3:
						// Edit preparation time
						System.out
								.println("Current Preparation Time: " + recipeToEdit.getPreparationTime() + " minutes");
						int newPrepTime = getPositiveIntegerInput("Enter the new preparation time in minutes: ");
						recipeToEdit.setPreparationTime(newPrepTime);
						System.out.println("Preparation Time updated to: " + newPrepTime + " minutes");
						break;
					case 4:
						// Edit ingredients
						System.out.println("Current Ingredients:");
						List<String> currentIngredients = recipeToEdit.getIngredients();
						for (int i = 0; i < currentIngredients.size(); i++) {
							System.out.println((i + 1) + ": " + currentIngredients.get(i));
						}
						System.out.println("Enter 'done' to finish editing ingredients.");

						// Loop to add ingredients
						List<String> newIngredients = new ArrayList<>();
						while (true) {
							String newIngredient = scanner.nextLine();
							if (newIngredient.equalsIgnoreCase("done")) {
								break;
							} else {
								newIngredients.add(newIngredient);
								System.out.println("Ingredient added: " + newIngredient);
							}
						}
						recipeToEdit.setIngredients(newIngredients);
						break;
					case 5:
						// Edit instructions
						System.out.println("Current Instructions:");
						System.out.println(recipeToEdit.getInstructions());
						System.out.println("Enter the new instructions (or 'done' to finish): ");
						String newInstructions = scanner.nextLine();
						if (!newInstructions.equalsIgnoreCase("done")) {
							recipeToEdit.setInstructions(newInstructions);
							System.out.println("Instructions updated to: " + newInstructions);
						}
						break;
					case 6:
						// Exit editing loop
						break;
					default:
						System.out.println("Invalid choice. Please select a valid aspect to edit.");
						break;
					}

					if (editChoice == 6) {
						// User is done editing
						break;
					}
				}

				// Update the recipe in the database
				recipeHelper.updateRecipe(recipeToEdit);

				// Display the entire updated recipe
				System.out.println("Recipe updated successfully!");
				System.out.println("Updated Recipe Details:");
				System.out.println(recipeToEdit.toString());
			} else {
				System.out.println("Invalid choice. Please select a valid recipe to edit.");
			}
		} catch (DatabaseAccessException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	// Method to handle deleting a recipe
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
			// Get the user's input
			try {
				choice = Integer.parseInt(scanner.nextLine());
				if (choice < 1 || choice > deleteOptions.size()) {
					System.out.println("Invalid choice. Please enter a valid number.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a valid number.");
			}
		}

		// Get the recipe to delete
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

	// Display the list of recipes
	private static void viewRecipeList() throws DatabaseAccessException {
		List<Recipe> allRecipes = recipeHelper.showAllRecipes();

		for (int i = 0; i < allRecipes.size(); i++) {
			Recipe recipe = allRecipes.get(i);

			// Add a separator line
			System.out.println("---------------------------");

			// Display the recipe number
			System.out.print("Recipe #" + (i + 1));

			// Display the recipe
			System.out.println(recipe.toString());

			// If it's not the last recipe, add a newline separator
			if (i < allRecipes.size() - 1) {
				System.out.println();
			}
		}

		// Add an option to return to the previous menu
		handleSubMenu();
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

		return confirmationInt == 1; // Return true if the user confirms, false otherwise
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

		// Handle the user's selection
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
		System.out.println("How would you like to search? "); // Display the search options
		System.out.println("1 : Search by Name"); // Search by name
		System.out.println("2 : Search by Category"); // Search by category
		System.out.println("3 : Search by Ingredient"); // Search by ingredient
		System.out.println("4 : Search by Serving Size"); // Search by serving size
		System.out.print("*  Your selection: "); // Prompt the user for input
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

	// Helper method to manage ingredients
	private static void manageIngredients() throws DatabaseAccessException {
		boolean continueManaging = true;

		// Main menu loop
		while (continueManaging) {
			System.out.println("--- Manage Ingredients ---");
			System.out.println("1 -- List Ingredients");
			System.out.println("2 -- Add Ingredient");
			System.out.println("3 -- Delete Ingredient");
			System.out.println("4 -- Return to Main Menu");
			System.out.print("Your selection: ");

			String input = scanner.nextLine();

			// Handle the user's selection
			try {
				int selection = Integer.parseInt(input);

				switch (selection) {
				case 1:
					viewAllIngredients();
					break;
				case 2:
					addIngredient();
					break;
				case 3:
					deleteIngredient();
					break;
				case 4:
					continueManaging = false;
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

	// Helper methods to add ingredients
	private static void addIngredient() {
		try {
			System.out.print("Enter the name of the ingredient: ");
			String ingredientName = scanner.nextLine();

			// Check if the ingredient already exists in the database
			Ingredient existingIngredient = ingredientHelper.getIngredientByName(ingredientName);

			// If the ingredient already exists, display an error message and return
			if (existingIngredient != null) {
				System.out.println("Ingredient already exists in the database.");
				return;
			}

			// Proceed to add the ingredient (whether it exists or not)
			Ingredient newIngredient = new Ingredient();
			newIngredient.setName(ingredientName);

			// Collect additional information about the ingredient
			System.out.print("Enter the quantity: ");
			scanner.nextLine();

			System.out.print("Enter the unit (e.g., cups, grams, etc.): ");
			scanner.nextLine();

			// Call the method to insert the new ingredient into the database
			ingredientHelper.insertIngredient(newIngredient);
			System.out.println("Ingredient added successfully!");

		} catch (DatabaseAccessException e) {
			System.out.println("Error adding ingredient: " + e.getMessage());
		}
	}

	// Helper methods to delete ingredients
	private static void deleteIngredient() {
		try {
			System.out.print("Enter the name of the ingredient to delete: ");
			String ingredientName = scanner.nextLine();

			// Check if the ingredient exists in the database
			Ingredient existingIngredient = ingredientHelper.getIngredientByName(ingredientName);

			// If the ingredient doesn't exist, display an error message and return
			if (existingIngredient == null) {
				System.out.println("Ingredient not found in the database.");
				return;
			}

			// If the ingredient exists, proceed to delete it
			ingredientHelper.deleteIngredient(existingIngredient);
			System.out.println("Ingredient deleted successfully!");
		} catch (DatabaseAccessException e) {
			System.out.println("Error deleting ingredient: " + e.getMessage());
		}
	}

	// Helper methods to view all ingredients
	private static void viewAllIngredients() {
		try {
			List<Ingredient> allIngredients = ingredientHelper.showAllIngredients();

			// If no ingredients were found, display an error message
			if (allIngredients.isEmpty()) {
				System.out.println("No ingredients found in the database.");
			} else {
				System.out.println("--- Ingredients ---");
				for (Ingredient ingredient : allIngredients) {
					System.out.println(ingredient.getName());
				}
			}
		} catch (DatabaseAccessException e) {
			System.out.println("Error retrieving ingredients: " + e.getMessage());
		}
	}

	// Helper method to manage categories
	private static void manageCategories() throws DatabaseAccessException {
		boolean continueManaging = true;

		// Main menu loop
		while (continueManaging) {
			System.out.println("--- Manage Categories ---");
			System.out.println("1 -- List Categories");
			System.out.println("2 -- Add Category");
			System.out.println("3 -- Delete Category");
			System.out.println("4 -- Return to Main Menu");
			System.out.print("Your selection: ");

			String input = scanner.nextLine();

			// Handle the user's selection
			try {
				int selection = Integer.parseInt(input);

				switch (selection) {
				case 1:
					viewAllCategories();
					break;
				case 2:
					addCategory();
					break;
				case 3:
					deleteCategory();
					break;
				case 4:
					continueManaging = false;
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

	// Helper methods to add categories
	private static void addCategory() {
		try {
			System.out.print("Enter the name of the category: ");
			String categoryName = scanner.nextLine();

			// Check if the category already exists in the database
			Category existingCategory = categoryHelper.getCategoryByName(categoryName);

			if (existingCategory != null) {
				System.out.println("Category already exists in the database.");
				return;
			}

			// If the category doesn't exist, proceed to add it
			Category newCategory = new Category();
			newCategory.setName(categoryName);

			// Call the method to insert the new category into the database
			categoryHelper.addCategory(newCategory);
			System.out.println("Category added successfully!");
		} catch (DatabaseAccessException e) {
			System.out.println("Error adding category: " + e.getMessage());
		}
	}

	// Helper methods to delete categories
	private static void deleteCategory() {
		try {
			System.out.print("Enter the name of the category to delete: ");
			String categoryName = scanner.nextLine();

			// Check if the category exists in the database
			Category existingCategory = categoryHelper.getCategoryByName(categoryName);

			// If the category doesn't exist, display an error message and return
			if (existingCategory == null) {
				System.out.println("Category not found in the database.");
				return;
			}

			// If the category exists, proceed to delete it
			categoryHelper.deleteCategory(existingCategory);
			System.out.println("Category deleted successfully!");
		} catch (DatabaseAccessException e) {
			System.out.println("Error deleting category: " + e.getMessage());
		}
	}

	// Helper methods to view all categories
	private static void viewAllCategories() {
		try {
			List<Category> allCategories = categoryHelper.getAllCategories();

			// If no categories were found, display an error message
			if (allCategories.isEmpty()) {
				System.out.println("No categories found in the database.");
			} else {
				System.out.println("--- Categories ---");
				for (Category category : allCategories) {
					System.out.println(category.getName());
				}
			}
		} catch (DatabaseAccessException e) {
			System.out.println("Error retrieving categories: " + e.getMessage());
		}
	}

	// Sub-menu for returning to the main menu or exiting the program
	private static void handleSubMenu() {
		while (true) {
			System.out.println("*  1 -- Return to Main Menu");
			System.out.println("*  2 -- Exit the program");
			System.out.print("*  Your selection: ");
			int subSelection = scanner.nextInt();
			scanner.nextLine(); // Consume the newline character

			// Handle the user's selection
			if (subSelection == 1) {
				return; // Return to Main Menu
			} else if (subSelection == 2) {
				recipeHelper.closeEntityManager();
				System.out.println("   Goodbye!   ");
				System.exit(0);
			} else {
				System.out.println("Invalid selection. Please try again.");
			}
		}
	}
}