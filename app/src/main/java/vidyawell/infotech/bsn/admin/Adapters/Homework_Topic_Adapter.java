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

import vidyawell.infotech.bsn.admin.Helpers.Class_Attendance_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Homework_Topic_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Student_Query_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;

public class Homework_Topic_Adapter extends ArrayAdapter<Homework_Topic_Helper> {


    ArrayList<Homework_Topic_Helper> arraylist;

    Context context;
    Typeface typeFace;
    List<Homework_Topic_Helper> homework_topic_helpers;
    private LayoutInflater inflater;


    public Homework_Topic_Adapter(@NonNull Context context, int resource, List<Homework_Topic_Helper> homework_topic_helpers) {
        super(context, resource, homework_topic_helpers);
        this.context = context;
        this.homework_topic_helpers=homework_topic_helpers;
        this.arraylist= new ArrayList<Homework_Topic_Helper>();
        this.arraylist.addAll(homework_topic_helpers);
    }

    @Override
    public int getCount() {
        return homework_topic_helpers.size();
    }

    @Override
    public Homework_Topic_Helper getItem(int position) {
        return  homework_topic_helpers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;


    }
    private class ViewHolder {

        TextView txt_topic,txt_topic_date,txt_section_id,txt_class_id,txt_emp_code;



    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Homework_Topic_Helper rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new Homework_Topic_Adapter.ViewHolder();
            convertView = mInflater.inflate(R.layout.homework_item_topic, null);
            typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/" + ServerApiadmin.FONT);
            holder = new ViewHolder();
            holder.txt_topic = (TextView) convertView.findViewById(R.id.txt_topic);
            holder.txt_topic_date = (TextView) convertView.findViewById(R.id.txt_topic_date);
            holder.txt_section_id = (TextView) convertView.findViewById(R.id.txt_section_id);
            holder.txt_class_id = (TextView) convertView.findViewById(R.id.txt_class_id);
            holder.txt_emp_code = (TextView) convertView.findViewById(R.id.txt_emp_code);

            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();


        holder.txt_topic.setTypeface(typeFace);
        holder.txt_topic_date.setTypeface(typeFace);
        //holder.txt_section_id.setTypeface(typeFace);

        holder.txt_topic.setText(rowItem.getTopicName());
       // holder.txt_topic_date.setText(rowItem.getHomeWorkShowDate());
       // holder.txt_section_id.setText(rowItem.getSectionId());
        //holder.txt_class_id.setText(rowItem.getClassId());
       // holder.txt_emp_code.setText(rowItem.getEmployeeCode());
        //holder.txt_topic_date.setText(rowItem.getqulification());
        //holder.contact_number.setText("Father: "+rowItem.getFatherName());
       // holder.subject_history.setText("Mother: "+rowItem.getMotherName());//"Father: "+rowItem.getFatherName());


        /*String imagerplace= ServerApiadmin.MAIN_IPLINK+rowItem.getStudentPhoto();
        imagerplace=imagerplace.replace("..","");
        holder.teachers_image.setImageURI(Uri.parse(imagerplace));
        Glide.get(context).clearMemory();
        Glide
                .with(getContext())
                .load(imagerplace)
                .into(holder.teachers_image);*/




        return convertView;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        homework_topic_helpers.clear();
        if (charText.length() == 0) {
            homework_topic_helpers.addAll(arraylist);
        } else {
            for (Homework_Topic_Helper wp : arraylist) {
                if (wp.getTopicName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    homework_topic_helpers.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
