package appewtc.masterung.bsrurestaurant;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ListDeskActivity extends ListActivity{

    private OrderTABLE objOrderTABLE;
    private SimpleCursorAdapter objSimpleCursorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list_desk);

        objOrderTABLE = new OrderTABLE(this);

        deleteAllOrder();

        synJSONtoOrder();

        createListView();

    }   // onCreate


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Cursor objCursor = (Cursor) l.getItemAtPosition(position);
        String strOfficer = objCursor.getString(objCursor.getColumnIndex(OrderTABLE.COLUMN_OFFICER));
        String strDrink = objCursor.getString(objCursor.getColumnIndex(OrderTABLE.COLUMN_DRINK));
        String strFood = objCursor.getString(objCursor.getColumnIndex(OrderTABLE.COLUMN_FOOD));
        String strDesk = objCursor.getString(objCursor.getColumnIndex(OrderTABLE.COLUMN_DESK));

        //ShowAlert
        showAlert(strOfficer, strDrink, strFood, strDesk);

    }   // onListItemClick

    private void showAlert(String strOfficer, String strDrink, String strFood, String strDesk) {

        AlertDialog.Builder objAlert = new AlertDialog.Builder(this);
        objAlert.setIcon(R.drawable.icon_myaccount);
        objAlert.setTitle("Order");
        objAlert.setMessage("Officer = " + strOfficer + "\n" +
                "Drink = " + strDrink + "\n" +
                "Food = " + strFood + "\n" +
                "Desk = " + strDesk);
        objAlert.setCancelable(false);
        objAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        objAlert.show();


    }   // showAlert

    private void createListView() {

        Cursor ListDesk = objOrderTABLE.readAllData();
        String[] from = new String[]{OrderTABLE.COLUMN_DESK};
        int[] target = new int[]{R.id.txtListDesk};
        objSimpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.activity_list_desk, ListDesk, from, target);
        setListAdapter(objSimpleCursorAdapter);

    }   //createListView

    private void synJSONtoOrder() {

        if (Build.VERSION.SDK_INT > 9) {

            StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(myPolicy);

        }   // if


        InputStream objInputStream = null;
        String strJSON = "";

        //Create InputStream
        try {

            HttpClient objHttpClient = new DefaultHttpClient();
            HttpPost objHttpPost = new HttpPost("http://swiftcodingthai.com/bsru/php_get_data_order.php");
            HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
            HttpEntity objHttpEntity = objHttpResponse.getEntity();
            objInputStream = objHttpEntity.getContent();

        } catch (Exception e) {
            Log.d("bsru", "InputStream ==> " + e.toString());
        }



        //Create strJSON
        try {

            BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
            StringBuilder objStringBuilder = new StringBuilder();
            String strLine = null;

            while ((strLine = objBufferedReader.readLine()) != null ) {

                objStringBuilder.append(strLine);

            }   //while

            objInputStream.close();
            strJSON = objStringBuilder.toString();


        } catch (Exception e) {
            Log.d("bsru", "strJSON ==> " + e.toString());
        }



        //UpDate Value to SQLite
        try {

            final JSONArray objJSONArray = new JSONArray(strJSON);

            for (int i = 0; i < objJSONArray.length(); i++) {

                JSONObject objJSONObject = objJSONArray.getJSONObject(i);
                String strOfficer = objJSONObject.getString("Officer");
                String strDrink = objJSONObject.getString("Drink");
                String strFood = objJSONObject.getString("Food");
                String strDesk = objJSONObject.getString("Desk");

                long orderValue = objOrderTABLE.addValuetoOrder(strOfficer, strDrink, strFood, strDesk);


            }   // for

        } catch (Exception e) {
            Log.d("bsru", "Updata ==> " + e.toString());
        }



    }   // synJSONtoOrder

    private void deleteAllOrder() {

        SQLiteDatabase objSQLite = openOrCreateDatabase("bsru.db", MODE_PRIVATE, null);
        objSQLite.delete("orderTABLE", null, null);

    }// deleteAllOrder


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_desk, menu);
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
