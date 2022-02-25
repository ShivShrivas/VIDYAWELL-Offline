package vidyawell.infotech.bsn.admin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vidyawell.infotech.bsn.admin.ApplicationControllerAdmin;
import vidyawell.infotech.bsn.admin.Helpers.Homework_Topic_Submit_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;

public class Homework_Topic_Submit_Adapter extends ArrayAdapter<Homework_Topic_Submit_Helper> {


    customButtonListener customListner;
    public interface customButtonListener {
        public void onButtonClickListner(int position, Homework_Topic_Submit_Helper value);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

    customButtonListener1 customListner1;
    public interface customButtonListener1 {
        public void onButtonClickListner1(int position, Homework_Topic_Submit_Helper value);
    }

    public void setCustomButtonListner1(customButtonListener1 listener1) {
        this.customListner1 = listener1;
    }





    ArrayList<Homework_Topic_Submit_Helper> arraylist;

    Context context;
    Typeface typeFace;
    List<Homework_Topic_Submit_Helper> homework_topic_submit_helpers;
    private LayoutInflater inflater;
    ApplicationControllerAdmin applicationController;

    public Homework_Topic_Submit_Adapter(@NonNull Context context, int resource, List<Homework_Topic_Submit_Helper> homework_topic_submit_helpers) {
        super(context, resource, homework_topic_submit_helpers);
        this.context = context;
        this.homework_topic_submit_helpers=homework_topic_submit_helpers;
        this.arraylist= new ArrayList<Homework_Topic_Submit_Helper>();
        this.arraylist.addAll(homework_topic_submit_helpers);
    }

    @Override
    public int getCount() {
        return homework_topic_submit_helpers.size();
    }

    @Override
    public Homework_Topic_Submit_Helper getItem(int position) {
        return  homework_topic_submit_helpers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;


    }





    ///////////////////////Data not refresh///////////////////////////////////

   /* @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }*/



    private class ViewHolder {

        TextView stu_name,stu_class,txt_subject_name,txt_topic_name,txt_tran_id,txt_vouch_id,IsSubmitted_id,SendNotification_id;
        Button homework_chekd,homework_unchekd;



    }







    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Homework_Topic_Submit_Helper rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new Homework_Topic_Submit_Adapter.ViewHolder();
            convertView = mInflater.inflate(R.layout.item_homework_notification_submit, null);
            typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/" + ServerApiadmin.FONT);
           // holder = new ViewHolder();
            holder = new Homework_Topic_Submit_Adapter.ViewHolder();
            applicationController=(ApplicationControllerAdmin)context.getApplicationContext();

            holder.stu_name = (TextView) convertView.findViewById(R.id.stu_name);
            holder.stu_class = (TextView) convertView.findViewById(R.id.stu_class);
           // holder.txt_subject_name = (TextView) convertView.findViewById(R.id.txt_subject_name);
            holder.txt_topic_name = (TextView) convertView.findViewById(R.id.txt_topic_name);
            holder.txt_tran_id = (TextView) convertView.findViewById(R.id.txt_tran_id);
            holder.txt_vouch_id = (TextView) convertView.findViewById(R.id.txt_vouch_id);
            holder.IsSubmitted_id = (TextView) convertView.findViewById(R.id.IsSubmitted_id);
            holder.SendNotification_id = (TextView) convertView.findViewById(R.id.SendNotification_id);
            holder.homework_chekd = (Button) convertView.findViewById(R.id.homework_chekd);
            holder.homework_unchekd = (Button) convertView.findViewById(R.id.homework_unchekd);
            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();


        holder.stu_name.setTypeface(typeFace);
        holder.txt_topic_name.setTypeface(typeFace);
       // holder.homework_chekd.setTypeface(typeFace);
        holder.stu_name.setText("Name:  "+rowItem.getStudentName());
        holder.stu_class.setText(rowItem.getStudentCode());
        //holder.txt_subject_name.setText(rowItem.getTopicName());
        holder.txt_topic_name.setText("TopicName:  "+rowItem.getTopicName());
        holder.txt_tran_id.setText(rowItem.getTransId());
        holder.txt_vouch_id.setText(rowItem.getVoucherNo());
        holder.IsSubmitted_id.setText(rowItem.getIsSubmitted());
        holder.SendNotification_id.setText(rowItem.getSendNotification());


          if(rowItem.getIsSubmitted().equalsIgnoreCase("1")){
              holder.homework_chekd.setBackgroundColor(Color.parseColor("#20dc80"));
              holder.homework_chekd.setTextColor(Color.parseColor("#ffffff"));
              holder.homework_unchekd.setBackgroundColor(Color.parseColor("#ffffff"));
              holder.homework_unchekd.setTextColor(Color.parseColor("#000000"));
          }else {
              holder.homework_unchekd.setBackgroundColor(Color.parseColor("#ed1f1f"));
              holder.homework_unchekd.setTextColor(Color.parseColor("#ffffff"));
              holder.homework_chekd.setBackgroundColor(Color.parseColor("#ffffff"));
              holder.homework_chekd.setTextColor(Color.parseColor("#000000"));
          }

        final Homework_Topic_Submit_Helper temp = getItem(position);
        final Homework_Topic_Submit_Adapter.ViewHolder finalHolder = holder;
        final Homework_Topic_Submit_Helper temp1 = getItem(position);
        final Homework_Topic_Submit_Adapter.ViewHolder finalHolder1 = holder;
        holder.homework_chekd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               // Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
                if (customListner != null) {
                    customListner.onButtonClickListner(position, temp);
                    finalHolder.homework_chekd.setBackgroundColor(Color.parseColor("#20dc80"));
                    finalHolder.homework_chekd.setTextColor(Color.parseColor("#ffffff"));
                    finalHolder1.homework_unchekd.setBackgroundColor(Color.parseColor("#ffffff"));
                    finalHolder1.homework_unchekd.setTextColor(Color.parseColor("#000000"));
                    // Toast.makeText(context, finalHolder.mobile_number.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.homework_unchekd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
                if (customListner1 != null) {
                    customListner1.onButtonClickListner1(position, temp1);
                    finalHolder1.homework_unchekd.setBackgroundColor(Color.parseColor("#ed1f1f"));
                    finalHolder1.homework_unchekd.setTextColor(Color.parseColor("#ffffff"));
                    finalHolder.homework_chekd.setBackgroundColor(Color.parseColor("#ffffff"));
                    finalHolder.homework_chekd.setTextColor(Color.parseColor("#000000"));
                    // Toast.makeText(context, finalHolder.mobile_number.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        homework_topic_submit_helpers.clear();
        if (charText.length() == 0) {
            homework_topic_submit_helpers.addAll(arraylist);
        } else {
            for (Homework_Topic_Submit_Helper wp : arraylist) {
                if (wp.getTopicName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    homework_topic_submit_helpers.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
