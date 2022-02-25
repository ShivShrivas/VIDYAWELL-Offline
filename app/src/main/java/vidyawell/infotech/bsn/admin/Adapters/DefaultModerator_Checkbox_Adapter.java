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

import vidyawell.infotech.bsn.admin.Helpers.DefaultModerator_Checkbox_Helper;

import vidyawell.infotech.bsn.admin.R;

public class DefaultModerator_Checkbox_Adapter extends ArrayAdapter<DefaultModerator_Checkbox_Helper> {

    DefaultModerator_Checkbox_Adapter.CheckcustomButtonListener6 customListner6;
    public interface CheckcustomButtonListener6 {
        public void onCheckClickListner6(int position, DefaultModerator_Checkbox_Helper value, boolean isChecked);
    }
    public void setCheckcustomButtonListener6(DefaultModerator_Checkbox_Adapter.CheckcustomButtonListener6 listener6) {
        this.customListner6 = listener6;
    }

    //////

  /*  DefaultModerator_Checkbox_Adapter.CheckcustomButtonListener11 customListner11;
    public interface CheckcustomButtonListener11 {
        public void onCheckClickListner11(int position, DefaultModerator_Checkbox_Helper value, boolean isChecked);
    }
    public void setCheckcustomButtonListener11(DefaultModerator_Checkbox_Adapter.CheckcustomButtonListener11 listener11) {
        this.customListner11 = listener11;
    }*/


    private Context mContext;
    private List<DefaultModerator_Checkbox_Helper> listState;
  ///  private DefaultModerator_Checkbox_Adapter myAdapter;
    private boolean isFromView = false;

    public DefaultModerator_Checkbox_Adapter(Context context, int resource, List<DefaultModerator_Checkbox_Helper> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = objects;
       // this.myAdapter = this;

       // int size1 =listState.size();

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
    public int getCount() {
        int size=listState.size();
        return listState.size();
    }
   *//* @Override
    public DefaultModerator_Checkbox_Helper getItem(int position) {
        return listState.get(position);
    }*//*
    @Override
    public long getItemId(int position) {
        return position;
    }
*/



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

        final DefaultModerator_Checkbox_Adapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_default_moderator_item_chekbox, null);
            holder = new DefaultModerator_Checkbox_Adapter.ViewHolder();
            holder.text_sname_chk_moderator = (TextView) convertView.findViewById(R.id.text_sname_chk_moderator);
            holder.text_sID_chk_moderato = (TextView) convertView.findViewById(R.id.text_sID_chk_moderato);
            holder.checkbox_moderato = (CheckBox) convertView.findViewById(R.id.checkbox_moderato);
            holder.checkbox_moderato_new = (CheckBox) convertView.findViewById(R.id.checkbox_moderato_new);

            convertView.setTag(holder);
        } else {
            holder = (DefaultModerator_Checkbox_Adapter.ViewHolder) convertView.getTag();
        }

        holder.text_sname_chk_moderator.setText(listState.get(position).getEmployeeName());
        holder.text_sID_chk_moderato.setText(listState.get(position).getEmployeeCode());
       // holder.checkbox_moderato.setChecked(listState.get(position).getcheck_status());

      /*  if(listState.get(position).getcheck_status()){
            holder.checkbox_moderato_new.setVisibility(View.VISIBLE);
            holder.checkbox_moderato.setVisibility(View.GONE);
        }else {

            holder.checkbox_moderato.setVisibility(View.VISIBLE);
            holder.checkbox_moderato_new.setVisibility(View.GONE);
        }*/

        // To check weather checked event fire from getview() or user input
       // isFromView = true;
       // holder.checkbox_stream.setChecked(listState.get(position).isSelected());
       // isFromView = false;

        if ((position == 0)) {
            holder.checkbox_moderato.setVisibility(View.INVISIBLE);
        } else {
            holder.checkbox_moderato.setVisibility(View.VISIBLE);
        }


        holder.checkbox_moderato.setTag(position);


        final DefaultModerator_Checkbox_Helper temp = getItem(position);
        holder.checkbox_moderato.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (customListner6 != null) {
                    customListner6.onCheckClickListner6(position, temp,isChecked);

                }
            }
        });



      /*  final DefaultModerator_Checkbox_Helper temp1 = getItem(position);
        holder.checkbox_moderato_new.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (customListner11!= null) {
                    customListner11.onCheckClickListner11(position, temp1,isChecked);

                }
            }
        });*/

        return convertView;
    }

    private class ViewHolder {
        private TextView text_sname_chk_moderator,text_sID_chk_moderato;
        private CheckBox checkbox_moderato,checkbox_moderato_new;
    }

}
