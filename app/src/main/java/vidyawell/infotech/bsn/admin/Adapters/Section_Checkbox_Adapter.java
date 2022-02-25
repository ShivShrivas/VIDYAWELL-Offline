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


import vidyawell.infotech.bsn.admin.Helpers.Complaint_View_Helper;
import vidyawell.infotech.bsn.admin.Helpers.SectionList_Checkbox_Helper;
import vidyawell.infotech.bsn.admin.R;

public class Section_Checkbox_Adapter extends ArrayAdapter<SectionList_Checkbox_Helper> {

    /*customButtonListener customListner;
    public interface customButtonListener {
        public void onButtonClickListner(int position, SectionList_Checkbox_Helper value,boolean isChecked);

       // void onButtonClickListner(int position, SectionList_Checkbox_Helper temp);
    }

    public void setCustomButtonListner(Section_Checkbox_Adapter.customButtonListener listener) {
        this.customListner = listener;
    }*/

    CheckcustomButtonListener customListner;
    public interface CheckcustomButtonListener {
        public void onCheckClickListner(int position, SectionList_Checkbox_Helper value, boolean isChecked);
    }
    public void setCheckcustomButtonListener(CheckcustomButtonListener listener) {
        this.customListner = listener;
    }









    private Context mContext;
    private ArrayList<SectionList_Checkbox_Helper> listState;
    private Section_Checkbox_Adapter myAdapter;
    private boolean isFromView = true;

    public Section_Checkbox_Adapter(Context context, int resource, List<SectionList_Checkbox_Helper> objects, Resources res) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = (ArrayList<SectionList_Checkbox_Helper>) objects;
        this.myAdapter = this;
    }

 /*   @Override
    public int getViewTypeCount() {
        return 1;
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }*/

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
      //  SectionList_Checkbox_Helper rowItem = getItem(position);

        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_section_item_chekbox, null);
            holder = new ViewHolder();
            holder.text_sname_chk_sec = (TextView) convertView.findViewById(R.id.text_sname_chk_sec);
            holder.text_sID_chk_sec = (TextView) convertView.findViewById(R.id.text_sID_chk_sec);
            holder.checkbox_sec = (CheckBox) convertView.findViewById(R.id.checkbox_sec);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text_sname_chk_sec.setText(listState.get(position).getSecName());
        holder.text_sID_chk_sec.setText(listState.get(position).getSecID());
      // holder.checkbox_sec.setChecked(listState.get(position).getcheck_status());


        // To check weather checked event fire from getview() or user input
        //isFromView = true;
      // holder.checkbox_sec.setChecked(listState.get(position).isSelected());
        //isFromView = false;

        if ((position == 0)) {

            holder.checkbox_sec.setVisibility(View.INVISIBLE);

        } else {
            holder.checkbox_sec.setVisibility(View.VISIBLE);

        }


       // holder.checkbox_sec.setChecked(true);

        //holder.checkbox_sec.setChecked(true);
       // holder.checkbox_sec.setTag(position);
       /* holder.checkbox_sec.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();

                if (!isFromView) {
                    listState.get(position).setSelected(isChecked);
                    listState.get(position).getSecID();
                  //  Toast.makeText(getContext(),""+ listState.get(position).getSecID(),Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        final SectionList_Checkbox_Helper temp = getItem(position);
        holder.checkbox_sec.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (customListner != null) {
                    customListner.onCheckClickListner(position, temp,isChecked);


                }
            }
        });


       /*  final SectionList_Checkbox_Helper temp = getItem(position);
        holder.checkbox_sec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
                if (customListner != null) {
                    customListner.onButtonClickListner(position, temp);

                    // Toast.makeText(context, finalHolder.mobile_number.getText().toString(), Toast.LENGTH_SHORT).show();
                }


            }
        });
*/

        return convertView;
    }


    private class ViewHolder {
        private TextView text_sname_chk_sec,text_sID_chk_sec;
        private CheckBox checkbox_sec;
    }



}
