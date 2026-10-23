package za.co.richfield.smartpantry;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;

public class SettingsActivity extends Activity {
    @Override public void onCreate(Bundle b){super.onCreate(b);
        LinearLayout root=Ui.page(this,"Settings");
        root.addView(Ui.text(this,"Smart Pantry Manager",18));
        root.addView(Ui.text(this,"Database: SQLite, stored locally on this device.",16));
        root.addView(Ui.text(this,"Recipe matching: every required ingredient must be present with a quantity of at least 1.0. Partial matches are excluded.",16));
        root.addView(Ui.text(this,"Tip: use consistent units and ingredient names. Expiry dates are optional notes and are not currently used to rank recipes.",15));
        Ui.addNav(this,root);setContentView(root);
    }
}
