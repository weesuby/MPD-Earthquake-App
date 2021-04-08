// Name: Subhaan Saleem     Matriculation Number: S1708061

package org.me.gcu.myapplication;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AdditionalInfo extends AppCompatActivity implements View.OnClickListener {

    LinearLayout ShowData2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extendedinfo);
        ShowData2 = (LinearLayout) findViewById(R.id.ExtendedInfo);
        Bundle bundle = getIntent().getExtras();

        //Formatting of data displaying on additional info of earthquakes
        TextView descriptionView = new TextView(AdditionalInfo.this);
        String Description = bundle.getString("Description");
        descriptionView.setText(Description);
        descriptionView.setPadding(25, 15, 0, 0);
        descriptionView.setTextColor(getColor(R.color.royal_blue));
        descriptionView.setTextSize(30);
        ShowData2.addView(descriptionView);
    }

    @Override
    public void onClick(View v) {

    }
}
