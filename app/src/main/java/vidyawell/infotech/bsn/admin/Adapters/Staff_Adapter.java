package vidyawell.infotech.bsn.admin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import androidx.annotation.NonNull;
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

import vidyawell.infotech.bsn.admin.Helpers.Staff_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;

public class Staff_Adapter extends ArrayAdapter<Staff_Helper> {
    Context context;
    Typeface typeFace;
    List<Staff_Helper> staff_helpers;
    private LayoutInflater inflater;
    ArrayList<Staff_Helper> arraylist;

    public Staff_Adapter(@NonNull Context context, int resource, List<Staff_Helper> staff_helpers) {
        super(context, resource, staff_helpers);
        this.context = context;
        this.staff_helpers=staff_helpers;
        this.arraylist= new ArrayList<Staff_Helper>();
        this.arraylist.addAll(staff_helpers);
    }


     @Override
     public int getCount() {
        return staff_helpers.size();
     }

     @Override
     public Staff_Helper getItem(int position) {
         return  staff_helpers.get(position);
     }

     @Override
     public long getItemId(int position) {
         return position;
     }


    private class ViewHolder {
        TextView staff_names,topic1,topic2,topic3,topic4;
        TextView qulification;
        TextView mobile,Empcode;
        RoundedImageView staff_image;
        TextView subject,url;
        Button arrow;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Staff_Helper rowItem = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new Staff_Adapter.ViewHolder();
            convertView = mInflater.inflate(R.layout.staff_item_list, null);
            typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/" + ServerApiadmin.FONT_DASHBOARD);
            holder = new ViewHolder();
            holder.staff_names = (TextView) convertView.findViewById(R.id.staff_names);
            holder.qulification = (TextView) convertView.findViewById(R.id.qulification);
            holder.mobile = (TextView) convertView.findViewById(R.id.mobile);
            holder.staff_image = (RoundedImageView) convertView.findViewById(R.id.staff_image);
            holder.subject = (TextView) convertView.findViewById(R.id.subject);
            holder.Empcode = (TextView) convertView.findViewById(R.id.text_empcode);
            holder.arrow = (Button) convertView.findViewById(R.id.teachres_status);
            holder.topic1 = (TextView) convertView.findViewById(R.id.topic1);
            holder.topic2 = (TextView) convertView.findViewById(R.id.topic2);
            holder.topic3 = (TextView) convertView.findViewById(R.id.topic3);
            holder.topic4 = (TextView) convertView.findViewById(R.id.topic4);
            holder.url=(TextView)convertView.findViewById(R.id.url);

            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();


        holder.staff_names.setTypeface(typeFace);
        holder.qulification.setTypeface(typeFace);
        holder.mobile.setTypeface(typeFace);
        holder.subject.setTypeface(typeFace);
        holder.topic1.setTypeface(typeFace);
        holder.topic2.setTypeface(typeFace);
        holder.topic3.setTypeface(typeFace);
        holder.topic4.setTypeface(typeFace);
        if(rowItem.getatttype().equals("Absent")){
            holder.arrow.setBackgroundResource(R.drawable.ic_absent_list);
        }else if(rowItem.getatttype().equals("Leave")){
            holder.arrow.setBackgroundResource(R.drawable.ic_onleave);
        }else{
            holder.arrow.setBackgroundResource(R.drawable.ic_present);
        }
        holder.staff_names.setText(rowItem.getstaff_names());
        holder.qulification.setText(rowItem.getqulification());
        holder.mobile.setText(rowItem.getmobile());
       // holder.subject.setText(rowItem.getsubject());
        holder.Empcode.setText(rowItem.getstaffcode());
        holder.url.setText(rowItem.getstaff_image());
        String imagerplace= ServerApiadmin.MAIN_IPLINK+rowItem.getstaff_image();
        imagerplace=imagerplace.replace("..","");
        holder.staff_image.setImageURI(Uri.parse(imagerplace));
        Glide.get(context).clearMemory();
        Glide
                .with(getContext())
                .load(imagerplace)
                .into(holder.staff_image);

        return convertView;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        staff_helpers.clear();
        if (charText.length() == 0) {
            staff_helpers.addAll(arraylist);
        } else {
            for (Staff_Helper wp : arraylist) {
                if (wp.getstaff_names().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    staff_helpers.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}

