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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vidyawell.infotech.bsn.admin.Helpers.Complaint_classlist_helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;

public class Complaint_classlist_Adapter  extends ArrayAdapter<Complaint_classlist_helper> {

    Context context;
    Typeface typeFace;
    List<Complaint_classlist_helper> calenderevent;
    private LayoutInflater inflater;
    ArrayList<Complaint_classlist_helper> arraylist;
    public Complaint_classlist_Adapter(Context context, int resource, List<Complaint_classlist_helper> calenderevent) {
        super(context, resource, calenderevent);
        this.context = context;
        this.calenderevent=calenderevent;
        this.arraylist= new ArrayList<Complaint_classlist_helper>();
        this.arraylist.addAll(calenderevent);
    }

    @Override
    public int getCount() {
        return calenderevent.size();
    }
    @Override
    public Complaint_classlist_helper getItem(int position) {
        return  calenderevent.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder {
        TextView txtComplain,txtComplainDate,txtStdName;
        TextView txtFatherName;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // View view = super.getView(position, convertView, parent);
        ViewHolder holder = null;
        Complaint_classlist_helper rowItem = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_fullcomplaint_list, null);
            typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/" + ServerApiadmin.FONT);
            holder = new ViewHolder();
            holder.txtComplain = (TextView) convertView.findViewById(R.id.complaint_Fdetails);
            holder.txtComplainDate = (TextView) convertView.findViewById(R.id.comp_date);
            holder.txtStdName = (TextView) convertView.findViewById(R.id.student_cName);
            holder.txtFatherName = (TextView) convertView.findViewById(R.id.student_CFName);
            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();


        holder.txtComplain.setTypeface(typeFace);
        holder.txtComplainDate.setTypeface(typeFace);
        holder.txtStdName.setTypeface(typeFace);
        holder.txtFatherName.setTypeface(typeFace);
        holder.txtComplain.setText(rowItem.getComplain());
        holder.txtComplainDate.setText(rowItem.getComplainDate());
        holder.txtStdName.setText(rowItem.getStdName());
        holder.txtFatherName.setText("Father's Name: "+rowItem.getFatherName());

        return convertView;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        calenderevent.clear();
        if (charText.length() == 0) {
            calenderevent.addAll(arraylist);
        } else {
            for (Complaint_classlist_helper wp : arraylist) {
                if (wp.getStdName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    calenderevent.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}

