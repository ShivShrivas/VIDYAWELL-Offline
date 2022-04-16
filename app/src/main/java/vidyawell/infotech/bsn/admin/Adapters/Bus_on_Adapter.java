package vidyawell.infotech.bsn.admin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vidyawell.infotech.bsn.admin.Helpers.Bus_on_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;

public class Bus_on_Adapter extends ArrayAdapter<Bus_on_Helper> {
    Context context;
    List<Bus_on_Helper> bus_on_helpers;
    private LayoutInflater inflater;
    ArrayList<Bus_on_Helper> arraylist;

    public Bus_on_Adapter(Context context, int resource, List<Bus_on_Helper> bus_on_helpers) {
        super(context, resource, bus_on_helpers);
        this.context = context;
        this.bus_on_helpers=bus_on_helpers;
        this.arraylist= new ArrayList<Bus_on_Helper>();
        this.arraylist.addAll(bus_on_helpers);

    }
  @Override
     public int getCount() {
         return bus_on_helpers.size();
     }
     @Override
     public Bus_on_Helper getItem(int position) {
         return  bus_on_helpers.get(position);
     }
     @Override
     public long getItemId(int position) {
         return 0;
     }
    private class ViewHolder {
         TextView vacle_number,track_time,driver_name,bus_stop,vichile_id;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent ) {
        ViewHolder holder = null;
        Bus_on_Helper rowItem = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.bus_onitem_route_bus, null);
            Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/"+ ServerApiadmin.FONT_DASHBOARD);

            holder.vacle_number = (TextView) convertView.findViewById(R.id.vacle_number);
            holder.track_time =(TextView)convertView.findViewById(R.id.track_time);
            holder.driver_name =(TextView)convertView.findViewById(R.id.driver_name);
            holder.bus_stop =(TextView)convertView.findViewById(R.id.bus_stop);
            holder.vichile_id=(TextView)convertView.findViewById(R.id.vichile_id);
            //complaint_text
            holder.vacle_number.setTypeface(typeFace);
            holder.track_time.setTypeface(typeFace);
            holder.driver_name.setTypeface(typeFace);
            holder.bus_stop.setTypeface(typeFace);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        holder.vacle_number.setText(rowItem.getVehicleNo());
        holder.track_time.setText(rowItem.geVehicleType());
        holder.driver_name.setText(rowItem.getOwnerName());
        holder.bus_stop.setText(rowItem.getOwnerAddress());
        return convertView;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        bus_on_helpers.clear();
        if (charText.length() == 0) {
            bus_on_helpers.addAll(arraylist);
        } else {
            for (Bus_on_Helper wp : arraylist) {
                if (wp.getOwnerName().toLowerCase(Locale.getDefault())
                        .contains(charText) || wp.getVehicleNo().toLowerCase(Locale.getDefault()).contains(charText)) {
                    bus_on_helpers.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
