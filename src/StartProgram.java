
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

import java.util.List;
import java.util.Scanner;

import controller.RecipeHelper;
import model.Category;
import model.Recipe;

public class StartProgram {

	static Scanner in = new Scanner(System.in);
	static RecipeHelper rh = new RecipeHelper();

	public static void main(String[] args) {
		runMenu();
	}

	public static void runMenu() {
		boolean goAgain = true;
		System.out.println("--- Welcome to our awesome recipe box! ---");

		while (goAgain) {
			System.out.println("*  Select an option:");
			System.out.println("*  1 -- Add a recipe");
			System.out.println("*  2 -- Delete a recipe");
			System.out.println("*  3 -- Search for recipes");
			System.out.println("*  4 -- View the list");
			System.out.println("*  5 -- Exit the awesome program");
			System.out.print("*  Your selection: ");

			int selection = in.nextInt();
			in.nextLine();

			switch (selection) {
			case 1:
				addARecipe();
				break;
			case 2:
				deleteARecipe();
				break;
			case 3:
				searchRecipes();
				break;
			case 4:
				viewTheList();
				break;
			default:
				rh.cleanUp();
				System.out.println("   Goodbye!   ");
				goAgain = false;
				break;
			}
		}
	}

	// Function to add a new recipe
	private static void addARecipe() {
		System.out.print("Enter a name: ");
		String name = in.nextLine();

		// Validate and get the number of servings
		int servings = getPositiveIntegerInput("Enter the number of servings: ");
		// Validate and get the preparation time in minutes
		int preparationTime = getPositiveIntegerInput("Enter the preparation time in minutes: ");

		System.out.print("Enter a category (e.g., dessert, main course, etc): ");
		String category = in.nextLine();

		Category newCat = new Category(category);
		Recipe toAdd = new Recipe(name, servings, preparationTime, newCat);
		rh.insertRecipe(toAdd);
	}

	// Function to delete a recipe
	private static void deleteARecipe() {
		List<Recipe> deleteOptions = searchByRecipeName();

		if (deleteOptions.isEmpty()) {
			System.out.println("No recipes found. Returning to the main menu.");
			return;
		}

		System.out.println("Select a recipe to delete:");

		for (int i = 0; i < deleteOptions.size(); i++) {
			Recipe recipe = deleteOptions.get(i);
			System.out.println((i + 1) + ": " + recipe.getName());
		}

		int choice = -1;
		while (choice < 1 || choice > deleteOptions.size()) {
			System.out.print("Enter the number of the recipe you want to delete: ");
			try {
				choice = Integer.parseInt(in.nextLine());
				if (choice < 1 || choice > deleteOptions.size()) {
					System.out.println("Invalid choice. Please enter a valid number.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a valid number.");
			}
		}

		Recipe toDelete = deleteOptions.get(choice - 1);

		if (confirmDelete(toDelete)) {
			rh.deleteRecipe(toDelete);
			System.out.println("Recipe deleted successfully.");
		} else {
			System.out.println("Deletion canceled. Returning to the main menu.");
		}
	}

	// Function to view the list of all recipes
	private static void viewTheList() {
		List<Recipe> allRecipes = rh.showAllRecipes();
		for (Recipe singleRecipe : allRecipes) {
			System.out.println(singleRecipe.toString());
		}
	}

	// Function to search for recipes by name
	private static List<Recipe> searchByRecipeName() {
		System.out.print("Enter the recipe's name: ");
		String recipeName = in.nextLine();
		List<Recipe> foundRecipes = rh.searchForRecipeByTitle(recipeName);

		if (!foundRecipes.isEmpty()) {
			displayFoundRecipes(foundRecipes);
		} else {
			System.out.println("No recipes found with the specified name.");
		}
		return foundRecipes;
	}

	// Function to display a list of found recipes
	private static void displayFoundRecipes(List<Recipe> recipes) {
		System.out.println("Found Recipes:");
		for (Recipe recipe : recipes) {
			System.out.println(recipe.toString());
		}
	}

	// Function to confirm recipe deletion
	private static boolean confirmDelete(Recipe toDelete) {
		System.out.println("Please confirm this is the recipe you would like to delete: ");
		System.out.println("Recipe #" + toDelete.getId() + " : " + toDelete.getName());
		System.out.println("1: Yes");
		System.out.println("2: No");
		int confirmationInt = in.nextInt();
		return confirmationInt == 1;
	}

	// Function to get positive integer input with validation
	private static int getPositiveIntegerInput(String message) {
		int value = -1;
		while (value < 0) {
			System.out.print(message);
			try {
				value = Integer.parseInt(in.nextLine());
				if (value < 0) {
					System.out.println("Please enter a positive number.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a number.");
			}
		}
		return value;
	}

	// Function to search for recipes based on user-defined criteria
	private static void searchRecipes() {
		int searchBy = selectSearchMethod();

		switch (searchBy) {
		case 1:
			searchByName();
			break;
		case 2:
			searchByCategory();
			break;
		case 3:
			searchByIngredient();
			break;
		case 4:
			searchByServingSize();
			break;
		default:
			System.out.println("Invalid choice. Please select a valid search method.");
			break;
		}
	}

	// Function to select the search method
	private static int selectSearchMethod() {
		System.out.println("How would you like to search? ");
		System.out.println("1 : Search by Name");
		System.out.println("2 : Search by Category");
		System.out.println("3 : Search by Ingredient");
		System.out.println("4 : Search by Serving Size");
		int searchBy = in.nextInt();
		in.nextLine();
		return searchBy;
	}

	// Function to search for recipes by name
	private static void searchByName() {
		System.out.print("Enter the recipe's name: ");
		String recipeName = in.nextLine();
		List<Recipe> foundRecipes = rh.searchForRecipeByTitle(recipeName);

		// Check if any recipes were found and display them
		if (!foundRecipes.isEmpty()) {
			displayFoundRecipes(foundRecipes);
		} else {
			System.out.println("No recipes found with the specified name.");
		}
	}

	// Function to search for recipes by category
	private static void searchByCategory() {
		System.out.print("Enter the category: ");
		String category = in.nextLine();
		List<Recipe> foundRecipes = rh.searchForRecipeByCategory(category);

		// Check if any recipes were found and display them
		if (!foundRecipes.isEmpty()) {
			displayFoundRecipes(foundRecipes);
		} else {
			System.out.println("No recipes found in the specified category.");
		}
	}

	// Function to search for recipes by ingredient
	private static void searchByIngredient() {
		System.out.print("Enter an ingredient: ");
		String ingredient = in.nextLine();
		List<Recipe> foundRecipes = rh.searchForRecipeByIngredient(ingredient);

		// Check if any recipes were found and display them
		if (!foundRecipes.isEmpty()) {
			displayFoundRecipes(foundRecipes);
		} else {
			System.out.println("No recipes found with the specified ingredient.");
		}
	}

	// Function to search for recipes by serving size
	private static void searchByServingSize() {
		System.out.print("Enter the serving size: ");
		int servingSize = in.nextInt();
		in.nextLine(); // Consume the newline character
		List<Recipe> foundRecipes = rh.searchForRecipeByServingSize(servingSize);

		// Check if any recipes were found and display them
		if (!foundRecipes.isEmpty()) {
			displayFoundRecipes(foundRecipes);
		} else {
			System.out.println("No recipes found with the specified serving size.");
		}
	}

}
