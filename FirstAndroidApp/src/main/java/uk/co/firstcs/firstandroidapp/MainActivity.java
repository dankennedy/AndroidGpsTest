package uk.co.firstcs.firstandroidapp;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends Activity implements InterestedInLocationUpdates {

    private boolean isRunning = false;
    private LocationManager locationManager = null;
    private CmacLocationListener locationListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void toggleTracking(View view) {
        Button startStopButton = (Button)findViewById(R.id.startstop_button);
        if (isRunning) {
            startStopButton.setText(R.string.button_start);
            StopTracking();
        } else {
            startStopButton.setText(R.string.button_stop);
            StartTracking();
        }
    }

    void StartTracking() {
        try{
            this.locationListener = new CmacLocationListener();
            this.locationListener.addInterestedParty(this);
            this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 35000, 10, this.locationListener);
            this.isRunning = true;
            locationUpdate(this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
        }catch (Exception e) {
            Exception ex = e;
        }
    }

    void StopTracking() {
        this.locationManager.removeUpdates(this.locationListener);
        this.locationListener.removeInterestedParty(this);
        this.locationManager = null;
        this.locationListener = null;
        this.isRunning = false;
    }

    @Override
    public void locationUpdate(Location l) {
        TextView coordView = (TextView)findViewById(R.id.coord_view);
        coordView.setText(createLocationMessage(l));
    }

    public String createLocationMessage(Location l) {

        final String SEPARATOR = "|";

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssZ");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        StringBuilder s = new StringBuilder("");
        s.append(sdf.format(new Date()));
        s.append(SEPARATOR);
        s.append(l.getProvider());
        s.append(SEPARATOR);
        s.append(l.getLatitude());
        s.append(SEPARATOR);
        s.append(l.getLongitude());
        s.append(SEPARATOR);
        s.append(l.getSpeed());
        s.append(SEPARATOR);
        s.append(l.getBearing());

        return s.toString();
    }
}
