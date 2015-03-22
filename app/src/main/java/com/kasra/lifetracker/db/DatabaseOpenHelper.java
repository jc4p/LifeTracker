package com.kasra.lifetracker.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * You shouldn't use this if you're not DatabaseManager.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {
    public static final String TABLE_TASK = "Task";
    public static final String TABLE_TASK_EVENT = "TaskEvent";

    // Task columns
    public static final String COLUMN_TASK_NAME = "Name";
    public static final String COLUMN_TASK_COLOR = "Color";
    public static final String COLUMN_TASK_GEOFENCE = "GeoFence";
    public static final String COLUMN_TASK_LAST_EVENT_TIMESTAMP = "LastEventTimestamp";

    // Event columns
    public static final String COLUMN_EVENT_TASK_ID = "TaskId";
    public static final String COLUMN_EVENT_TIMESTAMP = "Timestamp";
    public static final String COLUMN_EVENT_LOCATION = "Location";

    // The trigger for updating LastEventTimestamp
    public static final String TRIGGER_NEW_EVENT = "UpdateLastEventTimeStamp";

    private static int mDbVersion = 1;
    private static String mDbName = "life.db";

    public DatabaseOpenHelper(Context context) {
        super(context, mDbName, null, mDbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createInitialTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // By default, we just drop it and recreate everything.
        // Later on when we do a higher dbVersion int we can do the actual incremental upgrades.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK_EVENT);
        onCreate(db);
    }

    private void createInitialTables(SQLiteDatabase db ) {
        db.execSQL("CREATE TABLE " + TABLE_TASK + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TASK_NAME + " TEXT NOT NULL, " + COLUMN_TASK_COLOR + " INTEGER NOT NULL, " +
                COLUMN_TASK_GEOFENCE + " TEXT, " + COLUMN_TASK_LAST_EVENT_TIMESTAMP + " DATE DEFAULT NULL);");

        db.execSQL("CREATE TABLE " + TABLE_TASK_EVENT + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EVENT_TASK_ID + " INTEGER NOT NULL, " + COLUMN_EVENT_TIMESTAMP + " DATE DEFAULT CURRENT_DATE, " +
                COLUMN_EVENT_LOCATION + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_EVENT_TASK_ID + ") REFERENCES " + TABLE_TASK + "(_id));");

        db.execSQL("CREATE TRIGGER " + TRIGGER_NEW_EVENT + " AFTER INSERT ON " + TABLE_TASK_EVENT +
                " BEGIN " +
                    "UPDATE " + TABLE_TASK + " SET " + COLUMN_TASK_LAST_EVENT_TIMESTAMP + " = new." + COLUMN_EVENT_TIMESTAMP +
                    " WHERE _id = new." + COLUMN_EVENT_TASK_ID + ";" +
                " END;");
    }

}
