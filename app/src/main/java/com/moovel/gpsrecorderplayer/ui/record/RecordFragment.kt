package com.moovel.gpsrecorderplayer.ui.record

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.location.Location
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.moovel.gpsrecorderplayer.R
import kotlinx.android.synthetic.main.record_fragment.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter.ISO_DATE

class RecordFragment : Fragment(), OnMapReadyCallback {
    private lateinit var viewModel: RecordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.record_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        edit_record_name.setText(getString(R.string.record_new_record, LocalDate.now().format(ISO_DATE), 1))
        edit_record_name.requestFocus()
        edit_record_name.setSelection(edit_record_name.text.length)
    }

    override fun onMapReady(p0: GoogleMap?) {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RecordViewModel::class.java)
        viewModel.locationLiveData.observe(this, Observer<Location> {
            text_latitude.text = String.format("%.5f", it?.latitude)
            text_longitude.text = String.format("%.5f", it?.longitude)
            text_altitude.text = String.format("%.1f", it?.altitude)
            text_accuracy.text = String.format("%.1f", it?.accuracy)
            text_bearing.text = String.format("%.1f", it?.bearing)
            text_bearing_accuracy.text = String.format("%.1f", it?.bearingAccuracyDegrees)
            text_vertical_accuracy.text = String.format("%.1f", it?.verticalAccuracyMeters)
            text_speed.text = String.format("%.1f", it?.speed)
            text_speed_accuracy.text = String.format("%.1f", it?.speedAccuracyMetersPerSecond)
        })
    }
}
