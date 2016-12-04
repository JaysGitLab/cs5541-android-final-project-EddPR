package com.bignerdranch.android.taskmanager;

import android.app.Activity;

/**
 * Created by Eduardo on 12/1/2016.
 */
public class TaskManagerActivity extends Activity {

    /*public TaskManagerActivity() {
        super();
    }

    protected TaskManagerApplication getStuffApplication() {
        return (TaskManagerApplication)getApplication();
    }*/

    protected TaskManagerApplication getTaskManagerApplication() {
        TaskManagerApplication tma = (TaskManagerApplication) getApplication();
        return tma;
    }
}
