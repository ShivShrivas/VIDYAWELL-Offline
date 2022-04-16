package vidyawell.infotech.bsn.admin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import vidyawell.infotech.bsn.admin.ApplicationControllerAdmin;

import vidyawell.infotech.bsn.admin.Helpers.Emp_Attendance_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;

public class Emp_Attendance_Adapter extends ArrayAdapter<Emp_Attendance_Helper> {

    List<Emp_Attendance_Helper> emp_attendance_helpers;
    Context context;
    Typeface typeFace;
    private LayoutInflater mInflater;
    boolean present_button=true;
    ApplicationControllerAdmin applicationController;



    customButtonListener customListner;
    customButtonListener2 customListner2;
    customButtonListener3 customListner3;



    public interface customButtonListener {
        public void onButtonClickListner(int position, Emp_Attendance_Helper value);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }


    public interface customButtonListener2 {
        public void onButtonClickListner2(int position, Emp_Attendance_Helper value);
    }

    public void setCustomButtonListner2(customButtonListener2 listener) {
        this.customListner2 = listener;
    }


    public interface customButtonListener3 {
        public void onButtonClickListner3(int position, Emp_Attendance_Helper value);
    }

    public void setCustomButtonListner3(customButtonListener3 listener) {
        this.customListner3 = listener;
    }




    public Emp_Attendance_Adapter(@NonNull Context context, int resource, List<Emp_Attendance_Helper> emp_attendance_helpers) {
        super(context, resource, emp_attendance_helpers);
        this.context = context;
        this.emp_attendance_helpers = emp_attendance_helpers;
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }




   /* @Override
    public int getCount() {
        return class_attendance_helpers.size();
    }

    @Override
    public Class_Attendance_Helper getItem(int position) {
        return  class_attendance_helpers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
*/
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



    //new ///


   /* @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }
    @Override
    public int getCount() {
        return class_attendance_helpers.size();
    }

    @Override
    public Class_Attendance_Helper getItem(int position) {
        return class_attendance_helpers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

*/


    private class ViewHolder {

        TextView stu_name,studentid,studentcount,father_name,Roll_no,stu_id;
        Button stu_present,stu_absent,stu_leave;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Emp_Attendance_Helper rowItem = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            //holder = new ViewHolder();
            holder = new Emp_Attendance_Adapter.ViewHolder();
            convertView = mInflater.inflate(R.layout.emp_attendance_item_list, null);
            typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/" + ServerApiadmin.FONT);
            applicationController=(ApplicationControllerAdmin)context.getApplicationContext();
            holder = new Emp_Attendance_Adapter.ViewHolder();
            holder.stu_name = (TextView) convertView.findViewById(R.id.stu_name);
            holder.studentid = (TextView) convertView.findViewById(R.id.student_code);
            holder.stu_id = (TextView) convertView.findViewById(R.id.stu_id);
            holder.studentcount = (TextView) convertView.findViewById(R.id.stu_count);
            holder.stu_present = (Button) convertView.findViewById(R.id.stu_present);
            holder.stu_absent = (Button) convertView.findViewById(R.id.stu_absent);
            holder.stu_leave = (Button) convertView.findViewById(R.id.stu_leave);
            holder.father_name=(TextView)convertView.findViewById(R.id.father_name);
            holder.Roll_no=(TextView)convertView.findViewById(R.id.stu_roolnumber);


            convertView.setTag(holder);
        }else
            holder = (Emp_Attendance_Adapter.ViewHolder) convertView.getTag();


        holder.stu_name.setTypeface(typeFace);
        holder.studentid.setTypeface(typeFace);
        holder.studentcount.setTypeface(typeFace);
        holder.father_name.setTypeface(typeFace);
       // holder.Roll_no.setTypeface(typeFace);
        holder.stu_id.setTypeface(typeFace);
        holder.stu_name.setText(rowItem.getEmpName());
        holder.studentid.setText(rowItem.getEmployeeCode());
        holder.studentcount.setText(rowItem.getcount());
        holder.father_name.setText("Father's Name: "+rowItem.getFatherName());
       // holder.Roll_no.setText("Roll No: "+"Not Assigned");
        holder.stu_id.setText(rowItem.getEmployeeId());



        if(rowItem.getAttendanceDate().equalsIgnoreCase("null")){
        }else{
              //holder.stu_present.setEnabled(false);
              //holder.stu_absent.setEnabled(false);
              //holder.stu_leave.setEnabled(false);
              if(rowItem.getAttendanceAbbrId().equals(applicationController.getpresent_code())){
                  holder.stu_present.setBackgroundColor(Color.parseColor("#20dc80"));
                  holder.stu_present.setTextColor(Color.parseColor("#ffffff"));
                  holder.stu_absent.setBackgroundColor(Color.parseColor("#ffffff"));
                  holder.stu_absent.setTextColor(Color.parseColor("#000000"));
                  holder.stu_leave.setBackgroundColor(Color.parseColor("#ffffff"));
                  holder.stu_leave.setTextColor(Color.parseColor("#000000"));

              }else if(rowItem.getAttendanceAbbrId().equals(applicationController.getabsent_code())){
                  holder.stu_absent.setBackgroundColor(Color.parseColor("#ed1f1f"));
                  holder.stu_absent.setTextColor(Color.parseColor("#ffffff"));
                  holder.stu_present.setBackgroundColor(Color.parseColor("#ffffff"));
                  holder.stu_present.setTextColor(Color.parseColor("#000000"));
                  holder.stu_leave.setBackgroundColor(Color.parseColor("#ffffff"));
                  holder.stu_leave.setTextColor(Color.parseColor("#000000"));

              }else {
                  holder.stu_leave.setBackgroundColor(Color.parseColor("#3fbfdf"));
                  holder.stu_leave.setTextColor(Color.parseColor("#ffffff"));
                  holder.stu_present.setBackgroundColor(Color.parseColor("#ffffff"));
                  holder.stu_present.setTextColor(Color.parseColor("#000000"));
                  holder.stu_absent.setBackgroundColor(Color.parseColor("#ffffff"));
                  holder.stu_absent.setTextColor(Color.parseColor("#000000"));
              }
        }

        final Emp_Attendance_Helper temp = getItem(position);
        final ViewHolder finalHolder = holder;
        final Emp_Attendance_Helper temp2 = getItem(position);
        final ViewHolder finalHolder2 = holder;
        final Emp_Attendance_Helper temp3 = getItem(position);
        final ViewHolder finalHolder3 = holder;
        holder.stu_present.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (customListner != null) {
                    customListner.onButtonClickListner(position, temp);
                     finalHolder.stu_present.setBackgroundColor(Color.parseColor("#20dc80"));
                     finalHolder.stu_present.setTextColor(Color.parseColor("#ffffff"));
                     finalHolder2.stu_absent.setBackgroundColor(Color.parseColor("#ffffff"));
                     finalHolder2.stu_absent.setTextColor(Color.parseColor("#000000"));
                    finalHolder3.stu_leave.setBackgroundColor(Color.parseColor("#ffffff"));
                    finalHolder3.stu_leave.setTextColor(Color.parseColor("#000000"));

                    // Toast.makeText(context, finalHolder.mobile_number.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.stu_absent.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (customListner2 != null) {
                    customListner2.onButtonClickListner2(position, temp2);

                        finalHolder2.stu_absent.setBackgroundColor(Color.parseColor("#ed1f1f"));
                        finalHolder2.stu_absent.setTextColor(Color.parseColor("#ffffff"));
                        finalHolder.stu_present.setBackgroundColor(Color.parseColor("#ffffff"));
                        finalHolder.stu_present.setTextColor(Color.parseColor("#000000"));
                        finalHolder3.stu_leave.setBackgroundColor(Color.parseColor("#ffffff"));
                        finalHolder3.stu_leave.setTextColor(Color.parseColor("#000000"));

                    // Toast.makeText(context, finalHolder.mobile_number.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.stu_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customListner3 != null) {
                    customListner3.onButtonClickListner3(position, temp3);

                    finalHolder3.stu_leave.setBackgroundColor(Color.parseColor("#3fbfdf"));
                    finalHolder3.stu_leave.setTextColor(Color.parseColor("#ffffff"));
                    finalHolder2.stu_absent.setBackgroundColor(Color.parseColor("#ffffff"));
                    finalHolder2.stu_absent.setTextColor(Color.parseColor("#000000"));
                    finalHolder.stu_present.setBackgroundColor(Color.parseColor("#ffffff"));
                    finalHolder.stu_present.setTextColor(Color.parseColor("#000000"));


                    // Toast.makeText(context, finalHolder.mobile_number.getText().toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });



        return convertView;
    }

}
