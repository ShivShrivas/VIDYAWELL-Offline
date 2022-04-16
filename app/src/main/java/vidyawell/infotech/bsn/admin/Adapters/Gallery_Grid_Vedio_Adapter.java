package vidyawell.infotech.bsn.admin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;

import java.util.List;

import vidyawell.infotech.bsn.admin.ApplicationControllerAdmin;
import vidyawell.infotech.bsn.admin.Full_Screen;
import vidyawell.infotech.bsn.admin.Helpers.Gallery_Grid_Vedio_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;

public class Gallery_Grid_Vedio_Adapter extends ArrayAdapter<Gallery_Grid_Vedio_Helper> {

    Context context;
    Typeface typeFace;
    List<Gallery_Grid_Vedio_Helper> gallery_grid_vedio_helpers;
    private LayoutInflater inflater;
    ApplicationControllerAdmin applicationControllerAdmin;


    public Gallery_Grid_Vedio_Adapter(@NonNull Context context, int resource, List<Gallery_Grid_Vedio_Helper> gallery_grid_vedio_helpers) {
        super(context, resource, gallery_grid_vedio_helpers);
        this.context = context;
    }

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

    private class ViewHolder {


        TextView date,event_title,event_description,transection_id,imageurl_id;
        RoundedImageView staff_image3;
        WebView staff_image_vedio;
        LinearLayout full_screen,share_video;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final Gallery_Grid_Vedio_Helper rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new ViewHolder();

            typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/" + ServerApiadmin.FONT);
            applicationControllerAdmin=(ApplicationControllerAdmin)context.getApplicationContext();
            convertView = mInflater.inflate(R.layout.gallery_item_full_vedios, null);
            holder = new ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.event_title = (TextView) convertView.findViewById(R.id.event_title);
            holder.event_description = (TextView) convertView.findViewById(R.id.event_description);
            holder.transection_id =(TextView) convertView.findViewById(R.id.transection_idd);
            holder.imageurl_id=(TextView)convertView.findViewById(R.id.imageurl_id);
            holder.staff_image_vedio=(WebView) convertView.findViewById(R.id.staff_image_vedio);
            holder.full_screen=(LinearLayout)convertView.findViewById(R.id.full_screen);
            holder.share_video=(LinearLayout)convertView.findViewById(R.id.share_video);


            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();



        holder.date.setTypeface(typeFace);
        holder.event_title.setTypeface(typeFace);
        holder.event_description.setTypeface(typeFace);




             WebSettings webSettings = holder.staff_image_vedio.getSettings();
             webSettings.setJavaScriptEnabled(true);
             holder.staff_image_vedio.loadUrl(rowItem.getVedioUrl());
             holder.event_title.setText(rowItem.getTitle());


         holder.full_screen.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(context,Full_Screen.class);
                 intent.putExtra("URL",rowItem.getVedioUrl());
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 context.startActivity(intent);
             }
         });

         holder.share_video.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent sendIntent = new Intent();
                 sendIntent.setAction(Intent.ACTION_SEND);
                 sendIntent.putExtra(Intent.EXTRA_TEXT, rowItem.getVedioUrl());
                 sendIntent.setType("text/plain");
                 sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 context.startActivity(sendIntent);
             }
         });



        return convertView;
    }

}
