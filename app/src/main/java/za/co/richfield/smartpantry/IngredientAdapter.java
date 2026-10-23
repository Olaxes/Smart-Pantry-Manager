package za.co.richfield.smartpantry;

import android.content.Context;
import android.view.*;
import android.widget.*;
import java.util.List;

public class IngredientAdapter extends BaseAdapter {
    private final Context context; private final List<Ingredient> items;
    public IngredientAdapter(Context c, List<Ingredient> list) { context=c; items=list; }
    public int getCount(){return items.size();} public Object getItem(int p){return items.get(p);} public long getItemId(int p){return items.get(p).id;}
    public View getView(int p, View convert, android.view.ViewGroup parent) {
        TextView v = convert instanceof TextView ? (TextView)convert : new TextView(context);
        v.setText(items.get(p).toString()); v.setTextSize(17); v.setPadding(14,18,14,18); return v;
    }
}
