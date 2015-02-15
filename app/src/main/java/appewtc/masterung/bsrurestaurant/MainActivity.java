package appewtc.masterung.bsrurestaurant;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

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


public class MainActivity extends ActionBarActivity {

    private UserTABLE objUserTABLE;
    private OrderTABLE objOrderTABLE;
    private EditText edtUser, edtPassword;
    private String strUserChoose, strPasswordChoose, strPasswordTrue, strName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        bindWidget();

        //Create or Connect SQLite
        objUserTABLE = new UserTABLE(this);
        objOrderTABLE = new OrderTABLE(this);

        //Test Add Value
//        objUserTABLE.addValeToUser("User", "Password", "Name");
//        objOrderTABLE.addValuetoOrder("Officer", "Drink", "Food", "Desk");

        //Delete All Data
        deleteAllData();

        //Synchronize JSON to SQLite
        synJSONtoSQLite();

    }   // onCreate

    private void bindWidget() {
        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
    }   // bindWidget

    public void clickLogin(View view) {

        strUserChoose = edtUser.getText().toString().trim();
        strPasswordChoose = edtPassword.getText().toString().trim();

        if (strUserChoose.equals("") || strPasswordChoose.equals("") ) {

            MyAlertDialog objMyAlert = new MyAlertDialog();
            objMyAlert.myDialog(MainActivity.this, "มีช่องว่าง", "กรุณากรอกทุกช่อง");

        } else {

            //Check User
            checkUser();

        }

    }   //clickLogin

    private void checkUser() {

        try {

            String myData[] = objUserTABLE.searchUser(strUserChoose);
            strPasswordTrue = myData[2];
            strName = myData[3];

            Log.d("bsru", "Wellcome ==> " + strName);

            checkPassword();

        } catch (Exception e) {
            MyAlertDialog objMyAlert = new MyAlertDialog();
            objMyAlert.myDialog(MainActivity.this, "No User", "No " + strUserChoose + " in my Database");
        }

    }   // checkUser

    private void checkPassword() {

        if (strPasswordChoose.equals(strPasswordTrue)) {

            //Intent to OrderActivity
            Intent objIntent = new Intent(MainActivity.this, OrderActivity.class);
            objIntent.putExtra("Name", strName);
            startActivity(objIntent);
            finish();

        } else {

            MyAlertDialog objMyAlert = new MyAlertDialog();
            objMyAlert.myDialog(MainActivity.this, "Password False", "Please Try Again Password False");

        }

    }   // checkPassword


    private void deleteAllData() {

        SQLiteDatabase objSQLite = openOrCreateDatabase("bsru.db", MODE_PRIVATE, null);
        objSQLite.delete("userTABLE", null, null);

    }   //delete All Data

    private void synJSONtoSQLite() {

        // Open Permit All
        if (Build.VERSION.SDK_INT > 9) {

            StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(myPolicy);

        }   // if


        InputStream objInputStream = null;
        String strJSON = "";

        //Create InputStream
        try {

            HttpClient objHttpClient = new DefaultHttpClient();
            HttpPost objHttpPost = new HttpPost("http://swiftcodingthai.com/bsru/php_get_data_master.php");
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
                String strUser = objJSONObject.getString("User");
                String strPassword = objJSONObject.getString("Password");
                String strName = objJSONObject.getString("Name");

                long insertData = objUserTABLE.addValeToUser(strUser, strPassword, strName);

            }   // for

        } catch (Exception e) {
            Log.d("bsru", "Updata ==> " + e.toString());
        }



    }   //synJSONtoSQLite


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
