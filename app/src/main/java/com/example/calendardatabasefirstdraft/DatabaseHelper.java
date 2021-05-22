package com.example.calendardatabasefirstdraft;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    //Initialize Working Session Table
    public static final String WORKING_SESSION_TABLE = "WORKING_SESSION_TABLE";
    public static final String COLUMN_WORK_ID = "ID";
    public static final String COLUMN_DATE_ID = "DATE_ID";


    public static final String COLUMN_START_TIME = "START_TIME";
    public static final String COLUMN_END_TIME = "END_TIME";
    public static final String COLUMN_STATUS = "STATUS";
    public static final String COLUMN_IS_ON_TIME = "IS_ON_TIME";
    public static final String COLUMN_WORK_SESSION_NAME = "WORK_SESSION_NAME";


    //Initialize Tag table
    public static final String TAG_TABLE = "TAG_TABLE";
    public static final String COLUMN_TAG_NAME = "TAG_NAME";
    public static final String COLUMN_TAG_ID = "TAG_ID";


    //Initialize Day_Work_Tag Table
    public static final String DAY_WORK_TAG_TABLE = "DAY_WORK_TAG_TABLE";
    public static final String COLUMN_SUM_ID = "SUM_ID";
    public static final String COLUMN_KEY_DATE_ID = "KEY_DATE_ID";
    public static final String COLUMN_KEY_WORK_ID = "KEY_WORK_ID";
    public static final String COLUMN_KEY_TAG_ID = "KEY_TAG_ID";

    //Initialize Date Table
    public static final String DATE_TABLE = "DATE_TABLE";
    public static final String COLUMN_DAY_OF_WEEK = "DAY_OF_WEEK";
    public static final String COLUMN_CURRENTDATE = "CURRENTDATE";

    //Initialize Week Table
    public static final String WEEK_TABLE = "WEEK_TABLE";
    public static final String COLUMN_ORDER_WEEK = "ORDER_WEEK";
    public static final String COLUMN_WEEK_ID = "WEEK_ID";

    //Initialize Note Table
    public static final String NOTE_TABLE = "NOTE_TABLE";
    public static final String COLUMN_NOTE_ID = "NOTE_ID";
    public static final String COLUMN_NOTE = "NOTE";


    //Initialize Day Work IsOnTime Table
    public static final String DAY_WORK_IS_ON_TIME_TABLE = "DAY_WORK_IS_ON_TIME_TABLE";
    public static final String COLUMN_KEY_ID = "ID";
    public static final String COLUMN_KEY_IS_ON_TIME = "KEY_IS_ON_TIME";


    //Initialize Day Work Status Table
    public static final String DAY_WORK_STATUS_TABLE = "DAY_WORK_STATUS_TABLE";
    public static final String COLUMN_KEY_STATUS = "KEY_STATUS";

    //Initialize Day Note Table
    public static final String DATE_NOTE_TABLE = "DATE_NOTE_TABLE";
    public static final String COLUMN_DAY_NOTE_ID = "DAY_NOTE_ID";

    String createWorkingSessionStatement = "CREATE TABLE " + WORKING_SESSION_TABLE + " (" + COLUMN_WORK_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_WORK_SESSION_NAME + " TEXT ," + COLUMN_WEEK_ID + " INTEGER, " + COLUMN_DATE_ID + " INTEGER, " + COLUMN_TAG_ID + " INTEGER, " + COLUMN_START_TIME + " TEXT, " + COLUMN_END_TIME + " TEXT, " + COLUMN_STATUS + " BOOL, " + COLUMN_IS_ON_TIME + " BOOL)";
    String createDateStatement = "CREATE TABLE " + DATE_TABLE + " (" + COLUMN_DATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_WEEK_ID + " INTEGER, " + COLUMN_ORDER_WEEK + " INTEGER, " + COLUMN_DAY_OF_WEEK + " TEXT, " + COLUMN_CURRENTDATE + " TEXT  )";
    String createTagStatement = "CREATE TABLE " + TAG_TABLE + " (" + COLUMN_TAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TAG_NAME + " TEXT)";
    String createDay_Work_TagStatement = "CREATE TABLE " + DAY_WORK_TAG_TABLE + " (" + COLUMN_SUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_KEY_DATE_ID + " INTEGER, " + COLUMN_KEY_WORK_ID + " INTEGER, " + COLUMN_KEY_TAG_ID + " INTEGER)";
    String createWeekStatement = "CREATE TABLE " + WEEK_TABLE + " (" + COLUMN_WEEK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ORDER_WEEK + " INTEGER )";
    String createNoteStatement = "CREATE TABLE " + NOTE_TABLE + " ( " + COLUMN_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CURRENTDATE + " TEXT, " + COLUMN_NOTE + " TEXT)";
    String createDay_Work_Is_On_TimeStatement = "CREATE TABLE " + DAY_WORK_IS_ON_TIME_TABLE + " (" + COLUMN_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_KEY_DATE_ID + " INTEGER, " + COLUMN_KEY_WORK_ID + " INTEGER, " + COLUMN_KEY_IS_ON_TIME + " BOOL)";
    String createDay_Work_StatusStatement = "CREATE TABLE " + DAY_WORK_STATUS_TABLE + " (" + COLUMN_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_KEY_DATE_ID + " INTEGER, " + COLUMN_KEY_WORK_ID + " INTEGER, " + COLUMN_KEY_STATUS + " BOOL)";
    String createDay_NoteStatement = "CREATE TABLE " + DATE_NOTE_TABLE + " (" + COLUMN_DAY_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CURRENTDATE + " TEXT, " + COLUMN_NOTE_ID + " INTEGER)";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "dailyWorkManager.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createWorkingSessionStatement);
        db.execSQL(createTagStatement);
        db.execSQL(createDay_Work_TagStatement);
        db.execSQL(createDateStatement);
        db.execSQL(createWeekStatement);
        db.execSQL(createNoteStatement);
        db.execSQL(createDay_Work_Is_On_TimeStatement);
        db.execSQL(createDay_Work_StatusStatement);
        db.execSQL(createDay_NoteStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + WORKING_SESSION_TABLE);
        db.execSQL(" DROP TABLE IF EXISTS " + TAG_TABLE);
        db.execSQL(" DROP TABLE IF EXISTS " + DAY_WORK_TAG_TABLE);
        db.execSQL(" DROP TABLE IF EXISTS " + DATE_TABLE);
        db.execSQL(" DROP TABLE IF EXISTS " + WEEK_TABLE);
        db.execSQL(" DROP TABLE IF EXISTS " + NOTE_TABLE);
        db.execSQL(" DROP TABLE IF EXISTS " + DAY_WORK_IS_ON_TIME_TABLE);
        db.execSQL(" DROP TABLE IF EXISTS " + DAY_WORK_STATUS_TABLE);
        db.execSQL(" DROP TABLE IF EXISTS " + DATE_NOTE_TABLE);
    }

    /**
     * Create CRUD Functions
     */

    /**
     * CRUD Functions of WorkingSession
     */
    //Create Functions
    public long addWorkingSession(WorkingSession workingSession) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_WORK_SESSION_NAME, workingSession.getName());
        cv.put(COLUMN_WEEK_ID, workingSession.getWeekID());
        cv.put(COLUMN_DATE_ID, workingSession.getDateID());
        cv.put(COLUMN_TAG_ID, workingSession.getTagID());
        cv.put(COLUMN_START_TIME, workingSession.getStart_time());
        cv.put(COLUMN_END_TIME, workingSession.getEnd_time());
        cv.put(COLUMN_STATUS, workingSession.isStatus());
        cv.put(COLUMN_IS_ON_TIME, workingSession.isOnTime());

        return db.insert(WORKING_SESSION_TABLE, null, cv);
    }

    //Read Working Session
    public WorkingSession getWorkingSession(long workingSession_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + WORKING_SESSION_TABLE + " WHERE " + COLUMN_WORK_ID + " = " + workingSession_id;

        WorkingSession workingSession = new WorkingSession();

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();

            boolean status = c.getInt(c.getColumnIndex(COLUMN_STATUS)) == 1;
            boolean isOnTime = c.getInt(c.getColumnIndex(COLUMN_IS_ON_TIME)) == 1;


            workingSession.setWorkingSessionID(c.getInt(c.getColumnIndex(COLUMN_WORK_ID)));
            workingSession.setName(c.getString(c.getColumnIndex(COLUMN_WORK_SESSION_NAME)));
            workingSession.setStart_time(c.getString(c.getColumnIndex(COLUMN_START_TIME)));
            workingSession.setEnd_time(c.getString(c.getColumnIndex(COLUMN_END_TIME)));
            workingSession.setTagID(c.getInt(c.getColumnIndex(COLUMN_TAG_ID)));
            workingSession.setWeekID(c.getInt(c.getColumnIndex(COLUMN_WEEK_ID)));
            workingSession.setDateID(c.getInt(c.getColumnIndex(COLUMN_DATE_ID)));
            workingSession.setStatus(status);
            workingSession.setOnTime(isOnTime);
        }


        assert c != null;
        c.close();
        db.close();
        return workingSession;
    }


    // Get Working Sessions By Date
    public List<WorkingSession> getAllWorkingSessionsByDate(String selectedDate) {
        List<WorkingSession> workingSessions = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + WORKING_SESSION_TABLE + " ws, "
                + DATE_TABLE + " dt, " + DAY_WORK_TAG_TABLE + " dwt WHERE dt."
                + COLUMN_CURRENTDATE + " = '" + selectedDate + "'" + " AND dt." + COLUMN_DATE_ID
                + " = " + "dwt." + COLUMN_KEY_DATE_ID + " AND ws." + COLUMN_WORK_ID + " = "
                + "dwt." + COLUMN_KEY_WORK_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                boolean status = c.getInt(c.getColumnIndex(COLUMN_STATUS)) == 1;
                boolean isOnTime = c.getInt(c.getColumnIndex(COLUMN_IS_ON_TIME)) == 1;

                WorkingSession workingSession = new WorkingSession();
                workingSession.setWorkingSessionID(c.getInt(c.getColumnIndex(COLUMN_WORK_ID)));
                workingSession.setName(c.getString(c.getColumnIndex(COLUMN_WORK_SESSION_NAME)));
                workingSession.setStart_time(c.getString(c.getColumnIndex(COLUMN_START_TIME)));
                workingSession.setEnd_time(c.getString(c.getColumnIndex(COLUMN_END_TIME)));
                workingSession.setTagID(c.getInt(c.getColumnIndex(COLUMN_TAG_ID)));
                workingSession.setWeekID(c.getInt(c.getColumnIndex(COLUMN_WEEK_ID)));
                workingSession.setDateID(c.getInt(c.getColumnIndex(COLUMN_DATE_ID)));
                workingSession.setStatus(status);
                workingSession.setOnTime(isOnTime);

                workingSessions.add(workingSession);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return workingSessions;
    }


    //Get Working Sessions by Status in a Date

    public List<WorkingSession> getAllWorkSessionsByDateAndIsOnTime(int isOnTime, String selectedDate) {
        List<WorkingSession> workingSessions = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + WORKING_SESSION_TABLE + " ws, " + DATE_TABLE + " dt, " + DAY_WORK_IS_ON_TIME_TABLE + " DWI WHERE dt." +
                COLUMN_CURRENTDATE + " ='" + selectedDate + "'" + " AND dt." + COLUMN_DATE_ID + " = " + "DWI." + COLUMN_KEY_DATE_ID + " AND ws." + COLUMN_IS_ON_TIME + " = '"
                + isOnTime + "'" + "AND ws." + COLUMN_WORK_ID + " = " + "DWI." + COLUMN_KEY_WORK_ID;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                boolean status = c.getInt(c.getColumnIndex(COLUMN_STATUS)) == 1;
                boolean isOnTime1 = c.getInt(c.getColumnIndex(COLUMN_IS_ON_TIME)) == 1;

                WorkingSession workingSession = new WorkingSession();
                workingSession.setWorkingSessionID(c.getInt(c.getColumnIndex(COLUMN_WORK_ID)));
                workingSession.setName(c.getString(c.getColumnIndex(COLUMN_WORK_SESSION_NAME)));
                workingSession.setStart_time(c.getString(c.getColumnIndex(COLUMN_START_TIME)));
                workingSession.setEnd_time(c.getString(c.getColumnIndex(COLUMN_END_TIME)));
                workingSession.setTagID(c.getInt(c.getColumnIndex(COLUMN_TAG_ID)));
                workingSession.setWeekID(c.getInt(c.getColumnIndex(COLUMN_WEEK_ID)));
                workingSession.setDateID(c.getInt(c.getColumnIndex(COLUMN_DATE_ID)));
                workingSession.setStatus(status);
                workingSession.setOnTime(isOnTime1);

                workingSessions.add(workingSession);
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        return workingSessions;

    }

    //Get Working Sessions by Status in a Date

    public List<WorkingSession> getAllWorkSessionsByDateAndStatus(int status, String selectedDate) {
        List<WorkingSession> workingSessions = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + WORKING_SESSION_TABLE + " ws, " + DATE_TABLE + " dt, " + DAY_WORK_IS_ON_TIME_TABLE + " DWI WHERE dt." +
                COLUMN_CURRENTDATE + " ='" + selectedDate + "'" + " AND dt." + COLUMN_DATE_ID + " = " + "DWI." + COLUMN_KEY_DATE_ID + " AND ws." + COLUMN_STATUS + " = '"
                + status + "'" + "AND ws." + COLUMN_WORK_ID + " = " + "DWI." + COLUMN_KEY_WORK_ID;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                boolean status1 = c.getInt(c.getColumnIndex(COLUMN_STATUS)) == 1;
                boolean isOnTime = c.getInt(c.getColumnIndex(COLUMN_IS_ON_TIME)) == 1;

                WorkingSession workingSession = new WorkingSession();
                workingSession.setWorkingSessionID(c.getInt(c.getColumnIndex(COLUMN_WORK_ID)));
                workingSession.setName(c.getString(c.getColumnIndex(COLUMN_WORK_SESSION_NAME)));
                workingSession.setStart_time(c.getString(c.getColumnIndex(COLUMN_START_TIME)));
                workingSession.setEnd_time(c.getString(c.getColumnIndex(COLUMN_END_TIME)));
                workingSession.setTagID(c.getInt(c.getColumnIndex(COLUMN_TAG_ID)));
                workingSession.setWeekID(c.getInt(c.getColumnIndex(COLUMN_WEEK_ID)));
                workingSession.setDateID(c.getInt(c.getColumnIndex(COLUMN_DATE_ID)));
                workingSession.setStatus(status1);
                workingSession.setOnTime(isOnTime);

                workingSessions.add(workingSession);
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        return workingSessions;

    }

    //Get Working Sessions by Date and Tag

    public List<WorkingSession> getAllWorkSessionsByDateAndTag(String tag_name, String selectedDate) {
        List<WorkingSession> workingSessions = new ArrayList<>();
        String selectQuery = "SELECT * FROM" + WORKING_SESSION_TABLE + " ws, " + DATE_TABLE + " dt, " + TAG_TABLE + " tt, " + DAY_WORK_TAG_TABLE + " dwt WHERE dt." +
                COLUMN_CURRENTDATE + " =' " + selectedDate + "'" + " AND dt." + COLUMN_DATE_ID + " = " + "dwt." + COLUMN_KEY_DATE_ID + " AND tt." + COLUMN_TAG_NAME + " = '"
                + tag_name + "'" + "AND tt." + COLUMN_TAG_ID + " = " + "wdt." + COLUMN_KEY_TAG_ID + " AND ws." + COLUMN_WORK_ID + " = " + "dwt." + COLUMN_KEY_WORK_ID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                boolean status = c.getInt(c.getColumnIndex(COLUMN_STATUS)) == 1;
                boolean isOnTime = c.getInt(c.getColumnIndex(COLUMN_IS_ON_TIME)) == 1;

                WorkingSession workingSession = new WorkingSession();
                workingSession.setWorkingSessionID(c.getInt(c.getColumnIndex(COLUMN_WORK_ID)));
                workingSession.setName(c.getString(c.getColumnIndex(COLUMN_WORK_SESSION_NAME)));
                workingSession.setStart_time(c.getString(c.getColumnIndex(COLUMN_START_TIME)));
                workingSession.setEnd_time(c.getString(c.getColumnIndex(COLUMN_END_TIME)));
                workingSession.setTagID(c.getInt(c.getColumnIndex(COLUMN_TAG_ID)));
                workingSession.setWeekID(c.getInt(c.getColumnIndex(COLUMN_WEEK_ID)));
                workingSession.setDateID(c.getInt(c.getColumnIndex(COLUMN_DATE_ID)));
                workingSession.setStatus(status);
                workingSession.setOnTime(isOnTime);

                workingSessions.add(workingSession);
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        return workingSessions;
    }


    //Update Working Sessions

    public int updateWorkingSession(WorkingSession workingSession) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_WORK_SESSION_NAME, workingSession.getName());
        cv.put(COLUMN_WEEK_ID, workingSession.getWeekID());
        cv.put(COLUMN_DATE_ID, workingSession.getDateID());
        cv.put(COLUMN_TAG_ID, workingSession.getTagID());
        cv.put(COLUMN_START_TIME, workingSession.getStart_time());
        cv.put(COLUMN_END_TIME, workingSession.getEnd_time());
        cv.put(COLUMN_STATUS, workingSession.isStatus());
        cv.put(COLUMN_IS_ON_TIME, workingSession.isOnTime());

        return db.update(WORKING_SESSION_TABLE, cv, COLUMN_WORK_ID + " = ?", new String[]{String.valueOf(workingSession.getWorkingSessionID())});
    }

    //Delete a Working Sessions

    public void deleteWorkingSession(long workingSessionID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(WORKING_SESSION_TABLE, COLUMN_WORK_ID + " = ?", new String[]{String.valueOf(workingSessionID)});
    }

    /**
     * CRUD Functions of Tag table
     *
     * @return
     */

    //Create Tag
    public int addTag(String tagName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TAG_NAME, tagName);

        return (int) db.insertWithOnConflict(TAG_TABLE, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

    }

    //Get a tag name
    public Tag getOneTagName(long tag_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Tag tag = new Tag();

        String selectQuery = "SELECT * FROM " + TAG_TABLE + " WHERE " + COLUMN_TAG_ID + " = " + tag_id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
            String tagName = c.getString(c.getColumnIndex(COLUMN_TAG_NAME));
            int tagID = c.getInt(c.getColumnIndex(COLUMN_TAG_ID));

            tag.setTagName(tagName);
            tag.setTagID(tagID);
        }


        assert c != null;
        c.close();
        db.close();
        return tag;
    }

    //Get all tag names
    public List<Tag> getAllTagNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Tag> tags = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TAG_TABLE;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Tag tag = new Tag();
                tag.setTagName(c.getString(c.getColumnIndex(COLUMN_TAG_NAME)));
                tag.setTagID(c.getInt(c.getColumnIndex(COLUMN_TAG_ID)));

                tags.add(tag);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return tags;
    }

    //Update Tag table
    public int updateTag(Tag tag) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TAG_NAME, tag.getTagName());

        return db.update(TAG_TABLE, cv, COLUMN_TAG_ID + " = ?", new String[]{String.valueOf(tag.getTagID())});
    }


    /**
     * CRUD Functions of Date table
     */

    //Create Function
    public long addDate(DateCalendar dateCalendar) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues cv = new ContentValues();

        cv.put(COLUMN_WEEK_ID, dateCalendar.getWeekID());
        cv.put(COLUMN_ORDER_WEEK, dateCalendar.getWeekOrder());
        cv.put(COLUMN_DAY_OF_WEEK, dateCalendar.getDay_of_week());
        cv.put(COLUMN_CURRENTDATE, dateCalendar.getCurrentDate());

        return db.insert(DATE_TABLE, null, cv);

    }

    //Get Date Function
    public DateCalendar getDate(long dateCalendarID) {
        SQLiteDatabase db = this.getReadableDatabase();
        DateCalendar dateCalendar = new DateCalendar();

        String selectQuery = "SELECT * FROM " + DATE_TABLE + " WHERE " + COLUMN_DATE_ID + " = " + dateCalendarID;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();

            String currentDate = c.getString(c.getColumnIndex(COLUMN_CURRENTDATE));

            dateCalendar.setDateID(c.getInt(c.getColumnIndex(COLUMN_DATE_ID)));
            dateCalendar.setWeekID(c.getInt(c.getColumnIndex(COLUMN_WEEK_ID)));
            dateCalendar.setCurrentDate(c.getString(c.getColumnIndex(COLUMN_CURRENTDATE)));
            dateCalendar.setDay_of_week(c.getString(c.getColumnIndex(COLUMN_DAY_OF_WEEK)));
        } else {
            // Do nothing
        }

        assert c != null;
        c.close();
        db.close();
        return dateCalendar;
    }

    //Get All Date Function
    public List<DateCalendar> getAllDate() {
        SQLiteDatabase db = this.getReadableDatabase();
        DateCalendar dateCalendar = new DateCalendar();
        List<DateCalendar> dateCalendars = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DATE_TABLE;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
            do {
                dateCalendar.setDateID(c.getInt(c.getColumnIndex(COLUMN_DATE_ID)));
                dateCalendar.setWeekID(c.getInt(c.getColumnIndex(COLUMN_WEEK_ID)));
                dateCalendar.setCurrentDate(c.getString(c.getColumnIndex(COLUMN_CURRENTDATE)));
                dateCalendar.setDay_of_week(c.getString(c.getColumnIndex(COLUMN_DAY_OF_WEEK)));

                dateCalendars.add(dateCalendar);

            } while (c.moveToNext());

        }

        assert c != null;
        c.close();
        return dateCalendars;
    }

    //Get All Date by Week
    public List<DateCalendar> getAllDateByWeek(int weekOrder) throws ParseException {
        SQLiteDatabase db = this.getReadableDatabase();
        List<DateCalendar> dateCalendars = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + DATE_TABLE + " WHERE " + COLUMN_ORDER_WEEK + " = '" + weekOrder + "'";

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                DateCalendar dateCalendar = new DateCalendar();
                dateCalendar.setDateID(c.getInt(c.getColumnIndex(COLUMN_DATE_ID)));
                dateCalendar.setWeekID(c.getInt(c.getColumnIndex(COLUMN_WEEK_ID)));
                dateCalendar.setWeekID(c.getInt(c.getColumnIndex(COLUMN_ORDER_WEEK)));
                dateCalendar.setDay_of_week(c.getString(c.getColumnIndex(COLUMN_DAY_OF_WEEK)));
                dateCalendar.setCurrentDate(c.getString(c.getColumnIndex(COLUMN_CURRENTDATE)));

                dateCalendars.add(dateCalendar);
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        return dateCalendars;

    }


    /**
     * Date Work Tag table
     */

    // Create Function
    public long addDWT(int date_id, int work_id, int tag_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_KEY_DATE_ID, date_id);
        cv.put(COLUMN_KEY_WORK_ID, work_id);
        cv.put(COLUMN_KEY_TAG_ID, tag_id);

        long id = db.insert(DAY_WORK_TAG_TABLE, null, cv);
        return id;
    }

    //Update Function

    public int updateDWT(long id, long tag_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_KEY_TAG_ID, tag_id);

        return db.update(DAY_WORK_TAG_TABLE, cv, COLUMN_SUM_ID + " = ?", new String[]{String.valueOf(id)});
    }

    //Delete Function

    public void deleteDWT(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DAY_WORK_TAG_TABLE, COLUMN_SUM_ID + " = ?", new String[]{String.valueOf(id)});
    }


    /**
     * CRUD functions of Date Work Is On Time table
     */

    // Create Function
    public long addDWI(int date_id, int work_id, boolean isOnTime) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_KEY_DATE_ID, date_id);
        cv.put(COLUMN_KEY_WORK_ID, work_id);
        cv.put(COLUMN_KEY_IS_ON_TIME, isOnTime);

        long id = db.insert(DAY_WORK_IS_ON_TIME_TABLE, null, cv);
        return id;
    }

    //Update Function

    public int updateDWI(long id, boolean isOnTime) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_KEY_IS_ON_TIME, isOnTime);

        return db.update(DAY_WORK_IS_ON_TIME_TABLE, cv, COLUMN_KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }

    //Delete Function

    public void deleteDWI(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DAY_WORK_IS_ON_TIME_TABLE, COLUMN_KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }


    /**
     * Date Work Status table
     */

    // Create Function
    public long addDWS(int date_id, int work_id, boolean isOnTime) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_KEY_DATE_ID, date_id);
        cv.put(COLUMN_KEY_WORK_ID, work_id);
        cv.put(COLUMN_KEY_STATUS, isOnTime);

        long id = db.insert(DAY_WORK_STATUS_TABLE, null, cv);
        return id;
    }

    //Update Function

    public int updateDWS(long id, boolean status) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_KEY_STATUS, status);

        return db.update(DAY_WORK_STATUS_TABLE, cv, COLUMN_KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }

    //Delete Function

    public void deleteDWS(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DAY_WORK_STATUS_TABLE, COLUMN_KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }


    /**
     * CRUD functions of Week table
     */

    //Create functions
    public long addWeek(int order_week) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ORDER_WEEK, order_week);

        return db.insert(WEEK_TABLE, null, cv);
    }

    //Read functions
    public weekCalendar getWeek(long week_id) {

        SQLiteDatabase db = this.getReadableDatabase();
        weekCalendar weekCalendar = new weekCalendar();

        String selectQuery = "SELECT * FROM " + WEEK_TABLE + " WHERE " + COLUMN_WEEK_ID + " = " + week_id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {

            c.moveToFirst();

            weekCalendar.setWeekID(c.getInt(c.getColumnIndex(COLUMN_WEEK_ID)));
            weekCalendar.setOrder_week(c.getInt(c.getColumnIndex(COLUMN_ORDER_WEEK)));

        }


        c.close();
        db.close();
        return weekCalendar;
    }

    public int getWeekID(int week_order) {

        SQLiteDatabase db = this.getReadableDatabase();
        int week_id = 0;

        String selectQuery = "SELECT * FROM " + WEEK_TABLE + " WHERE " + COLUMN_ORDER_WEEK + " = " + week_order;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {


            week_id = c.getInt(c.getColumnIndex(COLUMN_ORDER_WEEK));

            c.close();
            db.close();
        }

        return week_id;
    }

    /**
     * Date Note table
     */

    // Create Function
    public long addDN(String currentDate, int note_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CURRENTDATE, currentDate);
        cv.put(COLUMN_NOTE_ID, note_id);

        long id = db.insert(DATE_NOTE_TABLE, null, cv);
        return id;
    }

    //Update Function

    public int updateDN(long id, int note_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NOTE_ID, note_id);

        return db.update(DATE_NOTE_TABLE, cv, COLUMN_DAY_NOTE_ID + " = ?", new String[]{String.valueOf(id)});
    }

    //Delete Function

    public void deleteDN(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATE_NOTE_TABLE, COLUMN_DAY_NOTE_ID + " = ?", new String[]{String.valueOf(id)});
    }


    /**
     * CRUD Function of Note Table
     */

    // Add Note
    public long addNote(String selectedDate, String noteText) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CURRENTDATE, selectedDate);
        cv.put(COLUMN_NOTE, noteText);

        return db.insert(NOTE_TABLE, null, cv);

    }

    // Get a Note
    public Note getOne(long noteID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Note note = new Note();
        String selectQuery = "SELECT * FROM " + NOTE_TABLE + " WHERE " + COLUMN_NOTE_ID + " = '" + noteID + "'";

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
            note.setNoteID(c.getInt(c.getColumnIndex(COLUMN_DAY_NOTE_ID)));
            note.setCurrentDate(c.getString(c.getColumnIndex(COLUMN_CURRENTDATE)));
            note.setText(c.getString(c.getColumnIndex(COLUMN_NOTE)));

            c.close();
            db.close();

        }
        return note;
    }

    //Get all Note by selectedDate
    public List<Note> getAllNoteByDate(String selectedDate) {
        List<Note> notes = new ArrayList<>();
        String selectQuery = " SELECT * FROM " + NOTE_TABLE + " WHERE " + COLUMN_CURRENTDATE + " = '" + selectedDate + "'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Note note = new Note();
                note.setNoteID(c.getInt(c.getColumnIndex(COLUMN_NOTE_ID)));
                note.setCurrentDate(c.getString(c.getColumnIndex(COLUMN_CURRENTDATE)));
                note.setText(c.getString(c.getColumnIndex(COLUMN_NOTE)));

                notes.add(note);
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        return notes;
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TAG_NAME, note.getText());

        return db.update(NOTE_TABLE, cv, COLUMN_NOTE_ID + " = ?", new String[]{String.valueOf(note.getNoteID())});
    }

    public void deleteNote(long noteID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NOTE_TABLE, COLUMN_NOTE_ID + " = ?", new String[]{String.valueOf(noteID)});
    }

    // Get all note by Date


}
