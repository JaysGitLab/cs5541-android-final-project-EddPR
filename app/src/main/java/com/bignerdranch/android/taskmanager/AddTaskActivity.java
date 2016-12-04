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
import android.widget.TextView;

import com.bignerdranch.android.taskmanager.tasks.Task;

/**
 * Created by Eduardo on 12/1/2016.
 */
public class AddTaskActivity extends TaskManagerActivity {

    protected static final int REQUEST_CHOOSE_ADDRESS = 1;

    private AlertDialog unsavedChangesDialog;
    private TextView addressText;
    private EditText taskNameEditText;
    private Button addButton;
    private Button cancelButton;
    private Button addLocationButton;
    private boolean changesPending;
    private Address address;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tasks);

        setUpViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null == address) {
            addLocationButton.setVisibility(View.VISIBLE);
            addressText.setVisibility(View.GONE);
        } else {
            addLocationButton.setVisibility(View.GONE);
            addressText.setVisibility(View.VISIBLE);
            addressText.setText(address.getAddressLine(0));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUEST_CHOOSE_ADDRESS == requestCode && RESULT_OK == resultCode) {
            address = data.getParcelableExtra(AddLocationMapActivity.ADDRESS_RESULT);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected void addTask() {
        String taskName = taskNameEditText.getText().toString();
        Task t = new Task(taskName);
        t.setAddress(address);
        getTaskManagerApplication().addTask(t);
        //getStuffApplication().addTask(t);

        finish();
    }

    protected void cancel() {
        if (changesPending) {
            unsavedChangesDialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.unsaved_changes_title)
                    .setMessage(R.string.unsaved_changes_message)
                    .setPositiveButton(R.string.add_task, new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            addTask();
                        }
                    })
                    .setNeutralButton(R.string.discard, new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new AlertDialog.OnClickListener() {
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

    private void setUpViews() {
        addressText = (TextView)findViewById(R.id.address_text);
        taskNameEditText = (EditText)findViewById(R.id.task_name);
        addButton = (Button)findViewById(R.id.add_button);
        addLocationButton = (Button)findViewById(R.id.add_location_button);
        cancelButton = (Button)findViewById(R.id.cancel_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addTask();
            }
        });

        addLocationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AddTaskActivity.this, AddLocationMapActivity.class);
                startActivityForResult(intent, REQUEST_CHOOSE_ADDRESS);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cancel();
            }
        });
        taskNameEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changesPending = true;
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void afterTextChanged(Editable s) { }
        });
    }
}
