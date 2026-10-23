package za.co.richfield.smartpantry;

import java.util.Arrays;
import java.util.List;

public class Recipe {
    public long id;
    public String name, ingredients, steps;
    public Recipe(long id, String name, String ingredients, String steps) {
        this.id=id; this.name=name; this.ingredients=ingredients; this.steps=steps;
    }
    public List<String> ingredientList() { return Arrays.asList(ingredients.split(",")); }
}
