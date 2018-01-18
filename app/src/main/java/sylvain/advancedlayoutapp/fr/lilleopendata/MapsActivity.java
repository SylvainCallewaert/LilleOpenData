package sylvain.advancedlayoutapp.fr.lilleopendata;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String libelle;
    private Double latitude;
    private Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Récupération des données passées à l'activité
        Bundle params = getIntent().getExtras();
        libelle = params.getString("libelle");
        latitude = params.getDouble("latitude");
        longitude = params.getDouble("longitude");
    }


    /**
     * Affichage de googlemap avec les coordonées de la bibliotheque selectionnée
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in libelle and move the camera
        LatLng coordonées = new LatLng(longitude, latitude);
        mMap.addMarker(new MarkerOptions().position(coordonées).title(libelle));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordonées));
    }
}
