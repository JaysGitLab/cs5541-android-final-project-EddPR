package com.bignerdranch.android.taskmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bignerdranch.android.taskmanager.tasks.Task;

/**
 * Created by Eduardo on 12/1/2016.
 */
public class AddTaskActivity extends TaskManagerActivity {
    private static final int REQUEST_CHOOSE_ADDRESS = 0;

    private EditText taskNameEditText;
    private Button addButton;
    private Button cancelButton;
    private boolean changesPending;
    private AlertDialog unsavedChangesDialog;
    private Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tasks);
        setUpViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CHOOSE_ADDRESS && requestCode == RESULT_OK) {
            address = data.getParcelableExtra(AddLocationMapActivity.ADDRESS_RESULT);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setUpViews() {
        taskNameEditText = (EditText) findViewById(R.id.task_name);
        addButton = (Button) findViewById(R.id.add_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        taskNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changesPending = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    protected void addTask() {
        String taskName = taskNameEditText.getText().toString();
        if (!taskName.equals("")) {
            Task t = new Task(taskName);
            getTaskManagerApplication().addTask(t);
        }
        finish();
    }

    public void addLocationButtonClicked(View view) {
        Intent intent = new Intent(this, AddLocationMapActivity.class);
        startActivityForResult(intent, REQUEST_CHOOSE_ADDRESS);
    }

    protected void cancel() {
        String taskName = taskNameEditText.getText().toString();
        if (changesPending && !taskName.equals("")) {
            unsavedChangesDialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.unsaved_changes_title)
                    .setMessage(R.string.unsaved_changes_message)
                    .setPositiveButton(R.string.add_task, new AlertDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            addTask();
                        }
                    })
                    .setNeutralButton(R.string.discard, new AlertDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new AlertDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            unsavedChangesDialog.cancel();
                        }
                    })
                    .create();
            unsavedChangesDialog.show();
        } else {
            finish();
        }
    }
}
