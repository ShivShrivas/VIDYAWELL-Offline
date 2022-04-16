package vidyawell.infotech.bsn.admin.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vidyawell.infotech.bsn.admin.Event_History_Item;
import vidyawell.infotech.bsn.admin.Event_History_List;
import vidyawell.infotech.bsn.admin.Helpers.EventHistory;
import vidyawell.infotech.bsn.admin.R;

public class EventHistoryNastedAdapeter extends RecyclerView.Adapter<EventHistoryNastedAdapeter.EventHistoryNastedViewHolder> {
    Context context;
    List<EventHistory> eventHistoryList=new ArrayList<>();

    public EventHistoryNastedAdapeter(Context context, List<EventHistory> eventHistoryList) {
        this.context=context;
        this.eventHistoryList=eventHistoryList;

    }



    @NonNull
    @Override
    public EventHistoryNastedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.event_histroy_nested_itemcard,parent,false);
        return new EventHistoryNastedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHistoryNastedViewHolder holder, @SuppressLint("RecyclerView") int position) {
        int[] androidColors = context.getResources().getIntArray(R.array.colors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        holder.layourCardHistory.setBackgroundColor(randomAndroidColor);
        String[] imgUrl = eventHistoryList.get(position).getEventPhotoPath().split(",");
            holder.viewMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(context, Event_History_Item.class);

                    i.putExtra("imageurl",imgUrl);
                    i.putExtra("eventTitle",eventHistoryList.get(position).getEventId());
                    i.putExtra("eventDesc",eventHistoryList.get(position).getEventDesc());
                    i.putExtra("eventTime",eventHistoryList.get(position).getEventDateTime());
                    i.putExtra("userName",eventHistoryList.get(position).getUserName());
                    i.putExtra("longitude",eventHistoryList.get(position).get_long());
                    i.putExtra("lattitude",eventHistoryList.get(position).getLat());
                    context.startActivity(i);
                }
            });
            holder.evenetDesctxt.setText(eventHistoryList.get(position).getEventDesc());
            holder.eventTitletxt.setText(eventHistoryList.get(position).getEventName());
            if (imgUrl.length<=1){
                holder.picdetailstxt.setText(String.valueOf(imgUrl.length)+" picture was uploaded");
            }
           else holder.picdetailstxt.setText(String.valueOf(imgUrl.length)+" pictures were uploaded");



    }

    @Override
    public int getItemCount() {
        return eventHistoryList.size();
    }

    public class EventHistoryNastedViewHolder extends RecyclerView.ViewHolder {
        TextView eventTitletxt,picdetailstxt,viewMore,evenetDesctxt;
        ConstraintLayout layourCardHistory;

        public EventHistoryNastedViewHolder(@NonNull View itemView) {
            super(itemView);
            evenetDesctxt=itemView.findViewById(R.id.evenetDesctxt);
            picdetailstxt=itemView.findViewById(R.id.picdetailstxt);
            viewMore=itemView.findViewById(R.id.viewMore);
            eventTitletxt=itemView.findViewById(R.id.eventTitletxt);
            layourCardHistory=itemView.findViewById(R.id.constraintLayoutChangable);
        }
    }
}
