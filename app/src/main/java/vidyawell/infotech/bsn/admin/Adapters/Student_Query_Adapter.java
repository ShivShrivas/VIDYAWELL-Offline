package vidyawell.infotech.bsn.admin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import vidyawell.infotech.bsn.admin.Helpers.Circular_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Student_Query_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;

public class Student_Query_Adapter extends ArrayAdapter<Student_Query_Helper> {

    ArrayList<Student_Query_Helper> arraylist;

    Context context;
    Typeface typeFace;
    List<Student_Query_Helper> student_query_helpers;
    private LayoutInflater inflater;


    public Student_Query_Adapter(@NonNull Context context, int resource, List<Student_Query_Helper> student_query_helpers) {
        super(context, resource, student_query_helpers);
        this.context = context;
        this.student_query_helpers=student_query_helpers;
        this.arraylist= new ArrayList<Student_Query_Helper>();
        this.arraylist.addAll(student_query_helpers);
    }

    @Override
    public int getCount() {
        return student_query_helpers.size();
    }

    @Override
    public Student_Query_Helper getItem(int position) {
        return  student_query_helpers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;


    }
    private class ViewHolder {

        TextView teachers_names,studentcode;
        TextView qulification;
        TextView subject_history;
        RoundedImageView teachers_image;
        TextView mobile_number,contact_number;
        Button teachres_call;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Student_Query_Helper rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new Student_Query_Adapter.ViewHolder();
            convertView = mInflater.inflate(R.layout.student_query_item_list, null);
            typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/" + ServerApiadmin.FONT);
            holder = new ViewHolder();
            holder.teachers_names = (TextView) convertView.findViewById(R.id.teachers_names);
            holder.subject_history = (TextView) convertView.findViewById(R.id.subject_history);
            holder.teachers_image = (RoundedImageView) convertView.findViewById(R.id.teachers_image);
            holder.mobile_number = (TextView) convertView.findViewById(R.id.mobile_number);
            holder.teachres_call = (Button) convertView.findViewById(R.id.teachres_call);
            holder.contact_number = (TextView) convertView.findViewById(R.id.contact_number);
            holder.studentcode=convertView.findViewById(R.id.studentcode);

            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();


        holder.teachers_names.setTypeface(typeFace);
        holder.subject_history.setTypeface(typeFace);
        holder.contact_number.setTypeface(typeFace);
        holder.teachers_names.setText(rowItem.getFullName());
       // holder.qulification.setText(rowItem.getqulification());
        holder.contact_number.setText("Father: "+rowItem.getFatherName());
        holder.subject_history.setText("Mother: "+rowItem.getMotherName());//"Father: "+rowItem.getFatherName());
        holder.mobile_number.setText(rowItem.getGuardianContactNo());
        holder.studentcode.setText(rowItem.getStudentCode());
        String imagerplace= ServerApiadmin.MAIN_IPLINK+rowItem.getStudentPhoto();
        imagerplace=imagerplace.replace("..","");
        holder.teachers_image.setImageURI(Uri.parse(imagerplace));
        Glide.get(context).clearMemory();
        Glide
                .with(getContext())
                .load(imagerplace)
                .into(holder.teachers_image);




        return convertView;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        student_query_helpers.clear();
        if (charText.length() == 0) {
            student_query_helpers.addAll(arraylist);
        } else {
            for (Student_Query_Helper wp : arraylist) {
                if (wp.getFullName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    student_query_helpers.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
