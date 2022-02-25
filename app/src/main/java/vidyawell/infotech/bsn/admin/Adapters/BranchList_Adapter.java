package vidyawell.infotech.bsn.admin.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vidyawell.infotech.bsn.admin.Helpers.BranchList_Helper;
import vidyawell.infotech.bsn.admin.Helpers.SectionList_Helper;
import vidyawell.infotech.bsn.admin.R;

public class BranchList_Adapter extends ArrayAdapter<String> {
    private Context activity;
    private ArrayList data;
    public Resources res;
    BranchList_Helper tempValues=null;
    LayoutInflater inflater;
    public BranchList_Adapter(Context activitySpinner, int textViewResourceId, ArrayList objects, Resources resLocal )
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
        View row = inflater.inflate(R.layout.spinner_branch_item, parent, false);

        /***** Get each Model object from Arraylist ********/
        tempValues = null;
        tempValues = (BranchList_Helper) data.get(position);
        TextView text_bname        = (TextView)row.findViewById(R.id.text_bname);
        TextView brach_text          = (TextView)row.findViewById(R.id.brach_text);
        text_bname.setText(tempValues.getBrach_Name());
        brach_text.setText(tempValues.getBranch_ID());

        return row;
    }
}
