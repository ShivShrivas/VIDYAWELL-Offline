package vidyawell.infotech.bsn.admin;

import android.content.Context;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import org.hamcrest.Description;
import java.util.ArrayList;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;
import vidyawell.infotech.bsn.admin.Services.TypefaceUtil;

public class Salary_Slip extends AppCompatActivity {
    PieChart mChart;
    // we're going to display pie chart for school attendance
    private int[] yValues = {21, 5};
    private String[] xValues = {"Present Days", "Absents"};
    Description ds;
    Context context;
    // colors for different sections in pieChart
    public static  final int[] MY_COLORS = {
            Color.rgb(4,195,169), Color.rgb(253,169,23), Color.rgb(249,38,6),
            Color.rgb(44,142,255), Color.rgb(215,60,55)
    };
    Button  button_current_year,button_last_year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_slip);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_maintop));
        TypefaceUtil fontChanger = new TypefaceUtil(getAssets(), "fonts/"+ ServerApiadmin.FONT_DASHBOARD);
        fontChanger.replaceFonts((LinearLayout)findViewById(R.id.layout_salaryslip));
        TypefaceSpan typefaceSpan = new TypefaceSpan("fonts/"+ ServerApiadmin.FONT);
        SpannableString str = new SpannableString("Salary Slip");
        str.setSpan(typefaceSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(str);
        button_current_year=findViewById(R.id.button_current_year);
        button_last_year=findViewById(R.id.button_last_year);
        mChart = findViewById(R.id.chart1);
        ArrayList NoOfEmp = new ArrayList();
        NoOfEmp.add(new Entry(1040f, 0));
        NoOfEmp.add(new Entry(500f, 1));
        PieDataSet dataSet = new PieDataSet(NoOfEmp, "Salary Chart");
        ArrayList year = new ArrayList();
        year.add("Paid");
        year.add("Deduction");
        PieData data = new PieData(year, dataSet);
        mChart.setData(data);
        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : MY_COLORS)
            colors.add(c);
        dataSet.setColors(colors);
        mChart.animateXY(1500, 1500);
        mChart.setDescription("");
        mChart.setContentDescription("");
        Legend l = mChart.getLegend();
        l.setEnabled(false);
        button_current_year.setBackgroundResource(R.drawable.button_rounded_corners);
        button_current_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_current_year.setBackgroundResource(R.drawable.button_rounded_corners);
                button_last_year.setBackgroundResource(R.drawable.round_button_red);
            }
        });
        button_last_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_current_year.setBackgroundResource(R.drawable.round_button_red);
                button_last_year.setBackgroundResource(R.drawable.button_rounded_corners);
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
