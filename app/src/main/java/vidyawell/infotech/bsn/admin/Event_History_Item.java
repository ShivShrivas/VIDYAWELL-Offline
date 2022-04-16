package vidyawell.infotech.bsn.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import vidyawell.infotech.bsn.admin.Adapters.HistoryItemAdapter;

public class Event_History_Item extends AppCompatActivity {
    private TextView evevntDesctxt,eventdate,eventtitel,usernameTxt;
    private String evevntDescString,eventdateString,eventtitelString,evevntLatString,evevntLongString,userName;
    private String[] imageUrlString;
    ImageView imageViewLoaction;
    RecyclerView recyclerViewHistoryItem;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Event History Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i=getIntent();
        imageUrlString=i.getStringArrayExtra("imageurl");
        userName=i.getStringExtra("userName");
        eventtitelString= i.getStringExtra("eventTitle");
        evevntDescString =i.getStringExtra("eventDesc");
        evevntLatString =i.getStringExtra("lattitude");
        evevntLongString =i.getStringExtra("longitude");
        eventdateString= i.getStringExtra("eventTime");
        setContentView(R.layout.activity_event_history_item);
        imageViewLoaction=findViewById(R.id.locationImg);
        eventtitel=findViewById(R.id.eventtitel);
        usernameTxt=findViewById(R.id.usernameTxt);
        evevntDesctxt=findViewById(R.id.evevntDesctxt);
        eventdate=findViewById(R.id.evettimedate);
        recyclerViewHistoryItem=findViewById(R.id.recyclerViewHistoryItem);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            imageViewLoaction.setTooltipText("Event Location");
        }
        imageViewLoaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", Double.parseDouble(evevntLatString),Double.parseDouble(evevntLongString));
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(Event_History_Item.this, "Location is not found", Toast.LENGTH_SHORT).show();
                }

            }
        });
        eventtitel.setText("Event Id: "+eventtitelString);
        eventdate.setText("Event On: "+eventdateString);
        usernameTxt.setText("Uploaded by: "+userName);
        evevntDesctxt.setText(evevntDescString);
        recyclerViewHistoryItem.setLayoutManager(new GridLayoutManager(this,2));
        recyclerViewHistoryItem.setAdapter(new HistoryItemAdapter(this,imageUrlString));



    }
}