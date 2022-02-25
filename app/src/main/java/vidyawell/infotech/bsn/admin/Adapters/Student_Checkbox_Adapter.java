package vidyawell.infotech.bsn.admin.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vidyawell.infotech.bsn.admin.Helpers.Complaint_View_Helper;
import vidyawell.infotech.bsn.admin.Helpers.SectionList_Checkbox_Helper;
import vidyawell.infotech.bsn.admin.Helpers.StreamList_Checkbox_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Student_Checkbox_Helper;
import vidyawell.infotech.bsn.admin.R;

public class Student_Checkbox_Adapter extends ArrayAdapter<Student_Checkbox_Helper> {

    Student_Checkbox_Adapter.CheckcustomButtonListener4 customListner4;
    public interface CheckcustomButtonListener4 {
        public void onCheckClickListner4(int position, Student_Checkbox_Helper value, boolean isChecked);
    }
    public void setCheckcustomButtonListener4(Student_Checkbox_Adapter.CheckcustomButtonListener4 listener4) {
        this.customListner4 = listener4;
    }


    private Context mContext;
    private ArrayList<Student_Checkbox_Helper> listState;
    private Student_Checkbox_Adapter myAdapter;
    private boolean isFromView = false;

    public Student_Checkbox_Adapter(Context context, int resource, List<Student_Checkbox_Helper> objects, Resources res) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = (ArrayList<Student_Checkbox_Helper>) objects;
        this.myAdapter = this;

    }

  /*  @Override
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
*/

 /*   @Override
    public int getCount() {
        return listState.size();
    }

    @Override
    public Student_Checkbox_Helper getItem(int position) {
        return  listState.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }*/




    @Override
    public int getCount() {
        return listState.size();
    }

    @Override
    public Student_Checkbox_Helper getItem(int position) {
        return  listState.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView,
                              ViewGroup parent) {

        final Student_Checkbox_Adapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_student_item_chekbox, null);
            holder = new Student_Checkbox_Adapter.ViewHolder();
            holder.text_sname_chk_student = (TextView) convertView.findViewById(R.id.text_sname_chk_student);
            holder.text_sID_chk_student = (TextView) convertView.findViewById(R.id.text_sID_chk_student);
            holder.text_sID_mobile_student = (TextView) convertView.findViewById(R.id.text_sID_mobile_student);
            holder.text_sID_email_student = (TextView) convertView.findViewById(R.id.text_sID_email_student);
            holder.checkbox_student =(CheckBox) convertView.findViewById(R.id.checkbox_student);

            convertView.setTag(holder);
        } else {
            holder = (Student_Checkbox_Adapter.ViewHolder) convertView.getTag();
        }

        holder.text_sname_chk_student.setText(listState.get(position).getStudentName());
        holder.text_sID_chk_student.setText(listState.get(position).getStudentCode());
        holder.text_sID_mobile_student.setText(listState.get(position).getCorrespondanceMobileNo());
        holder.text_sID_email_student.setText(listState.get(position).getCorrespondanceEmail());
        holder.checkbox_student.setChecked(listState.get(position).getcheck_status());

        // To check weather checked event fire from getview() or user input
       // isFromView = true;
       // holder.checkbox_stream.setChecked(listState.get(position).isSelected());
       // isFromView = false;

        if ((position == 0)) {
            holder.checkbox_student.setVisibility(View.INVISIBLE);
        } else {
            holder.checkbox_student.setVisibility(View.VISIBLE);
        }


       // holder.checkbox_stream.setTag(position);
       /* holder.checkbox_stream.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();

                if (!isFromView) {
                    listState.get(position).setSelected(isChecked);
                    // Toast.makeText(getContext(),position,Toast.LENGTH_SHORT).show();
                }
            }
        });*/


        final Student_Checkbox_Helper temp = getItem(position);
        holder.checkbox_student.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (customListner4 != null) {
                    customListner4.onCheckClickListner4(position, temp,isChecked);

                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView text_sname_chk_student,text_sID_chk_student,text_sID_mobile_student,text_sID_email_student;
        private CheckBox checkbox_student;
    }

}
