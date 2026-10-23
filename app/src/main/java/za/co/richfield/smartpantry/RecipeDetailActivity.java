package za.co.richfield.smartpantry;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class RecipeDetailActivity extends Activity {
    @Override public void onCreate(Bundle b){
        super.onCreate(b); DatabaseHelper db=new DatabaseHelper(this);
        long id=getIntent().getLongExtra("recipe_id",-1); Recipe r=db.getRecipe(id);
        LinearLayout content=Ui.page(this,r==null?"Recipe not found":r.name);
        if(r!=null){
            content.addView(Ui.text(this,"Required ingredients",19));
            content.addView(Ui.text(this,r.ingredients.replace(",",", "),16));
            content.addView(Ui.text(this,"Preparation method",19));
            content.addView(Ui.text(this,r.steps,16));
        }
        Button back=Ui.button(this,"Back to suggestions"); back.setOnClickListener(v->finish()); content.addView(back);
        ScrollView scroll=new ScrollView(this); scroll.addView(content); setContentView(scroll);
    }
}
