package com.ratatouille;
import com.ratatouille.Models.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
 
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class FileHandler {

    public static final Scanner scanner = new Scanner(System.in);

    public static final File FilePathRecipes = new File("gourmetquest\\src\\main\\Data\\Breakfast.json");
    public static final File FilePathSaving = new File("gourmetquest\\src\\main\\Data\\SavedRecipes.json");
    public static final File FilePathToDoList = new File("gourmetquest\\src\\main\\Data\\RecipeToDoList.json");

    private static final ObjectMapper mapper = new ObjectMapper();

    private Produced produced;
    private MeatProduct meatProduct;
    private HerbSpices herbSpices;
    private Dairy dairy;
    private OilCondiments oilCondiments;
    private Pantry pantry;


    public FileHandler (Produced produced, MeatProduct meatProduct, HerbSpices herbSpices, Dairy dairy, OilCondiments oilCondiments, Pantry pantry) {
        this.produced = produced;
        this.meatProduct = meatProduct;
        this.herbSpices = herbSpices;
        this.dairy = dairy;
        this.oilCondiments = oilCondiments;
        this.pantry = pantry;
    }

    public static List<Recipes> deserialize(File Filepath) throws IOException{
        if (Filepath == null || !Filepath.exists()) {
            throw new IOException();
        }

        return mapper.readValue(Filepath, new TypeReference<List<Recipes>>() {});
    }

    public static void serialize(Recipes pickRecipe, File FilePath) throws IOException {
        if (!FilePath.exists()) {
             Files.writeString(FilePath.toPath(), "[]");
        }
 
        List<Recipes> recipes = deserialize(FilePath);

        boolean isSaved = false;
        for (Recipes Recipe : recipes) {
            if(Recipe.getName().equalsIgnoreCase(pickRecipe.getName())) {
                isSaved = true;
                break;
            }
        }
        if (!isSaved) {
            recipes.add(pickRecipe);
            App.clearScreen();
            System.out.println("Successfully added: " + pickRecipe.getName());
            System.out.println("-".repeat(150));
        }
        else {
            if(FilePath.equals(FilePathSaving)) {
                App.clearScreen();
                System.out.println( "The recipe " + pickRecipe.getName() + " was already saved");
            }
            else if (FilePath.equals(FilePathToDoList)) {
                App.clearScreen();
                System.out.println( "The recipe " + pickRecipe.getName() + " is already on your to do list.");
            }

            System.out.println("-".repeat(150));


        }

        mapper.writerWithDefaultPrettyPrinter().writeValue(FilePath, recipes);

    }

    public static void removedARecipe(Recipes selectedItem, List<Recipes> listOfResults, String CallerType) throws IOException{
        if (CallerType.equalsIgnoreCase("SavedRecipes")) {

            listOfResults.remove(selectedItem);

            mapper.writerWithDefaultPrettyPrinter().writeValue(FilePathSaving, listOfResults);

            App.clearScreen();
            System.out.println(selectedItem.getName() + " was removed from saved recipes");

        }
        else if (CallerType.equalsIgnoreCase("toDoList")) {
            listOfResults.remove(selectedItem);

            mapper.writerWithDefaultPrettyPrinter().writeValue(FilePathToDoList, listOfResults);

            App.clearScreen();
            System.out.println(selectedItem.getName() + " was removed from To do list");
        }
        System.out.println("-".repeat(150));
    }

    public static List<Recipes> searchByName (String search) throws IOException{

        List<Recipes> recipes = deserialize(FilePathRecipes);

        List<Recipes> listOfResults = new ArrayList<>();

        if(search.isEmpty()) {
            return listOfResults;
        }
        if (recipes != null) {
            for (Recipes recipe : recipes){
                if (recipe.getName().toLowerCase().contains(search.toLowerCase())){
                    listOfResults.add(recipe);
                }
            }
        }
        return listOfResults;
    }
    // filtering every items
    public static  List<Recipes> filteringAll (List<String> allIngredient) throws IOException {
        List<Recipes> recipes = deserialize(FilePathRecipes);

        List<Recipes> listOfResults = new ArrayList<>();

        for (Recipes recipe : recipes) {
            boolean allRecipeIngredientsFound = true;
            for (String recipeIngredient : recipe.getIngredients()) {
                boolean found = false;
                for (String ingredient : allIngredient) {
                    if (recipeIngredient.toLowerCase().contains(ingredient.toLowerCase())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    allRecipeIngredientsFound = false;
                    break;
                }
            }

            if (allRecipeIngredientsFound) {
                listOfResults.add(recipe);
            }
        }
        return listOfResults;
    }
    
    //Output saved or data recipes
    public static void outputRecipes (List<Recipes> listOfResults) {
        int count = 1;
            for(Recipes recipe : listOfResults){
                System.out.println("-".repeat(150));
                System.out.println(count + " -- " + recipe.getName());
                count++;
            } 
    }

    //filtering thorgh ingredients At least one
    public static  List<Recipes> filteringAtLeast1 (List<String> anyIngredient) throws IOException {
        List<Recipes> recipes = deserialize(FilePathRecipes);

        List<Recipes> listOfResults = new ArrayList<>();

        for (Recipes recipe : recipes ) {
            for (String RecipeIngredient : recipe.getIngredients()) {
                for (String ingredient : anyIngredient) {
                    if(RecipeIngredient.toLowerCase().contains(ingredient.toLowerCase())) {
                    listOfResults.add(recipe);
                    }
                } 
            }
        }
        return listOfResults;
    }
    // This will removed ingredients automcatically.
    public List<String> ouputIngredientsToRemoved(String ingredientsPick) {
        List<String> totalBasketsByCategory = null;
        if (ingredientsPick.equalsIgnoreCase("1")) {
            List<String> basketVegetables = produced.getVegetables();
            List<String> basketFruits = produced.getFruits();
            totalBasketsByCategory = combineBaskets(basketVegetables, basketFruits);

        }
        else if (ingredientsPick.equalsIgnoreCase("2")) {
            List<String> basketLandMeats = meatProduct.getLandMeats();
            List<String> basketFishes = meatProduct.getFishes();
            List<String> basketProcessedMeats = meatProduct.getProcessedMeats();
            totalBasketsByCategory = combineBaskets(basketLandMeats, basketFishes, basketProcessedMeats);
        }
        else if (ingredientsPick.equalsIgnoreCase("3")) {
            List<String> basketSpices = herbSpices.getSpices();
            List<String> basketHerbs = herbSpices.getHerbs();
            totalBasketsByCategory = combineBaskets(basketSpices, basketHerbs);
        }
        else if (ingredientsPick.equalsIgnoreCase("4")) {
            List<String> basketCondiments = oilCondiments.getCondiments();
            List<String> basketSauces = oilCondiments.getSauces();
            List<String> basketOils = oilCondiments.getOils();
            totalBasketsByCategory = combineBaskets(basketCondiments, basketSauces, basketOils);
        }
        else if (ingredientsPick.equalsIgnoreCase("5")) { 
           List<String> basketMilks = dairy.getMilks();
            List<String> basketCheeses= dairy.getCheeses();
            List<String> baskettCreams = dairy.getCreams();
            List<String> basketButters = dairy.getButters();
            totalBasketsByCategory = combineBaskets(basketMilks, basketCheeses, baskettCreams, basketButters);
        }
        else if (ingredientsPick.equalsIgnoreCase("6")) { 
            List<String> basketGrains = pantry.getGrains();
            List<String> basketCannedGoods = pantry.getCannedGoods();
            List<String> basketBakingStaples = pantry.getBakingStaples();
            totalBasketsByCategory = combineBaskets(basketGrains, basketCannedGoods, basketBakingStaples);
        }
        return totalBasketsByCategory;
    }

    @SafeVarargs
    public final List<String> combineBaskets(List<String>... baskets) {

    List<String> totalBasketsByCategory  = new ArrayList<>();

    for (List<String>  basket : baskets) {
        for (String player : basket) {
            totalBasketsByCategory.add(player);
        }
    }
    return totalBasketsByCategory;
    }
    
}
