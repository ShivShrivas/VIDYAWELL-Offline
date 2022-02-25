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

import vidyawell.infotech.bsn.admin.Helpers.SectionList_Checkbox_Helper;
import vidyawell.infotech.bsn.admin.R;

public class BranchList_Checkbox_Adapter extends ArrayAdapter<BranchList_Checkbox_Helper> {


    BranchList_Checkbox_Adapter.CheckcustomButtonListener2 customListner2;
    public interface CheckcustomButtonListener2 {
        public void onCheckClickListner2(int position, BranchList_Checkbox_Helper value, boolean isChecked);
    }
    public void setCheckcustomButtonListener2(CheckcustomButtonListener2 listener2) {
        this.customListner2 = listener2;
    }

    private Context mContext;
    private ArrayList<BranchList_Checkbox_Helper> listState;
    private BranchList_Checkbox_Adapter myAdapter;
    private boolean isFromView = false;

    public BranchList_Checkbox_Adapter(Context context, int resource, List<BranchList_Checkbox_Helper> objects, Resources res) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = (ArrayList<BranchList_Checkbox_Helper>) objects;
        this.myAdapter = this;
    }


    /*@Override
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

        final BranchList_Checkbox_Adapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_branch_item_chekbox, null);
            holder = new BranchList_Checkbox_Adapter.ViewHolder();
            holder.text_sname_chk_bran = (TextView) convertView.findViewById(R.id.text_sname_chk_bran);
            holder.text_sID_chk_bran = (TextView) convertView.findViewById(R.id.text_sID_chk_bran);
            holder.checkbox_bran = (CheckBox) convertView.findViewById(R.id.checkbox_bran);

            convertView.setTag(holder);
        } else {
            holder = (BranchList_Checkbox_Adapter.ViewHolder) convertView.getTag();
        }

        holder.text_sname_chk_bran.setText(listState.get(position).getBrach_Name());
        holder.text_sID_chk_bran.setText(listState.get(position).getBranch_ID());


        // To check weather checked event fire from getview() or user input
       // isFromView = true;
       // holder.checkbox_bran.setChecked(listState.get(position).isSelected());
       // isFromView = false;

        if ((position == 0)) {
            holder.checkbox_bran.setVisibility(View.INVISIBLE);
        } else {
            holder.checkbox_bran.setVisibility(View.VISIBLE);
        }
       // holder.checkbox_bran.setTag(position);



        final BranchList_Checkbox_Helper temp = getItem(position);
        holder.checkbox_bran.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (customListner2 != null) {
                    customListner2.onCheckClickListner2(position, temp,isChecked);

                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView text_sname_chk_bran,text_sID_chk_bran;
        private CheckBox checkbox_bran;
    }

}
