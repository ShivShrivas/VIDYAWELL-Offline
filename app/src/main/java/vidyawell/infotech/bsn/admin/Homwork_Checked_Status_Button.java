package vidyawell.infotech.bsn.admin;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Homwork_Checked_Status_Button extends AppCompatActivity {
    ApplicationControllerAdmin applicationControllerAdmin;
    LinearLayout txt_submited_history,txt_status;
    String SectionId,ClassId,TopicName,EmployeeCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homwork__checked__status__button);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        applicationControllerAdmin = (ApplicationControllerAdmin) getApplication();
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/" + ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((LinearLayout) findViewById(R.id.layout_homework_status));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/" + ServerApiadmin.FONT_DASHBOARD);
        SpannableString str = new SpannableString("Homework Activity");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);

        Intent intent=getIntent();
        SectionId=intent.getStringExtra("SectionId");
        ClassId=intent.getStringExtra("ClassId");
        TopicName=intent.getStringExtra("TopicName");


        txt_status=(LinearLayout)findViewById(R.id.txt_status);
        txt_submited_history=(LinearLayout)findViewById(R.id.txt_submited_history);


        txt_submited_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homwork_Checked_Status_Button.this,Homework_notificatin_submit.class);
                intent.putExtra("SectionId",SectionId);
                intent.putExtra("ClassId",ClassId);
                intent.putExtra("TopicName",TopicName);
                startActivityForResult(intent,100);
            }
        });

        txt_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homwork_Checked_Status_Button.this,Checked_Unchecked.class);
                intent.putExtra("SectionId",SectionId);
                intent.putExtra("ClassId",ClassId);
                intent.putExtra("TopicName",TopicName);
                startActivityForResult(intent,100);
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

}
