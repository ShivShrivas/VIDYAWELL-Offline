package vidyawell.infotech.bsn.admin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import vidyawell.infotech.bsn.admin.Helpers.Leave_History_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
public class Leave_History_Adapter extends ArrayAdapter<Leave_History_Helper> {





    Context context;
    List<Leave_History_Helper> leave_history_helpers;
    private LayoutInflater inflater;


    public Leave_History_Adapter(Context context, int resource, List<Leave_History_Helper> leave_history_helpers) {
        super(context, resource,leave_history_helpers);
        this.context = context;
    }

    private class ViewHolder {

        TextView formdate,todate,reason,remarks,status,from_leave_date,to_leave_date;
        LinearLayout layout_adminremarks;



    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent ) {
        Leave_History_Adapter.ViewHolder holder = null;
        Leave_History_Helper rowItem = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_leave_history, null);
            Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/"+ ServerApiadmin.FONT);
            holder = new ViewHolder();
            holder.formdate = (TextView) convertView.findViewById(R.id.formdate);
            holder.todate = (TextView) convertView.findViewById(R.id.todate);
            holder.reason=(TextView) convertView.findViewById(R.id.reason);
            holder.remarks=(TextView) convertView.findViewById(R.id.remarks);
            holder.status=(TextView)convertView.findViewById(R.id.status);
            holder.from_leave_date=(TextView)convertView.findViewById(R.id.from_leave_date);
            holder.to_leave_date=(TextView)convertView.findViewById(R.id.to_leave_date);
            holder.layout_adminremarks=convertView.findViewById(R.id.layout_adminremarks);




            holder.formdate.setTypeface(typeFace);
            holder.todate.setTypeface(typeFace);
            holder.reason.setTypeface(typeFace);
            holder.remarks.setTypeface(typeFace);
            holder.status.setTypeface(typeFace);
            holder.from_leave_date.setTypeface(typeFace);
            holder.to_leave_date.setTypeface(typeFace);



            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();



        holder.reason.setText(rowItem.getLeaveReason());
        holder.remarks.setText(rowItem.getRemarks());
        holder.status.setText(rowItem.getIsApproved());

        if(rowItem.getRemarks().equals("null")){
            holder.layout_adminremarks.setVisibility(View.GONE);
        }else{
            holder.layout_adminremarks.setVisibility(View.VISIBLE);
        }



        if(rowItem.IsApproved.toString().equalsIgnoreCase("Approved")){
            holder.status.setBackgroundColor(Color.parseColor("#047c5e"));
            holder.formdate.setText(rowItem.getApprovedFrom());
            holder.todate.setText(rowItem.getApprovedTo());
            holder.from_leave_date.setText("Approved From");
            holder.to_leave_date.setText("Approved To");

            holder.formdate.setTextColor(Color.parseColor("#047c5e"));
            holder.todate.setTextColor(Color.parseColor("#047c5e"));
        }else if(rowItem.IsApproved.toString().equalsIgnoreCase("Rejected")){
            holder.formdate.setText(rowItem.getLeaveFrom());
            holder.todate.setText(rowItem.getLeaveTo());
            holder.status.setBackgroundColor(Color.RED);
            holder.from_leave_date.setText("From Date");
            holder.to_leave_date.setText("To Date");
            holder.formdate.setTextColor(Color.DKGRAY);
            holder.todate.setTextColor(Color.DKGRAY);
        }else{
            holder.formdate.setText(rowItem.getLeaveFrom());
            holder.todate.setText(rowItem.getLeaveTo());
            holder.status.setBackgroundColor(Color.DKGRAY);
            holder.from_leave_date.setText("From Date");
            holder.to_leave_date.setText("To Date");
            holder.formdate.setTextColor(Color.DKGRAY);
            holder.todate.setTextColor(Color.DKGRAY);
        }







        return convertView;
    }

}
