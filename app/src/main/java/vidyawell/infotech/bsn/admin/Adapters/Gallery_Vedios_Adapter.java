package vidyawell.infotech.bsn.admin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.RoundedImageView;

import java.util.List;

import vidyawell.infotech.bsn.admin.ApplicationControllerAdmin;
import vidyawell.infotech.bsn.admin.Helpers.Gallery_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Gallery_Vedios_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;

public class Gallery_Vedios_Adapter extends ArrayAdapter<Gallery_Vedios_Helper> {
    /* Classmates_Adapter.customButtonListenerclassmates customListner;
     public interface customButtonListenerclassmates {
         public void onButtonClickListner(int position, Syllabus_Helper value);
     }

     public void setcustomButtonListenerclassmates(Syllabus_Adapter.customButtonListenerclassmates listener) {
         this.customListner = listener;
     }
 */
    Context context;
    Typeface typeFace;
    List<Gallery_Helper> gallery_helpers;
    private LayoutInflater inflater;
    ApplicationControllerAdmin applicationControllerAdmin;


    public Gallery_Vedios_Adapter(@NonNull Context context, int resource, List<Gallery_Vedios_Helper> gallery_vedios_helpers) {
        super(context, resource, gallery_vedios_helpers);
        this.context = context;
    }



    private class ViewHolder {


        TextView date,event_title,event_description,transection_id,imageurl_id;
        RoundedImageView staff_image2,staff_image3;




    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Gallery_Vedios_Adapter.ViewHolder holder = null;
        Gallery_Vedios_Helper rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new Gallery_Vedios_Adapter.ViewHolder();

            typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/" + ServerApiadmin.FONT);
            applicationControllerAdmin=(ApplicationControllerAdmin)context.getApplicationContext();
            convertView = mInflater.inflate(R.layout.gallery_item_vedios, null);
            holder = new Gallery_Vedios_Adapter.ViewHolder();

            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.event_title = (TextView) convertView.findViewById(R.id.event_title);
            holder.event_description = (TextView) convertView.findViewById(R.id.event_description);
            holder.transection_id =(TextView) convertView.findViewById(R.id.transection_id_v);
            holder.imageurl_id=(TextView)convertView.findViewById(R.id.imageurl_id);
            holder.staff_image2 =(RoundedImageView) convertView.findViewById(R.id.staff_image2);
            convertView.setTag(holder);
        }else
            holder = (Gallery_Vedios_Adapter.ViewHolder) convertView.getTag();

            holder.date.setTypeface(typeFace);
            holder.event_title.setTypeface(typeFace);
            holder.event_description.setTypeface(typeFace);

            holder.date.setText(rowItem.getEventDate());
            holder.event_title.setText(rowItem.getTitle());
            holder.event_description.setText(rowItem.getDescription());
            holder.transection_id.setText(rowItem.getTransId());

            holder.date.setVisibility(View.VISIBLE);
            holder.event_title.setVisibility(View.VISIBLE);
            holder.event_description.setVisibility(View.GONE);

            holder.imageurl_id.setText(rowItem.getAlbumIcon());
            String imagerplace= ServerApiadmin.MAIN_IPLINK+rowItem.getAlbumIcon();
            imagerplace=imagerplace.replace("..","");
            holder.staff_image2.setImageURI(Uri.parse(imagerplace));
            Glide.get(context).clearMemory();
            Glide
                    .with(getContext())
                    .load(imagerplace)
                    .into(holder.staff_image2);

        return convertView;
    }

}
