
/**
 * @author Lily Ellison - lbellison
 * CIS175 - Fall 2023
 * Oct 6, 2023
 * @author Adam Reese - amreese3
 * CIS175 - Fall 2023
 * Oct 6, 2023
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
import java.util.*;

// Main class
public final class StartProgram {
	private static RecipeHelper recipeHelper;
	private static IngredientHelper ingredientHelper;
	private static CategoryHelper categoryHelper;
	private static EntityManager entityManager;
	private static Scanner scanner = new Scanner(System.in);

	// Constructor to initialize the program
	public StartProgram(EntityManager entityManager) {
		recipeHelper = new RecipeHelper(entityManager);
		categoryHelper = new CategoryHelper(entityManager);
		ingredientHelper = new IngredientHelper(entityManager);
		StartProgram.entityManager = entityManager;
	}

	// Main method
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		// Prompt the user for their MySQL password
		System.out.print("Enter your MySQL password: ");

		// Get the user's password
		String password = scanner.nextLine();

		// Create a map to hold the database connection properties
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("javax.persistence.jdbc.password", password);

		// Create an EntityManagerFactory and EntityManager
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("RecipeBox", properties);
		EntityManager entityManager = emf.createEntityManager();

		// Create a new StartProgram object and run the menu
		try {
			StartProgram program = new StartProgram(entityManager);
			program.runMenu(scanner);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (entityManager != null && entityManager.isOpen()) {
				entityManager.close();
			}
			if (emf.isOpen()) {
				emf.close();
			}
			scanner.close();
		}
	}

	// Sub-menu for adding a recipe
	private static void addRecipe(EntityManager em) throws DatabaseAccessException {
		System.out.print("Enter a name for the recipe: ");
		String name = scanner.nextLine();
		int servings = getPositiveIntegerInput("Enter the number of servings: ");
		int preparationTime = getPositiveIntegerInput("Enter the preparation time in minutes: ");
		System.out.print("Enter a category: ");
		String categoryName = scanner.nextLine();

		// Check if the category already exists in the database and create it if needed
		Category foundCategory;
		foundCategory = categoryHelper.getCategoryByName(categoryName);

		if (foundCategory == null) {
			foundCategory = new Category(categoryName);
			categoryHelper.addCategory(foundCategory);
		}

		// Declare a List<Ingredient> to hold the ingredients
		List<Ingredient> ingredients = new ArrayList<>();

		// Declare a List<String> to hold the instructions
		List<String> instructions = new ArrayList<>();

		// Loop to collect ingredients from the user
		while (true) {
			System.out.print("Please add ingredients (or type 'done' to finish). Use plural form if needed.\n");
			String ingredientLine = scanner.nextLine();

			// Check if the user is done adding ingredients
			if (ingredientLine.equalsIgnoreCase("done")) {
				break;
			}

			// Ask the user for the quantity and unit type
			System.out.print("Enter the quantity and unit type (e.g., '1 cup of') for '" + ingredientLine + "': ");
			String ingredientQuantity = scanner.nextLine();

			// Combine the ingredient quantity and name into a single string
			String ingredientInfo = ingredientQuantity + " " + ingredientLine;

			// Create an Ingredient object and add it to the list
			Ingredient ingredient = new Ingredient(ingredientInfo);
			ingredients.add(ingredient);
		}

		// Loop to collect instructions from the user
		while (true) {
			System.out.println("Add an instruction (or type 'done' to finish): ");
			String instruction = scanner.nextLine();

			// Check if the user is done adding instructions
			if (instruction.equalsIgnoreCase("done")) {
				break;
			}
			// Add the instruction to the list
			instructions.add(instruction);
		}

		// Create a new recipe and insert it into the database
		Recipe newRecipe = new Recipe(name, servings, preparationTime, foundCategory, String.join("\n", instructions));

		// Insert the recipe into the database
		try {
			recipeHelper.insertRecipe(newRecipe, ingredients);
		} catch (DatabaseAccessException e) {
			System.out.println("Error: " + e.getMessage());
		}

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

			// Display the list of recipes
			for (int i = 0; i < recipes.size(); i++) {
				System.out.println((i + 1) + ": " + recipes.get(i).getName());
			}

			// Get the user's selection
			int choice = getPositiveIntegerInput("Enter the number of the recipe to edit: ");

			// Check if the user's selection is valid
			if (choice >= 1 && choice <= recipes.size()) {
				Recipe recipeToEdit = recipes.get(choice - 1);

				// Keep track of whether the user wants to continue editing
				boolean continueEditing = true;

				// Display the recipe to edit
				while (continueEditing) {
					System.out.println("  --- Edit Recipe ---");
					System.out.println("   1: Name");
					System.out.println("   2: Servings");
					System.out.println("   3: Preparation Time");
					System.out.println("   4: Category");
					System.out.println("   5: Ingredients");
					System.out.println("   6: Instructions");
					System.out.println("   7: Done Editing");
					int editChoice = getPositiveIntegerInput("*  Your selection: ");

					// Handle the user's selection
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
						// Edit category
						System.out.println("Current Category: " + recipeToEdit.getCategory().getName());
						System.out.print("Enter the new category: ");
						String newCategoryName = scanner.nextLine();
						Category newCategory = new Category(newCategoryName);
						recipeToEdit.setCategory(newCategory);
						System.out.println("Category updated to: " + newCategoryName);
						break;
					case 5:
						// Edit ingredients
						System.out.println("Current Ingredients:");
						List<Ingredient> currentIngredients = recipeToEdit.getIngredients();
						for (int i = 0; i < currentIngredients.size(); i++) {
							System.out.println((i + 1) + ": " + currentIngredients.get(i).getName());
						}
						System.out.println("Enter 'done' to finish editing ingredients.");

						// Loop to add ingredients
						List<Ingredient> newIngredients = new ArrayList<>();
						while (true) {
							String newIngredientName = scanner.nextLine();
							if (newIngredientName.equalsIgnoreCase("done")) {
								break;
							} else {
								Ingredient newIngredient = new Ingredient(newIngredientName);
								newIngredients.add(newIngredient);
								System.out.println("Ingredient added: " + newIngredientName);
							}
						}
						recipeToEdit.setIngredients(newIngredients);
						break;
					case 6:
						// Edit instructions
						System.out.println("Current Instructions:");
						System.out.println(recipeToEdit.getInstructions());
						System.out.println("Enter the new instructions (or 'done' to finish): ");

						List<String> newInstructionList = new ArrayList<>();
						while (true) {
							String newInstruction = scanner.nextLine();
							if (newInstruction.equalsIgnoreCase("done")) {
								break;
							}
							newInstructionList.add(newInstruction);
						}

						// Combine the instructions into a single string
						String newInstructions = String.join("\n", newInstructionList);
						recipeToEdit.setInstructions(newInstructions);
						System.out.println("Instructions updated.");
						break;
					case 7:
						// Exit editing loop
						continueEditing = false;
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

	// Helper method to delete a recipe
	private static void deleteRecipe() {
		try {
			// List recipes for deletion
			System.out.println("Recipes available for deletion:");
			List<Recipe> recipes = recipeHelper.showAllRecipes();
			for (int i = 0; i < recipes.size(); i++) {
				System.out.println((i + 1) + ": " + recipes.get(i).getName());
			}
			// Get the user's selection
			System.out.print("Enter the number of the recipe you want to delete: ");
			int recipeNumber = scanner.nextInt();
			scanner.nextLine(); // Consume the newline character

			// Check if the entered number is valid
			if (recipeNumber < 1 || recipeNumber > recipes.size()) {
				System.out.println("Invalid recipe number.");
				return;
			}

			// Get the recipe to delete
			Recipe recipeToDelete = recipes.get(recipeNumber - 1);

			// Ask for confirmation before deleting
			System.out.print("Are you sure you want to delete this recipe? (yes/no): ");
			String confirmation = scanner.nextLine();

			// Check if the user confirmed the deletion
			if ("yes".equalsIgnoreCase(confirmation)) {
				// If the user confirms, proceed to delete the category
				System.out.println("Recipe deleted successfully!");
			} else {
				System.out.println("Recipe deletion cancelled.");

				// Delete the ingredients associated with the recipe
				for (Ingredient ingredient : recipeToDelete.getIngredients()) {
					ingredientHelper.deleteIngredient(ingredient);
				}

				// Delete the recipe itself
				recipeHelper.deleteRecipe(recipeToDelete);
				System.out.println("Recipe deleted successfully!");
			}
		} catch (DatabaseAccessException e) {
			System.out.println("Error deleting recipe: " + e.getMessage());
		}
	}

	// Display the list of recipes
	private static void viewRecipeList() throws DatabaseAccessException {
		List<Recipe> allRecipes = recipeHelper.showAllRecipes();

		// Loop through the list of recipes
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
			System.out.println("Invalid choice. Please select a valid search method."); // Invalid selection
			break;
		}

		// Return to the previous menu
		handleSubMenu();
	}

	// Helper method to select a search method
	private static int selectSearchMethod() {
		System.out.println("  --- Search ---"); // Display the search options
		System.out.println("   1 - Search by Name");
		System.out.println("   2 - Search by Category");
		System.out.println("   3 - Search by Ingredient");
		System.out.println("   4 - Search by Serving Size");
		System.out.print("*  Your selection: "); // Prompt the user for input
		int searchBy = scanner.nextInt();
		scanner.nextLine();
		return searchBy;
	}

	// Helper methods to search for recipes by name
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

	// Helper methods to search for recipes by category
	private static void searchByCategory() throws DatabaseAccessException {
		System.out.print("Enter the category: ");
		String categoryName = scanner.nextLine();

		// Use CategoryHelper to get the Category object by its name
		Category categoryObj = categoryHelper.getCategoryByName(categoryName);

		// If the category exists, search for recipes in that category
		if (categoryObj != null) {
			List<Recipe> foundRecipes = recipeHelper.searchForRecipeByCategory(categoryObj.getName());

			// If recipes were found, display them. Otherwise, display an error message
			if (!foundRecipes.isEmpty()) {
				displayFoundRecipes(foundRecipes);
			} else {
				System.out.println("No recipes found in the specified category.");
			}
		} else {
			System.out.println("Category not found. Please enter a valid category.");
		}
	}

	// Helper methods to search for recipes by ingredient
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

	// Helper methods to search for recipes by serving size
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
	private static void manageIngredients(StartProgram program) throws DatabaseAccessException {
		boolean continueManaging = true;

		// Loop until the user chooses to return to the previous menu
		while (continueManaging) {
			System.out.println("  --- Manage Ingredients ---");
			System.out.println("   1 - Add Ingredient");
			System.out.println("   2 - Edit Ingredient");
			System.out.println("   3 - Delete Ingredient");
			System.out.println("   4 - List Ingredients");
			System.out.println("   5 - Return to Main Menu");
			System.out.print("*  Your selection: ");

			String input = scanner.nextLine(); // Get the user's input

			try {
				int selection = Integer.parseInt(input);

				switch (selection) {
				case 1:
					addIngredient(); // Add an ingredient
					break;
				case 2:
					program.editIngredient(); // Edit an ingredient
					break;
				case 3:
					program.deleteIngredient(); // Delete an ingredient
					break;
				case 4:
					viewAllIngredients(); // View all ingredients
					break;
				case 5:
					continueManaging = false; // Return to the previous menu
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

			// Check if the ingredient name is empty
			if (ingredientName.trim().isEmpty()) {
				System.out.println("Ingredient name cannot be empty.");
				return;
			}

			// Check if the ingredient already exists in the database
			Ingredient existingIngredient = ingredientHelper.findIngredientByName(ingredientName);

			// If the ingredient already exists, display an error message and return
			if (existingIngredient != null) {
				System.out.println("Ingredient already exists in the database.");
				return;
			}

			// Proceed to add the ingredient (whether it exists or not)
			Ingredient newIngredient = new Ingredient();
			newIngredient.setName(ingredientName);

			// Call the method to insert the new ingredient into the database
			ingredientHelper.insertIngredient(newIngredient);
			System.out.println("Ingredient added successfully!");

		} catch (DatabaseAccessException e) {
			System.out.println("Error adding ingredient: " + e.getMessage());
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
			System.out.println("  --- Manage Categories ---");
			System.out.println("   1 - Add Category");
			System.out.println("   2 - Edit Category");
			System.out.println("   3 - Delete Category");
			System.out.println("   4 - List Categories");
			System.out.println("   5 - Return to Main Menu");
			System.out.print("*  Your selection: ");

			// Get the user's input
			String input = scanner.nextLine();

			// Handle the user's selection
			try {
				int selection = Integer.parseInt(input); // Convert the input to an integer

				switch (selection) {
				case 1:
					addCategory(); // Add a category
					break;
				case 2:
					editCategory(); // Edit a category
					break;
				case 3:
					deleteCategory(); // Delete a category
					break;
				case 4:
					viewAllCategories(); // View all categories
					break;
				case 5:
					continueManaging = false; // Return to the previous menu
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

			// If the category already exists, display an error message and return
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

	// Helper method to edit a category
	private static void editCategory() throws DatabaseAccessException {
		List<Category> allCategories = categoryHelper.getAllCategories();
		Collections.sort(allCategories, Comparator.comparing(Category::getName));

		// Display a list of categories for the user to choose from
		System.out.println("Select a category to edit:");
		int index = 1;
		for (Category category : allCategories) {
			System.out.println(index + ": " + category.getName());
			index++;
		}

		// Get the user's selection
		String input = scanner.nextLine();
		int selection = Integer.parseInt(input) - 1; // Convert to zero-based index
		Category existingCategory = allCategories.get(selection);

		// Display the current name and prompt the user for a new name
		System.out.println("Current Name: " + existingCategory.getName());
		System.out.print("Enter the new name: ");
		String newName = scanner.nextLine();

		// Check if the new name is empty
		Category anotherCategory = categoryHelper.getCategoryByName(newName);
		if (anotherCategory != null && anotherCategory.getId() != existingCategory.getId()) {
			System.out.println("A category with this name already exists.");
			return;
		}

		// Update the category's name
		existingCategory.setName(newName);
		System.out.println("Name updated to: " + newName);

		// Update the category in the database
		categoryHelper.updateCategory(existingCategory);
		System.out.println("Category updated successfully!");
	}

	// Helper method to delete a category by name
	private static void deleteCategory() throws DatabaseAccessException {
		List<Category> allCategories = categoryHelper.getAllCategories();
		Collections.sort(allCategories, Comparator.comparing(Category::getName));

		// Display a list of categories for the user to choose from
		System.out.println("Select a category to delete:");
		int index = 1;
		for (Category category : allCategories) {
			System.out.println(index + ": " + category.getName());
			index++;
		}

		// Get the user's selection
		String input = scanner.nextLine();
		int selection = Integer.parseInt(input) - 1; // Convert to zero-based index
		Category categoryToDelete = allCategories.get(selection);

		// Ask for confirmation before deleting
		System.out.print("Are you sure you want to delete this category? (yes/no): ");
		String confirmation = scanner.nextLine();

		// Check if the user confirmed the deletion
		if ("yes".equalsIgnoreCase(confirmation)) {
			categoryHelper.deleteCategory(categoryToDelete);
			System.out.println("Category deleted successfully!");
		} else {
			System.out.println("Category deletion cancelled.");
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
			System.out.println("*  1 - Return to Main Menu");
			System.out.println("*  2 - Exit the program");
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

	// Helper method to handle the main menu
	public void runMenu(Scanner scanner) {
		boolean continueRunning = true;
		System.out.println("--- Welcome to our awesome recipe box! ---");

		// Main menu loop
		while (continueRunning) {
			System.out.println("*  Select an option:");
			System.out.println("   1 - Add a new recipe");
			System.out.println("   2 - Edit a recipe");
			System.out.println("   3 - Delete a recipe");
			System.out.println("   4 - Search for recipes");
			System.out.println("   5 - View recipe list");
			System.out.println("   6 - Manage ingredients");
			System.out.println("   7 - Manage categories");
			System.out.println("   8 - Quit");

			// Get the user's input
			System.out.print("*  Your selection: ");
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
					manageIngredients(this);
					break;
				case 7:
					// Initialize categoryHelper with the EntityManager
					categoryHelper = new CategoryHelper(entityManager);
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

	// Helper method to check if an ingredient is used in any recipes
	private boolean isIngredientUsedInRecipes(String ingredientName) throws DatabaseAccessException {
		List<Recipe> recipes = recipeHelper.showAllRecipes();
		for (Recipe recipe : recipes) {
			List<Ingredient> ingredients = recipe.getIngredients();
			for (Ingredient ingredient : ingredients) {
				if (ingredient.getName().equals(ingredientName)) {
					return true;
				}
			}
		}
		return false;
	}

	// Helper method to edit an ingredient
	private void editIngredient() throws DatabaseAccessException {
		List<Ingredient> allIngredients = ingredientHelper.showAllIngredients();
		Collections.sort(allIngredients, Comparator.comparing(Ingredient::getName));

		// Display a list of ingredients for the user to choose from
		System.out.println("Select an ingredient to edit:");
		int index = 1;
		for (Ingredient ingredient : allIngredients) {
			System.out.println(index + ": " + ingredient.getName());
			index++;
		}

		// Get the user's selection
		String input = scanner.nextLine();
		int selection = Integer.parseInt(input) - 1; // Convert to zero-based index
		Ingredient existingIngredient = allIngredients.get(selection);

		// Display the current name and prompt the user for a new name
		System.out.println("Current Name: " + existingIngredient.getName());
		System.out.print("Enter the new name: ");
		String newName = scanner.nextLine();

		// Check if the new name is empty
		Ingredient anotherIngredient = ingredientHelper.findIngredientByName(newName);
		if (anotherIngredient != null && anotherIngredient.getId() != existingIngredient.getId()) {
			System.out.println("An ingredient with this name already exists.");
			return;
		}

		// Update the ingredient's name
		existingIngredient.setName(newName);
		System.out.println("Name updated to: " + newName);

		// Update the ingredient in the database
		ingredientHelper.updateIngredient(existingIngredient);
		System.out.println("Ingredient updated successfully!");
	}

	// Helper method to delete an ingredient by name
	private void deleteIngredient() throws DatabaseAccessException {
		List<Ingredient> allIngredients = ingredientHelper.showAllIngredients();
		Collections.sort(allIngredients, Comparator.comparing(Ingredient::getName));

		// Display a list of ingredients for the user to choose from
		System.out.println("Select an ingredient to delete:");
		int index = 1;
		for (Ingredient ingredient : allIngredients) {
			System.out.println(index + ": " + ingredient.getName());
			index++;
		}

		// Get the user's selection
		String input = scanner.nextLine();
		int selection = Integer.parseInt(input) - 1; // Convert to zero-based index
		Ingredient ingredientToDelete = allIngredients.get(selection);

		// Ask for confirmation before deleting
		System.out.print("Are you sure you want to delete this ingredient? (yes/no): ");
		String confirmation = scanner.nextLine();

		// Check if the user confirmed the deletion
		if ("yes".equalsIgnoreCase(confirmation)) {
			if (isIngredientUsedInRecipes(ingredientToDelete.getName())) {
				System.out.println("Ingredient is used in one or more recipes and cannot be deleted.");
				return;
			}

			// If the user confirms, proceed to delete the ingredient
			ingredientHelper.deleteIngredient(ingredientToDelete);
			System.out.println("Ingredient deleted successfully!");
		} else {
			System.out.println("Ingredient deletion cancelled.");
		}
	}
}