package appewtc.masterung.bsrurestaurant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by masterUNG on 2/14/15 AD.
 */
public class MyOpenHelper extends SQLiteOpenHelper{

    //Explicit
    private static final String DATABASE_NAME = "bsru.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_USER_TABLE = "create table userTABLE (_id integer primary key, "+" User text, Password text, Name text);";
    private static final String CREATE_ORDER_TABLE = "create table orderTABLE (_id integer primary key, "+" Officer text, Drink text, Food text, Desk text);";

    public MyOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }   // Constructor

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_ORDER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}   // Main Class
