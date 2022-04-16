package vidyawell.infotech.bsn.admin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import vidyawell.infotech.bsn.admin.Helpers.Complaint_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;

public class Complaint_Adapter  extends ArrayAdapter<Complaint_Helper> {
    Context context;
    List<Complaint_Helper> circular_helperList;
    private LayoutInflater inflater;

    public Complaint_Adapter(@NonNull Context context, int resource, List<Complaint_Helper> circular_helpers) {
        super(context, resource,circular_helpers);
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

        TextView class_name;
        TextView comp_count;
        TextView class_id,text_section_ID;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent ) {
        Complaint_Adapter.ViewHolder holder = null;
        Complaint_Helper rowItem = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new Complaint_Adapter.ViewHolder();
            convertView = mInflater.inflate(R.layout.item_complaint_list, null);
            Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/"+ ServerApiadmin.FONT);
            holder.class_name = (TextView) convertView.findViewById(R.id.text_class_name);
            holder.comp_count = (TextView) convertView.findViewById(R.id.text_comp_count);
            holder.class_id = (TextView) convertView.findViewById(R.id.text_class_ID);
            holder.text_section_ID= (TextView) convertView.findViewById(R.id.text_section_ID);

            holder.class_name.setTypeface(typeFace);
            holder.comp_count.setTypeface(typeFace);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.class_name.setText(rowItem.getclass_name()+"  ("+rowItem.getSectionName()+")");
        holder.comp_count.setText(rowItem.getcomp_count());
        holder.class_id.setText(rowItem.getclass_id());
        holder.text_section_ID.setText(rowItem.getSectionId());

        return convertView;
    }

}
