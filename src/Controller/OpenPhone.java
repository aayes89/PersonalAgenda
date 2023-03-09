package Controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class OpenPhone {

    private final Context con;
    private final Intent intent;

    public OpenPhone(Context con, String phoneNumber){
        this.con = con;
        this.intent  = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
    }

    public void launchIntent(){
        if(intent.resolveActivity(con.getPackageManager()) != null) {
            con.startActivity(intent);
        }
    }
}
