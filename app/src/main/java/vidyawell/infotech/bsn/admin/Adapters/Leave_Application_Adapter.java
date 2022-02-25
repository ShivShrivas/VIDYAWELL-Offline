package vidyawell.infotech.bsn.admin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vidyawell.infotech.bsn.admin.Helpers.Leave_Application_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Leave_History_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;

public class Leave_Application_Adapter extends ArrayAdapter<Leave_Application_Helper> {





    Context context;
    List<Leave_Application_Helper> leave_history_helpers;
    private LayoutInflater inflater;
    ArrayList<Leave_Application_Helper> arraylist;


    public Leave_Application_Adapter(Context context, int resource, List<Leave_Application_Helper> leave_history_helpers) {
        super(context, resource,leave_history_helpers);
        this.context = context;
        this.leave_history_helpers = leave_history_helpers;
        this.arraylist= new ArrayList<Leave_Application_Helper>();
        this.arraylist.addAll(leave_history_helpers);
    }

    private class ViewHolder {

        TextView formdate,todate,reason,remarks,status,from_leave_date,to_leave_date,text_empname,application_id,txt_remark;
        TextView emp_code;


    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent ) {
        Leave_Application_Adapter.ViewHolder holder = null;
        Leave_Application_Helper rowItem = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_leave_application, null);
            Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/"+ ServerApiadmin.FONT);
            holder = new ViewHolder();
            holder.formdate = (TextView) convertView.findViewById(R.id.formdate_new);
            holder.todate = (TextView) convertView.findViewById(R.id.todate_new);
            holder.reason=(TextView) convertView.findViewById(R.id.reason_new);
            holder.remarks=(TextView) convertView.findViewById(R.id.remarks_emp_new);
            holder.txt_remark=(TextView) convertView.findViewById(R.id.txt_remark_new);
            holder.status=(TextView)convertView.findViewById(R.id.status_new);
            holder.from_leave_date=(TextView)convertView.findViewById(R.id.from_leave_date_new);
            holder.to_leave_date=(TextView)convertView.findViewById(R.id.to_leave_date_new);
            holder.text_empname=(TextView)convertView.findViewById(R.id.text_empname_new);
            holder.application_id=(TextView)convertView.findViewById(R.id.application_id);
            holder.emp_code=(TextView)convertView.findViewById(R.id.emp_code);

            holder.formdate.setTypeface(typeFace);
            holder.todate.setTypeface(typeFace);
            holder.reason.setTypeface(typeFace);
            holder.remarks.setTypeface(typeFace);
            holder.txt_remark.setTypeface(typeFace);
            holder.status.setTypeface(typeFace);
            holder.from_leave_date.setTypeface(typeFace);
            holder.to_leave_date.setTypeface(typeFace);
            holder.text_empname.setTypeface(typeFace);



            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();


        holder.reason.setText(rowItem.getLeaveReason());
        holder.remarks.setText(rowItem.getRemarks());
        holder.status.setText(rowItem.getIsApproved());
        holder.text_empname.setText(rowItem.getEmpname());
        holder.application_id.setText(rowItem.getEmpAttendanceId());
        holder.emp_code.setText(rowItem.getEmployeeCode());

        if(rowItem.IsApproved.toString().equalsIgnoreCase("1")){
            holder.status.setBackgroundColor(Color.parseColor("#009688"));
            holder.status.setText("Approved");
            holder.formdate.setText(rowItem.getLeaveFrom());
            holder.todate.setText(rowItem.getLeaveTo());
            holder.from_leave_date.setText("From Date");
            holder.to_leave_date.setText("To Date");

            holder.formdate.setTextColor(Color.parseColor("#047c5e"));
            holder.todate.setTextColor(Color.parseColor("#047c5e"));
        }else if(rowItem.IsApproved.toString().equalsIgnoreCase("2")){
            holder.status.setText("Rejected");
            holder.status.setBackgroundColor(Color.parseColor("#B41717"));
            holder.formdate.setText(rowItem.getLeaveFrom());
            holder.todate.setText(rowItem.getLeaveTo());
        }else{
            holder.status.setText("Pending");
            holder.formdate.setText(rowItem.getLeaveFrom());
            holder.todate.setText(rowItem.getLeaveTo());
            holder.status.setBackgroundColor(Color.GRAY);
            holder.from_leave_date.setText("From Date");
            holder.to_leave_date.setText("To Date");
        }
        if(rowItem.getRemarks().equalsIgnoreCase("null")){
            holder.remarks.setVisibility(View.GONE);
            holder.txt_remark.setVisibility(View.GONE);
        }else{
            holder.remarks.setVisibility(View.VISIBLE);
            holder.txt_remark.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        leave_history_helpers.clear();
        if (charText.length() == 0) {
            leave_history_helpers.addAll(arraylist);
        } else {
            for (Leave_Application_Helper wp : arraylist) {
                if (wp.getEmpname().toLowerCase(Locale.getDefault())
                        .contains(charText))  {
                    leave_history_helpers.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
