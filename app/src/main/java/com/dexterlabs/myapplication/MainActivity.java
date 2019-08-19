package com.dexterlabs.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karan.churi.PermissionManager.PermissionManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;
    DatabaseReference databaseActivity;
    EditText ED_Name, ED_School;
    List<Address> SchoolLocation = null;
    String School_Location = null;
    String loc_Latitude = null, loc_Longitude = null;

    String name;
    String school;
    String timestamp;
    String day;

    PermissionManager permissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionManager = new PermissionManager(){};
        permissionManager.checkAndRequestPermissions(this);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        databaseActivity = FirebaseDatabase.getInstance().getReference("users");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

        onLocationChanged(location);

//        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        double longitude = location.getLongitude();
//        double latitude = location.getLatitude();
//
//        Log.e("Lat Long",latitude+"  "+longitude);


        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }

        getLocation();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        ArrayList<String> granted = permissionManager.getStatus().get(0).granted;
        ArrayList<String>denied = permissionManager.getStatus().get(0).denied;
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, this);
            Toast.makeText(this, "Location Gained",Toast.LENGTH_SHORT).show();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(Location location) {

//        String longitude ="";
//                longitude = String.valueOf(location.getLongitude());
//        String latitude ="";
//                latitude = String.valueOf(location.getLatitude());
//
//        Log.e("Lat Long",latitude+"  "+longitude);

        //locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);


            Log.e("School Location",addresses.toString());

            SchoolLocation = addresses;

            School_Location = SchoolLocation.get(0).getAddressLine(0);

            loc_Latitude = String.valueOf(location.getLatitude());
            loc_Longitude = String.valueOf(location.getLongitude());

            addresses = null;


            //    Toast.makeText(this,School_Location + loc_Longitude + loc_Latitude + day,Toast.LENGTH_LONG).show();
            // Toast.makeText(this,String.valueOf(currentTime) + String.valueOf(addresses),Toast.LENGTH_LONG).show();
            // Toast.makeText(this,String.valueOf(location.getLatitude()),Toast.LENGTH_SHORT).show();

            // Toast.makeText(this,String.valueOf(addresses),Toast.LENGTH_LONG).show();


            //   locationText.setText(locationText.getText() + "\n"+addresses.get(0).getAddressLine(0)+", "+

            //   addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2));
        } catch (Exception e) {

        }


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

        Toast.makeText(MainActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_LONG).show();
    }

    public void punchAttendance(View view) {


        ED_Name = (EditText) findViewById(R.id.CoachName);
        ED_School = (EditText) findViewById(R.id.CoachSchool);

        name = ED_Name.getText().toString();
        school = ED_School.getText().toString();

        if (name.equals("") || school.equals("")) {
            Toast.makeText(this, "Please enter valid details", Toast.LENGTH_SHORT).show();
        } else {

//            if (School_Location.equals("")||day.equals("")){
//                Toast.makeText(this,"Please Enable GPS and Internet or give permission of GPS to the app",Toast.LENGTH_LONG).show();
//
//            }
//
//            else {


           if (School_Location==null) {

                Toast.makeText(this, "Please Restart the Application with Active Internet and GPS with High Accuracy setting ", Toast.LENGTH_SHORT).show();

            } else {

                Date currentTime = Calendar.getInstance().getTime();

                timestamp = currentTime.toString();
                timestamp = timestamp.substring(0,20);
                day = timestamp.substring(0, 3);

               Log.e("Time Stamp",timestamp);




               String id = databaseActivity.push().getKey();
               Log.e("id",id);


               Model model = new Model();

               model.setAddress(School_Location);
               model.setDay(day);
               model.setLatitude(loc_Latitude);
               model.setLongitude(loc_Longitude);
               model.setName(name);
               model.setSchool(school);
               model.setTimestamp(timestamp);

               // Model model = new Model(name, school, timestamp, School_Location, loc_Latitude, loc_Longitude, day);

                databaseActivity.child(id).setValue(model);

                Toast.makeText(this, "Attendance Punched Successfully!", Toast.LENGTH_SHORT).show();

               new AlertDialog.Builder(this)
                       .setTitle("Attendence Punched Successfully")
                       .setMessage("Name "+name+"\nTime "+timestamp+"\nSchool "+school+"\nLocation "+School_Location)

                       // Specifying a listener allows you to take an action before dismissing the dialog.
                       // The dialog is automatically dismissed when a dialog button is clicked.
                       .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int which) {
                               // Continue with delete operation

                               finish();
                           }
                       })

                       // A null listener allows the button to dismiss the dialog and take no further action.
                       .setNegativeButton(android.R.string.no, null)
                       .setIcon(android.R.drawable.ic_dialog_alert)
                       .show();
            }


        }
    }   // end of Punch in

}
