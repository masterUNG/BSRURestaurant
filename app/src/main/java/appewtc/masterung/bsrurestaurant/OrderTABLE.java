package appewtc.masterung.bsrurestaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by masterUNG on 2/14/15 AD.
 */
public class OrderTABLE {

    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSQLite, readSQLite;

    public static final String ORDER_TABLE = "orderTABLE";
    public static final String COLUMN_ID_ORDER = "_id";
    public static final String COLUMN_OFFICER = "Officer";
    public static final String COLUMN_DRINK = "Drink";
    public static final String COLUMN_FOOD = "Food";
    public static final String COLUMN_DESK = "Desk";

    public OrderTABLE(Context context) {

        objMyOpenHelper = new MyOpenHelper(context);
        writeSQLite = objMyOpenHelper.getWritableDatabase();
        readSQLite = objMyOpenHelper.getReadableDatabase();

    }  // Constructor

    public Cursor readAllData() {

        Cursor objCursor = readSQLite.query(ORDER_TABLE, new String[]{COLUMN_ID_ORDER, COLUMN_OFFICER, COLUMN_DRINK, COLUMN_FOOD, COLUMN_DESK}, null, null, null, null, null);

        if (objCursor != null) {
            objCursor.moveToFirst();
        }

        return objCursor;
    }



    public long addValuetoOrder(String strOfficer, String strDrink, String strFood, String strDesk) {

        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_OFFICER, strOfficer);
        objContentValues.put(COLUMN_DRINK, strDrink);
        objContentValues.put(COLUMN_FOOD, strFood);
        objContentValues.put(COLUMN_DESK, strDesk);

        return writeSQLite.insert(ORDER_TABLE, null, objContentValues);
    }

}   // Main Class
