package vidyawell.infotech.bsn.admin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import vidyawell.infotech.bsn.admin.Helpers.Homework_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;


public class Homework_Insert_Adapter extends ArrayAdapter<Homework_Helper> {

    Context context;
    List<Homework_Helper> homework_helpers;
    private LayoutInflater inflater;

    public Homework_Insert_Adapter(@NonNull Context context, int resource, List<Homework_Helper> homework_helpers) {
        super(context, resource,homework_helpers);
        this.context = context;
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

        TextView class_homework,subject_homework,section_homework,homework_topic;
        ImageView imageview_attachemnt,txt_attachemnt_vedio;
        TextView txt_transection_id,txt_vacher_no;


    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent ) {
        Homework_Insert_Adapter.ViewHolder holder = null;
        final Homework_Helper rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_homework_insert, null);
            Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/"+ ServerApiadmin.FONT);
            holder = new Homework_Insert_Adapter.ViewHolder();
            holder.class_homework = (TextView) convertView.findViewById(R.id.class_homework);
            holder.subject_homework = (TextView) convertView.findViewById(R.id.subject_homework);
            holder.section_homework = (TextView) convertView.findViewById(R.id.section_homework);
            holder.homework_topic=(TextView)convertView.findViewById(R.id.homework_topic);
            holder.txt_transection_id=(TextView)convertView.findViewById(R.id.txt_transection_id);
            holder.txt_vacher_no=(TextView)convertView.findViewById(R.id.txt_vacher_no);
            holder.imageview_attachemnt=convertView.findViewById(R.id.imageview_attachemnt);
            holder.txt_attachemnt_vedio=(ImageView) convertView.findViewById(R.id.txt_attachemnt_vedio);

            holder.class_homework.setTypeface(typeFace);
            holder.subject_homework.setTypeface(typeFace);
            holder.section_homework.setTypeface(typeFace);
            holder.homework_topic.setTypeface(typeFace);

            convertView.setTag(holder);





        } else

            holder = (Homework_Insert_Adapter.ViewHolder) convertView.getTag();


       if(position %2 == 1)
        {
            // do something change color
            convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));

        }
        else
        {
            // default state
            convertView.setBackgroundColor(Color.parseColor("#FFCCCCCC"));
        }


        if(rowItem.getVedioPath().length()>5){
          //  holder.txt_attachemnt_vedio.setVisibility(View.INVISIBLE);
            holder.txt_attachemnt_vedio.setVisibility(View.VISIBLE);
            holder.txt_attachemnt_vedio.setImageResource(R.drawable.ic_youtube);
        }else {
            holder.txt_attachemnt_vedio.setVisibility(View.INVISIBLE);
           /* holder.txt_attachemnt_vedio.setVisibility(View.VISIBLE);
            holder.txt_attachemnt_vedio.setImageResource(R.drawable.ic_youtube);*/


        }

        if(rowItem.getimagerplace().length()>5){
            holder.imageview_attachemnt.setVisibility(View.VISIBLE);
            holder.imageview_attachemnt.setImageResource(R.drawable.attachment);

        }else{
            holder.imageview_attachemnt.setVisibility(View.INVISIBLE);


        }

            holder.class_homework.setText(rowItem.getClassName());
            holder.subject_homework.setText(rowItem.getSubjectName());
            holder.section_homework.setText(rowItem.getSectionName());
            holder.homework_topic.setText(rowItem.getTopicName());
            holder.txt_transection_id.setText(rowItem.getTransId());
            holder.txt_vacher_no.setText(rowItem.getVoucherNo());



        holder.imageview_attachemnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = rowItem.getimagerplace();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });

            holder.txt_attachemnt_vedio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = rowItem.getVedioPath();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
                }
            });



        return convertView;
    }

}
