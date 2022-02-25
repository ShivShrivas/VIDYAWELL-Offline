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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.ivbaranov.mli.MaterialLetterIcon;
import com.github.siyamed.shapeimageview.RoundedImageView;

import java.util.List;
import java.util.Random;

import vidyawell.infotech.bsn.admin.Helpers.Notification_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;


public class Notification_Adapter extends ArrayAdapter<Notification_Helper> {
    Context context;
    Typeface typeFace;
    List<Notification_Helper> notification_helpers;
    private LayoutInflater inflater;
    private int[] mMaterialColors;
    private static final Random RANDOM = new Random();
    public Notification_Adapter(@NonNull Context context, int resource, List<Notification_Helper> notification_helpers) {
        super(context, resource, notification_helpers);
        this.context = context;
        mMaterialColors = context.getResources().getIntArray(R.array.colors);
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
    TextView date_notification,letter_n;
    TextView notification;
    TextView title;
    RoundedImageView notification_image;
    MaterialLetterIcon icon;
    TextView id_Notification;
}

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Notification_Adapter.ViewHolder holder = null;
        Notification_Helper rowItem = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new Notification_Adapter.ViewHolder();
            convertView = mInflater.inflate(R.layout.notification_listitem, null);
            typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/" + ServerApiadmin.FONT);
            holder = new Notification_Adapter.ViewHolder();
            holder.date_notification = (TextView) convertView.findViewById(R.id.date_notification);
            holder.notification = (TextView) convertView.findViewById(R.id.notification);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.notification_image = (RoundedImageView) convertView.findViewById(R.id.notification_image);
            holder.icon=(MaterialLetterIcon)convertView.findViewById(R.id.letter_circle);
            holder.letter_n=(TextView)convertView.findViewById(R.id.letter_n);
            holder.id_Notification=(TextView)convertView.findViewById(R.id.id_Notification);
            convertView.setTag(holder);
        }else
            holder = (Notification_Adapter.ViewHolder) convertView.getTag();


        holder.date_notification.setTypeface(typeFace);
        holder.notification.setTypeface(typeFace);
        holder.title.setTypeface(typeFace);

        holder.icon.setInitials(true);
        holder.icon.setInitialsNumber(2);
        holder.icon.setLetterSize(14);
        holder.icon.setShapeColor(mMaterialColors[RANDOM.nextInt(mMaterialColors.length)]);
        holder.icon.setLetter(rowItem.getletter());
        holder.letter_n.setText(rowItem.getletter());
        holder.title.setText(rowItem.getTitle());
        holder.id_Notification.setText(rowItem.getTransId());
        holder.notification.setText(rowItem.getNotification());
        holder.date_notification.setText(rowItem.getfont());
        String imagerplace= ServerApiadmin.MAIN_IPLINK+rowItem.getStudentPhoto();
        imagerplace=imagerplace.replace("..","");
        holder.notification_image.setImageURI(Uri.parse(imagerplace));
        Glide.get(context).clearMemory();
        Glide

                .with(getContext())
                .load(imagerplace)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(holder.notification_image);


        return convertView;
    }

}
