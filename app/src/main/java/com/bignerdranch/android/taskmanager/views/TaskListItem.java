package com.bignerdranch.android.taskmanager.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.android.taskmanager.R;
import com.bignerdranch.android.taskmanager.tasks.Task;

/**
 * Created by Eduardo on 12/1/2016.
 */
public class TaskListItem extends LinearLayout {

    private Task task;
    private CheckedTextView checkbox;
    private TextView addressText;

    public TaskListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        checkbox = (CheckedTextView) findViewById(android.R.id.text1);
        addressText = (TextView) findViewById(R.id.address_text);
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
        checkbox.setText(task.getName());
        checkbox.setChecked(task.isComplete());
        if (task.hasAddress()) {
            addressText.setText(task.getAddress());
            addressText.setVisibility(View.VISIBLE);
        } else {
            addressText.setVisibility(View.GONE);
        }
    }
}
