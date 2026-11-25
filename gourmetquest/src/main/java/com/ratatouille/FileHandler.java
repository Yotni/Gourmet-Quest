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




}
