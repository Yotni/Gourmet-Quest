package com.ratatouille;

import java.io.File;
import java.util.Scanner;
import java.util.logging.FileHandler;
import com.ratatouille.Models.*;

public class GourmetQuest {
    public static final Scanner scanner = new Scanner(System.in);

    // private static File FilePathRecipes = FileHandler.FilePathRecipes;
    private static File FilePathSaving = FileHandler.FilePathSaving;
    // private static File FilePathToDoList = DataHandler.FilePathToDoList;

    private Produced produced;
    private MeatProduct meatProduct;
    private HerbSpices herbSpices;
    private Dairy dairy;
    private OilCondiments oilCondiments;
    private Pantry pantry;

    private FileHandler fileHandler;

    public GourmetQuest (Produced produced, MeatProduct meatProduct, HerbSpices herbSpices, Dairy dairy, OilCondiments oilCondiments, Pantry pantry, FileHandler fileHandler) {
        this.produced = produced;
        this.meatProduct = meatProduct;
        this.herbSpices = herbSpices;
        this.dairy = dairy;
        this.oilCondiments = oilCondiments;
        this.pantry = pantry;
        this.fileHandler = fileHandler;
    }
}
