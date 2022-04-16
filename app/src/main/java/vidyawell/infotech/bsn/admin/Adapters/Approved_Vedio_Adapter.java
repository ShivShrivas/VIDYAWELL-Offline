package vidyawell.infotech.bsn.admin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vidyawell.infotech.bsn.admin.Helpers.Approved_Vedio_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Class_Attendance_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Homework_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;


public class Approved_Vedio_Adapter extends ArrayAdapter<Approved_Vedio_Helper> {

    Context context;
    List<Approved_Vedio_Helper> approved_vedio_helpers;
    private LayoutInflater inflater;

    Approved_Vedio_Adapter.customButtonListener customListner;


    public interface customButtonListener {
        public void onButtonClickListner(int position, Approved_Vedio_Helper value);
    }

    public void setCustomButtonListner(Approved_Vedio_Adapter.customButtonListener listener) {
        this.customListner = listener;
    }

    public Approved_Vedio_Adapter( Context context, int resource, List<Approved_Vedio_Helper> approved_vedio_helpers) {
        super(context, resource,approved_vedio_helpers);
        this.context = context;
        this.approved_vedio_helpers = approved_vedio_helpers;
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    ///////////////////////Data not refresh///////////////////////////////////

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
    /* @Override
     public int getCount() {
         return 0;
     }

     @Override
     public Homwork_Helper getItem(int position) {
         return  homework_list.get(position);
     }

     @Override
     public long getItemId(int position) {
         return 0;
     }*/
    private class ViewHolder {

        TextView txt_transection_id,txt_vacher_no,txt_is_approval_video;
        ImageView imageview_attachemnt,txt_path_link;
        Button btn_approved_vedio;
        TextView txt_link,txt_discrtpn_name,txt_topic_name;


    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent ) {
        Approved_Vedio_Adapter.ViewHolder holder = null;
        final Approved_Vedio_Helper rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_approve_vedio, null);
            Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/"+ ServerApiadmin.FONT);
            holder = new Approved_Vedio_Adapter.ViewHolder();
            holder.txt_topic_name=(TextView)convertView.findViewById(R.id.txt_topic_name);
            holder.txt_discrtpn_name=(TextView)convertView.findViewById(R.id.txt_discrtpn_name);
            holder.txt_transection_id=(TextView)convertView.findViewById(R.id.txt_transection_id);
            holder.txt_vacher_no=(TextView)convertView.findViewById(R.id.txt_vacher_no);
            holder.txt_is_approval_video=(TextView)convertView.findViewById(R.id.txt_is_approval_video);
            holder.txt_link=(TextView)convertView.findViewById(R.id.txt_link);
            holder.txt_path_link=(ImageView)convertView.findViewById(R.id.txt_path_link);
            holder.btn_approved_vedio=(Button) convertView.findViewById(R.id.btn_approved_vedio);

          //  holder.txt_path_link.setTypeface(typeFace);


            convertView.setTag(holder);





        } else

            holder = (Approved_Vedio_Adapter.ViewHolder) convertView.getTag();




           /* holder.class_homework.setText(rowItem.getClassName());
            holder.subject_homework.setText(rowItem.getSubjectName());
            holder.section_homework.setText(rowItem.getSectionName());
            holder.homework_topic.setText(rowItem.getTopicName());*/
        //   holder.txt_path_link.setImageResource(Integer.parseInt(rowItem.getVedioPath()));
           holder.txt_is_approval_video.setText(rowItem.getIsVedioApproval());
           holder.txt_topic_name.setText(rowItem.getSubjectName());
           holder.txt_discrtpn_name.setText(rowItem.getTopicName());

           if(rowItem.getIsVedioApproval().equalsIgnoreCase("1")){
               holder.btn_approved_vedio.setVisibility(View.VISIBLE);
               holder.txt_path_link.setVisibility(View.VISIBLE);
               holder.txt_path_link.setImageResource(R.drawable.ic_youtube);
           }else {
               holder.btn_approved_vedio.setVisibility(View.GONE);
               holder.txt_path_link.setVisibility(View.VISIBLE);

           }

           /* if(rowItem.getVedioPath().length()>5){
               holder.txt_path_link.setVisibility(View.VISIBLE);
               holder.txt_path_link.setImageResource(R.drawable.ic_youtube);

            }else {
                holder.txt_path_link.setVisibility(View.INVISIBLE);

            }*/





            holder.txt_path_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = rowItem.getVedioPath();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
                }
            });


         final  Approved_Vedio_Helper temp = getItem(position);
        final ViewHolder finalHolder = holder;
        holder.btn_approved_vedio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (customListner != null) {
                        customListner.onButtonClickListner(position, temp);
                        finalHolder.btn_approved_vedio.setBackgroundColor(Color.parseColor("#009688"));


                        // Toast.makeText(context, finalHolder.mobile_number.getText().toString(), Toast.LENGTH_SHORT).show();
                    }


                }
            });

        return convertView;
    }

}
