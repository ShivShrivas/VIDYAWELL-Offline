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

import java.util.List;

import vidyawell.infotech.bsn.admin.Helpers.Calenderevent;
import vidyawell.infotech.bsn.admin.Helpers.Staff_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;

/**
 * Created by AmitAIT on 26-10-2018.
 */

public class Holiday_Adapter  extends ArrayAdapter<Calenderevent> {
    Context context;
    Typeface typeFace;
    List<Calenderevent> calenderevent;
    private LayoutInflater inflater;
    public Holiday_Adapter(@NonNull Context context, int resource, List<Calenderevent> calenderevent) {
        super(context, resource, calenderevent);
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
        TextView Activity,StartDate,EndDate;
        RoundedImageView staff_image;
        TextView subject,url;
        Button arrow;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       // View view = super.getView(position, convertView, parent);
        ViewHolder holder = null;
        Calenderevent rowItem = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.holiday_listitem_layout, null);
            typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/" + ServerApiadmin.FONT);
            holder = new ViewHolder();
            holder.Activity = (TextView) convertView.findViewById(R.id.text_title);
            holder.StartDate = (TextView) convertView.findViewById(R.id.text_fromdate);
            holder.EndDate = (TextView) convertView.findViewById(R.id.text_todate);
            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();

        holder.Activity.setTypeface(typeFace);
        holder.StartDate.setTypeface(typeFace);
        holder.EndDate.setTypeface(typeFace);
        if (position % 2 == 1) {
            convertView.setBackgroundResource(R.drawable.gradiant_blue);
        }else{
            convertView.setBackgroundResource(R.drawable.gradiant_orange);
        }
        if(rowItem.getIsRange().equalsIgnoreCase("Multiple Day")){
            holder.EndDate.setVisibility(View.VISIBLE);
            holder.EndDate.setText("End Date:-"+rowItem.getEndDate());
            holder.Activity.setText(rowItem.getActivity());
            holder.StartDate.setText("Start Date:-"+rowItem.getStartDate());
        }else{
            holder.EndDate.setVisibility(View.INVISIBLE);
            holder.EndDate.setText("End Date:-"+rowItem.getEndDate());
            holder.Activity.setText(rowItem.getActivity());
            holder.StartDate.setText("Date:-"+rowItem.getStartDate());
        }

        return convertView;
    }

}

