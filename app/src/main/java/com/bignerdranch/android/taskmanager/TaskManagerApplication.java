package com.bignerdranch.android.taskmanager;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.taskmanager.tasks.Task;
import com.bignerdranch.android.taskmanager.tasks.TasksSQLiteOpenHelper;

import java.util.ArrayList;

import static com.bignerdranch.android.taskmanager.tasks.TasksSQLiteOpenHelper.TASKS_TABLE;
import static com.bignerdranch.android.taskmanager.tasks.TasksSQLiteOpenHelper.TASK_COMPLETE;
import static com.bignerdranch.android.taskmanager.tasks.TasksSQLiteOpenHelper.TASK_ID;
import static com.bignerdranch.android.taskmanager.tasks.TasksSQLiteOpenHelper.TASK_NAME;

/**
 * Created by Eduardo on 12/1/2016.
 */
public class TaskManagerApplication extends Application {

    private ArrayList<Task> currentTasks;
    private SQLiteDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        TasksSQLiteOpenHelper helper = new TasksSQLiteOpenHelper(this);
        database = helper.getWritableDatabase();
        if (null == currentTasks) {
            loadTasks();
        }
    }

    private void loadTasks() {
        currentTasks = new ArrayList<Task>();
        Cursor tasksCursor = database.query(
                TASKS_TABLE, new String[]{TASK_ID, TASK_NAME, TASK_COMPLETE},
                null, null, null, null, String.format("%s,%s", TASK_COMPLETE, TASK_NAME));
        tasksCursor.moveToFirst();
        Task t;
        if (!tasksCursor.isAfterLast()) {
            do {
                long id = tasksCursor.getLong(0);
                String name = tasksCursor.getString(1);
                String boolValue = tasksCursor.getString(2);
                boolean complete = Boolean.parseBoolean(boolValue);
                t = new Task(name);
                t.setId(id);
                t.setComplete(complete);
                currentTasks.add(t);
            } while (tasksCursor.moveToNext());
        }

        tasksCursor.close();
    }

    public void addTask(Task t) {
        assert(t != null);

        ContentValues values = new ContentValues();
        values.put(TASK_NAME, t.getName());
        values.put(TASK_COMPLETE, Boolean.toString(t.isComplete()));
        long id = database.insert(TASKS_TABLE, null, values);
        t.setId(id);
        currentTasks.add(t);
    }

    public void saveTask(Task t) {
        assert(t != null);

        ContentValues values = new ContentValues();
        values.put(TASK_NAME, t.getName());
        values.put(TASK_COMPLETE, Boolean.toString(t.isComplete()));

        long id = t.getId();
        String where = String.format("%s = ?", TASK_ID, id);

        database.update(TASKS_TABLE, values, where, new String[]{id+""});
    }

    public void deleteTasks(Long[] ids) {
        StringBuffer idList = new StringBuffer();
        for (int i = 0; i < ids.length; i++) {
            idList.append(ids[i]);
            if (i < ids.length - 1) {
                idList.append(",");
            }
        }
        String where = String.format("%s in (%s)", TASK_ID, idList);
        database.delete(TASKS_TABLE, where, null);
    }

    public void setCurrentTasks(ArrayList<Task> currentTasks) {
        this.currentTasks = currentTasks;
    }

    public ArrayList<Task> getCurrentTasks() {
        return currentTasks;
    }
}
