package com.bignerdranch.android.taskmanager.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

import com.bignerdranch.android.taskmanager.tasks.Task;

/**
 * Created by Eduardo on 12/1/2016.
 */
public class TaskListItem extends LinearLayout {

    private Task task;
    private CheckedTextView checkbox;

    public TaskListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        checkbox = (CheckedTextView) findViewById(android.R.id.text1);
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
        checkbox.setText(task.getName());
        checkbox.setChecked(task.isComplete());
    }
}
