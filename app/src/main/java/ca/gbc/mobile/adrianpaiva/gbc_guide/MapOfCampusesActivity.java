package ca.gbc.mobile.adrianpaiva.gbc_guide;

import android.app.Activity;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsRoute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MapOfCampusesActivity extends Activity implements LocationListener,GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    private final LatLng casaLoma = new LatLng(43.676655,-79.410123);

    private final LatLng stJamesA = new LatLng(43.651190, -79.370203);
    private final LatLng stJamesB = new LatLng(43.652423, -79.370020);
    private final LatLng stJamesC = new LatLng(43.652002, -79.370052);
    private final LatLng stJamesD = new LatLng(43.652423, -79.370020);
    private final LatLng stJamesE = new LatLng(43.653418, -79.371112);
    private final LatLng stJamesF = new LatLng(43.651500, -79.369002);
    private final LatLng stJamesG = new LatLng(43.650894, -79.370270);
    private final LatLng stJamesH = new LatLng(43.652144, -79.365041);

    private final LatLng ryerson = new LatLng(43.660164, -79.377072);

    private final LatLng waterfront = new LatLng(43.644083, -79.365312);

    private final LatLng youngCentre = new LatLng(43.650572, -79.358554);

    private ArrayList<LatLng> campuses;
    LatLng myLocation;
    List<Polyline> polylines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_of_campuses);


        polylines = new ArrayList<Polyline>();

        campuses = new ArrayList<LatLng>();
        campuses.add(casaLoma);
        campuses.add(stJamesA);
        campuses.add(stJamesB);
        campuses.add(stJamesC);
        campuses.add(stJamesD);
        campuses.add(stJamesE);
        campuses.add(stJamesF);
        campuses.add(stJamesG);
        campuses.add(stJamesH);
        campuses.add(ryerson);
        campuses.add(waterfront);
        campuses.add(youngCentre);



        setUpMapIfNeeded(); // add map markers

        // enable location
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);

        if(location!=null){
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(provider, 20000, 0, this);

        myLocation = new LatLng(location.getLatitude(),location.getLongitude());

        mMap.setOnMarkerClickListener(this);

        Toast.makeText(getApplicationContext(), "Select a Campus to View Directions", Toast.LENGTH_SHORT).show();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map_of_campuses, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {

                mMap.setMyLocationEnabled(true);

                mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(casaLoma, 11.0f) ); // default camera location

                addMarkers();


            }
        }
    }

    public String getAddressFromCoordinates(LatLng position) {

        Geocoder geocoder;
        List<Address> addresses = null;

        String address= null;
        String city = null;
        String country = null;
        String completeAddress = null;

        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(position.latitude, position.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses != null)
        {
            address = addresses.get(0).getAddressLine(0);
            city = addresses.get(0).getAddressLine(1);
            country = addresses.get(0).getAddressLine(2);
            completeAddress = address + "," + city + "," + country;

        }

        return completeAddress;

    }
    @Override
    public void onLocationChanged(Location location) {
        /*
        double latitude = location.getLatitude();

        double longitude = location.getLongitude();

        LatLng latLng = new LatLng(latitude, longitude);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        mMap.animateCamera(CameraUpdateFactory.zoomTo(11), 2000, null);
        */

    }

    public void getDirections(String currentAddress, String destAddress)
    {
        GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyBTapNnH4omO-MNgp7JVegJg89ofWy41iU");
        DirectionsApiRequest req =  DirectionsApi.getDirections(context, currentAddress, destAddress);


        try {
            DirectionsRoute routs[] = req.await();

            if (routs!=null)
            {
                if (routs.length>0)
                {
                    for(Polyline line : polylines)
                    {
                        line.remove();
                    }

                    polylines.clear();

                    DirectionsRoute r = routs[0];
                    List<com.google.maps.model.LatLng> points =
                            r.overviewPolyline.decodePath();
                    PolylineOptions pline = new PolylineOptions();
                    Log.i("Location", "size:" + points.size());
                    for (int i=0; i<points.size();i++) {
                        Log.i("Location",points.get(i).toString());
                        LatLng p = new LatLng(points.get(i).lat,points.get(i).lng);
                        pline.add(p);
                    }

                    polylines.add(mMap.addPolyline(pline));

                }
            }
            // Handle successful request.
        } catch (Exception e) {
            // Handle error
            e.printStackTrace();
        }
    }
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
    public void addMarkers()
    {
        mMap.addMarker(new MarkerOptions()
                .position(casaLoma)
                .title("Casa Loma Campus")
                .snippet("160 Kendal ave")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        mMap.addMarker(new MarkerOptions()
            .position(stJamesA)
            .title("St. James Campus")
            .snippet("200 King Street East")
            .icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        mMap.addMarker(new MarkerOptions()
                .position(stJamesB)
                .title("St. James Campus - Centre for Hospitality and Culinary Arts")
                .snippet("300 Adelaide Street East")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        mMap.addMarker(new MarkerOptions()
                .position(stJamesC)
                .title("St. James Campus - Financial Services")
                .snippet("290 Adelaide Street East")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        mMap.addMarker(new MarkerOptions()
                .position(stJamesD)
                .title("St. James Campus - The Chefs' House, Centre for Hospitality & Culinary Arts")
                .snippet("215 King Street East")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        mMap.addMarker(new MarkerOptions()
                .position(stJamesE)
                .title("St. James Campus - School of Design")
                .snippet("230 Richmond Street East")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.addMarker(new MarkerOptions()
                .position(stJamesF)
                .title("St. James Campus - Alumni Office")
                .snippet("210 King Street East")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        mMap.addMarker(new MarkerOptions()
                .position(stJamesG)
                .title("St. James Campus - School of Makeup and Esthetics")
                .snippet("193 King Street East")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.addMarker(new MarkerOptions()
                .position(stJamesG)
                .title("St. James Campus - School of Design")
                .snippet("341 King Street East")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        mMap.addMarker(new MarkerOptions()
                .position(ryerson)
                .title("Ryerson Campus - Sally Horsfall Eaton Centre for Studies in Community Health")
                .snippet("99 Gerrard St. E.")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

        mMap.addMarker(new MarkerOptions()
                .position(waterfront)
                .title("Waterfront Campus")
                .snippet("51 Dockside Drive")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

        mMap.addMarker(new MarkerOptions()
                .position(youngCentre)
                .title("Young Centre for the Performing Arts")
                .snippet("50 Tankhouse Lane")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));

    }
    @Override
    public boolean onMarkerClick(Marker marker) {

        for(LatLng location: campuses)
        {
            if (location.latitude == marker.getPosition().latitude && location.longitude == marker.getPosition().longitude)
            {
                String currentAddress = getAddressFromCoordinates(myLocation);
                String destAddress = getAddressFromCoordinates(location);

                getDirections(currentAddress,destAddress);

            }
        }
        return false;
    }
}
