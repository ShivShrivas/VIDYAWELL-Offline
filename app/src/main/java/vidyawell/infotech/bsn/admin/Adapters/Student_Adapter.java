package vidyawell.infotech.bsn.admin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import androidx.annotation.NonNull;
import android.util.Log;
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


import vidyawell.infotech.bsn.admin.ApplicationControllerAdmin;
import vidyawell.infotech.bsn.admin.Helpers.Student_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;

public class Student_Adapter extends ArrayAdapter<Student_Helper> {

    Context context;
    Typeface typeFace;
    List<Student_Helper> student_helpers;
    private LayoutInflater inflater;
    ArrayList<Student_Helper> arraylist;
    ApplicationControllerAdmin applicationControllerAdmin;


    public Student_Adapter(@NonNull Context context, int resource, List<Student_Helper> student_helpers) {
        super(context, resource, student_helpers);
        this.context = context;
        this.student_helpers=student_helpers;
        this.arraylist= new ArrayList<Student_Helper>();
        this.arraylist.addAll(student_helpers);
    }

     @Override
     public int getCount() {
         return student_helpers.size();
     }

     @Override
     public Student_Helper getItem(int position) {
         return student_helpers.get(position);
     }

     @Override
     public long getItemId(int position) {
         return position;
     }
    private class ViewHolder {

        TextView student_names,topic1,topic2,topic3,topic4;
        TextView fother_name;
        TextView mobile_stu,text_stucode,class_name;
        RoundedImageView student_image;
        TextView url_stu;
        Button arrow;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Student_Helper rowItem = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.student_item_list, null);
            typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/" + ServerApiadmin.FONT_DASHBOARD);
            applicationControllerAdmin=(ApplicationControllerAdmin)context.getApplicationContext();
            holder = new ViewHolder();
            holder.student_names = (TextView) convertView.findViewById(R.id.student_names);
            holder.fother_name = (TextView) convertView.findViewById(R.id.fother_name);
            holder.mobile_stu = (TextView) convertView.findViewById(R.id.mobile_stu);
            holder.student_image = (RoundedImageView)convertView.findViewById(R.id.student_image);
            holder.class_name = (TextView) convertView.findViewById(R.id.class_name);
            holder.text_stucode = (TextView) convertView.findViewById(R.id.text_stucode);
            holder.arrow = (Button) convertView.findViewById(R.id.student_status);
            holder.topic1 = (TextView) convertView.findViewById(R.id.topic1);
            holder.topic2 = (TextView) convertView.findViewById(R.id.topic2);
            holder.topic3 = (TextView) convertView.findViewById(R.id.topic_class3);
            holder.topic4 = (TextView) convertView.findViewById(R.id.topic4);
            holder.url_stu=(TextView)convertView.findViewById(R.id.url_stu);
            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();


        holder.student_names.setTypeface(typeFace);
        holder.fother_name.setTypeface(typeFace);
        holder.mobile_stu.setTypeface(typeFace);
        holder.class_name.setTypeface(typeFace);
        holder.topic1.setTypeface(typeFace);
        holder.topic2.setTypeface(typeFace);
        holder.topic3.setTypeface(typeFace);
        holder.topic4.setTypeface(typeFace);

        if(applicationControllerAdmin.getschooltype().equals("0")){
            holder.topic3.setText("Class");
        }else{
            holder.topic3.setText("Course");
        }

        if(rowItem.getatttype().equals("Absent")){
            holder.arrow.setBackgroundResource(R.drawable.ic_absent_list);
        }else if(rowItem.getatttype().equals("Leave")){
            holder.arrow.setBackgroundResource(R.drawable.ic_onleave);
        }else{
            holder.arrow.setBackgroundResource(R.drawable.ic_present);
        }
        holder.student_names.setText(rowItem.getstudent_name());
        holder.fother_name.setText(rowItem.getfother_name());
        holder.mobile_stu.setText(rowItem.getmobile());
        holder.class_name.setText(rowItem.getclass_name());
        holder.text_stucode.setText(rowItem.getstaffcode());
        holder.url_stu.setText(rowItem.getstudent_image());
        String imagerplace= ServerApiadmin.MAIN_IPLINK+holder.url_stu.getText().toString();
        imagerplace=imagerplace.replace("..","");
        holder.student_image.setImageURI(Uri.parse(imagerplace));
        Glide.get(context).clearMemory();
        Glide
                .with(getContext())
                .load(imagerplace)
                .into(holder.student_image);
        Log.d("link",rowItem.getstudent_image());
        return convertView;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        student_helpers.clear();
        if (charText.length() == 0) {
            student_helpers.addAll(arraylist);
        } else {
            for (Student_Helper wp : arraylist) {
                if (wp.getstudent_name().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    student_helpers.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}

