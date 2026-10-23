package za.co.richfield.smartpantry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "smart_pantry.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) { super(context, DB_NAME, null, DB_VERSION); }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE pantry(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, quantity REAL NOT NULL, unit TEXT NOT NULL, expiry TEXT)");
        db.execSQL("CREATE TABLE recipes(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, ingredients TEXT NOT NULL, steps TEXT NOT NULL)");
        seedRecipes(db);
    }
    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    private void seedRecipes(SQLiteDatabase db) {
        String[][] data = {
            {"Tomato Egg Scramble","egg,tomato","1. Chop tomato. 2. Beat eggs. 3. Cook together in a pan until set."},
            {"Cheese Omelette","egg,cheese","1. Beat eggs. 2. Cook in a pan. 3. Add cheese and fold."},
            {"Banana Pancakes","banana,egg,flour","1. Mash banana. 2. Mix with egg and flour. 3. Fry small pancakes."},
            {"Garlic Pasta","pasta,garlic,oil","1. Boil pasta. 2. Gently cook chopped garlic in oil. 3. Toss together."},
            {"Tomato Pasta","pasta,tomato,onion","1. Cook pasta. 2. Cook chopped onion and tomato. 3. Mix and serve."},
            {"Potato Hash","potato,onion,oil","1. Dice potato and onion. 2. Fry until tender and golden."},
            {"Cheese Toast","bread,cheese","1. Place cheese on bread. 2. Toast until the cheese melts."},
            {"Peanut Banana Toast","bread,peanut butter,banana","1. Spread peanut butter on bread. 2. Add banana slices."},
            {"Rice and Egg Bowl","rice,egg,soy sauce","1. Cook rice. 2. Scramble egg. 3. Combine with soy sauce."},
            {"Simple Fried Rice","rice,egg,carrot,soy sauce","1. Dice carrot. 2. Cook carrot and egg. 3. Stir-fry with rice and soy sauce."},
            {"Carrot Soup","carrot,onion,water","1. Chop carrot and onion. 2. Simmer in water until soft. 3. Blend carefully."},
            {"Apple Oat Bowl","apple,oats,milk","1. Warm oats with milk. 2. Add chopped apple and cook until soft."},
            {"Yoghurt Fruit Bowl","yoghurt,banana,apple","1. Slice fruit. 2. Mix with yoghurt and serve."},
            {"Tuna Sandwich","bread,tuna,mayonnaise","1. Drain tuna. 2. Mix with mayonnaise. 3. Serve on bread."},
            {"Bean Tomato Stew","beans,tomato,onion","1. Cook onion and tomato. 2. Add cooked beans and simmer."},
            {"Spinach Omelette","egg,spinach,cheese","1. Wilt spinach. 2. Add beaten eggs. 3. Add cheese and cook through."},
            {"Chicken Rice","chicken,rice,onion","1. Cook chicken thoroughly. 2. Cook onion. 3. Serve with rice."},
            {"Avocado Toast","bread,avocado,lemon","1. Mash avocado with lemon juice. 2. Spread over toast."},
            {"Cucumber Salad","cucumber,tomato,lemon","1. Chop cucumber and tomato. 2. Dress with lemon juice."},
            {"Flour Flatbread","flour,water,salt,oil","1. Mix flour, water and salt. 2. Roll thinly. 3. Pan-cook with a little oil."}
        };
        for (String[] r : data) {
            ContentValues cv = new ContentValues(); cv.put("name",r[0]); cv.put("ingredients",r[1]); cv.put("steps",r[2]);
            db.insert("recipes", null, cv);
        }
    }

    public long addIngredient(String name, double quantity, String unit, String expiry) {
        ContentValues cv = new ContentValues(); cv.put("name", clean(name)); cv.put("quantity", quantity); cv.put("unit", unit.trim()); cv.put("expiry", expiry);
        return getWritableDatabase().insert("pantry", null, cv);
    }
    public int updateIngredient(long id, String name, double quantity, String unit, String expiry) {
        ContentValues cv = new ContentValues(); cv.put("name", clean(name)); cv.put("quantity", quantity); cv.put("unit", unit.trim()); cv.put("expiry", expiry);
        return getWritableDatabase().update("pantry", cv, "id=?", new String[]{String.valueOf(id)});
    }
    public int deleteIngredient(long id) { return getWritableDatabase().delete("pantry", "id=?", new String[]{String.valueOf(id)}); }

    public List<Ingredient> getIngredients() {
        List<Ingredient> list = new ArrayList<>();
        try (Cursor c = getReadableDatabase().rawQuery("SELECT id,name,quantity,unit,expiry FROM pantry ORDER BY name", null)) {
            while(c.moveToNext()) list.add(new Ingredient(c.getLong(0),c.getString(1),c.getDouble(2),c.getString(3),c.getString(4)));
        }
        return list;
    }
    public Ingredient getIngredient(long id) {
        try (Cursor c = getReadableDatabase().rawQuery("SELECT id,name,quantity,unit,expiry FROM pantry WHERE id=?", new String[]{String.valueOf(id)})) {
            if(c.moveToFirst()) return new Ingredient(c.getLong(0),c.getString(1),c.getDouble(2),c.getString(3),c.getString(4));
        }
        return null;
    }
    public List<Recipe> getRecipes() {
        List<Recipe> list = new ArrayList<>();
        try (Cursor c = getReadableDatabase().rawQuery("SELECT id,name,ingredients,steps FROM recipes ORDER BY name", null)) {
            while(c.moveToNext()) list.add(new Recipe(c.getLong(0),c.getString(1),c.getString(2),c.getString(3)));
        }
        return list;
    }
    public Recipe getRecipe(long id) {
        try (Cursor c = getReadableDatabase().rawQuery("SELECT id,name,ingredients,steps FROM recipes WHERE id=?", new String[]{String.valueOf(id)})) {
            if(c.moveToFirst()) return new Recipe(c.getLong(0),c.getString(1),c.getString(2),c.getString(3));
        }
        return null;
    }

    public List<Recipe> getStrictMatches() {
        List<Recipe> matches = new ArrayList<>();
        List<Ingredient> pantry = getIngredients();
        for (Recipe recipe : getRecipes()) {
            boolean allPresent = true;
            for (String required : recipe.ingredientList()) {
                String target = normalize(required);
                boolean found = false;
                for (Ingredient have : pantry) {
                    if (normalize(have.name).equals(target) && have.quantity >= 1.0) { found = true; break; }
                }
                if (!found) { allPresent = false; break; }
            }
            if (allPresent) matches.add(recipe);
        }
        return matches;
    }
    private String clean(String s) { return s == null ? "" : s.trim(); }
    private String normalize(String s) {
        String v = s == null ? "" : s.toLowerCase(Locale.ROOT).trim();
        if (v.endsWith("ies") && v.length() > 4) v = v.substring(0,v.length()-3) + "y";
        else if (v.endsWith("s") && !v.endsWith("ss") && v.length() > 3) v = v.substring(0,v.length()-1);
        return v;
    }
}
