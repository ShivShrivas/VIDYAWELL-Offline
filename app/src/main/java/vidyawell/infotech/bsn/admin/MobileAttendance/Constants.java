package vidyawell.infotech.bsn.admin.MobileAttendance;


public class Constants {


    public static final String PACKAGE_NAME = "com.google.android.gms.location.Geofence";
    public static final String SHARED_PREFERENCES_NAME = PACKAGE_NAME + ".SHARED_PREFERENCES_NAME";
    public static final String GEOFENCES_ADDED_KEY = PACKAGE_NAME + ".GEOFENCES_ADDED_KEY";
    public static final String GEOFENCES_IN_KEY = PACKAGE_NAME + ".INKEY";

    public static final long GEOFENCE_EXPIRATION_IN_HOURS = 1000;
    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;
   // public static final float GEOFENCE_RADIUS_IN_METERS =  Float.parseFloat(GRADIUS);
    public static final float GEOFENCE_RADIUS_IN_METERS =  50;

   // public static final HashMap<String, LatLng> BAY_AREA_LANDMARKS = new HashMap<String, LatLng>();

    /*static {
        String tt=GLAT;
        String ttr=GLON;
      //  BAY_AREA_LANDMARKS.put("Bsn office", new LatLng(Double.parseDouble(GLAT), Double.parseDouble(GLON)));

       // BAY_AREA_LANDMARKS.put("Bsn office", new LatLng(23.0000, 40.0000));
    }*/
}
