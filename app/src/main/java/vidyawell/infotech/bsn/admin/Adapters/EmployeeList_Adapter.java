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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vidyawell.infotech.bsn.admin.ApplicationControllerAdmin;
import vidyawell.infotech.bsn.admin.Helpers.EmployeeList_Helper;

import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;

public class EmployeeList_Adapter extends ArrayAdapter<EmployeeList_Helper> {
    ArrayList<EmployeeList_Helper> arraylist;
    List<EmployeeList_Helper> employeeList_helpers;

    customButtonListener customListner;
    public interface customButtonListener {
        public void onButtonClickListner(int position, EmployeeList_Helper value);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }
    Context context;
    Typeface typeFace;
    private LayoutInflater inflater;
    ApplicationControllerAdmin applicationControllerAdmin;


    public EmployeeList_Adapter(@NonNull Context context, int resource, List<EmployeeList_Helper> employeeList_helpers) {
        super(context, resource, employeeList_helpers);
        this.context = context;
        this.employeeList_helpers=employeeList_helpers;
        this.arraylist= new ArrayList<EmployeeList_Helper>();
        this.arraylist.addAll(employeeList_helpers);
    }


    @Override
    public int getCount() {
        return employeeList_helpers.size();
    }

    @Override
    public EmployeeList_Helper getItem(int position) {
        return  employeeList_helpers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;


    }


    private class ViewHolder {

        TextView teachers_names;
        TextView qulification;
        TextView subject_history;
        RoundedImageView teachers_image;
        TextView mobile_number,contact_number,msg_count,teacherId;
        Button teachres_call;
        ImageView arrow;
        TextView emloyee_code,designation,text_image_gone;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        EmployeeList_Helper rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new EmployeeList_Adapter.ViewHolder();
            convertView = mInflater.inflate(R.layout.teachers_item_list, null);
            applicationControllerAdmin=(ApplicationControllerAdmin) context.getApplicationContext();
            typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/" + ServerApiadmin.FONT);
            holder = new ViewHolder();
            holder.teachers_names = (TextView) convertView.findViewById(R.id.teachers_names);
            holder.subject_history = (TextView) convertView.findViewById(R.id.subject_history);
            holder.teachers_image = (RoundedImageView) convertView.findViewById(R.id.teachers_image);
            holder.mobile_number = (TextView) convertView.findViewById(R.id.mobile_number);
            holder.teachres_call = (Button) convertView.findViewById(R.id.teachres_call);
            holder.contact_number = (TextView) convertView.findViewById(R.id.contact_number);
           // holder.msg_count = (TextView) convertView.findViewById(R.id.msg_count);
            holder.teacherId = (TextView) convertView.findViewById(R.id.TeacherId);
            holder.emloyee_code = (TextView) convertView.findViewById(R.id.emloyee_code);
            holder.designation = (TextView) convertView.findViewById(R.id.designation);
            holder.text_image_gone = (TextView) convertView.findViewById(R.id.text_image_gone);
            holder.arrow=(ImageView)convertView.findViewById(R.id.arrow);

            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();


        holder.teachers_names.setTypeface(typeFace);
        holder.subject_history.setTypeface(typeFace);
        holder.contact_number.setTypeface(typeFace);


            holder.emloyee_code.setText(rowItem.getEmployeeCode());
           // holder.msg_count.setText(rowItem.getcount());
            holder.teachers_names.setText("Name   " +rowItem.getFullName());
            holder.subject_history.setText("Father  "+rowItem.getSubjectName());
            holder.designation.setText("Designation  " +rowItem.getIsClassTeacher());
            holder.mobile_number.setText(rowItem.getContactNo());
            holder.text_image_gone.setText(rowItem.getPhotoPath());
            holder.contact_number.setVisibility(View.GONE);
            holder.teachres_call.setVisibility(View.GONE);
            holder.arrow.setVisibility(View.VISIBLE);



        String imagerplace= ServerApiadmin.MAIN_IPLINK+rowItem.getPhotoPath();
        imagerplace=imagerplace.replace("..","");
        holder.teachers_image.setImageURI(Uri.parse(imagerplace));
        Glide.get(context).clearMemory();
        Glide
                .with(getContext())
                .load(imagerplace)
                .into(holder.teachers_image);


      //  final Teachers_Helper temp = getItem(position);
        final EmployeeList_Helper temp = getItem(position);
        holder.teachres_call.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (customListner != null) {
                    customListner.onButtonClickListner(position, temp);
                   // Toast.makeText(context, finalHolder.mobile_number.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });





       /* final ViewHolder finalHolder = holder;
        holder.teachers_image.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Drawable mDrawable =  finalHolder.teachers_image.getDrawable();
                Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();
                System.out.print(mBitmap);

            }
        });*/




        return convertView;
    }


    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        employeeList_helpers.clear();
        if (charText.length() == 0) {
            employeeList_helpers.addAll(arraylist);
        } else {
            for (EmployeeList_Helper wp : arraylist) {
                if (wp.getFullName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    employeeList_helpers.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
