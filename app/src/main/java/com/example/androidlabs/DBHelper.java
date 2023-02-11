package com.example.androidlabs;
import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String TABLE_NAME = "ToDoList";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TASK = "Task";
    public static final String COLUMN_URGENT = "Urgent";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " + TABLE_NAME +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_TASK + " text,"
                        + COLUMN_URGENT + " text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertTask(String task, String urgent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TASK, task);
        contentValues.put(COLUMN_URGENT, urgent);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where id=" + id, null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }


    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME + " where 'id'=" + id);
    }

    public ArrayList<String> getAllTask() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        res.moveToFirst();
        int index = res.getColumnIndex(COLUMN_TASK);

        while (res.isAfterLast() == false) {

            array_list.add(res.getString(index));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getAllUrgent() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        res.moveToFirst();
        int index = res.getColumnIndex(COLUMN_URGENT);

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(index));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Integer> getAllId() {
        ArrayList<Integer> array_list = new ArrayList<Integer>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        res.moveToFirst();
        int index = 0;

        while (res.isAfterLast() == false) {
            array_list.add(res.getInt(index));
            res.moveToNext();
        }
        return array_list;
    }

    public int getMaxId() {
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        res.moveToLast();
        int index = 0;
        int maxId = res.getInt(index);

        return maxId;
    }
}
//    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("name", name);
//        contentValues.put("phone", phone);
//        contentValues.put("email", email);
//        contentValues.put("street", street);
//        contentValues.put("place", place);
//        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
//        return true;
//    }