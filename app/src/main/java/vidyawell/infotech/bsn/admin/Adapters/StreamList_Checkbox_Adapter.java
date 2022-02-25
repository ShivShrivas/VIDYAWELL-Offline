package vidyawell.infotech.bsn.admin.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vidyawell.infotech.bsn.admin.Helpers.BranchList_Checkbox_Helper;
import vidyawell.infotech.bsn.admin.Helpers.StreamList_Checkbox_Helper;
import vidyawell.infotech.bsn.admin.R;

public class StreamList_Checkbox_Adapter extends ArrayAdapter<StreamList_Checkbox_Helper> {

    StreamList_Checkbox_Adapter.CheckcustomButtonListener3 customListner3;
    public interface CheckcustomButtonListener3 {
        public void onCheckClickListner3(int position, StreamList_Checkbox_Helper value, boolean isChecked);
    }
    public void setCheckcustomButtonListener3(StreamList_Checkbox_Adapter.CheckcustomButtonListener3 listener3) {
        this.customListner3 = listener3;
    }


    private Context mContext;
    private ArrayList<StreamList_Checkbox_Helper> listState;
    private StreamList_Checkbox_Adapter myAdapter;
    private boolean isFromView = false;

    public StreamList_Checkbox_Adapter(Context context, int resource, List<StreamList_Checkbox_Helper> objects, Resources res) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = (ArrayList<StreamList_Checkbox_Helper>) objects;
        this.myAdapter = this;
    }


   /* @Override
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
    }*/



    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView,
                              ViewGroup parent) {

        final StreamList_Checkbox_Adapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_stream_item_chekbox, null);
            holder = new StreamList_Checkbox_Adapter.ViewHolder();
            holder.text_sname_chk_stream = (TextView) convertView.findViewById(R.id.text_sname_chk_stream);
            holder.text_sID_chk_stream = (TextView) convertView.findViewById(R.id.text_sID_chk_stream);
            holder.checkbox_stream = (CheckBox) convertView.findViewById(R.id.checkbox_stream);

            convertView.setTag(holder);
        } else {
            holder = (StreamList_Checkbox_Adapter.ViewHolder) convertView.getTag();
        }

        holder.text_sname_chk_stream.setText(listState.get(position).getStreamName());
        holder.text_sID_chk_stream.setText(listState.get(position).getStreamID());


        // To check weather checked event fire from getview() or user input
       // isFromView = true;
       // holder.checkbox_stream.setChecked(listState.get(position).isSelected());
       // isFromView = false;

        if ((position == 0)) {
            holder.checkbox_stream.setVisibility(View.INVISIBLE);
        } else {
            holder.checkbox_stream.setVisibility(View.VISIBLE);
        }


       // holder.checkbox_stream.setTag(position);
       /* holder.checkbox_stream.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();

                if (!isFromView) {
                    listState.get(position).setSelected(isChecked);
                    // Toast.makeText(getContext(),position,Toast.LENGTH_SHORT).show();
                }
            }
        });*/


        final StreamList_Checkbox_Helper temp = getItem(position);
        holder.checkbox_stream.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (customListner3 != null) {
                    customListner3.onCheckClickListner3(position, temp,isChecked);

                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView text_sname_chk_stream,text_sID_chk_stream;
        private CheckBox checkbox_stream;
    }

}
