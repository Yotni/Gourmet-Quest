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




}
