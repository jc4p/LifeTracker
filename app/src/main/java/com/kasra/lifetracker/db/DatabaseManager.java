package com.kasra.lifetracker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kasra.lifetracker.models.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.kasra.lifetracker.db.DatabaseOpenHelper.*;

/**
 * Singleton to manage all SQLite operations.
 *
 * This only does the actual CRUD operations, the management of the database itself (creation,
 * upgrades, etc) happens in DatabaseOpenHelper.
 *
 * I wonder how many times in my life I've made this exact class
 */
public class DatabaseManager {
    private static DatabaseManager sInstance;

    private DatabaseOpenHelper mHelper;
    private SQLiteDatabase mDb;

    protected DatabaseManager(Context ctx) {
        this.mHelper = new DatabaseOpenHelper(ctx);
        this.mDb = mHelper.getWritableDatabase();
    }

    public static synchronized DatabaseManager getInstance(Context ctx) {
        if (sInstance == null) {
            sInstance = new DatabaseManager(ctx.getApplicationContext());
        }

        return sInstance;
    }

    public List<Task> getAllTasks() {
        Cursor c = mDb.query(TABLE_TASK, new String[] { "_id", COLUMN_TASK_NAME, COLUMN_TASK_COLOR, COLUMN_TASK_GEOFENCE },
                null, null, null, null, null);

        int count = c.getCount();
        ArrayList<Task> tasks = new ArrayList<>(count);

        while(c.moveToNext()) {
            long id = c.getLong(c.getColumnIndex("_id"));
            String name = c.getString(c.getColumnIndex(COLUMN_TASK_NAME));
            int color = c.getInt(c.getColumnIndex(COLUMN_TASK_COLOR));

            String geoFenceStr = c.isNull(c.getColumnIndex(COLUMN_TASK_GEOFENCE)) ? null : c.getString(c.getColumnIndex(COLUMN_TASK_GEOFENCE));
            double[] geoFence = new double[2];
            if (geoFenceStr != null) {
                // hey should this be a indexOf() and substrings instead?
                String[] split = geoFenceStr.split(",");
                geoFence[0] = Double.valueOf(split[0]);
                geoFence[1] = Double.valueOf(split[1]);
            }

            Date lastEventAt = null;
            if (!c.isNull(c.getColumnIndex(COLUMN_TASK_LAST_EVENT_TIMESTAMP))) {
                try {
                    lastEventAt = SimpleDateFormat.getInstance().parse(c.getString(c.getColumnIndex(COLUMN_TASK_LAST_EVENT_TIMESTAMP)));
                } catch (ParseException e) { /* who cares */ }
            }

            Task t = new Task(id, name, color, geoFence, lastEventAt);
            tasks.add(t);
        }

        c.close();

        return tasks;
    }

    public long createTask(String name, int color, double[] geoFence) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME, name);
        values.put(COLUMN_TASK_COLOR, color);

        String geoFenceStr = geoFence == null ? null : geoFence[0] + "," + geoFence[1];
        values.put(COLUMN_TASK_GEOFENCE, geoFenceStr);

        return mDb.insert(TABLE_TASK, null, values);
    }

    public long createTaskEvent(long taskId, double[] location) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_TASK_ID, taskId);

        String locationStr = location == null ? null : location[0] + "," + location[1];
        values.put(COLUMN_EVENT_LOCATION, locationStr);

        return mDb.insert(TABLE_TASK_EVENT, null, values);
    }
}
