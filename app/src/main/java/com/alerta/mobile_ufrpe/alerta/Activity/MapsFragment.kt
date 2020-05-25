package com.alerta.mobile_ufrpe.alerta.Activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : SupportMapFragment(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback, com.google.android.gms.location.LocationListener, GoogleMap.OnMarkerClickListener {
    internal lateinit var mMap: GoogleMap
    private var mGoogleApiClient: GoogleApiClient? = null
    private val marker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //Botão de Zoom
        mMap.uiSettings.isZoomControlsEnabled = true

        //Permissão do usuário para localização atual
        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        // botão de posição atual
        mMap.isMyLocationEnabled = true

        //Markers de Recife
        customAddMarker(LatLng(-8.05428, -34.8813), "Recife", "O Marcador foi reposicionado")
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-8.05428, -34.8813), 12f))


        mMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoWindow(marker: Marker): View? {
                return null
            }

            override fun getInfoContents(marker: Marker): View? {
                return null
            }
        })


        //Markers adcionados pelo usuário
        mMap.setOnMapClickListener { latLng ->
            mMap.addMarker(MarkerOptions().position(latLng)
                    .title(latLng.latitude.toString()
                            + "," + latLng.longitude.toString()))
        }

        //Evento de click para exclusão do local de alagamento
        mMap.setOnMarkerClickListener { marker ->
            AlertDialog.Builder(context)
                    .setTitle("Deletando ponto de alagamento")
                    .setMessage("Tem certeza que deseja deletar esse ponto de alagamento?")
                    .setPositiveButton("Sim"
                    ) { dialogInterface, i ->
                        marker.remove()

                        Toast.makeText(context, "Ponto de Alagamento Removido " + marker.id.toString(), Toast.LENGTH_LONG).show()
                    }
                    .setNegativeButton("Não", null)
                    .show()

            true
        }

        callConection()
    }


    @Synchronized
    private fun callConection() {
        mGoogleApiClient = GoogleApiClient.Builder(context!!)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build()
        mGoogleApiClient!!.connect()
    }


    override fun onConnected(bundle: Bundle?) {
        Log.i("LOG", "onConnected($bundle)")
        @SuppressLint("MissingPermission") val l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)
        if (l != null) {
            Log.i("LOG", "latitude: " + l.latitude)
            Log.i("LOG", "longitude: " + l.longitude)
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.i("LOG", "onConnectionFailed($connectionResult)")
    }

    override fun onConnectionSuspended(i: Int) {
        Log.i("LOG", "onConnectionSuspended($i)")
    }

    override fun onLocationChanged(location: Location) {

    }

    //Metodo para adicionar pontos pre-estabelecidos no mapa
    fun customAddMarker(latLng: LatLng, title: String, snippet: String) {
        val options = MarkerOptions()
        options.position(latLng).title(title).snippet(snippet).draggable(true)
        val marker = mMap.addMarker(options)
    }

    override fun onMarkerClick(marker: Marker): Boolean {

        return false
    }
}
