package appewtc.masterung.bsrurestaurant;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by masterUNG on 2/15/15 AD.
 */
public class MyAlertDialog {

    AlertDialog.Builder objAlert;

    public void myDialog(Context context, String strTitle, String strMessage) {

        objAlert = new AlertDialog.Builder(context);
        objAlert.setIcon(R.drawable.danger);
        objAlert.setTitle(strTitle);
        objAlert.setMessage(strMessage);
        objAlert.setCancelable(false);
        objAlert.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        objAlert.show();

    }

}   // Main Class
