package vidyawell.infotech.bsn.admin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import vidyawell.infotech.bsn.admin.Helpers.Complaint_Helper_history;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;

public class Complaint_Adapter_history extends ArrayAdapter<Complaint_Helper_history> {

    Context context;

    List<Complaint_Helper_history> complaint_helpers;
    private LayoutInflater inflater;


    public Complaint_Adapter_history(@NonNull Context context, int resource, List<Complaint_Helper_history> complaint_helpers) {
        super(context, resource, complaint_helpers);
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


        TextView complaint_post,complaint_date;
        TextView complaint_text;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent ) {
        Complaint_Adapter_history.ViewHolder holder = null;
        Complaint_Helper_history rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_complaint_hist, null);
            Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/"+ ServerApiadmin.FONT_DASHBOARD);
            holder = new Complaint_Adapter_history.ViewHolder();
            holder.complaint_post = (TextView) convertView.findViewById(R.id.complaint_post);
            holder.complaint_date = (TextView) convertView.findViewById(R.id.complaint_date);
            holder.complaint_text = (TextView) convertView.findViewById(R.id.complaint_text);


            //complaint_text
            holder.complaint_post.setTypeface(typeFace);
            holder.complaint_date.setTypeface(typeFace);
            holder.complaint_text.setTypeface(typeFace);

            convertView.setTag(holder);
        } else
            holder = (Complaint_Adapter_history.ViewHolder) convertView.getTag();




        if(rowItem.getEmpName().equals("")){
            holder.complaint_post.setVisibility(View.GONE);
        }else{
            holder.complaint_post.setVisibility(View.VISIBLE);
            holder.complaint_post.setText(rowItem.getEmpName());
        }

        holder.complaint_date.setText(rowItem.getComplainDate());
        holder.complaint_text.setText(rowItem.getComplain());

        //SimpleDateFormat yyyyMMddFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US); // 2018-08-31


        return convertView;
    }

}
