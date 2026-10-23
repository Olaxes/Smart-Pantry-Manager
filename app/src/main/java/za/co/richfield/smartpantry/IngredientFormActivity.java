package za.co.richfield.smartpantry;

import android.app.*;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class IngredientFormActivity extends Activity {
    DatabaseHelper db; EditText name,quantity,unit,expiry; long id=-1;
    @Override public void onCreate(Bundle b){super.onCreate(b);db=new DatabaseHelper(this); if(getIntent().hasExtra("ingredient_id"))id=getIntent().getLongExtra("ingredient_id",-1); build();}
    private void build(){
        LinearLayout root=Ui.page(this,id==-1?"Add Ingredient":"Edit Ingredient");
        name=field(root,"Ingredient name (e.g. tomato)");
        quantity=field(root,"Quantity (number)");
        unit=field(root,"Unit (e.g. item, g, ml)");
        expiry=field(root,"Expiry date (optional, YYYY-MM-DD)");
        if(id!=-1){Ingredient x=db.getIngredient(id); if(x!=null){name.setText(x.name);quantity.setText(String.valueOf(x.quantity));unit.setText(x.unit);expiry.setText(x.expiry);}}
        Button save=Ui.button(this,"Save ingredient"); save.setOnClickListener(v->save());root.addView(save);
        Button cancel=Ui.button(this,"Cancel");cancel.setOnClickListener(v->finish());root.addView(cancel);
        Ui.addNav(this,root);setContentView(root);
    }
    private EditText field(LinearLayout root,String hint){EditText e=new EditText(this);e.setHint(hint);e.setSingleLine(true);root.addView(e,new LinearLayout.LayoutParams(-1,-2));return e;}
    private void save(){
        String n=name.getText().toString().trim(), q=quantity.getText().toString().trim(), u=unit.getText().toString().trim(), ex=expiry.getText().toString().trim();
        if(n.isEmpty()||q.isEmpty()||u.isEmpty()){Toast.makeText(this,"Name, quantity and unit are required.",Toast.LENGTH_LONG).show();return;}
        double amount;try{amount=Double.parseDouble(q);if(amount<=0)throw new NumberFormatException();}catch(Exception e){Toast.makeText(this,"Quantity must be a number greater than zero.",Toast.LENGTH_LONG).show();return;}
        if(id==-1)db.addIngredient(n,amount,u,ex);else db.updateIngredient(id,n,amount,u,ex);
        Toast.makeText(this,"Ingredient saved.",Toast.LENGTH_SHORT).show();finish();
    }
}
