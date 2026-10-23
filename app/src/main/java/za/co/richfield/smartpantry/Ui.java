package za.co.richfield.smartpantry;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.*;
import android.graphics.Color;

public class Ui {
    public static final int GREEN = Color.rgb(46,125,50);
    public static LinearLayout page(Context c, String title) {
        LinearLayout root = new LinearLayout(c); root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(20,18,20,16); root.setBackgroundColor(Color.rgb(248,250,247));
        TextView heading = new TextView(c); heading.setText(title); heading.setTextSize(25); heading.setTypeface(null, Typeface.BOLD); heading.setTextColor(GREEN);
        root.addView(heading, new LinearLayout.LayoutParams(-1,-2));
        return root;
    }
    public static Button button(Context c, String text) {
        Button b = new Button(c); b.setText(text); b.setAllCaps(false); return b;
    }
    public static TextView text(Context c, String value, int size) {
        TextView t = new TextView(c); t.setText(value); t.setTextSize(size); t.setPadding(0,8,0,8); return t;
    }
    public static void addNav(Context c, LinearLayout root) {
        LinearLayout nav = new LinearLayout(c); nav.setOrientation(LinearLayout.HORIZONTAL);
        Button pantry=button(c,"Pantry"), recipes=button(c,"Suggestions"), settings=button(c,"Settings");
        nav.addView(pantry,new LinearLayout.LayoutParams(0,-2,1)); nav.addView(recipes,new LinearLayout.LayoutParams(0,-2,1)); nav.addView(settings,new LinearLayout.LayoutParams(0,-2,1));
        pantry.setOnClickListener(v -> ((android.app.Activity)c).startActivity(new android.content.Intent(c,MainActivity.class)));
        recipes.setOnClickListener(v -> ((android.app.Activity)c).startActivity(new android.content.Intent(c,SuggestedRecipesActivity.class)));
        settings.setOnClickListener(v -> ((android.app.Activity)c).startActivity(new android.content.Intent(c,SettingsActivity.class)));
        root.addView(nav);
    }
}
