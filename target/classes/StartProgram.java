

import java.util.List;
import java.util.Scanner;

import controller.RecipeHelper;
import model.Recipe;

public class StartProgram {

		static Scanner in = new Scanner(System.in);
		static RecipeHelper rh = new RecipeHelper();

		private static void addARecipe() {
			// TODO Auto-generated method stub
			System.out.print("Enter a title: ");
			String title = in.nextLine();
			System.out.print("Enter a tyep (ex: dessert, main course, etc): ");
			String type = in.nextLine();
			Recipe toAdd = new Recipe(title, type);
			rh.insertRecipe(toAdd);

		}
		
		private static int selectSearchMethod() {
			System.out.println("How would you like to search? ");
			System.out.println("1 : Search by Title");
			System.out.println("2 : Search by Type");
			int searchBy = in.nextInt();
			in.nextLine();
			return searchBy;
		}

		private static void deleteARecipe() {
			// TODO Auto-generated method stub
			
			int wayToSearch = selectSearchMethod();
			List<Recipe> deleteOptions = searchForRecipe(wayToSearch);
			Recipe toDelete = pickOne(deleteOptions);
			if(toDelete == null) {
				runMenu();
			} else {
				if(confirmDelete(toDelete)) {
					rh.deleteRecipe(toDelete);
				} else {
					System.out.println("Results not confirmed. No action taken. Please try again.");
					runMenu();
				}
			}			

		}

		/**
		 * @param toDelete
		 * @return
		 */
		private static boolean confirmDelete(Recipe toDelete) {
			// TODO Auto-generated method stub
			
			System.out.println("Please confirm this is the recipe you would like to delete: ");
			System.out.println("Recipe #" + toDelete.getId() + " : " + toDelete.getTitle() + ", a " + toDelete.getType());
			System.out.println("1: Yes");
			System.out.println("2: No");
			int confirmationInt = in.nextInt();
			if(confirmationInt == 1) {
				System.out.println("Deleting Recipe #" + toDelete.getId() + " : " + toDelete.getTitle() + ", a " + toDelete.getType());
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
			
			if(verifyRecipe(deleteOptions)) {
				System.out.println("Found Results.");
				for (Recipe r : deleteOptions) {
					System.out.println("Recipe #" + r.getId() + " : " + r.getTitle() + ", a " + r.getType());
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
			if(!deleteOptions.isEmpty()) {
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
		private static List<Recipe> searchForRecipe(int wayToSearch) {
			// TODO Auto-generated method stub
			List<Recipe> foundRecipes;
			if (wayToSearch == 1) {
				System.out.print("Enter the recipe's title: ");
				String recipeTitle = in.nextLine();
				foundRecipes = rh.searchForRecipeByTitle(recipeTitle);
				
			} else {
				System.out.print("Enter the type: ");
				String recipeType = in.nextLine();
				foundRecipes = rh.searchForRecipeByType(recipeType);

			}
			return foundRecipes;
		}

		private static void editARecipe() {
			// TODO Auto-generated method stub
			
			int wayToSearch = selectSearchMethod();
			List<Recipe> editOptions = searchForRecipe(wayToSearch);
			Recipe toEdit = pickOne(editOptions);
			if(toEdit == null) {
				runMenu();
			} else {			
				System.out.println("Retrieved " + toEdit.getTitle() + ", a " + toEdit.getType());
				System.out.println("1 : Update Title");
				System.out.println("2 : Update Type");
				int update = in.nextInt();
				in.nextLine();

				if (update == 1) {
					System.out.print("New Title: ");
					String newTitle = in.nextLine();
					toEdit.setTitle(newTitle);
				} else if (update == 2) {
					System.out.print("New Type: ");
					String newType = in.nextLine();
					toEdit.setType(newType);
				} 

				rh.updateRecipe(toEdit);
			}

		} 
			


		

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
				System.out.println("*  2 -- Edit a recipe");
				System.out.println("*  3 -- Delete a recipe");
				System.out.println("*  4 -- View the list");
				System.out.println("*  5 -- Exit the awesome program");
				System.out.print("*  Your selection: ");
				int selection = in.nextInt();
				in.nextLine();

				if (selection == 1) {
					addARecipe();
				} else if (selection == 2) {
					editARecipe();
				} else if (selection == 3) {
					deleteARecipe();
				} else if (selection == 4) {
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
			for(Recipe singleRecipe : allRecipes) {
				System.out.println(singleRecipe.toString());
			}
			

		}

	}