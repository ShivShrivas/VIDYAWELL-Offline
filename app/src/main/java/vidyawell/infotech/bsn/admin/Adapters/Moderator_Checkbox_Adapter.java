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

import vidyawell.infotech.bsn.admin.Helpers.Moderator_Checkbox_Helper;
import vidyawell.infotech.bsn.admin.Helpers.Student_Checkbox_Helper;
import vidyawell.infotech.bsn.admin.R;

public class Moderator_Checkbox_Adapter extends ArrayAdapter<Moderator_Checkbox_Helper> {

    Moderator_Checkbox_Adapter.CheckcustomButtonListener5 customListner5;
    public interface CheckcustomButtonListener5 {
        public void onCheckClickListner5(int position, Moderator_Checkbox_Helper value, boolean isChecked);
    }
    public void setCheckcustomButtonListener5(Moderator_Checkbox_Adapter.CheckcustomButtonListener5 listener5) {
        this.customListner5 = listener5;
    }


    private Context mContext;
    private ArrayList<Moderator_Checkbox_Helper> listState;
    private Moderator_Checkbox_Adapter myAdapter;
    private boolean isFromView = false;

    public Moderator_Checkbox_Adapter(Context context, int resource, List<Moderator_Checkbox_Helper> objects, Resources res) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = (ArrayList<Moderator_Checkbox_Helper>) objects;
        this.myAdapter = this;

    }

  /*  @Override
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

   /* @Override
    public int getCount() {
        return listState.size();
    }

    @Override
    public Moderator_Checkbox_Helper getItem(int position) {
        return  listState.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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

        final Moderator_Checkbox_Adapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_moderator_item_chekbox, null);
            holder = new Moderator_Checkbox_Adapter.ViewHolder();
            holder.text_sname_chk_moderator = (TextView) convertView.findViewById(R.id.text_sname_chk_moderator);
            holder.text_sID_chk_moderato = (TextView) convertView.findViewById(R.id.text_sID_chk_moderato);
            holder.checkbox_moderato = (CheckBox) convertView.findViewById(R.id.checkbox_moderato);

            convertView.setTag(holder);
        } else {
            holder = (Moderator_Checkbox_Adapter.ViewHolder) convertView.getTag();
        }

        holder.text_sname_chk_moderator.setText(listState.get(position).getEmployeeName());
        holder.text_sID_chk_moderato.setText(listState.get(position).getEmployeeCode());


        // To check weather checked event fire from getview() or user input
       // isFromView = true;
       // holder.checkbox_stream.setChecked(listState.get(position).isSelected());
       // isFromView = false;

        if ((position == 0)) {
            holder.checkbox_moderato.setVisibility(View.INVISIBLE);
        } else {
            holder.checkbox_moderato.setVisibility(View.VISIBLE);
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


        final Moderator_Checkbox_Helper temp = getItem(position);
        holder.checkbox_moderato.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (customListner5 != null) {
                    customListner5.onCheckClickListner5(position, temp,isChecked);

                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView text_sname_chk_moderator,text_sID_chk_moderato;
        private CheckBox checkbox_moderato;
    }

}
