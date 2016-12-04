package com.bignerdranch.android.taskmanager;

import android.Manifest;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bignerdranch.android.taskmanager.adapter.TaskListAdapter;
import com.bignerdranch.android.taskmanager.tasks.Task;

public class ViewTasksActivity extends ListActivity implements LocationListener {

    private static final long LOCATION_FILTER_DISTANCE = 200;

    private TextView locationText;
    private Button addButton;
    private Button removeButton;
    private TaskListAdapter adapter;
    private TaskManagerApplication app;
    private ToggleButton localTasksToggle;
    private LocationManager locationManager;
    private Location latestLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks);

        setUpViews();
        app = (TaskManagerApplication) getApplication();
        adapter = new TaskListAdapter(this, app.getCurrentTasks());
        setListAdapter(adapter);
        setUpLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.forceReload();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        adapter.toggleTaskCompleteAtPosition(position);
        Task t = adapter.getItem(position);
        app.saveTask(t);
    }

    @Override
    public void onLocationChanged(Location location) {
        latestLocation = location;
        String locationString = String.format(
                "@ %f, %f +/- %fm",
                location.getLatitude(),
                location.getLongitude(),
                location.getAccuracy());
        locationText.setText(locationString);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    protected void removeCompletedTasks() {
        Long[] ids = adapter.removeCompletedTasks();
        app.deleteTasks(ids);
    }

    protected void showLocalTasks(boolean checked) {
        if (checked) {
            adapter.filterTasksByLocation(latestLocation, LOCATION_FILTER_DISTANCE);
        } else {
            adapter.removeLocationFilter();
        }
    }

    private void setUpViews() {
        addButton = (Button) findViewById(R.id.add_button);
        removeButton = (Button) findViewById(R.id.remove_button);
        localTasksToggle = (ToggleButton) findViewById(R.id.show_local_tasks_toggle);
        locationText = (TextView) findViewById(R.id.location_text);

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ViewTasksActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });
        removeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeCompletedTasks();
            }
        });
        localTasksToggle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showLocalTasks(localTasksToggle.isChecked());
            }
        });

    }

    private void setUpLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                60,
                5,
                this);
    }
}
