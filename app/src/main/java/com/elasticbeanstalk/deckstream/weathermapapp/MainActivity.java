package com.elasticbeanstalk.deckstream.weathermapapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.share.widget.ShareDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

interface AsyncResponse {
    void processFinish(JSONObject output) throws JSONException;
}

public class MainActivity extends AppCompatActivity  implements AsyncResponse, View.OnClickListener{
    JSONObject json;
    URL sample;
    ShareDialog share ;

    public JSONObject result(JSONObject j)
    {
        return j;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button search = (Button) findViewById(R.id.Search);
        Button about = (Button) findViewById(R.id.About);
        Button clear = (Button) findViewById(R.id.Clear);
        about.setOnClickListener(this);
        search.setOnClickListener(this);
        clear.setOnClickListener(this);
        String[] stateCodes = new String[52];  // 50 states + DC + blank
        String[] stateNames = new String[52];

        int stateCount = 0;
        stateCodes[stateCount ] = "Select";
        stateNames[stateCount ++] = "Select";

        stateCodes[stateCount]= "AL";
        stateNames[stateCount++] = "Alabama";
        stateCodes[stateCount]= "AK";
        stateNames[stateCount++] = "Alaska";
        stateCodes[stateCount]= "AZ";
        stateNames[stateCount++] = "Arizona";
        stateCodes[stateCount]= "AR";
        stateNames[stateCount++] = "Arkansas";
        stateCodes[stateCount]= "CA";
        stateNames[stateCount++] = "California";
        stateCodes[stateCount]= "CO";
        stateNames[stateCount++] = "Colorado";
        stateCodes[stateCount]= "CT";
        stateNames[stateCount++] = "Connecticut";
        stateCodes[stateCount]= "DE";
        stateNames[stateCount++] = "Delaware";
        stateCodes[stateCount]= "FL";
        stateNames[stateCount++] = "Florida";
        stateCodes[stateCount]= "GA";
        stateNames[stateCount++] = "Georgia";
        stateCodes[stateCount]= "HI";
        stateNames[stateCount++] = "Hawaii";
        stateCodes[stateCount]= "ID";
        stateNames[stateCount++] = "Idaho";
        stateCodes[stateCount]= "IA";
        stateNames[stateCount++] = "Iowa";
        stateCodes[stateCount]= "IL";
        stateNames[stateCount++] = "Illinois";
        stateCodes[stateCount]= "IN";
        stateNames[stateCount++] = "Indiana";
        stateCodes[stateCount]= "KS";
        stateNames[stateCount++] = "Kansas";
        stateCodes[stateCount]= "KY";
        stateNames[stateCount++] = "Kentucky";
        stateCodes[stateCount]= "LA";
        stateNames[stateCount++] = "Louisiana";
        stateCodes[stateCount]= "ME";
        stateNames[stateCount++] = "Maine";
        stateCodes[stateCount]= "MD";
        stateNames[stateCount++] = "Maryland";
        stateCodes[stateCount]= "MA";
        stateNames[stateCount++] = "Massachusetts";
        stateCodes[stateCount]= "MI";
        stateNames[stateCount++] = "Michigan";
        stateCodes[stateCount]= "MN";
        stateNames[stateCount++] = "Minnesota";
        stateCodes[stateCount]= "MS";
        stateNames[stateCount++] = "Mississippi";
        stateCodes[stateCount]= "MO";
        stateNames[stateCount++] = "Missouri";
        stateCodes[stateCount]= "MT";
        stateNames[stateCount++] = "Montana";
        stateCodes[stateCount]= "NE";
        stateNames[stateCount++] = "Nebraska";
        stateCodes[stateCount]= "NV";
        stateNames[stateCount++] = "Nevada";
        stateCodes[stateCount]= "NH";
        stateNames[stateCount++] = "New Hampshire";
        stateCodes[stateCount]= "NJ";
        stateNames[stateCount++] = "New Jersey";
        stateCodes[stateCount]= "NM";
        stateNames[stateCount++] = "New Mexico";
        stateCodes[stateCount]= "NY";
        stateNames[stateCount++] = "New York";
        stateCodes[stateCount]= "NC";
        stateNames[stateCount++] = "North Carolina";
        stateCodes[stateCount]= "ND";
        stateNames[stateCount++] = "North Dakota";
        stateCodes[stateCount]= "OH";
        stateNames[stateCount++] = "Ohio";
        stateCodes[stateCount]= "OK";
        stateNames[stateCount++] = "Oklahoma";
        stateCodes[stateCount]= "OR";
        stateNames[stateCount++] = "Oregon";
        stateCodes[stateCount]= "PA";
        stateNames[stateCount++] = "Pennsylvania";
        stateCodes[stateCount]= "RI";
        stateNames[stateCount++] = "Rhode Island";
        stateCodes[stateCount]= "SC";
        stateNames[stateCount++] = "South Carolina";
        stateCodes[stateCount]= "SD";
        stateNames[stateCount++] = "South Dakota";
        stateCodes[stateCount]= "TN";
        stateNames[stateCount++] = "Tennessee";
        stateCodes[stateCount]= "TX";
        stateNames[stateCount++] = "Texas";
        stateCodes[stateCount]= "UT";
        stateNames[stateCount++] = "Utah";
        stateCodes[stateCount]= "VT";
        stateNames[stateCount++] = "Vermont";
        stateCodes[stateCount]= "VA";
        stateNames[stateCount++] = "Virginia";
        stateCodes[stateCount]= "WA";
        stateNames[stateCount++] = "Washington";
        stateCodes[stateCount]= "DC";
        stateNames[stateCount++] = "Washington DC";
        stateCodes[stateCount]= "WV";
        stateNames[stateCount++] = "West Virgina";
        stateCodes[stateCount]= "WI";
        stateNames[stateCount++] = "Wisconsin";
        stateCodes[stateCount]= "WY";
        stateNames[stateCount++] = "Wyoming";

        Spinner dropdown = (Spinner)findViewById(R.id.state);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stateCodes);
        dropdown.setAdapter(adapter);
        ImageView forecast = (ImageView) findViewById(R.id.imageView2);

        forecast.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://forecast.io/"));
                startActivity(intent);
            }
        });




    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.Search:



                RadioButton us_check =  (RadioButton)  findViewById(R.id.us);
                Boolean us = us_check.isChecked();
                String degree;
                if(us) { degree = "us";}
                else{degree = "si";}
                EditText city_text = (EditText) findViewById(R.id.city);

                String city = city_text.getText().toString();
                EditText address_text = (EditText) findViewById(R.id.street);
                String address = address_text.getText().toString();
                Spinner dropdown = (Spinner)findViewById(R.id.state);

                String state_text = dropdown.getSelectedItem().toString();

                String urlP = "address=" + address + "&city=" + city + "&state=" + state_text + "&degree=" + degree;
                String urlParameters = urlP.replaceAll(" ", "+");
                TextView error = (TextView) findViewById(R.id.error);
                if(!address.matches("") ){
                    if(!city.matches("")){
                        if(!state_text.equals("Select")){
                            try {
                                URL url = new URL("http://deckstream.elasticbeanstalk.com/index1.php?" + urlParameters);
                                conn send = new conn();
                                send.delegate = this;

                                send.url = url;
                                send.execute();

                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        else {error.setText("Please select a State."); error.setVisibility(View.VISIBLE);}
                    }
                     else {error.setText("Please enter a City.");error.setVisibility(View.VISIBLE);}

                }
                else {error.setText("Please enter a Street.");error.setVisibility(View.VISIBLE);}




                break;
            case R.id.About:
                startActivity(new Intent(MainActivity.this, about.class));
                break;
            case R.id.detail:
                startActivity(new Intent(MainActivity.this, detail.class));

                break;
            case R.id.Clear:
                  RadioGroup r = (RadioGroup) findViewById(R.id.radioGroup);
                r.check(R.id.us);
                EditText city_clear = (EditText) findViewById(R.id.city);
                EditText address_clear = (EditText) findViewById(R.id.street);
                city_clear.setText("");
                address_clear.setText("");
                Spinner dropdown_clear = (Spinner)findViewById(R.id.state);
                dropdown_clear.setSelection(0);
                TextView error_clear = (TextView) findViewById(R.id.error);
                error_clear.setVisibility(View.GONE);
                break;

        }


    }



    @Override
    public void processFinish(final JSONObject json) throws JSONException {
        this.json = json;

        if(json == null){                TextView error = (TextView) findViewById(R.id.error); error.setText("No result returned, are you sure the address you enter exits?");error.setVisibility(View.VISIBLE);}

        else {
            EditText city_text = (EditText) findViewById(R.id.city);
            final String city = city_text.getText().toString();
            Spinner dropdown = (Spinner) findViewById(R.id.state);
            final String state_text = dropdown.getSelectedItem().toString();
            RadioButton us_check = (RadioButton) findViewById(R.id.us);
            final Boolean us = us_check.isChecked();



            Intent i = new Intent(MainActivity.this, recent.class);
            i.putExtra("json", String.valueOf(json));
            i.putExtra("city", city);
            i.putExtra("state", state_text);
            i.putExtra("degree", us);
            startActivity(i);

        }


    }







    class conn extends AsyncTask<String, JSONObject, JSONObject> {
        public URL url;
        public AsyncResponse delegate = null;


        @Override
        protected JSONObject doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            JSONArray response = new JSONArray();

            try {

                urlConnection = (HttpURLConnection) url.openConnection();
                int responseCode = urlConnection.getResponseCode();
                Log.v("MainActivity", "asq1:");
                Log.v("MainActivity", String.valueOf(url));

                if (responseCode == 200) {

                    Log.v("conn", "asq2:");

                    String responseString = readStream(urlConnection.getInputStream());
                    Log.v("conn", String.valueOf(responseString));
                    json = new JSONObject(responseString);

                    Log.v("conn", "asq3:");
                } else {
                    Log.v("CatalogClient", "Response code:" + responseCode);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }


            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json)
        {
            try {
                Log.i("Main", String.valueOf(json));            delegate.processFinish(json);



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        private String readStream(InputStream in) {
            BufferedReader reader = null;
            StringBuffer response = new StringBuffer();
            try {
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return response.toString();
        }

    }

}





