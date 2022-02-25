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


import java.util.List;

import vidyawell.infotech.bsn.admin.Helpers.Conversation_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;

public class Conversation_Adapter extends ArrayAdapter<Conversation_Helper> {

    chatcustomButtonListener chatcustomListner;
    public interface chatcustomButtonListener {
        public void onButtonClickListner(int position, Conversation_Helper value);
    }

    public void setCustomButtonListner(chatcustomButtonListener listener) {
        this.chatcustomListner = listener;
    }
    Context context;

    List<Conversation_Helper> conversation_helpers;
    private LayoutInflater inflater;
    public Conversation_Adapter(@NonNull Context context, int resource, List<Conversation_Helper> conversation_helpers) {
        super(context, resource,conversation_helpers);
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
        TextView msg;
        TextView text_conversion_id;
        TextView datetime;
        TextView msgtype;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent ) {
        Conversation_Adapter.ViewHolder holder = null;
        Conversation_Helper rowItem = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            if(rowItem.getsend_type().equals("T")){
                convertView = mInflater.inflate(R.layout.conversation_list_item_send, null);
            }else{
                convertView = mInflater.inflate(R.layout.conversation_list_item_send, null);
            }
            Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/"+ ServerApiadmin.FONT);
            holder = new Conversation_Adapter.ViewHolder();
            holder.msg = (TextView) convertView.findViewById(R.id.text_chatmsg_new);
            holder.text_conversion_id = (TextView) convertView.findViewById(R.id.text_conversion_id);
            holder.datetime = (TextView) convertView.findViewById(R.id.text_chatdate_new);

            holder.msg.setTypeface(typeFace);
            holder.datetime.setTypeface(typeFace);
            convertView.setTag(holder);
        } else

        holder = (Conversation_Adapter.ViewHolder) convertView.getTag();
        holder.msg.setText(rowItem.getmsg());
        holder.datetime.setText(rowItem.getdatetime());
        holder.text_conversion_id.setText(rowItem.getConversationId());
        return convertView;
    }
}
