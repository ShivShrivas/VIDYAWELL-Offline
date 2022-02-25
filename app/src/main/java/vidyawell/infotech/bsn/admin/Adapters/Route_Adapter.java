package vidyawell.infotech.bsn.admin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import vidyawell.infotech.bsn.admin.Gifimage.GifDecoderView;
import vidyawell.infotech.bsn.admin.Helpers.Route_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;


public class Route_Adapter extends ArrayAdapter<Route_Helper> {

    Context context;

    List<Route_Helper> route_helpers;
    private LayoutInflater inflater;


    public Route_Adapter(@NonNull Context context, int resource, List<Route_Helper> route_helpers) {
        super(context, resource, route_helpers);
        this.context = context;
    }


/* @Override
     public int getCount() {
         return route_helpers.size();
     }

     @Override
     public Route_Helper getItem(int position) {
         return  route_helpers.get(position);
     }

     @Override
     public long getItemId(int position) {
         return 0;
     }*/
    private class ViewHolder {


        GifDecoderView gifImageView;
        TextView route_name,route_time;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent ) {
        Route_Adapter.ViewHolder holder = null;
        Route_Helper rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.route_item_list, null);
            Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/"+ ServerApiadmin.FONT_DASHBOARD);
            holder = new Route_Adapter.ViewHolder();
            holder.route_name = (TextView) convertView.findViewById(R.id.route_name);
            holder.route_time =(TextView)convertView.findViewById(R.id.route_time);
            holder.gifImageView =(GifDecoderView)convertView. findViewById(R.id.live_loc_icon);
            //complaint_text
            holder.route_name.setTypeface(typeFace);
            holder.route_time.setTypeface(typeFace);


            convertView.setTag(holder);
        } else
            holder = (Route_Adapter.ViewHolder) convertView.getTag();


        if(position<3){
            holder.route_name.setTextColor(Color.RED);
        }else{
            holder.route_name.setTextColor(Color.BLACK);
        }


        if(position==3){
            holder.gifImageView.setVisibility(View.VISIBLE);
            InputStream stream = null;
            try {
                stream = context.getAssets().open("live_position.gif");
            } catch (IOException e) {
                e.printStackTrace();
            }

            holder.gifImageView.playGif(stream);

        }else{
            holder.gifImageView.setVisibility(View.INVISIBLE);
        }

        holder.route_name.setText(rowItem.getRouteName());
        holder.route_time.setText(rowItem.route_time);



        return convertView;
    }


}
