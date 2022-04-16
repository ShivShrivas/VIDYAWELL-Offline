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


import vidyawell.infotech.bsn.admin.Helpers.Circular_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;

public class Circular_Adapter extends ArrayAdapter<Circular_Helper> {
    Context context;
    List<Circular_Helper> circular_helpers;
    private LayoutInflater inflater;
    ArrayList<Circular_Helper> arraylist;

    public Circular_Adapter(@NonNull Context context, int resource, List<Circular_Helper> circular_helpers) {
        super(context, resource,circular_helpers);
        this.context = context;
        this.circular_helpers=circular_helpers;
        this.arraylist= new ArrayList<Circular_Helper>();
        this.arraylist.addAll(circular_helpers);
    }

    @Override
    public int getCount() {
        return circular_helpers.size();
    }

    @Override
    public Circular_Helper getItem(int position) {
        return  circular_helpers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder {

        TextView circulardate;
        TextView transactionid;
        TextView circularheadline;
        TextView circular_details;
        TextView circular_attechment;
        Button circular_attechment1;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent ) {
        Circular_Adapter.ViewHolder holder = null;
        Circular_Helper rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.circular_listitem, null);
            Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/"+ ServerApiadmin.FONT);
            holder = new Circular_Adapter.ViewHolder();

            holder.transactionid = (TextView) convertView.findViewById(R.id.transactionid);
            holder.circulardate = (TextView) convertView.findViewById(R.id.circulardate);
            holder.circularheadline = (TextView) convertView.findViewById(R.id.circularheadline);
            holder.circular_details = (TextView) convertView.findViewById(R.id.circular_details);
            holder.circular_attechment=(TextView)convertView.findViewById(R.id.circular_attechment);
            holder.circular_attechment1 = (Button) convertView.findViewById(R.id.circular_attechment1);
            holder.circulardate.setTypeface(typeFace);
            holder.circularheadline.setTypeface(typeFace);
           // holder.circular_details.setTypeface(typeFace);
            convertView.setTag(holder);
        } else
            holder = (Circular_Adapter.ViewHolder) convertView.getTag();

         if(rowItem.getAttachment().equals("null")){
             holder.circular_attechment1.setVisibility(View.INVISIBLE);
         }else {
             holder.circular_attechment1.setVisibility(View.VISIBLE);
         }
        String Attachment= ServerApiadmin.MAIN_IPLINK+rowItem.getAttachment();
        Attachment=Attachment.replace("..","");
        holder.transactionid.setText(rowItem.getTransId());
        holder.circulardate.setText(rowItem.getCircularDate());
        holder.circularheadline.setText(rowItem.getHeadline());
        holder.circular_details.setText(Html.fromHtml(rowItem.getCircularDetails()));
        holder.circular_attechment.setText(Attachment);
     final Circular_Adapter.ViewHolder finalHolder = holder;
        holder.circular_attechment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = finalHolder.circular_attechment.getText().toString();
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
        circular_helpers.clear();
        if (charText.length() == 0) {
            circular_helpers.addAll(arraylist);
        } else {
            for (Circular_Helper wp : arraylist) {
                if (wp.getCircularDate().toLowerCase(Locale.getDefault())
                        .contains(charText)  || wp.getHeadline().toLowerCase(Locale.getDefault()).contains(charText))  {
                    circular_helpers.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
