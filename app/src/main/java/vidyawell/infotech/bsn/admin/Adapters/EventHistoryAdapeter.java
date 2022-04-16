package vidyawell.infotech.bsn.admin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import java.util.List;

import vidyawell.infotech.bsn.admin.Helpers.EventHistory;
import vidyawell.infotech.bsn.admin.R;

public class EventHistoryAdapeter extends RecyclerView.Adapter<EventHistoryAdapeter.EventHistoryViewHolder> {
    Context context;
    List<EventHistory> eventHistoryList=new ArrayList<>();
    List<String> headerDateList=new ArrayList<>();
    public EventHistoryAdapeter(Context context, List<EventHistory> eventHistoryList, List<String> headerDateList) {
        this.context=context;
        this.headerDateList=headerDateList;
        this.eventHistoryList=eventHistoryList;
    }

    @NonNull
    @Override
    public EventHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.event_history_itemcard,parent,false);
        return new EventHistoryViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull EventHistoryViewHolder holder, int position) {
        List<EventHistory> eventHistoryListdateWise=new ArrayList<>();
        LinearLayoutManager mLayoutManager;
        holder.text_bname.setText(headerDateList.get(position));
        for (int i = 0; i < eventHistoryList.size(); i++) {
            if (eventHistoryList.get(i).getSyncDate().equals(headerDateList.get(position))){
                eventHistoryListdateWise.add(eventHistoryList.get(i));
            }
        }
        mLayoutManager = new LinearLayoutManager(context);
        holder.eventHistoryNestedRecview.setLayoutManager(mLayoutManager);
        holder.eventHistoryNestedRecview.setItemAnimator(new DefaultItemAnimator());
        holder.adapeter=new EventHistoryNastedAdapeter(context,eventHistoryListdateWise);
        holder.eventHistoryNestedRecview.setAdapter(holder.adapeter);
    }

    @Override
    public int getItemCount() {
        return headerDateList.size();
    }

    public class EventHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView text_bname;
        RecyclerView eventHistoryNestedRecview;
        EventHistoryNastedAdapeter adapeter;
        public EventHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            text_bname=itemView.findViewById(R.id.headedatetxt);
            eventHistoryNestedRecview=itemView.findViewById(R.id.eventHistoryNestedRecview);

        }
    }
}
