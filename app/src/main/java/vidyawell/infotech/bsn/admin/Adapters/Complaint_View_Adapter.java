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

import vidyawell.infotech.bsn.admin.Helpers.Complaint_View_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;


public class Complaint_View_Adapter extends ArrayAdapter<Complaint_View_Helper> {
        Context context;
        ArrayList<Complaint_View_Helper> arraylist;
        List<Complaint_View_Helper> complaint_view_helpers;
        private LayoutInflater inflater;

    CheckcustomButtonListener customListner;
    public interface CheckcustomButtonListener {
        public void onCheckClickListner(int position, Complaint_View_Helper value,boolean isChecked);
    }
    public void setCheckcustomButtonListener(CheckcustomButtonListener listener) {
        this.customListner = listener;
    }

public Complaint_View_Adapter(@NonNull Context context, int resource, List<Complaint_View_Helper> complaint_view_helpers) {
        super(context, resource,complaint_view_helpers);
        this.context = context;
    this.complaint_view_helpers=complaint_view_helpers;
    this.arraylist= new ArrayList<Complaint_View_Helper>();
    this.arraylist.addAll(complaint_view_helpers);
        }

    @Override
    public int getCount() {
        return complaint_view_helpers.size();
    }

    @Override
    public Complaint_View_Helper getItem(int position) {
        return  complaint_view_helpers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

private class ViewHolder {
    TextView student_name,father_name,student_count,student_ID,Roll_no;
    CheckBox checkbox_comp;

}
    @Override
    public View getView(final int position, View convertView, ViewGroup parent ) {
        Complaint_View_Adapter.ViewHolder holder = null;
        Complaint_View_Helper rowItem = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_complaint_view, null);
            Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/"+ ServerApiadmin.FONT_DASHBOARD);
            holder = new Complaint_View_Adapter.ViewHolder();
           // holder = new ViewHolder();
            holder.student_name = (TextView)convertView.findViewById(R.id.student_name);
            holder.father_name = (TextView)convertView.findViewById(R.id.father_name);
            holder.student_count=(TextView)convertView.findViewById(R.id.student_count);
            holder.student_ID=(TextView)convertView.findViewById(R.id.student_ID);
            holder.checkbox_comp=(CheckBox)convertView.findViewById(R.id.checkbox_comp);
            holder.Roll_no=(TextView)convertView.findViewById(R.id.stu_roolnumberc);
            holder.student_name.setTypeface(typeFace);
            holder.father_name.setTypeface(typeFace);
            holder.student_count.setTypeface(typeFace);
            holder.Roll_no.setTypeface(typeFace);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();


        holder.student_name.setText(rowItem.getstudent_name());
        holder.father_name.setText("Father's Name: "+rowItem.getfather_name());
        holder.student_count.setText(rowItem.getCont());
        holder.student_ID.setText(rowItem.getStudentCode());
        holder.checkbox_comp.setChecked(rowItem.getcheck_status());
        holder.Roll_no.setText("Roll No.: "+"Not Assigned");
        final Complaint_View_Helper temp = getItem(position);
        holder.checkbox_comp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (customListner != null) {
                    customListner.onCheckClickListner(position, temp,isChecked);

                }
            }
        });
        return convertView;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        complaint_view_helpers.clear();
        if (charText.length() == 0) {
            complaint_view_helpers.addAll(arraylist);
        } else {
            for (Complaint_View_Helper wp : arraylist) {
                if (wp.getstudent_name().toLowerCase(Locale.getDefault()).contains(charText)) {
                    complaint_view_helpers.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
