// Name: Subhaan Saleem     Matriculation Number: S1708061

package org.me.gcu.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SearchTool extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<PullParser> alist;
    private TextView startDate;
    private TextView endDate;
    private Button searchButton;
    private LinearLayout resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        alist = (ArrayList<PullParser>) getIntent().getExtras().getSerializable("Items");
        startDate = (TextView) findViewById(R.id.startDate);
        endDate = (TextView) findViewById(R.id.endDate);
        searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this::onClick);
        resultList = (LinearLayout) findViewById(R.id.resultList);
    }


    @Override
    public void onClick(View v) {
        if (v == searchButton) {
            searchFunction();
        }
    }

    private void searchFunction() {
        SimpleDateFormat enterDateFormat = new SimpleDateFormat("dd-MM-yyyy"); // Date format when user inputs date
        SimpleDateFormat PullParserDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
        ArrayList<PullParser> inPull = new ArrayList();

        try {
            Date startDateString = enterDateFormat.parse(startDate.getText().toString());
            Date endDateDateString = enterDateFormat.parse(endDate.getText().toString());

            for (int i = 0; i < alist.size(); i++) {
                try {
                    Date currDate = PullParserDateFormat.parse(alist.get(i).getPubDate());
                    if (currDate.after(startDateString) && currDate.before(endDateDateString)) {
                        inPull.add(alist.get(i));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            PullParser east = null;
            PullParser west = null;
            PullParser north = null;
            PullParser south = null;
            PullParser magnitude = null;
            PullParser deepestdepth = null;
            PullParser shallowestdepth = null;


            for (int f = 0; f < inPull.size(); f++) {
                Log.e("DATA1", String.valueOf(f));
                Log.e("DATA2", String.valueOf(inPull.get(f).getLatitude()));
                Log.e("DATA3", String.valueOf(inPull.get(f).getLongitude()));
                Log.e("DATA4", String.valueOf(inPull.get(f).getMagnitude()));
                Log.e("DATA5", String.valueOf(inPull.get(f).getDepth()));
                Log.e("DATA6", String.valueOf(inPull.get(f).getDepth()));
                if (f == 0) {
                    north = inPull.get(0);
                    south = inPull.get(0);
                    west = inPull.get(0);
                    east = inPull.get(0);
                    deepestdepth = inPull.get(0);
                    shallowestdepth = inPull.get(0);

                    //Logical operations to show lowest or highest of values, to requirement
                } else {
                    if (Float.parseFloat(inPull.get(f).getLatitude()) > Float.parseFloat(north.getLatitude())) {
                        north = inPull.get(f);
                    } else if (Float.parseFloat(inPull.get(f).getLatitude()) < Float.parseFloat(south.getLatitude())) {
                        south = inPull.get(f);
                    } else if (Float.parseFloat(inPull.get(f).getLongitude()) < Float.parseFloat(west.getLongitude())) {
                        west = inPull.get(f);
                    } else if (Float.parseFloat(inPull.get(f).getLongitude()) > Float.parseFloat(east.getLongitude())) {
                        east = inPull.get(f);
                    }

                }
                if (f == 0) {
                    magnitude = inPull.get(0);
                } else if (Float.parseFloat(inPull.get(f).getMagnitude()) > Float.parseFloat(magnitude.getMagnitude())) {
                    magnitude = inPull.get(f);

                }
                if (f == 0) {
                    deepestdepth = inPull.get(0);
                } else if (Float.parseFloat(inPull.get(f).getDepth()) > Float.parseFloat(deepestdepth.getDepth())) {
                    deepestdepth = inPull.get(f);

                }
                if (f == 0) {
                    shallowestdepth = inPull.get(0);
                } else if (Float.parseFloat(inPull.get(f).getDepth()) < Float.parseFloat(shallowestdepth.getDepth())) {
                    shallowestdepth = inPull.get(f);

                }
            }

            // Formatting of search feed
            TextView northText = new TextView(SearchTool.this);
            northText.setText("Most Northern: " + north.getLocation());
            northText.setBackgroundColor(getColor(R.color.royal_blue));
            northText.setTextSize(20);
            northText.setPadding(50, 25, 0, 25);

            TextView southText = new TextView(SearchTool.this);
            southText.setText("Most Southern: " + south.getLocation());
            southText.setBackgroundColor(getColor(R.color.royal_blue));
            southText.setTextSize(20);
            southText.setPadding(50, 25, 0, 25);

            TextView westText = new TextView(SearchTool.this);
            westText.setText("Most Western: " + west.getLocation());
            westText.setBackgroundColor(getColor(R.color.royal_blue));
            westText.setTextSize(20);
            westText.setPadding(50, 25, 0, 25);

            TextView eastText = new TextView(SearchTool.this);
            eastText.setText("Most Eastern: " + east.getLocation());
            eastText.setBackgroundColor(getColor(R.color.royal_blue));
            eastText.setTextSize(20);
            eastText.setPadding(50, 25, 0, 25);

            TextView magnitudeText = new TextView(SearchTool.this);
            magnitudeText.setText("Largest Magnitude: " + magnitude.getMagnitude());
            magnitudeText.setBackgroundColor(getColor(R.color.royal_blue));
            magnitudeText.setTextSize(20);
            magnitudeText.setPadding(50, 25, 0, 25);

            TextView deepestDepthText = new TextView(SearchTool.this);
            deepestDepthText.setText("Deepest Depth: " + deepestdepth.getDepth() + "KM");
            deepestDepthText.setBackgroundColor(getColor(R.color.royal_blue));
            deepestDepthText.setTextSize(20);
            deepestDepthText.setPadding(50, 25, 0, 25);

            TextView shallowestDepthText = new TextView(SearchTool.this);
            shallowestDepthText.setText("Shallowest Depth: " + shallowestdepth.getDepth() + "KM");
            shallowestDepthText.setBackgroundColor(getColor(R.color.royal_blue));
            shallowestDepthText.setTextSize(20);
            shallowestDepthText.setPadding(50, 25, 0, 25);

            //Display info from variables
            resultList.addView(northText);
            resultList.addView(southText);
            resultList.addView(eastText);
            resultList.addView(westText);
            resultList.addView(magnitudeText);
            resultList.addView(shallowestDepthText);
            resultList.addView(deepestDepthText);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
