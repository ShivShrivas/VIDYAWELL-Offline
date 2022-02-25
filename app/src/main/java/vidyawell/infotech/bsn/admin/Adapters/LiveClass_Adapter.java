package vidyawell.infotech.bsn.admin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import vidyawell.infotech.bsn.admin.Gifimage.GifDecoderView;
import vidyawell.infotech.bsn.admin.Helpers.LiveClass_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;


public class LiveClass_Adapter extends ArrayAdapter<LiveClass_Helper> {

    LiveClass_Adapter.customButtonListener customListner;
    public interface customButtonListener {
        public void onButtonClickListner(int position, LiveClass_Helper value);
    }

    public void setCustomButtonListner(LiveClass_Adapter.customButtonListener listener) {
        this.customListner = listener;
    }


    //////

    LiveClass_Adapter.customButtonListener1 customListner1;
    public interface customButtonListener1 {
        public void onButtonClickListner1(int position, LiveClass_Helper value);
    }

    public void setCustomButtonListner1(LiveClass_Adapter.customButtonListener1 listener1) {
        this.customListner1 = listener1;
    }


    Context context;

    List<LiveClass_Helper> liveClass_helpers;
    private LayoutInflater inflater;
    ArrayList<LiveClass_Helper> arraylist;

    public LiveClass_Adapter(@NonNull Context context, int resource, List<LiveClass_Helper> liveClass_helpers) {
        super(context, resource,liveClass_helpers);
        this.context = context;
        this.liveClass_helpers=liveClass_helpers;
        this.arraylist= new ArrayList<LiveClass_Helper>();
        this.arraylist.addAll(liveClass_helpers);
    }


    @Override
    public int getCount() {
        return liveClass_helpers.size();
    }
    @Override
    public LiveClass_Helper getItem(int position) {
        return  liveClass_helpers.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder {

        TextView liveclassdate;
        TextView liveclass_topic;
        TextView transection;
        TextView meeting_id,RunningMeetingUrl_id,main_url_id;
        TextView liveclass_details,liveclass_massage,txt_join_more;
        Button  homework_attechment;
        GifDecoderView gif_id;
        LinearLayout layout_joint_more;
        TextView txt_name,txt_meeting_name,txt_time,txt_duration;
        Switch aSwitch_active;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent ) {
        LiveClass_Adapter.ViewHolder holder = null;
        LiveClass_Helper rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.liveclass_listitem, null);
            Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/"+ ServerApiadmin.FONT);
            holder = new LiveClass_Adapter.ViewHolder();
            holder.liveclassdate = (TextView) convertView.findViewById(R.id.liveclassdate);
            holder.liveclass_topic = (TextView) convertView.findViewById(R.id.liveclass_topic);
            holder.liveclass_details = (TextView) convertView.findViewById(R.id.liveclass_details);
            holder.liveclass_massage = (TextView) convertView.findViewById(R.id.liveclass_massage);
            holder.txt_join_more = (TextView) convertView.findViewById(R.id.txt_join_more);
            holder.meeting_id = (TextView) convertView.findViewById(R.id.meeting_id);
            holder.RunningMeetingUrl_id = (TextView) convertView.findViewById(R.id.RunningMeetingUrl_id);
            holder.main_url_id = (TextView) convertView.findViewById(R.id.main_url_id);
            holder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
            holder.txt_meeting_name = (TextView) convertView.findViewById(R.id.txt_meeting_name);
            holder.txt_time = (TextView) convertView.findViewById(R.id.txt_time);
            holder.txt_duration = (TextView) convertView.findViewById(R.id.txt_duration);
            holder.gif_id = (GifDecoderView) convertView.findViewById(R.id.gif_id);
            holder.layout_joint_more = (LinearLayout) convertView.findViewById(R.id.layout_joint_more);
            holder.aSwitch_active = (Switch) convertView.findViewById(R.id.switch_activeclinic);

            //  holder.transection = (TextView) convertView.findViewById(R.id.transactionid);
            // holder.attechment=(TextView)convertView.findViewById(R.id.notice_attechment);
            // holder.homework_attechment = (Button) convertView.findViewById(R.id.homework_attechment1);

            // holder.attechment = (TextView) convertView.findViewById(R.id.attechment);
            try {
                holder.gif_id.playGif(context.getAssets().open("test.gif"));
            } catch (IOException e) {
                e.printStackTrace();
            }


            holder.liveclassdate.setTypeface(typeFace);
            holder.liveclass_topic.setTypeface(typeFace);
            holder.liveclass_massage.setTypeface(typeFace);
            holder.liveclass_details.setTypeface(typeFace);
            holder.txt_join_more.setTypeface(typeFace);
            holder.txt_name.setTypeface(typeFace);
            holder.txt_meeting_name.setTypeface(typeFace);
            holder.txt_time.setTypeface(typeFace);
            holder.txt_duration.setTypeface(typeFace);
            convertView.setTag(holder);
        } else
            holder = (LiveClass_Adapter.ViewHolder) convertView.getTag();

        /* if(rowItem.getAttachment().equals("null")){
             holder.homework_attechment.setVisibility(View.INVISIBLE);
         }else {
             holder.homework_attechment.setVisibility(View.VISIBLE);
         }*/

        // String Attachment= ServerApi.MAIN_IPLINK+rowItem.getAttachment();
        //  Attachment=Attachment.replace("..","");
     /*   holder.liveclassdate.setText(rowItem.getStartDate());
        holder.liveclass_topic.setText(rowItem.getTopic());
        holder.meeting_id.setText(rowItem.getMeetingId());
    //    holder.RunningMeetingUrl_id.setText(rowItem.getRunningMeetingUrl());
        holder.main_url_id.setText(rowItem.geturl());
        holder.liveclass_massage.setText("Time:    "+ rowItem.getStartTime());
        holder.liveclass_details.setText(Html.fromHtml(rowItem.getDescription()));*/

        holder.liveclassdate.setText(rowItem.getStartDate());
        holder.liveclass_topic.setText(rowItem.getTopic());
       // holder.txt_name.setText(rowItem.getName());
        holder.txt_time.setText(rowItem.getStartTime());
        holder.txt_meeting_name.setText(rowItem.getMeetingName()+"  Class");
        holder.txt_duration.setText(rowItem.getDuration()+"   min");
        holder.meeting_id.setText(rowItem.getMeetingId());
       // holder.RunningMeetingUrl_id.setText(rowItem.getRunningMeetingUrl());
        holder.main_url_id.setText(rowItem.geturl());
        holder.liveclass_massage.setText(rowItem.getStartDate());
        holder.liveclass_details.setText(rowItem.getClassName()+"  "+ rowItem.getSectionName());
      //  holder.liveclass_details.setText(Html.fromHtml(rowItem.getClassName()));
        holder.aSwitch_active.setChecked((rowItem.getIsActive()));

      /*  // holder.attechment.setText(Attachment);
        if(holder.aSwitch_active.isChecked()){
          // holder.aSwitch_active.setText("Inactive     ");
            //holder.aSwitch_active.setTextColor(Color.parseColor("#CA0505"));
            holder.aSwitch_active.setChecked(false);
        }else{
    *//*        holder.aSwitch_active.setText("Active     ");
            holder.aSwitch_active.setTextColor(Color.parseColor("#009688"));*//*
            holder.aSwitch_active.setChecked(true);
        }*/


        final LiveClass_Helper temp = getItem(position);
        holder.layout_joint_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
                if (customListner != null) {
                    customListner.onButtonClickListner(position, temp);

                    // Toast.makeText(context, finalHolder.mobile_number.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        final LiveClass_Helper temp1 = getItem(position);
        holder.aSwitch_active.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
                if (customListner1 != null) {
                    customListner1.onButtonClickListner1(position, temp1);



                    // Toast.makeText(context, finalHolder.mobile_number.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });


     /*final LiveClass_Adapter.ViewHolder finalHolder = holder;
        holder.txt_join_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               *//* String url = finalHolder.attechment.getText().toString();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setData(Uri.parse(url));
                context.startActivity(i);*//*


            }
        });*/

        return convertView;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        liveClass_helpers.clear();
        if (charText.length() == 0) {
            liveClass_helpers.addAll(arraylist);
        } else {
            for (LiveClass_Helper wp : arraylist) {
                if (wp.getTopic().toLowerCase(Locale.getDefault())
                        .contains(charText) || wp.getStartDate().toLowerCase(Locale.getDefault()).contains(charText)) {
                    liveClass_helpers.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
