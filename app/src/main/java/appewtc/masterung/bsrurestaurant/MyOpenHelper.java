package appewtc.masterung.bsrurestaurant;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by masterUNG on 2/14/15 AD.
 */
public class MyOpenHelper extends SQLiteOpenHelper{

    public MyOpenHelper() {
        super(null, null, null, 0);
    }   // Constructor

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}   // Main Class
