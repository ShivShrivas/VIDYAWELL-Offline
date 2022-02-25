package vidyawell.infotech.bsn.admin.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vidyawell.infotech.bsn.admin.Helpers.ClassList_Helper;
import vidyawell.infotech.bsn.admin.Helpers.EMP_Presenter_Helper;
import vidyawell.infotech.bsn.admin.R;

/**
 * Created by AmitAIT on 03-11-2018.
 */

public class EMP_Presenter_Adapter extends ArrayAdapter<String> {
    private Context activity;
    private ArrayList data;
    public Resources res;
    EMP_Presenter_Helper tempValues=null;
    LayoutInflater inflater;
    public EMP_Presenter_Adapter(Context activitySpinner, int textViewResourceId, ArrayList objects, Resources resLocal )
    {
        super(activitySpinner, textViewResourceId, objects);

        /********** Take passed values **********/
        activity = activitySpinner;
        data     = objects;
        res      = resLocal;

        /***********  Layout inflator to call external xml layout () **********************/
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        View row = inflater.inflate(R.layout.spinner_emp_presenter_item, parent, false);

        /***** Get each Model object from Arraylist ********/
        tempValues = null;
        tempValues = (EMP_Presenter_Helper) data.get(position);
        TextView text_emp_name        = (TextView)row.findViewById(R.id.text_emp_name);
        TextView text_EMP_ID          = (TextView)row.findViewById(R.id.text_EMP_ID);
        text_emp_name.setText(tempValues.getFullName());
        text_EMP_ID.setText(tempValues.getEmployeeCode());

        if(position==0){

        }
        else
        {
        }

        return row;
    }
}
