package com.bignerdranch.android.taskmanager.adapter;

import android.content.Context;
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

    private ArrayList<Task> tasks;
    private Context context;

    public TaskListAdapter(ArrayList<Task> tasks, Context context) {
        super();
        this.tasks = tasks;
        this.context = context;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Task getItem(int position) {
        return (tasks == null) ? null : tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TaskListItem tli;
        if (convertView == null) {
            tli = (TaskListItem) View.inflate(context, R.layout.task_list_item, null);
        } else {
            tli = (TaskListItem) convertView;
        }
        tli.setTask(tasks.get(position));
        return tli;
    }

    public void forceReload() {
        notifyDataSetChanged();
    }

    public void toggleTaskCompleteAtPosition(int position) {
        Task t = tasks.get(position);
        t.toggleComplete();
        notifyDataSetChanged();
    }

    public Long[] removeCompletedTasks() {
        ArrayList<Long> completedIds = new ArrayList<Long>();
        ArrayList<Task> completedTasks = new ArrayList<Task>();
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
}
