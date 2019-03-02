package com.halfbyte.greedywallet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.support.v7.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * @author dwivedi ji     *
 *        */
public class CheckInActivity extends AppCompatActivity implements OnMapReadyCallback{

    private String[] placeName;
    private String[] imageUrl;

    List<Places> findPlaces;
    private GoogleMap map;
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        gps = new GPSTracker(this);
        new GetPlaces(this).execute();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
    }

    class GetPlaces extends AsyncTask<Void, Void, Void>{
        Context context;
        private ProgressDialog bar;
        public GetPlaces(Context context) {
            // TODO Auto-generated constructor stub
            this.context = context;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            bar.dismiss();
            for (Places p:findPlaces){
                LatLng latLng = new LatLng(p.getLatitude(), p.getLongitude());
                map.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(p.getName()));
            }
            LatLng latLng = new LatLng(gps.getLatitude(), gps.getLongitude());
            map.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Current Position"));

        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            bar =  new ProgressDialog(context);
            bar.setIndeterminate(true);
            bar.setTitle("Loading");
            bar.show();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            findNearLocation();
            return null;
        }

    }

    public void findNearLocation()   {

        PlacesService service = new PlacesService("AIzaSyAkoyfEzMuLxEEEkle830xD-zr9_BlPeQ0");

       /*
        Hear you should call the method find nearst place near to central park new delhi then we pass the lat and lang of central park. hear you can be pass you current location lat and lang.The third argument is used to set the specific place if you pass the atm the it will return the list of nearest atm list. if you want to get the every thing then you should be pass "" only
       */


        findPlaces = service.findPlaces(gps.getLatitude(),gps.getLongitude(),"supermarket");
        // Hear third argument, we pass the atm for getting atm , if you pass the hospital then this method return list of hospital , If you pass nothing then it will return all landmark

        placeName = new String[findPlaces.size()];
        imageUrl = new String[findPlaces.size()];

        for (int i = 0; i < findPlaces.size(); i++) {

            Places placeDetail = findPlaces.get(i);
            placeDetail.getIcon();

            System.out.println(  placeDetail.getName());
            placeName[i] =placeDetail.getName();

            imageUrl[i] =placeDetail.getIcon();

        }





    }



}