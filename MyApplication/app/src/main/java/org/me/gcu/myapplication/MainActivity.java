// Name: Subhaan Saleem     Matriculation Number: S1708061

package org.me.gcu.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private Button mapButton;
    private String result = "";
    private String urlSource = "http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    LinkedList<PullParser> alist = new LinkedList<PullParser>();
    LinearLayout showData;
    Button search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MyTag", "in onCreate");
        // Set up the raw links to the graphical components
        Log.e("MyTag", "after startButton");
        search = (Button) findViewById(R.id.Search);
        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(org.me.gcu.myapplication.MainActivity.this, SearchTool.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Items", alist);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mapButton = (Button) findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Googlemaps.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Items", alist);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        // More Code goes here
        showData = (LinearLayout) findViewById(R.id.showData);
        startProgress();
    }


    public void startProgress() {
        // Run network access on a separate thread;
        new Thread(new Task(urlSource)).start();
    } //

    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    private class Task implements Runnable {
        private String url;

        public Task(String aurl) {
            url = aurl;
        }

        @Override
        public void run() {

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";


            Log.e("MyTag", "in run");

            try {
                Log.e("MyTag", "in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                Log.e("MyTag", "after ready");
                //
                // Now read the data. Make sure that there are no specific headers
                // in the data file that you need to ignore.
                // The useful data that you need is in each of the item entries
                //
                while ((inputLine = in.readLine()) != null) {
                    result = result + inputLine;
                    Log.e("MyTag", inputLine);

                }
                in.close();
            } catch (IOException ae) {
                Log.e("MyTag", "ioexception in run");
            }

            //
            // Now that you have the xml data you can parse it
            //

            LinkedList<PullParser> infoList = parseData(result);

            if (infoList != null) {
                Log.e("MyTag", "List not null");
                for (Object o : infoList) {
                    Log.e("MyTag", o.toString());
                }
            } else {
                Log.e("MyTag", "List is null");
            }

            org.me.gcu.myapplication.MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    for (int i = 0; i < infoList.size(); i++) {
                        Button button = new Button(MainActivity.this);
                        button.setBackgroundColor(getColor(R.color.royal_blue));
                        button.setTextSize(20);
                        button.setText(infoList.get(i).getLocation() + "\n" + infoList.get(i).getMagnitude());
                        String Description = infoList.get(i).getDescription();
                        button.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this, AdditionalInfo.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("Items", infoList);
                                intent.putExtras(bundle);
                                intent.putExtra("Description", Description);
                                startActivity(intent);
                            }
                        });

                        if (Float.parseFloat(infoList.get(i).getMagnitude()) < 1) {
                            button.setTextColor(Color.parseColor("green"));
                        } else if (Float.parseFloat(infoList.get(i).getMagnitude()) >= 1 && Float.parseFloat(infoList.get(i).getMagnitude()) <= 2) {
                            button.setTextColor(Color.parseColor("yellow"));
                        } else if (Float.parseFloat(infoList.get(i).getMagnitude()) > 2) {
                            button.setTextColor(Color.parseColor("red"));
                        }
                        button.setGravity(Gravity.CENTER);
                        showData.addView(button);
                    }
                }
            });
        }

    }

    private LinkedList<PullParser> parseData(String notNulled) {

        PullParser item = new PullParser();
        try {
            String dataToParse = notNulled.replace("null", "");
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(dataToParse));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equalsIgnoreCase("item")) {

                        item = new PullParser();
                    } else if (xpp.getName().equalsIgnoreCase("title")) {

                        String temp = xpp.nextText();
                        item.setTitle(temp);

                    } else if (xpp.getName().equalsIgnoreCase("description")) {
                        String temp = xpp.nextText();

                        item.setDescription(temp);
                    } else if (xpp.getName().equalsIgnoreCase("link")) {
                        String temp = xpp.nextText();
                        item.setLink(temp);

                    } else if (xpp.getName().equalsIgnoreCase("pubDate")) {
                        String temp = xpp.nextText();
                        item.setPubDate(temp);

                    } else if (xpp.getName().equalsIgnoreCase("category")) {
                        String temp = xpp.nextText();
                        item.setCategory(temp);

                    } else if (xpp.getName().equalsIgnoreCase("geo:lat")) {
                        String temp = xpp.nextText();

                        item.setLatitude(temp);
                    } else if (xpp.getName().equalsIgnoreCase("geo:long")) {
                        String temp = xpp.nextText();

                        item.setLongitude(temp);
                    }


                } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {


                    alist.add(item);

                }

                eventType = xpp.next();

            }
        } catch (XmlPullParserException ae1) {
            Log.e("MyTag", "Parsing error" + ae1.toString());
        } catch (IOException ae1) {
            Log.e("MyTag", "IO error during parsing");
        }

        Log.e("MyTag", "End document");

        return alist;

    }

}