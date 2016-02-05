package com.elasticbeanstalk.deckstream.weathermapapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by x on 2015/11/16.
 */
public class recent extends FragmentActivity {
    JSONObject json;
    CallbackManager call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
   call =  CallbackManager.Factory.create();

        setContentView(R.layout.activity_detail);
        Bundle extras = getIntent().getExtras();
        String need = null;
        String city = null;
        Boolean us= null;
        String state_text = null;
        if (savedInstanceState == null) {
            if(extras == null) {
                need= null;
                city= null;

            } else {
                need= extras.getString("json");
                city= extras.getString("city");;
                state_text= extras.getString("state");;
                us = extras.getBoolean("degree");
            }
        } else {
            need= (String) savedInstanceState.getSerializable("json");
            city= (String) savedInstanceState.getSerializable("city");
            state_text= (String) savedInstanceState.getSerializable("state");
        }

        try {
            json = new JSONObject(need);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try{



        Double precipIntensity;
        if (us) {
            precipIntensity = json.getJSONObject("currently").getDouble("precipIntensity");
        } else {
            precipIntensity = json.getJSONObject("currently").getDouble("precipIntensity") / 25.4;
        }
        String pre = pre_convert(precipIntensity);

        String ChanceRain = String.valueOf((int) json.getJSONObject("currently").getDouble("precipProbability") * 100 + "%");

        String windSpeed_temp = String.valueOf(json.getJSONObject("currently").getDouble("windSpeed"));
        String wind;
        if (us) {
            wind = windSpeed_temp + "mph";
        } else {
            wind = windSpeed_temp + "m/s";
        }

        String deww = String.valueOf(json.getJSONObject("currently").getDouble("dewPoint"));
        String dew;
        if (us) {
            dew = deww + "\u2109";
        } else {
            dew = deww + "\u2103";
        }

        String humidity = String.valueOf((int) json.getJSONObject("currently").getDouble("humidity") * 100 ) + "%";

        String visibility = String.valueOf(json.getJSONObject("currently").getDouble("visibility"));
        String vis;
        if (us) {
            vis = visibility + "mi";
        } else {
            vis = visibility + "km";
        }
        final Double latitude = json.getDouble("latitude");
        final Double longitude = json.getDouble("longitude");
        String rise = json.getJSONArray("0").getString(0);
        String set = json.getJSONArray("0").getString(1);

        final String summary = json.getJSONObject("currently").getString("summary") + " in" + " " + city + ", " + state_text;
        final String sum_fb = json.getJSONObject("currently").getString("summary");
        String min = String.valueOf((int) json.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getDouble("temperatureMin"));
        String max = String.valueOf((int) json.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getDouble("temperatureMax"));
        String min_max = "L:" + min + (char) 0x00B0 + "|" + "H:" + max + (char) 0x00B0;
        String tempe = String.valueOf((int) json.getJSONObject("currently").getDouble("temperature"));
           final String summary_final =  json.getJSONObject("currently").getString("summary");
         String te = tempe;
            if (us) {
                te = te + "\u2109";
            } else {
                te = te + "\u2103";
            }

            final String te_final = te;
            setContentView(R.layout.recent);
        ImageView fb = (ImageView) findViewById(R.id.fb);
        final String icon   =  json.getJSONObject("currently").getString("icon");
            final String finalcity = city;
            final String state = state_text;
        fb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                String title = "Current Weather in " + finalcity + ", " + state;

                FacebookSdk.sdkInitialize(getApplicationContext());

                ShareDialog shareDialog = new ShareDialog(recent.this);

                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle(title)
                        .setImageUrl(Uri.parse(icon_select(icon)))
                        .setContentDescription(
                                summary_final + ", "+te_final)
                        .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                        .build();

                shareDialog.show(linkContent);

                shareDialog.registerCallback(call, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(getApplicationContext(), "Facebook Post Successful", Toast.LENGTH_SHORT).show();


                    } @Override
                    public void onCancel() {
                        Log.v("asd", "asd");
                        Toast.makeText(getApplicationContext(), "Posted Cancelled",
                                Toast.LENGTH_LONG).show();
                    }


                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(getApplicationContext(), "Unknown Error",
                                Toast.LENGTH_LONG).show();
                    }
                });







            }
        });

        TextView tv1 = (TextView) findViewById(R.id.recent1);
        TextView tv2 = (TextView) findViewById(R.id.recent2);
        TextView tv3 = (TextView) findViewById(R.id.recent3);
        TextView tv4 = (TextView) findViewById(R.id.recent4);
        TextView tv5 = (TextView) findViewById(R.id.recent5);
        TextView tv6 = (TextView) findViewById(R.id.recent6);
        TextView tv7 = (TextView) findViewById(R.id.recent7);
        TextView tv8 = (TextView) findViewById(R.id.recent8);
        TextView sum = (TextView) findViewById(R.id.summary);
        TextView degree = (TextView) findViewById(R.id.degree);
        TextView minmax = (TextView) findViewById(R.id.min);
        TextView temp = (TextView) findViewById(R.id.temper);

        ImageView two  = (ImageView) findViewById(R.id.imageView3);
        //    TextView temp_degree  = (TextView)  findViewById(R.id.temp_degree);

        tv1.setText(pre);
        tv2.setText(ChanceRain);
        tv3.setText(wind);
        tv4.setText(dew);
        tv5.setText(humidity);
        tv6.setText(vis);
        tv7.setText(rise);
        tv8.setText(set);
        sum.setText(summary);
        minmax.setText(min_max);
        temp.setText(tempe);
        if(us){degree.setText("\u2109");}
        else{degree.setText("\u2103");}


        if(icon.equals("clear-day")  )
        {                 two.setImageResource(R.drawable.clear);}

        if(icon.equals("clear-night"))
        {    two.setImageResource(R.drawable.clear_night);}

        if(icon.equals("rain"))
        {  two.setImageResource(R.drawable.rain);}

        if(icon.equals("snow")  )
        { two.setImageResource(R.drawable.snow);}

        if(icon.equals( "sleet"))
        { two.setImageResource(R.drawable.sleet);}


        if(icon.equals("wind")  )
        { two.setImageResource(R.drawable.wind);}

        if(icon.equals( "fog"))
        { two.setImageResource(R.drawable.fog);}


        if(icon.equals( "cloudy"))
        { two.setImageResource(R.drawable.cloudy);}

        if(icon.equals( "partly-cloudy-day"))
        { two.setImageResource(R.drawable.cloud_day);}

        if(icon.equals( "partly-cloudy-night"))
        { two.setImageResource(R.drawable.cloud_night);}


        Button detail = (Button) findViewById(R.id.detail);
        Button map = (Button) findViewById(R.id.map);
       final Boolean finalus = us;
        detail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(recent.this, detail.class);
                i.putExtra("json", String.valueOf(json));
                i.putExtra("city", finalcity);
                i.putExtra("degree", finalus);
                i.putExtra("state", state);
                startActivity(i);

            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent i = new Intent(recent.this, MapView.class);
                i.putExtra("latitude", latitude);
                i.putExtra("longitude", longitude);


                startActivity(i);

            }
        });

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }



    public String pre_convert(Double p)
    {
        if(p>=0 && p < 0.002 )
        { return "None";}

        if(p < 0.017 && p >= 0.002 )
        { return "Very Light";}

        if(p < 0.1 && p >= 0.017 )
        { return "Light";}

        if(p < 0.4 && p >=0.1 )
        { return "Moderate";}

        if(p >=0.4)
        { return "Heavy";}

        return "N.A";

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        call.onActivityResult(requestCode, resultCode, data);
    }

    public String icon_select(String p)
    {
        if(p.equals("clear-day"))
        { return "http://cs-server.usc.edu:45678/hw/hw8/images/clear.png";}

        if(p.equals("clear-night" ))
        { return "http://cs-server.usc.edu:45678/hw/hw8/images/clear_night.png";}

        if(p.equals("rain")  )
        { return "http://cs-server.usc.edu:45678/hw/hw8/images/rain.png";}

        if(p.equals( "snow"))
        { return "http://cs-server.usc.edu:45678/hw/hw8/images/snow.png";}

        if(p.equals( "sleet"))
        { return "http://cs-server.usc.edu:45678/hw/hw8/images/sleet.png";}


        if(p.equals("wind"))
        { return "http://cs-server.usc.edu:45678/hw/hw8/images/wind.png";}



        if(p.equals("fog"))
        { return "http://cs-server.usc.edu:45678/hw/hw8/images/fog.png";}


        if(p.equals("cloudy") )
        { return "http://cs-server.usc.edu:45678/hw/hw8/images/cloudy.png";}

        if(p.equals("partly-cloudy-day"))
        { return "http://cs-server.usc.edu:45678/hw/hw8/images/cloud_day.png";}

        if(p.equals("partly-cloudy-night"))
        { return "http://cs-server.usc.edu:45678/hw/hw8/images/cloud_night.png";}

        return null;
    }




}
