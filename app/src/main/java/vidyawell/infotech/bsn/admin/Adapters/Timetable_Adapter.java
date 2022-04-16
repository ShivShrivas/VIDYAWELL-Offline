package vidyawell.infotech.bsn.admin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vidyawell.infotech.bsn.admin.Helpers.Timetable_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;


public class Timetable_Adapter extends ArrayAdapter<Timetable_Helper> {

    Context context;
    List<Timetable_Helper> homework_list;
    private LayoutInflater inflater;

    public Timetable_Adapter(@NonNull Context context, int resource, List<Timetable_Helper> timetable_helpers) {
        super(context, resource,timetable_helpers);
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
        ImageView imageView;
        TextView discptiontest;
        TextView txtDesc;
        TextView textLec,lunch_tag;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent ) {
        ViewHolder holder = null;
        Timetable_Helper rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.timetable_list_item, null);
            Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/"+ ServerApiadmin.FONT);
            Typeface typeFace1 = Typeface.createFromAsset(context.getAssets(),
                    "fonts/SqueakyChalkSound.ttf");
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.subject);
            holder.textLec = (TextView) convertView.findViewById(R.id.teacher_name);
            holder.imageView = (ImageView) convertView.findViewById(R.id.timetable_image);
            holder.discptiontest = (TextView) convertView.findViewById(R.id.lecture_time);
            holder.lunch_tag=(TextView)convertView.findViewById(R.id.lunch_tag);
            holder.txtDesc.setTypeface(typeFace);
            holder.textLec.setTypeface(typeFace);
            holder.discptiontest.setTypeface(typeFace);
            holder.lunch_tag.setTypeface(typeFace1);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        if(rowItem.getPeriodType().equals("LunchPeriod")){
            holder.txtDesc.setVisibility(View.GONE);
            holder.textLec.setVisibility(View.GONE);
            holder.discptiontest.setVisibility(View.GONE);
            holder.imageView.setImageResource(rowItem.getimagevalue());
            holder.lunch_tag.setText("Lunch Break ");
            holder.lunch_tag.setVisibility(View.VISIBLE);
        }else{
            holder.lunch_tag.setVisibility(View.GONE);
            holder.txtDesc.setVisibility(View.VISIBLE);
            holder.textLec.setVisibility(View.VISIBLE);
            holder.discptiontest.setVisibility(View.VISIBLE);
            holder.txtDesc.setText(rowItem.getHomelistvalue());
            holder.textLec.setText(rowItem.getlecvalue());
            holder.discptiontest.setText(rowItem.getdescvalue());
            holder.imageView.setImageResource(rowItem.getimagevalue());

            if(rowItem.getHomelistvalue().equals("")){
                holder.lunch_tag.setText("Free Lecture");
                holder.txtDesc.setVisibility(View.GONE);
                holder.textLec.setVisibility(View.GONE);
                holder.discptiontest.setVisibility(View.GONE);
                holder.lunch_tag.setVisibility(View.VISIBLE);
            }
        }
        return convertView;
    }

}
