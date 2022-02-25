package vidyawell.infotech.bsn.admin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vidyawell.infotech.bsn.admin.Database.Note;
import vidyawell.infotech.bsn.admin.Helpers.Chat_Helper;
import vidyawell.infotech.bsn.admin.Helpers.MerksEnter_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;


public class MarksEntry_Adapter extends ArrayAdapter<Note> {

    chatcustomButtonListener chatcustomListner;
    public interface chatcustomButtonListener {
        public void onButtonClickListner(int position, Note value,double ntrymark_theory,double entrymark_enrichment,double entrymark_notebook,double entrymark_practical,String islock);
    }

    public void setCustomButtonListner(chatcustomButtonListener listener) {
        this.chatcustomListner = listener;
    }
    Context context;
    List<Note> staff_helpers;
    private LayoutInflater inflater;
    ArrayList<Note> arraylist;
    Typeface typeFace;

    public MarksEntry_Adapter(@NonNull Context context, int resource, List<Note> homework_list) {
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
        TextView text_snumber;
        TextView textview_studentname;
        Button button_lock;
        EditText marks_theory,marks_practical,marks_notebook,marks_enrichment;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent ) {
        ViewHolder holder = null;
        final Note rowItem = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new MarksEntry_Adapter.ViewHolder();
            convertView = mInflater.inflate(R.layout.item_marksentry, null);
            Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
                    "fonts/"+ ServerApiadmin.FONT);
            holder = new ViewHolder();
            holder.text_snumber =convertView.findViewById(R.id.text_snumber);
            holder.textview_studentname =convertView.findViewById(R.id.textview_studentname);
            holder.button_lock =convertView.findViewById(R.id.button_lock);
            holder.marks_theory =convertView.findViewById(R.id.marks_theory);
            holder.marks_practical =convertView.findViewById(R.id.marks_practical);
            holder.marks_notebook =convertView.findViewById(R.id.marks_notebook);
            holder.marks_enrichment =convertView.findViewById(R.id.marks_enrichment);
            holder.text_snumber.setTypeface(typeFace);
            holder.textview_studentname.setTypeface(typeFace);
            holder.button_lock.setTypeface(typeFace);
            convertView.setTag(holder);
        } else
            holder = (MarksEntry_Adapter.ViewHolder) convertView.getTag();

            holder.text_snumber.setText(rowItem.getrowcount());
            holder.textview_studentname.setText(rowItem.getName());
            holder.marks_theory.setText(rowItem.gettheory());
            holder.marks_practical.setText(rowItem.getpractical());
            holder.marks_notebook.setText(rowItem.getnotebook());
            holder.marks_enrichment.setText(rowItem.getenrichment());
            if(rowItem.getmaxpractical().equalsIgnoreCase("")){
            holder.marks_practical.setVisibility(View.GONE);
        }else{
            holder.marks_practical.setVisibility(View.GONE);
        }
        if(rowItem.getlock().equalsIgnoreCase("0")){
            holder.button_lock.setBackgroundColor(Color.parseColor("#009688"));
            holder.button_lock.setText("UnLock");
        }else{
            holder.button_lock.setBackgroundColor(Color.parseColor("#C00521"));
            holder.button_lock.setText("Lock");
        }

        final Note temp = getItem(position);
        final ViewHolder finalHolder = holder;
        holder.button_lock.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if (chatcustomListner != null) {
                      if(finalHolder.marks_theory.getText().toString().length()==0){
                          Toast.makeText(getContext(),"Enter Theory Mark", Toast.LENGTH_LONG).show();
                      }else if(finalHolder.marks_notebook.getText().toString().length()==0){
                          Toast.makeText(getContext(),"Enter Notebook Mark", Toast.LENGTH_LONG).show();
                      }else if(finalHolder.marks_enrichment.getText().toString().length()==0){
                          Toast.makeText(getContext(),"Enter Enrichment Mark", Toast.LENGTH_LONG).show();
                      }else{
                          double entrymark_theory=Double.parseDouble(finalHolder.marks_theory.getText().toString());
                          double entrymark_enrichment=Double.parseDouble(finalHolder.marks_enrichment.getText().toString());
                          double entrymark_notebook=Double.parseDouble(finalHolder.marks_notebook.getText().toString());
                          double maxmark_theory=Double.parseDouble(rowItem.getmaxtheory());
                          double entrymark_practical=0;
                         // int maxmark_notebook=Integer.parseInt(finalHolder.marks_notebook.getText().toString());
                          if(entrymark_theory>maxmark_theory){
                              Toast.makeText(getContext(),"Entered theory mark not valid", Toast.LENGTH_LONG).show();
                          }else if(entrymark_notebook>5.0){
                              Toast.makeText(getContext(),"Entered notebook mark not valid", Toast.LENGTH_LONG).show();
                          }else if(entrymark_enrichment>5.0){
                              Toast.makeText(getContext(),"Entered enrichment mark not valid", Toast.LENGTH_LONG).show();
                          }else{
                              if(finalHolder.button_lock.getText().toString().equalsIgnoreCase("UnLock")){
                                  finalHolder.button_lock.setText("Lock");
                                  finalHolder.button_lock.setBackgroundColor(Color.parseColor("#C00521"));
                                  chatcustomListner.onButtonClickListner(position, temp,entrymark_theory,entrymark_enrichment,entrymark_notebook,entrymark_practical,"1");
                              }else{
                                 // finalHolder.button_lock.setText("Lock");
                                 // finalHolder.button_lock.setBackgroundColor(Color.parseColor("#C00521"));
                                  finalHolder.button_lock.setBackgroundColor(Color.parseColor("#009688"));
                                  finalHolder.button_lock.setText("UnLock");
                                  chatcustomListner.onButtonClickListner(position, temp,entrymark_theory,entrymark_enrichment,entrymark_notebook,entrymark_practical,"0");
                              }
                          }
                      }
                  }
              }
          });
        return convertView;
    }
}
