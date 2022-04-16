package vidyawell.infotech.bsn.admin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import androidx.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vidyawell.infotech.bsn.admin.Helpers.Noticeboard_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;


public class Noticeboard_Adapter extends ArrayAdapter<Noticeboard_Helper> {
    Context context;
    List<Noticeboard_Helper> noticeboard_helpers;
    private LayoutInflater inflater;
    ArrayList<Noticeboard_Helper> arraylist;

    public Noticeboard_Adapter(@NonNull Context context, int resource, List<Noticeboard_Helper> noticeboard_helpers) {
        super(context, resource, noticeboard_helpers);
        this.context = context;
        this.noticeboard_helpers = noticeboard_helpers;
        this.arraylist = new ArrayList<Noticeboard_Helper>();
        this.arraylist.addAll(noticeboard_helpers);
    }

    @Override
    public int getCount() {
        return noticeboard_helpers.size();
    }

    @Override
    public Noticeboard_Helper getItem(int position) {
        return  noticeboard_helpers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView date;
        TextView headline;
        TextView transection;
        TextView attechment;
        TextView ditails;
        Button homework_attechment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent ) {
        Noticeboard_Adapter.ViewHolder holder = null;
        Noticeboard_Helper rowItem = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.notice_listitem, null);
            Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/"+ ServerApiadmin.FONT);
            holder = new Noticeboard_Adapter.ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.noticedate);
            holder.headline = (TextView) convertView.findViewById(R.id.noticeheadline);
            holder.ditails = (TextView) convertView.findViewById(R.id.notice_details);
            holder.transection = (TextView) convertView.findViewById(R.id.transactionid);
            holder.attechment=(TextView)convertView.findViewById(R.id.notice_attechment);
            holder.homework_attechment = (Button) convertView.findViewById(R.id.homework_attechment1);
            holder.date.setTypeface(typeFace);
            holder.headline.setTypeface(typeFace);
           // holder.ditails.setTypeface(typeFace);
            convertView.setTag(holder);
        } else
            holder = (Noticeboard_Adapter.ViewHolder) convertView.getTag();

         if(rowItem.getAttachment().equals("null")){
             holder.homework_attechment.setVisibility(View.INVISIBLE);
         }else {
             holder.homework_attechment.setVisibility(View.VISIBLE);
         }
        String Attachment= ServerApiadmin.MAIN_IPLINK+rowItem.getAttachment();
        Attachment=Attachment.replace("..","");
        holder.date.setText(rowItem.getNoticeDate());
        holder.headline.setText(rowItem.getHeadline());
        holder.ditails.setText(Html.fromHtml(rowItem.getNoticeDetails()));
        holder.attechment.setText(Attachment);
     final Noticeboard_Adapter.ViewHolder finalHolder = holder;
        holder.homework_attechment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = finalHolder.attechment.getText().toString();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });
        return convertView;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        noticeboard_helpers.clear();
        if (charText.length() == 0) {
            noticeboard_helpers.addAll(arraylist);
        } else {
            for (Noticeboard_Helper wp : arraylist) {
                if (wp.getNoticeDate().toLowerCase(Locale.getDefault())
                        .contains(charText)  || wp.getHeadline().toLowerCase(Locale.getDefault()).contains(charText))   {
                    noticeboard_helpers.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
