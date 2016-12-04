package com.bignerdranch.android.taskmanager.adapter;

import android.content.Context;
import android.location.Location;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bignerdranch.android.taskmanager.R;
import com.bignerdranch.android.taskmanager.tasks.Task;
import com.bignerdranch.android.taskmanager.views.TaskListItem;

import java.util.ArrayList;

/**
 * Created by Eduardo on 12/1/2016.
 */
public class TaskListAdapter extends BaseAdapter {

    private ArrayList<Task> filteredTasks;
    private ArrayList<Task> unfilteredTasks;
    private ArrayList<Task> tasks;
    private Context context;

    public TaskListAdapter(Context context, ArrayList<Task> tasks) {
        this.tasks = tasks;
        this.unfilteredTasks = tasks;
        this.context = context;
    }

    public int getCount() {
        return tasks.size();
    }

    public Task getItem(int position) {
        return (tasks == null) ? null : tasks.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TaskListItem tli;
        if (convertView == null) {
            tli = (TaskListItem)View.inflate(context, R.layout.task_list_item, null);
        } else {
            tli = (TaskListItem)convertView;
        }
        tli.setTask(tasks.get(position));

        return tli;
    }

    public void forceReload() {
        notifyDataSetChanged();
    }

    public void toggleTaskCompleteAtPosition(int position) {
        Task task = getItem(position);
        task.toggleComplete();

        notifyDataSetChanged();
    }

    public Long[] removeCompletedTasks() {
        ArrayList<Task> completedTasks = new ArrayList<Task>();
        ArrayList<Long> completedIds = new ArrayList<Long>();
        for (Task task : tasks) {
            if (task.isComplete()) {
                completedIds.add(task.getId());
                completedTasks.add(task);
            }
        }
        tasks.removeAll(completedTasks);
        notifyDataSetChanged();

        return completedIds.toArray(new Long[]{});
    }

    public void filterTasksByLocation(Location location, long distance) {
        filteredTasks = new ArrayList<Task>();
        for (Task task : tasks) {
            if (task.hasLocation() && taskIsWithinGeofence(task, location, distance)) {
                filteredTasks.add(task);
            }
        }
        tasks = filteredTasks;

        notifyDataSetChanged();
    }

    private boolean taskIsWithinGeofence(Task task, Location location, long distance) {
        float[] distanceArray = new float[1];
        Location.distanceBetween(
                task.getLatitude(),
                task.getLongitude(),
                location.getLatitude(),
                location.getLongitude(),
                distanceArray
        );

        return (distanceArray[0] < distance);
    }

    public void removeLocationFilter() {
        tasks = unfilteredTasks;

        notifyDataSetChanged();
    }
}
