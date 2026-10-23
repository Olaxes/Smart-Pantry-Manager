package za.co.richfield.smartpantry;

import android.app.*;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import java.util.List;

public class SuggestedRecipesActivity extends Activity {
    DatabaseHelper db;
    @Override public void onCreate(Bundle b){super.onCreate(b);db=new DatabaseHelper(this);}
    @Override protected void onResume(){super.onResume();show();}
    private void show(){
        LinearLayout root=Ui.page(this,"Suggested Recipes");
        root.addView(Ui.text(this,"Only recipes with every required ingredient in your pantry are shown.",15));
        List<Recipe> matches=db.getStrictMatches();
        if(matches.isEmpty())root.addView(Ui.text(this,"No recipes match your pantry yet — add more ingredients.",17));
        else {ListView list=new ListView(this);list.setAdapter(new RecipeAdapter(this,matches));list.setOnItemClickListener((p,v,pos,id)->{Intent i=new Intent(this,RecipeDetailActivity.class);i.putExtra("recipe_id",id);startActivity(i);});root.addView(list,new LinearLayout.LayoutParams(-1,0,1));}
        Ui.addNav(this,root);setContentView(root);
    }
}
