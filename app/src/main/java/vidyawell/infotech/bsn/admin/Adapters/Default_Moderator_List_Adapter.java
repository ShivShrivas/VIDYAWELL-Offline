package vidyawell.infotech.bsn.admin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vidyawell.infotech.bsn.admin.Helpers.Default_Moderator_List_Helper;

import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;


public class Default_Moderator_List_Adapter extends ArrayAdapter<Default_Moderator_List_Helper> {
        Context context;
        ArrayList<Default_Moderator_List_Helper> arraylist;
        List<Default_Moderator_List_Helper> default_moderator_list_helpers;
        private LayoutInflater inflater;
        boolean isChecked;


    CheckcustomButtonListener_default_moderator customListner;
    public interface CheckcustomButtonListener_default_moderator{
        public void CheckcustomButtonListener_default_moderator(int position, Default_Moderator_List_Helper value, boolean isChecked);
    }
    public void setCheckcustomButtonListener_default_moderator(CheckcustomButtonListener_default_moderator listener) {
        this.customListner = listener;
    }


    CheckcustomButtonListener_default_moderator_new customListner_2;
    public interface CheckcustomButtonListener_default_moderator_new {
        public void onCheckcustomButtonListener_default_moderator_new(int position, Default_Moderator_List_Helper value, boolean isChecked);
    }
    public void setCheckcustomButtonListener_default_moderator_new(CheckcustomButtonListener_default_moderator_new listener_2) {
        this.customListner_2 = listener_2;
    }

public Default_Moderator_List_Adapter(@NonNull Context context, int resource, List<Default_Moderator_List_Helper> default_moderator_list_helpers) {
        super(context, resource,default_moderator_list_helpers);
        this.context = context;

    this.default_moderator_list_helpers=default_moderator_list_helpers;
    this.arraylist= new ArrayList<Default_Moderator_List_Helper>();
    this.arraylist.addAll(default_moderator_list_helpers);
   /* LayoutInflater mInflater = (LayoutInflater) context
            .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);*/
        }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }
   // @Override
   /* public long getItemId(int position) {
        return 0;
    }*/





    @Override
    public int getCount() {
        return default_moderator_list_helpers.size();
    }

    @Override
    public Default_Moderator_List_Helper getItem(int position) {
        return  default_moderator_list_helpers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


  /*  @Override
    public int getCount() {
        return student_list_helpers.size();
    }

    @Override
    public Student_List_Helper getItem(int position) {
        return  student_list_helpers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }*/

private class ViewHolder {
    TextView student_name,father_name,student_count,student_ID,Roll_no;
    CheckBox checkbox_comp,checkbox_comp_new;

    String stu_code="";

}
    @Override
    public View getView(final int position, View convertView, ViewGroup parent ) {
        ViewHolder holder = null;
        Default_Moderator_List_Helper rowItem = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_defaultmoderator_list, null);
            Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/"+ ServerApiadmin.FONT_DASHBOARD);
            holder = new Default_Moderator_List_Adapter.ViewHolder();
           // holder = new ViewHolder();
            holder.student_name = (TextView)convertView.findViewById(R.id.student_name);
           // holder.father_name = (TextView)convertView.findViewById(R.id.father_name);
            holder.student_count=(TextView)convertView.findViewById(R.id.student_count);
            holder.student_ID=(TextView)convertView.findViewById(R.id.student_ID);
            holder.checkbox_comp=(CheckBox)convertView.findViewById(R.id.checkbox_comp);
            holder.checkbox_comp_new=(CheckBox)convertView.findViewById(R.id.checkbox_comp_new);
          //  holder.Roll_no=(TextView)convertView.findViewById(R.id.stu_roolnumberc);
            holder.student_name.setTypeface(typeFace);
          //  holder.father_name.setTypeface(typeFace);
            holder.student_count.setTypeface(typeFace);
           // holder.Roll_no.setTypeface(typeFace);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();


        holder.student_name.setText(rowItem.getEmployeeName());
        holder.student_count.setText(rowItem.getCont());
        holder.student_ID.setText(rowItem.getEmployeeCode());


        if(rowItem.getcheck_status()){
            holder.checkbox_comp_new.setVisibility(View.VISIBLE);
            holder.checkbox_comp.setVisibility(View.GONE);

           // holder.stu_code=holder.stu_code+rowItem.getStudentCode();
        }else {
            holder.checkbox_comp.setVisibility(View.VISIBLE);
            holder.checkbox_comp_new.setVisibility(View.GONE);
        }



      /*  if(rowItem.getcheck_status()){
            holder.checkbox_comp.setVisibility(View.VISIBLE);
            holder.checkbox_comp.setChecked(true);
         //   holder.checkbox_comp.setVisibility(View.GONE);

            // holder.stu_code=holder.stu_code+rowItem.getStudentCode();
        }else {
            holder.checkbox_comp.setChecked(false);
          //  holder.checkbox_comp_new.setVisibility(View.GONE);
        }*/





        final Default_Moderator_List_Helper temp = getItem(position);
        final ViewHolder finalHolder = holder;
        holder.checkbox_comp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (customListner != null) {
                    customListner.CheckcustomButtonListener_default_moderator(position, temp,isChecked);
                }
            }
        });

        final Default_Moderator_List_Helper temp1 = getItem(position);
        holder.checkbox_comp_new.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (customListner_2 != null) {
                    customListner_2.onCheckcustomButtonListener_default_moderator_new(position, temp1,isChecked);
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
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        default_moderator_list_helpers.clear();
        if (charText.length() == 0) {
            default_moderator_list_helpers.addAll(arraylist);
        } else {
            for (Default_Moderator_List_Helper wp : arraylist) {
                if (wp.getEmployeeName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    default_moderator_list_helpers.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
