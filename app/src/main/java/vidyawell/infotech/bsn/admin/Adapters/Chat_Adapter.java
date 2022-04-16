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

import vidyawell.infotech.bsn.admin.Helpers.Chat_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;


public class Chat_Adapter extends ArrayAdapter<Chat_Helper> {

    chatcustomButtonListener chatcustomListner;
    public interface chatcustomButtonListener {
        public void onButtonClickListner(int position, Chat_Helper value);
    }

    public void setCustomButtonListner(chatcustomButtonListener listener) {
        this.chatcustomListner = listener;
    }
    Context context;

    List<Chat_Helper> homework_list;
    private LayoutInflater inflater;
    public Chat_Adapter(@NonNull Context context, int resource, List<Chat_Helper> homework_list) {
        super(context, resource,homework_list);
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
        TextView datetime;
        TextView msgtype;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent ) {
        Chat_Adapter.ViewHolder holder = null;
        Chat_Helper rowItem = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            if(rowItem.getsend_type().equals("S")){
                convertView = mInflater.inflate(R.layout.chat_list_item_send, null);
            }else{
                convertView = mInflater.inflate(R.layout.chat_list_item_recieve, null);
            }
            Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/"+ ServerApiadmin.FONT);
            holder = new Chat_Adapter.ViewHolder();
            holder.msg = (TextView) convertView.findViewById(R.id.text_chatmsg);
            holder.datetime = (TextView) convertView.findViewById(R.id.text_chatdate);
            holder.msg.setTypeface(typeFace);
            holder.datetime.setTypeface(typeFace);
            convertView.setTag(holder);
        } else
            holder = (Chat_Adapter.ViewHolder) convertView.getTag();
            holder.msg.setText(rowItem.getmsg());
            holder.datetime.setText(rowItem.getdatetime());
        return convertView;
    }
}
