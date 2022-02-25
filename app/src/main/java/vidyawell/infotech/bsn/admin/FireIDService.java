package vidyawell.infotech.bsn.admin;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by AmitAIT on 12-11-2018.
 */

public class FireIDService extends FirebaseInstanceIdService {



    @Override

    public void onTokenRefresh() {

        String tkn = FirebaseInstanceId.getInstance().getToken();

        Log.d("Not","Token ["+tkn+"]");



    }
}
