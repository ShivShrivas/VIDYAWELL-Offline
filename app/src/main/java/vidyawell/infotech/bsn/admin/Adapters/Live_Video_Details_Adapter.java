
package vidyawell.infotech.bsn.admin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;

import java.util.List;

import vidyawell.infotech.bsn.admin.ApplicationControllerAdmin;
import vidyawell.infotech.bsn.admin.Full_Screen_Live;
import vidyawell.infotech.bsn.admin.Helpers.Live_Video_Details_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;

public class Live_Video_Details_Adapter extends ArrayAdapter<Live_Video_Details_Helper> {

    Context context;
    Typeface typeFace;
    List<Live_Video_Details_Helper> live_video_details_helpers;
    private LayoutInflater inflater;
    ApplicationControllerAdmin applicationControllerAdmin;

    Live_Video_Details_Adapter.customButtonListener customListner;
    Live_Video_Details_Adapter.customButtonListener1 customListner1;
    Live_Video_Details_Adapter.customButtonListener3 customListner3;


    public interface customButtonListener {
        public void onButtonClickListner(int position, Live_Video_Details_Helper value);
    }

    public void setCustomButtonListner(Live_Video_Details_Adapter.customButtonListener listener) {
        this.customListner = listener;
    }



    public interface customButtonListener1 {
        public void onButtonClickListner2(int position, Live_Video_Details_Helper value);
    }

    public void setCustomButtonListner1(Live_Video_Details_Adapter.customButtonListener1 listener) {
        this.customListner1 = listener;
    }

    public interface customButtonListener3 {
        public void onButtonClickListner3(int position, Live_Video_Details_Helper value);
    }

    public void setCustomButtonListner3(Live_Video_Details_Adapter.customButtonListener3 listener) {
        this.customListner3 = listener;
    }


    public Live_Video_Details_Adapter(@NonNull Context context, int resource, List<Live_Video_Details_Helper> live_video_details_helpers) {
        super(context, resource, live_video_details_helpers);
        this.context = context;
        this.live_video_details_helpers = live_video_details_helpers;
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
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
    }

    private class ViewHolder {


        TextView date,event_title,event_description,transection_id,imageurl_id,approved_id;
        RoundedImageView staff_image3;
        WebView staff_image_vedio;
        LinearLayout full_screen,share_video,layout_save_time;
        Button save_time;
        TextView txt_time;
        TextView video_approve_time;
        ImageView image_deleteproduct;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final Live_Video_Details_Helper rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new ViewHolder();

            typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/" + ServerApiadmin.FONT);
            applicationControllerAdmin=(ApplicationControllerAdmin)context.getApplicationContext();
            convertView = mInflater.inflate(R.layout.item_live_video_details, null);
            holder = new ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.event_title = (TextView) convertView.findViewById(R.id.event_title);
            holder.event_description = (TextView) convertView.findViewById(R.id.event_description);
            holder.transection_id =(TextView) convertView.findViewById(R.id.transection_idd);
            holder.approved_id =(TextView) convertView.findViewById(R.id.approved_id);
            holder.imageurl_id=(TextView)convertView.findViewById(R.id.imageurl_id);
            holder.staff_image_vedio=(WebView) convertView.findViewById(R.id.staff_image_vedio);
            holder.full_screen=(LinearLayout)convertView.findViewById(R.id.full_screen);
            holder.share_video=(LinearLayout)convertView.findViewById(R.id.share_video);
            holder.layout_save_time=(LinearLayout)convertView.findViewById(R.id.layout_save_time);
            holder.save_time=(Button)convertView.findViewById(R.id.save_time);
            holder.txt_time=(TextView)convertView.findViewById(R.id.txt_time);
            holder.video_approve_time=(TextView)convertView.findViewById(R.id.video_approve_time);
            holder.image_deleteproduct=(ImageView) convertView.findViewById(R.id.image_deleteproduct);

            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();



        holder.date.setTypeface(typeFace);
        holder.event_title.setTypeface(typeFace);
        holder.event_description.setTypeface(typeFace);
        holder.txt_time.setTypeface(typeFace);

     //   holder.layout_save_time.setVisibility(View.GONE);

       /* if(applicationControllerAdmin.getActiveempcode().equalsIgnoreCase("2")){
            holder.save_time.setVisibility(View.VISIBLE);
            holder.txt_time.setVisibility(View.VISIBLE);
          //  holder.layout_save_time.setVisibility(View.VISIBLE);


        }else {
            holder.layout_save_time.setVisibility(View.VISIBLE);
            holder.save_time.setVisibility(View.GONE);
            holder.txt_time.setVisibility(View.VISIBLE);
        }*/


       /* if(rowItem.getIsLiveClassApproval().equalsIgnoreCase("1")){
            holder.save_time.setVisibility(View.VISIBLE);
            holder.txt_time.setVisibility(View.VISIBLE);

        }else {
            holder.save_time.setVisibility(View.GONE);
            holder.txt_time.setVisibility(View.VISIBLE);
        }*/



       if (rowItem.getIsLiveClassApproval().equalsIgnoreCase("1")){

           if(applicationControllerAdmin.getLoginType().equalsIgnoreCase("2")){
               holder.save_time.setVisibility(View.VISIBLE);
               holder.txt_time.setVisibility(View.VISIBLE);
           }else {
               holder.save_time.setVisibility(View.GONE);
               holder.txt_time.setVisibility(View.VISIBLE);
           }
       }else {
           holder.save_time.setVisibility(View.GONE);
           holder.txt_time.setVisibility(View.VISIBLE);
       }







             WebSettings webSettings = holder.staff_image_vedio.getSettings();
             webSettings.setJavaScriptEnabled(true);
             holder.staff_image_vedio.loadUrl(rowItem.getVedioUrl());
             holder.event_title.setText(rowItem.getTitle());
             holder.transection_id.setText(rowItem.getTransId());
             holder.txt_time.setText(rowItem.getIsApproved());
             holder.video_approve_time.setText(rowItem.getIsLiveClassApproval());




         holder.full_screen.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(context, Full_Screen_Live.class);
                 intent.putExtra("URL",rowItem.getVedioUrl());
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 context.startActivity(intent);
             }
         });

         holder.share_video.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent sendIntent = new Intent();
                 sendIntent.setAction(Intent.ACTION_SEND);
                 sendIntent.putExtra(Intent.EXTRA_TEXT, rowItem.getVedioUrl());
                 sendIntent.setType("text/plain");
                 sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 context.startActivity(sendIntent);
             }
         });


        final  Live_Video_Details_Helper temp = getItem(position);
        final Live_Video_Details_Adapter.ViewHolder finalHolder = holder;
        final Live_Video_Details_Helper temp2 = getItem(position);
        final Live_Video_Details_Adapter.ViewHolder finalHolder2 = holder;
       /* final  Live_Video_Details_Helper temp1 = getItem(position);
        final Live_Video_Details_Adapter.ViewHolder finalHolder1 = holder;*/
        holder.save_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (customListner != null) {
                    customListner.onButtonClickListner(position, temp);
                   // finalHolder.save_time.setBackgroundColor(Color.parseColor("#009688"));


                    // Toast.makeText(context, finalHolder.mobile_number.getText().toString(), Toast.LENGTH_SHORT).show();
                }


            }
        });


        final  Live_Video_Details_Helper temp3 = getItem(position);
        final Live_Video_Details_Adapter.ViewHolder finalHolder3 = holder;
        holder.image_deleteproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (customListner3 != null) {
                    customListner3.onButtonClickListner3(position, temp3);
                    // finalHolder.save_time.setBackgroundColor(Color.parseColor("#009688"));


                    //Toast.makeText(context, finalHolder.mobile_number.getText().toString(), Toast.LENGTH_SHORT).show();
                }


            }
        });




      /*  holder.txt_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (customListner1 != null) {
                    customListner1.onButtonClickListner2(position, temp2);
                  //  finalHolder.txt_time.setVisibility(View.GONE);
                    finalHolder.save_time.setVisibility(View.VISIBLE);
                  //  finalHolder.txt_time.setBackgroundColor(Color.parseColor("#009688"));


                    // Toast.makeText(context, finalHolder.mobile_number.getText().toString(), Toast.LENGTH_SHORT).show();
                }


            }
        });*/


        return convertView;
    }

}
