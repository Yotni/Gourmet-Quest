package com.ratatouille;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import com.ratatouille.Models.*;

public class App {
    // private static File FilePathRecipes = FileHandler.FilePathRecipes;
    private static File FilePathSaving = FileHandler.FilePathSaving;
    private static File FilePathToDoList = FileHandler.FilePathToDoList;

    public static final Scanner scanner = new Scanner(System.in);
    public static final Produced produced = new Produced();

    public static final MeatProduct meatProduct = new MeatProduct();
    public static final HerbSpices herbSpices = new HerbSpices();
    public static final Dairy dairy = new Dairy();
    public static final OilCondiments oilCondiments = new OilCondiments();
    public static final Pantry pantry = new Pantry();

    public static FileHandler fileHandler = new FileHandler(produced, meatProduct, herbSpices, dairy, oilCondiments, pantry);
    public static GourmetQuest gourmetQuest = new GourmetQuest(produced, meatProduct, herbSpices, dairy, oilCondiments, pantry, fileHandler);

    public static void main( String[] args ){

        while(true){
            System.out.println("Gourmet Quest: Search A food that will change your Life!");
            System.out.println("[1] Search recipes");
            System.out.println("[2] Saved Recipes");
            System.out.println("[3] To do List");
            System.out.println("[4] What can I cook? [Random Recipes]");
            System.out.println("[5] Exit");
            System.out.print("Choose Your Path: ");

            String FoodPath = scanner.nextLine();

            switch (FoodPath.toLowerCase()) {
                case "1":
                    searchRecipes();
                    break;
                case "2":
                    SavedRecipes();
                    break;
                case "3":
                    //toDoList();
                    break;
                case "4":
                    //RandomRecipes();
                    break;
                case "x":
                    System.out.println("\nUntil we meet again traveler\n");
                    programmedClosed();
                default:
                    invalidInput();
                    continue;

            }
            clearScreen();
        }
    }

    private static void searchRecipes() {
        clearScreen();
        searchRecipes : while (true) {
            System.out.println("How do you want to search[X to exit]:");
            System.out.println("[1] Search through name");
            System.out.println("[2] Search through filtering");
            System.out.print("Choose Your Path: ");

            String SearchPath = scanner.nextLine();
                if (SearchPath.equalsIgnoreCase("X")) {
                    break searchRecipes;
                }
                if (SearchPath.equalsIgnoreCase("1")) {
                    gourmetQuest.SearchName();
                    clearScreen();
                }
                else if (SearchPath.equalsIgnoreCase("2")) {
                    gourmetQuest.filterIngredients();
                    clearScreen();
                }
                else {
                    invalidInput();
                }            
        }

    }

   
    private static void SavedRecipes() {

        clearScreen();
        savedrecipes : while (true) {
            System.out.println("Recipes You have saved [Type TD to put it into to do list]: ");

            if(!FilePathSaving.exists()) {
                System.out.println("Could not find file path for Saving!");
                break savedrecipes;
            }

            List<Recipes> listOfResults = oop.gourmetquest.FileHandler.FileHandler.deserialize(FilePathSaving);

            if (listOfResults.isEmpty()) {
                System.out.println("Your saved recipes is empty!");
            }
            else {
                FileHandler.outputRecipes(listOfResults);

            }

            System.out.println("-".repeat(30));
                System.out.print("Choose a recipe to view[X to Exit]: ");
                String PickARecipe = scanner.nextLine();

                if (PickARecipe.equalsIgnoreCase("X")) {
                    break savedrecipes;
                }
                if (listOfResults.isEmpty()) {
                    System.out.println("-".repeat(150));
                    System.out.println("You cannot choose if you dont have a choice at all!");
                    System.out.println("-".repeat(150));
                }
                else if (PickARecipe.equalsIgnoreCase("TD")) {
                    // gourmetQuest.SearchName();
                }
                else {
                    Recipes selectedItem = listOfResults.get(Integer.parseInt(PickARecipe) - 1);
                    System.out.println("-".repeat(150));
                    selectedItem.DisplayFood(); 
                }            

        }

    }

    // private static void RandomRecipes () {
    //     System.out.println("Choose a meal type: ");
    //     System.out.println("[1] Breakfast");
    //     System.out.println("[2] Lunch");
    //     System.out.println("[4] Dessert ");
    //     System.out.println("[5] Appetizer");
    //     System.out.print("Choose Your Path: ");

    //     int pickrandomRecipe = scanner.nextInt();

    // }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J\033[3J");
        System.out.flush();
    }
    //this is just for invalid input wihtout try catch.
    //method overriding
    public static void handleError() {
        clearScreen();
        System.out.println("Invalid input. Please enter a valid input!");
        System.out.println("-".repeat(150));
    }

    public static void handleError(String ErrorType) {

        if (ErrorType.equals("notNumber")) {
            clearScreen();
            System.out.println("Input Error: Please only enter a number!");
            System.out.println("-".repeat(150));
        }
        else if (ErrorType.equals("OutOfRange")) {
            clearScreen();
            System.out.println("Input Error: Recipe number is out of range!");
            System.out.println("-".repeat(150));
        }
        else if (ErrorType.equals("FileNotExist")) {
            clearScreen();
            System.out.println(" File Error: A file is missing!");
            System.out.println("-".repeat(150));
        } 
        else {
            clearScreen();
            System.out.println("Invalid input. Please enter a valid input!");
            System.out.println("-".repeat(150));
        }
    }

    public static void handleException(Exception e) {

        if (e instanceof NumberFormatException) {
            clearScreen();
            System.out.println("Error: Please enter numbers only!");
            System.out.println("-".repeat(150));
        }
        else if (e instanceof IndexOutOfBoundsException) {
            clearScreen();
            System.out.println("Error: Recipe number is out of range!");
            System.out.println("-".repeat(150));
        }
        else if (e instanceof NullPointerException) {
            clearScreen();
            System.out.println("Error: Something is null!");
            System.out.println("-".repeat(150));
        }
        else if (e instanceof InputMismatchException) {
            clearScreen();
            System.out.println("Invalid input. Please enter a valid number!");
            System.out.println("-".repeat(150));
        }else if (e instanceof FileNotFoundException ) {
            clearScreen();
            System.out.println("File Exception: Unable to access the required file");
            System.out.println("-".repeat(150));
        }
        else if (e instanceof IOException) {
            clearScreen();
            System.out.println("File Exception: Unable to read or save the file");
            System.out.println("-".repeat(150));
        }
        else {
            clearScreen();
            System.out.println("Error: " + e.getMessage());
            System.out.println("-".repeat(150));
        }

    }

    public static void programmedClosed(){
        System.exit(0);
    }
}
