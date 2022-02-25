package vidyawell.infotech.bsn.admin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import vidyawell.infotech.bsn.admin.Helpers.Student_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Visitor_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;

public class Visitor_Adapter  extends ArrayAdapter<Visitor_Helper> {
    Context context;
    Typeface typeFace;
    List<Visitor_Helper> student_helpers;
    private LayoutInflater inflater;
    ArrayList<Visitor_Helper> arraylist;

    public Visitor_Adapter(@NonNull Context context, int resource, List<Visitor_Helper> student_helpers) {
        super(context, resource, student_helpers);
        this.context = context;
        this.student_helpers=student_helpers;
        this.arraylist= new ArrayList<Visitor_Helper>();
        this.arraylist.addAll(student_helpers);
    }


    @Override
    public int getCount() {
        return student_helpers.size();
    }

    @Override
    public Visitor_Helper getItem(int position) {
        return student_helpers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder {

        TextView visiter_names,visiter_company,visiter_mobile,visiter_reference;
        TextView visiter_purpose,photo,HOWUKNOWs,AnyReference;
        TextView TransId,Status,status_meeting,response,V_address;
        CircleImageView student_image;
        TextView VisitTypes,EmailId,TotalPassMember,OfficeEmail;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Visitor_Helper rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.vister_list_item, null);
            typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/" + ServerApiadmin.FONT_DASHBOARD);
            holder.visiter_names = (TextView) convertView.findViewById(R.id.visiter_names);
            holder.visiter_company = (TextView) convertView.findViewById(R.id.visiter_company);
            holder.visiter_mobile = (TextView) convertView.findViewById(R.id.visiter_mobile);
            holder.student_image = (CircleImageView) convertView.findViewById(R.id.main_visiter_bigimage);
            holder.visiter_reference = (TextView) convertView.findViewById(R.id.visiter_reference);
            holder.visiter_purpose = (TextView) convertView.findViewById(R.id.visiter_purpose);


            holder.Status = (TextView) convertView.findViewById(R.id.visitor_status);
            holder.HOWUKNOWs = (TextView) convertView.findViewById(R.id.HOWUKNOWs);
            holder.AnyReference = (TextView) convertView.findViewById(R.id.AnyReference);
            holder.TransId = (TextView) convertView.findViewById(R.id.text_vistorcode);
            holder.VisitTypes=(TextView)convertView.findViewById(R.id.VisitTypes);
            holder.EmailId=(TextView)convertView.findViewById(R.id.EmailId);
            holder.TotalPassMember=(TextView)convertView.findViewById(R.id.TotalPassMember);
            holder.OfficeEmail=(TextView)convertView.findViewById(R.id.OfficeEmail);
            holder.status_meeting=(TextView)convertView.findViewById(R.id.status_meeting);
            holder.V_address = (TextView) convertView.findViewById(R.id.V_address);
            holder.photo=(TextView)convertView.findViewById(R.id.visitor_image);

            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();

        holder.visiter_names.setTypeface(typeFace);
        holder.visiter_company.setTypeface(typeFace);
        holder.visiter_mobile.setTypeface(typeFace);
        holder.visiter_reference.setTypeface(typeFace);
        holder.visiter_purpose.setTypeface(typeFace);
        holder.visiter_names.setText(rowItem.getVisitorName());
        holder.visiter_company.setText(rowItem.getOrganizationName());
        holder.visiter_mobile.setText(rowItem.getPhoneNo());
        holder.visiter_reference.setText(rowItem.getAnyReference());
        holder.visiter_purpose.setText(rowItem.getPurposes());
        holder.photo.setText(rowItem.getVphoto());
        holder.HOWUKNOWs.setText(rowItem.getHOWUKNOWs());
        holder.AnyReference.setText(rowItem.getAnyReference());
        holder.TransId.setText(rowItem.getTransId());
        holder.VisitTypes.setText(rowItem.getVisitTypes());
        holder.EmailId.setText(rowItem.getEmailId());
        holder.TotalPassMember.setText(rowItem.getTotalPassMember());
        holder.OfficeEmail.setText(rowItem.getOfficeEmail());
        holder.Status.setText(rowItem.getstatus());
        holder.V_address.setText(rowItem.getAddress());
        if(holder.Status.getText().equals("1")){
            holder.status_meeting.setText("Walk In...");
            holder.status_meeting.setTextColor(Color.parseColor("#1d894a"));
        }else if(holder.Status.getText().equals("2")){
            holder.status_meeting.setText("Waiting...");
            holder.status_meeting.setTextColor(Color.parseColor("#f59b39"));
        }else if(holder.Status.getText().equals("3")){
            holder.status_meeting.setText("Rejected");
            holder.status_meeting.setTextColor(Color.RED);
        }else{
            holder.status_meeting.setText("On Call...");
            holder.status_meeting.setTextColor(Color.DKGRAY);
        }

        if(rowItem.getVphoto().equals("null")){
            holder.student_image.setImageResource(R.drawable.ic_user_avatar_main_picture);
        }else{
            String imagerplace= ServerApiadmin.MAIN_IPLINK+rowItem.getVphoto();
            imagerplace=imagerplace.replace("..","");
            holder.student_image.setImageURI(Uri.parse(imagerplace));
            Glide.get(context).clearMemory();
            Glide
                    .with(getContext())
                    .load(imagerplace)
                    .into(holder.student_image);
        }

        return convertView;
    }

}

