
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

	private static void addARecipe() {
		System.out.print("Enter a name: ");
		String name = in.nextLine();

		// Validate servings (positive number input)
		int servings = -1;
		while (servings < 0) {
			System.out.print("Enter the number of servings: ");
			try {
				servings = Integer.parseInt(in.nextLine());
				if (servings < 0) {
					System.out.println("Please enter a positive number for servings.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a number for servings.");
			}
		}

		// Validate preparation time (positive number input)
		int preparationTime = -1;
		while (preparationTime < 0) {
			System.out.print("Enter the preparation time in minutes: ");
			try {
				preparationTime = Integer.parseInt(in.nextLine());
				if (preparationTime < 0) {
					System.out.println("Please enter a positive number for preparation time.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a number for preparation time.");
			}
		}

		System.out.print("Enter a category (e.g., dessert, main course, etc): ");
		String category = in.nextLine();

		// Validate category if needed.

		Category newCat = new Category(category);
		Recipe toAdd = new Recipe(name, servings, preparationTime, newCat);
		rh.insertRecipe(toAdd);
	}



	private static void deleteARecipe() {
		// Retrieve a list of recipes that match the entered name
		List<Recipe> deleteOptions = searchByRecipeName();

		// If no recipes are found, inform the user and return to the main menu
		if (deleteOptions.isEmpty()) {
			System.out.println("No recipes found. Returning to the main menu.");
			return;
		}

		System.out.println("Select a recipe to delete:");

		// Display the list of recipes and allow the user to choose one for deletion
		for (int i = 0; i < deleteOptions.size(); i++) {
			Recipe recipe = deleteOptions.get(i);
			System.out.println((i + 1) + ": " + recipe.getName());
		}

		int choice = -1;
		while (choice < 1 || choice > deleteOptions.size()) {
			// Prompt the user to enter the number of the recipe they want to delete
			System.out.print("Enter the number of the recipe you want to delete: ");
			try {
				choice = Integer.parseInt(in.nextLine());

				// Validate the user's choice to ensure it's within a valid range
				if (choice < 1 || choice > deleteOptions.size()) {
					System.out.println("Invalid choice. Please enter a valid number.");
				}
			} catch (NumberFormatException e) {
				// Handle cases where the user enters non-numeric input
				System.out.println("Invalid input. Please enter a valid number.");
			}
		}

		// Get the recipe to delete based on the user's choice
		Recipe toDelete = deleteOptions.get(choice - 1);

		if (confirmDelete(toDelete)) {
			// If the user confirms the deletion, remove the recipe from the database
			rh.deleteRecipe(toDelete);
			System.out.println("Recipe deleted successfully.");
		} else {
			// If the user cancels the deletion, inform them and return to the main menu
			System.out.println("Deletion canceled. Returning to the main menu.");
		}
	}

	/**
	 * Search for recipes based on user-defined criteria such as name, category, ingredient, or serving size.
	 */
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

	/**
	 * Search for recipes by name.
	 */
	private static void searchByName() {
	    System.out.print("Enter the recipe's name: ");
	    String recipeName = in.nextLine();
	    List<Recipe> foundRecipes = rh.searchForRecipeByTitle(recipeName);

	    if (!foundRecipes.isEmpty()) {
	        displayFoundRecipes(foundRecipes);
	    } else {
	        System.out.println("No recipes found with the specified name.");
	    }
	}

	/**
	 * Search for recipes by category.
	 */
	private static void searchByCategory() {
	    System.out.print("Enter the category: ");
	    String category = in.nextLine();
	    // Implement the search by category logic here
	    // Example: List<Recipe> foundRecipes = rh.searchForRecipeByCategory(category);

	    // Check if any recipes were found and display them
	    // if (!foundRecipes.isEmpty()) {
	    //     displayFoundRecipes(foundRecipes);
	    // } else {
	    //     System.out.println("No recipes found in the specified category.");
	    // }
	}

	/**
	 * Search for recipes by ingredient.
	 */
	private static void searchByIngredient() {
	    System.out.print("Enter an ingredient: ");
	    String ingredient = in.nextLine();
	    // Implement the search by ingredient logic here
	    // Example: List<Recipe> foundRecipes = rh.searchForRecipeByIngredient(ingredient);

	    // Check if any recipes were found and display them
	    // if (!foundRecipes.isEmpty()) {
	    //     displayFoundRecipes(foundRecipes);
	    // } else {
	    //     System.out.println("No recipes found with the specified ingredient.");
	    // }
	}

	/**
	 * Search for recipes by serving size.
	 */
	private static void searchByServingSize() {
	    System.out.print("Enter the serving size: ");
	    int servingSize = in.nextInt();
	    in.nextLine(); // Consume the newline character
	    // Implement the search by serving size logic here
	    // Example: List<Recipe> foundRecipes = rh.searchForRecipeByServingSize(servingSize);

	    // Check if any recipes were found and display them
	    // if (!foundRecipes.isEmpty()) {
	    //     displayFoundRecipes(foundRecipes);
	    // } else {
	    //     System.out.println("No recipes found with the specified serving size.");
	    // }
	}

	/**
	 * Display a list of found recipes.
	 *
	 * @param recipes The list of found recipes to display.
	 */
	private static void displayFoundRecipes(List<Recipe> recipes) {
	    System.out.println("Found Recipes:");
	    for (Recipe recipe : recipes) {
	        System.out.println(recipe.toString());
	    }
	}


	/**
	 * @param toDelete
	 * @return
	 */
	private static boolean confirmDelete(Recipe toDelete) {
		// TODO Auto-generated method stub

		System.out.println("Please confirm this is the recipe you would like to delete: ");
		System.out.println("Recipe #" + toDelete.getId() + " : " + toDelete.getName());
		System.out.println("1: Yes");
		System.out.println("2: No");
		int confirmationInt = in.nextInt();
		if (confirmationInt == 1) {
			System.out.println("Deleting Recipe #" + toDelete.getId() + " : " + toDelete.getName());
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param deleteOptions
	 * @return
	 */
	private static Recipe pickOne(List<Recipe> deleteOptions) {
		// TODO Auto-generated method stub

		if (verifyRecipe(deleteOptions)) {
			System.out.println("Found Results.");
			for (Recipe r : deleteOptions) {
				System.out.println("Recipe #" + r.getId() + " : " + r.getName());
			}
			System.out.print("Which Recipe #: ");
			int idToEdit = in.nextInt();
			Recipe toEdit = rh.searchForRecipeById(idToEdit);
			return toEdit;
		} else {
			return null;
		}

	}

	/**
	 * @param deleteOptions
	 * @return
	 */
	private static boolean verifyRecipe(List<Recipe> deleteOptions) {
		// TODO Auto-generated method stub
		if (!deleteOptions.isEmpty()) {
			return true;
		} else {
			System.out.println("---- No results found.");
			System.out.println("Please try again.");
			return false;
		}
	}

	/**
	 * @param wayToSearch
	 * @return
	 */
	/**
	 * private static List<Recipe> searchForRecipe(int wayToSearch) { // TODO
	 * Auto-generated method stub List<Recipe> foundRecipes; if (wayToSearch == 1) {
	 * System.out.print("Enter the recipe's title: "); String recipeTitle =
	 * in.nextLine(); foundRecipes = rh.searchForRecipeByTitle(recipeTitle);
	 * 
	 * } else { System.out.print("Enter the type: "); String recipeType =
	 * in.nextLine(); foundRecipes = rh.searchForRecipeByType(recipeType);
	 * 
	 * } return foundRecipes; }
	 */
	/**
	 * private static void editARecipe() { //int wayToSearch = selectSearchMethod();
	 * //List<Recipe> editOptions = searchForRecipe(wayToSearch);
	 * 
	 * List<Recipe> editOptions = searchByRecipeName(); Recipe toEdit =
	 * pickOne(editOptions); if(toEdit == null) { runMenu(); } else {
	 * System.out.println("Retrieved " + toEdit.getName() + ", a " +
	 * toEdit.getType()); System.out.println("1 : Update Title");
	 * System.out.println("2 : Update Type"); int update = in.nextInt();
	 * in.nextLine();
	 * 
	 * if (update == 1) { System.out.print("New Title: "); String newTitle =
	 * in.nextLine(); toEdit.setTitle(newTitle); } else if (update == 2) {
	 * System.out.print("New Type: "); String newType = in.nextLine();
	 * toEdit.setType(newType); }
	 * 
	 * rh.updateRecipe(toEdit); }
	 * 
	 * }
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		runMenu();

	}

	public static void runMenu() {
		boolean goAgain = true;
		System.out.println("--- Welcome to our awesome recipe box! ---");
		while (goAgain) {
			System.out.println("*  Select an option:");
			System.out.println("*  1 -- Add a recipe");
			System.out.println("*  2 -- Delete a recipe");
			System.out.println("*  3 -- View the list");
			System.out.println("*  4 -- Exit the awesome program");
			System.out.print("*  Your selection: ");
			int selection = in.nextInt();
			in.nextLine();

			if (selection == 1) {
				addARecipe();
			} else if (selection == 2) {
				deleteARecipe();
			} else if (selection == 3) {
				viewTheList();
			} else {
				rh.cleanUp();
				System.out.println("   Goodbye!   ");
				goAgain = false;
			}

		}

	}

	private static void viewTheList() {
		List<Recipe> allRecipes = rh.showAllRecipes();
		for (Recipe singleRecipe : allRecipes) {
			System.out.println(singleRecipe.toString());
		}

	}

}