package za.co.richfield.smartpantry;

import android.app.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.util.List;

public class MainActivity extends Activity {
    DatabaseHelper db; ListView list; TextView empty;
    @Override public void onCreate(Bundle b) { super.onCreate(b); db=new DatabaseHelper(this); show(); }
    @Override protected void onResume(){super.onResume(); if(db!=null) show();}
    private void show() {
        LinearLayout root=Ui.page(this,"Smart Pantry Manager");
        root.addView(Ui.text(this,"Track what you have. Cook with what is already at home.",15));
        Button add=Ui.button(this,"+ Add ingredient"); add.setOnClickListener(v -> startActivity(new Intent(this,IngredientFormActivity.class))); root.addView(add);
        Button suggest=Ui.button(this,"Find recipes I can make"); suggest.setOnClickListener(v -> startActivity(new Intent(this,SuggestedRecipesActivity.class))); root.addView(suggest);
        root.addView(Ui.text(this,"Your pantry (tap an item to edit; long-press to delete)",16));
        list=new ListView(this); List<Ingredient> items=db.getIngredients(); list.setAdapter(new IngredientAdapter(this,items));
        list.setOnItemClickListener((p,v,pos,id)-> { Intent i=new Intent(this,IngredientFormActivity.class); i.putExtra("ingredient_id",id); startActivity(i); });
        list.setOnItemLongClickListener((p,v,pos,id)-> { new AlertDialog.Builder(this).setTitle("Delete ingredient?").setMessage(items.get(pos).name).setNegativeButton("Cancel",null).setPositiveButton("Delete",(d,w)->{db.deleteIngredient(id);show();}).show(); return true; });
        if(items.isEmpty()) root.addView(Ui.text(this,"Your pantry is empty. Add an ingredient to get started.",15));
        root.addView(list,new LinearLayout.LayoutParams(-1,0,1)); Ui.addNav(this,root); setContentView(root);
    }
}
