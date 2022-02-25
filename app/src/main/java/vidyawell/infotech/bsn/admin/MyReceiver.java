package vidyawell.infotech.bsn.admin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
  static String status;
    @Override
    public void onReceive(Context context, Intent intent) {
         status = NetworkUtil.getConnectivityStatusString(context);
        if(status.isEmpty()) {
            status="No internet is available";
        }
        Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }


}