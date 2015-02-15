package appewtc.masterung.bsrurestaurant;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;


public class OrderActivity extends ActionBarActivity {

    private TextView txtShowOfficer;
    private Spinner spnDrink, spnFood;
    private RadioGroup ragDesk;
    private RadioButton rad1, rad2, rad3, rad4, rad5, rad6, rad7, rad8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        bindWidget();

    }   // onCreate

    private void bindWidget() {

        txtShowOfficer = (TextView) findViewById(R.id.txtShowOfficer);
        spnDrink = (Spinner) findViewById(R.id.spinner);
        spnFood = (Spinner) findViewById(R.id.spinner2);
        ragDesk = (RadioGroup) findViewById(R.id.ragDesk);
        rad1 = (RadioButton) findViewById(R.id.radioButton);
        rad2 = (RadioButton) findViewById(R.id.radioButton2);
        rad3 = (RadioButton) findViewById(R.id.radioButton3);
        rad4 = (RadioButton) findViewById(R.id.radioButton4);
        rad5 = (RadioButton) findViewById(R.id.radioButton5);
        rad6 = (RadioButton) findViewById(R.id.radioButton6);
        rad7 = (RadioButton) findViewById(R.id.radioButton7);
        rad8 = (RadioButton) findViewById(R.id.radioButton8);

    }   // bindWidget

    public void clickOrder(View view) {

    }   // clickOrder

    public void clickRead(View view) {

    }   // clickRead

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}   // Main Class
