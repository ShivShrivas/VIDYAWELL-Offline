package vidyawell.infotech.bsn.admin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vidyawell.infotech.bsn.admin.Helpers.HomeworkDetails_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;

public class HomeworkDetails_Adapter extends ArrayAdapter<HomeworkDetails_Helper> {


    customButtonListener customListner;
    public interface customButtonListener {
        public void onButtonClickListner(int position, HomeworkDetails_Helper value);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

    customButtonListener1 customListner1;
    public interface customButtonListener1 {
        public void onButtonClickListner1(int position, HomeworkDetails_Helper value);
    }

    public void setCustomButtonListner1(customButtonListener1 listener1) {
        this.customListner1 = listener1;
    }




    ArrayList<HomeworkDetails_Helper> arraylist;

    Context context;
    Typeface typeFace;
    List<HomeworkDetails_Helper> homeworkDetails_helpers;
    private LayoutInflater inflater;


    public HomeworkDetails_Adapter(@NonNull Context context, int resource, List<HomeworkDetails_Helper> homeworkDetails_helpers) {
        super(context, resource, homeworkDetails_helpers);
        this.context = context;
       // this.homeworkDetails_helpers=homeworkDetails_helpers;
       // this.arraylist= new ArrayList<HomeworkDetails_Helper>();
       // this.arraylist.addAll(homeworkDetails_helpers);
    }

  /*  @Override
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


    }*/

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



    private class ViewHolder {

        TextView txt_submit,stu_name_homework,txt_date,txt_tran_id,txt_IsHomeworkSubmitted_id;
        Button homework_chekd,homework_unchekd;
        ImageView imageview_attachemnt,img_remark;
        TextView txt_roll_no,txt_fother_name;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        HomeworkDetails_Helper rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new HomeworkDetails_Adapter.ViewHolder();
            convertView = mInflater.inflate(R.layout.item_homeworkdetails, null);
            typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/" + ServerApiadmin.FONT);
            holder = new ViewHolder();
            holder.stu_name_homework = (TextView) convertView.findViewById(R.id.stu_name_homework);
            holder.txt_submit = (TextView) convertView.findViewById(R.id.txt_submit);
            holder.txt_date = (TextView) convertView.findViewById(R.id.txt_date);
            holder.txt_tran_id = (TextView) convertView.findViewById(R.id.txt_trans_id);
            holder.txt_IsHomeworkSubmitted_id = (TextView) convertView.findViewById(R.id.txt_IsHomeworkSubmitted_id);
            //holder.txt_vouch_id = (TextView) convertView.findViewById(R.id.txt_vouch_id);
           // holder.IsSubmitted_id = (TextView) convertView.findViewById(R.id.IsSubmitted_id);
            //holder.SendNotification_id = (TextView) convertView.findViewById(R.id.SendNotification_id);
           // holder.homework_chekd = (Button) convertView.findViewById(R.id.homework_chekd);
           // holder.homework_unchekd = (Button) convertView.findViewById(R.id.homework_unchekd);
            holder.imageview_attachemnt = (ImageView) convertView.findViewById(R.id.imageview_attachemnt);
            holder.img_remark = (ImageView) convertView.findViewById(R.id.img_remark);
            holder.txt_roll_no = (TextView) convertView.findViewById(R.id.txt_roll_no);
            holder.txt_fother_name = (TextView) convertView.findViewById(R.id.txt_fother_name);
            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();


        holder.stu_name_homework.setTypeface(typeFace);
        holder.txt_submit.setTypeface(typeFace);
        holder.txt_date.setTypeface(typeFace);


        holder.stu_name_homework.setText(rowItem.getStudentName());
        holder.txt_date.setText(rowItem.getSubmissionDate());
        //holder.txt_tran_id.setText(rowItem.getTransId());
        holder.txt_submit.setText(rowItem.getIsSubmitted());
        holder.txt_roll_no.setText("StudentId:  "  + rowItem.getStudentId());
        holder.txt_fother_name.setText("Father: "  + rowItem.getFatherName());


      if(rowItem.getIsSubmitted().equalsIgnoreCase("Verified")){
          ////visible
          final HomeworkDetails_Helper temp1 = getItem(position);
          holder.img_remark.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if (customListner1 != null) {
                      customListner1.onButtonClickListner1(position, temp1);

                      // Toast.makeText(context, finalHolder.mobile_number.getText().toString(), Toast.LENGTH_SHORT).show();
                  }
              }
          });
          holder.img_remark.setImageResource(R.drawable.ic_commentary);
        //  holder.img_remark.setVisibility(View.VISIBLE);
        }else {
        ////  gone
          holder.img_remark.setImageResource(R.drawable.ic_commentry2);
         // holder.img_remark.setVisibility(View.INVISIBLE);
      }




        if(rowItem.IsHomeworkSubmitted.equalsIgnoreCase("1")){
            final HomeworkDetails_Helper temp = getItem(position);
            holder.imageview_attachemnt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
                    if (customListner != null) {
                        customListner.onButtonClickListner(position, temp);

                        // Toast.makeText(context, finalHolder.mobile_number.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.imageview_attachemnt.setImageResource(R.drawable.ic_view);
            holder.imageview_attachemnt.setFocusable(true);
        }else {
            holder.imageview_attachemnt.setImageResource(R.drawable.ic_invisible);
            holder.imageview_attachemnt.setFocusable(false);
        }




       /* final HomeworkDetails_Helper temp = getItem(position);
        holder.imageview_attachemnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
                if (customListner != null) {
                    customListner.onButtonClickListner(position, temp);

                    // Toast.makeText(context, finalHolder.mobile_number.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });*/

/*
        final HomeworkDetails_Helper temp = getItem(position);
        final HomeworkDetails_Adapter.ViewHolder finalHolder = holder;
        final HomeworkDetails_Helper temp1 = getItem(position);
        final HomeworkDetails_Adapter.ViewHolder finalHolder1 = holder;
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
        });*/

        return convertView;
    }
/*    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        HomeworkDetails_Helper.clear();
        if (charText.length() == 0) {
            HomeworkDetails_Helper.addAll(arraylist);
        } else {
            for (HomeworkDetails_Helper wp : arraylist) {
                if (wp.getTopicName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    homeworkDetails_helpers.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }*/
}
