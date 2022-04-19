package vidyawell.infotech.bsn.admin.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import vidyawell.infotech.bsn.admin.R;

public class HistoryItemAdapter extends RecyclerView.Adapter<HistoryItemAdapter.HistoryItemViewHolder> {
    Context context;
    String[] imageUrlString;
    public HistoryItemAdapter(Context context, String[] imageUrlString) {
        this.context=context;
        this.imageUrlString=imageUrlString;
    }

    @NonNull
    @Override
    public HistoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_image_card,parent,false);
       return new HistoryItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryItemViewHolder holder, int position) {
        String newImgUrl=imageUrlString[position].replace(" ../wwwroot","http://uploadeventsvc.bsninfotech.org");
        Log.d("TAG", "onBindViewHolder: "+imageUrlString);
        Glide.with(holder.imageMain1).load(newImgUrl).into(holder.imageMain1);
    }

    @Override
    public int getItemCount() {
        return imageUrlString.length;
    }

    public class HistoryItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageMain1;
        public HistoryItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageMain1=itemView.findViewById(R.id.imageMain1);
        }
    }
}
