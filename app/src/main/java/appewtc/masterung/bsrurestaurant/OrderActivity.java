package appewtc.masterung.bsrurestaurant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;


public class OrderActivity extends ActionBarActivity {

    private TextView txtShowOfficer;
    private Spinner spnDrink, spnFood;
    private RadioGroup ragDesk;
    private RadioButton rad1, rad2, rad3, rad4, rad5, rad6, rad7, rad8;
    private String strOfficer, strDrink, strFood, strDesk;
    private String strDrinkArray[], strFoodArray[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        bindWidget();

        //Show officer
        showOfficer();

        //Drink Spinner
        drinkSpinner();

        //Food Spinner
        foodSpinner();

        //About Desk
        aboutDesk();

    }   // onCreate

    private void aboutDesk() {

        ragDesk.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.radioButton:
                        strDesk = getString(R.string.one);
                        break;
                    case R.id.radioButton2:
                        strDesk = getString(R.string.two);
                        break;
                    case R.id.radioButton3:
                        strDesk = getString(R.string.three);
                        break;
                    case R.id.radioButton4:
                        strDesk = getString(R.string.four);
                        break;
                    case R.id.radioButton5:
                        strDesk = getString(R.string.five);
                        break;
                    case R.id.radioButton6:
                        strDesk = getString(R.string.six);
                        break;
                    case R.id.radioButton7:
                        strDesk = getString(R.string.seven);
                        break;
                    case R.id.radioButton8:
                        strDesk = getString(R.string.eight);
                        break;

                }   // switch

            }
        });

    }   // aboutDesk

    private void foodSpinner() {

        strFoodArray = getResources().getStringArray(R.array.food);
        ArrayAdapter<String> foodAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strFoodArray);
        spnFood.setAdapter(foodAdapter);

        spnFood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strFood = strFoodArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                strFood = strFoodArray[0];
            }
        });

    }   // foodSpinner

    private void drinkSpinner() {

        strDrinkArray = getResources().getStringArray(R.array.drink);

        ArrayAdapter<String> drinkAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strDrinkArray);
        spnDrink.setAdapter(drinkAdapter);


        spnDrink.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strDrink = strDrinkArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                strDrink = strDrinkArray[0];
            }
        });


    }   // drinkSpinner

    private void showOfficer() {

        strOfficer = getIntent().getExtras().getString("Name");
        txtShowOfficer.setText("order by..." + strOfficer);

    }   // showOfficer

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

        AlertDialog.Builder objAlert = new AlertDialog.Builder(this);
        objAlert.setIcon(R.drawable.icon_myaccount);
        objAlert.setTitle("Confirm Order");
        objAlert.setMessage("Officer = " + strOfficer + "\n" + "Drink = " + strDrink + "\n" + "Food = " + strFood + "\n" + "Desk = " + strDesk);
        objAlert.setCancelable(false);
        objAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                updateOrder();
                Toast.makeText(OrderActivity.this, "Update Finish", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
        objAlert.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        objAlert.show();

    }   // clickOrder

    private void updateOrder() {

        //Setup New Policy
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(myPolicy);
        }   // if

        try {

            ArrayList<NameValuePair> objArrayList = new ArrayList<NameValuePair>();
            objArrayList.add(new BasicNameValuePair("isAdd", "true"));
            objArrayList.add(new BasicNameValuePair("Officer", strOfficer));
            objArrayList.add(new BasicNameValuePair("Drink", strDrink));
            objArrayList.add(new BasicNameValuePair("Food", strFood));
            objArrayList.add(new BasicNameValuePair("Desk", strDesk));

            HttpClient objHttpClient = new DefaultHttpClient();
            HttpPost objHttpPost = new HttpPost("http://swiftcodingthai.com/bsru/php_add_data_restaurant.php");
            objHttpPost.setEntity(new UrlEncodedFormEntity(objArrayList, "UTF-8"));
            objHttpClient.execute(objHttpPost);

        } catch (Exception e) {
            Log.d("bsru", "Cannot Update ==> " + e.toString());
        }


    }   // updateOrder

    public void clickRead(View view) {

        Intent objIntent = new Intent(OrderActivity.this, ListDeskActivity.class);
        startActivity(objIntent);
        finish();
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
