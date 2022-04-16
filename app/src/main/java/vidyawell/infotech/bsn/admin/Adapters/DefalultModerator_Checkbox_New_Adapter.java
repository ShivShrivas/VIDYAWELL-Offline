package vidyawell.infotech.bsn.admin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vidyawell.infotech.bsn.admin.Helpers.DefaultModerator_Checkbox_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;


public class DefalultModerator_Checkbox_New_Adapter extends ArrayAdapter<DefaultModerator_Checkbox_Helper> {
        Context context;
        ArrayList<DefaultModerator_Checkbox_Helper> arraylist;
        List<DefaultModerator_Checkbox_Helper> defaultModerator_checkbox_helpers;
        private LayoutInflater inflater;
        boolean isChecked;


    DefalultModerator_Checkbox_New_Adapter.CheckcustomButtonListener6 customListner6;
    public interface CheckcustomButtonListener6 {
        public void onCheckClickListner6(int position, DefaultModerator_Checkbox_Helper value, boolean isChecked);
    }
    public void setCheckcustomButtonListener6(DefalultModerator_Checkbox_New_Adapter.CheckcustomButtonListener6 listener6) {
        this.customListner6 = listener6;
    }

public DefalultModerator_Checkbox_New_Adapter(@NonNull Context context, int resource, List<DefaultModerator_Checkbox_Helper> defaultModerator_checkbox_helpers) {
        super(context, resource,defaultModerator_checkbox_helpers);
        this.context = context;

    this.defaultModerator_checkbox_helpers=defaultModerator_checkbox_helpers;
    this.arraylist= new ArrayList<DefaultModerator_Checkbox_Helper>();
    this.arraylist.addAll(defaultModerator_checkbox_helpers);
   /* LayoutInflater mInflater = (LayoutInflater) context
            .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);*/
        }

  /*  @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }






    @Override
    public int getCount() {
        return defaultModerator_checkbox_helpers.size();
    }

    @Override
    public DefaultModerator_Checkbox_Helper getItem(int position) {
        return  defaultModerator_checkbox_helpers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }*/




private class ViewHolder {
    TextView text_sname_chk_moderator,text_sID_chk_moderato;
    CheckBox checkbox_moderato,checkbox_moderato_new;

    String stu_code="";

}
    @Override
    public View getView(final int position, View convertView, ViewGroup parent ) {
        ViewHolder holder = null;
        DefaultModerator_Checkbox_Helper rowItem = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.spinner_default_moderator_item_chekbox, null);
            Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/"+ ServerApiadmin.FONT_DASHBOARD);
            holder = new DefalultModerator_Checkbox_New_Adapter.ViewHolder();
           // holder = new ViewHolder();
            holder.text_sname_chk_moderator = (TextView) convertView.findViewById(R.id.text_sname_chk_moderator);
            holder.text_sID_chk_moderato = (TextView) convertView.findViewById(R.id.text_sID_chk_moderato);
            holder.checkbox_moderato = (CheckBox) convertView.findViewById(R.id.checkbox_moderato);
            holder.checkbox_moderato_new = (CheckBox) convertView.findViewById(R.id.checkbox_moderato_new);

            //  holder.Roll_no=(TextView)convertView.findViewById(R.id.stu_roolnumberc);
           // holder.student_name.setTypeface(typeFace);
          //  holder.father_name.setTypeface(typeFace);
          //  holder.student_count.setTypeface(typeFace);
           // holder.Roll_no.setTypeface(typeFace);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();


        holder.text_sname_chk_moderator.setText(rowItem.getEmployeeName());
        holder.text_sID_chk_moderato.setText(rowItem.getEmployeeCode());

        if ((position == 0)) {
            holder.checkbox_moderato.setVisibility(View.INVISIBLE);
        } else {
            holder.checkbox_moderato.setVisibility(View.VISIBLE);
        }








        final DefaultModerator_Checkbox_Helper temp = getItem(position);
        holder.checkbox_moderato.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (customListner6 != null) {
                    customListner6.onCheckClickListner6(position, temp,isChecked);

                }
            }
        });

       /* holder.checkbox_comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customListner != null) {
                    customListner.onCheckClickListner(position, temp,isChecked);

                }
            }
        });*/


        //if (convertView != null) return convertView;


        return convertView;
    }

}
