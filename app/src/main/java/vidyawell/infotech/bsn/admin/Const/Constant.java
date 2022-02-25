package vidyawell.infotech.bsn.admin.Const;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Created by AmitAIT on 17-10-2018.
 */

public class Constant {




    public static boolean isConnection(Activity activity) {
        ConnectivityManager manage = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manage.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
}
