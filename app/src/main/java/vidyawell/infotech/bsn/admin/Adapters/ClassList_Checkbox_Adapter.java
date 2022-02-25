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

import vidyawell.infotech.bsn.admin.Helpers.ClassList_Checkbox_Helper;
import vidyawell.infotech.bsn.admin.Helpers.SectionList_Checkbox_Helper;
import vidyawell.infotech.bsn.admin.R;

public class ClassList_Checkbox_Adapter extends ArrayAdapter<ClassList_Checkbox_Helper> {
    private Context mContext;
    private ArrayList<ClassList_Checkbox_Helper> listState;
    private ClassList_Checkbox_Adapter myAdapter;
    private boolean isFromView = false;

    public ClassList_Checkbox_Adapter(Context context, int resource, List<ClassList_Checkbox_Helper> objects, Resources res) {
        super(context, resource, objects);
        this.mContext = context;
        this.listState = (ArrayList<ClassList_Checkbox_Helper>) objects;
        this.myAdapter = this;
    }

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

        final ClassList_Checkbox_Adapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_class_item_chekbox, null);
            holder = new ClassList_Checkbox_Adapter.ViewHolder();
            holder.mTextView = (TextView) convertView.findViewById(R.id.text_sname_chk);
            holder.text_sID = (TextView) convertView.findViewById(R.id.text_sID_chk);
            holder.mCheckBox = (CheckBox) convertView.findViewById(R.id.checkbox_);

            convertView.setTag(holder);
        } else {
            holder = (ClassList_Checkbox_Adapter.ViewHolder) convertView.getTag();
        }

        holder.mTextView.setText(listState.get(position).getClassName());
        holder.text_sID.setText(listState.get(position).getClassID());


        // To check weather checked event fire from getview() or user input
        isFromView = true;
        holder.mCheckBox.setChecked(listState.get(position).isSelected());
        isFromView = false;

        if ((position == 0)) {
            holder.mCheckBox.setVisibility(View.INVISIBLE);
        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);
        }
        holder.mCheckBox.setTag(position);
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();

                if (!isFromView) {
                    listState.get(position).setSelected(isChecked);
                    // Toast.makeText(getContext(),position,Toast.LENGTH_SHORT).show();
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private TextView mTextView,text_sID;
        private CheckBox mCheckBox;
    }

}
