package com.example.androidlabs;
import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String TABLE_NAME = "ToDoList";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TASK = "Task";
    public static final String COLUMN_URGENT = "Urgent";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " + TABLE_NAME +
                        "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
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

    public void printCursor(Cursor res) {
        SQLiteDatabase db = this.getReadableDatabase();
        int version = db.getVersion();
        int columncount= res.getColumnCount();
        String[] colnames= res.getColumnNames();
        int resultnumber = res.getCount();
        System.out.println("Version: "+version);
        System.out.println("Columns: "+columncount);
        for (int i=0;i<colnames.length;i++) {
            System.out.println("Column "+i+" :"+colnames[i]);
        }
        System.out.println("Number of results: "+resultnumber);
        res.moveToFirst();
        for (int i=0;i<resultnumber;i++) {
            System.out.print("row :"+i);
            for (int j = 1; j < columncount; j++) {
                System.out.println(res.getString(j));
            }
            res.moveToNext();
        }

    }

    public Cursor getCursor () {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + TABLE_NAME, null);
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + TABLE_NAME + " where _id=" + id, null);
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }


    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME + " where _id=" + id);
    }

    public ArrayList<String> getAllTask() {
        ArrayList<String> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        res.moveToFirst();
        int index = res.getColumnIndex(COLUMN_TASK);

        while (!res.isAfterLast()) {

            array_list.add(res.getString(index));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getAllUrgent() {
        ArrayList<String> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        res.moveToFirst();
        int index = res.getColumnIndex(COLUMN_URGENT);

        while (!res.isAfterLast()) {
            array_list.add(res.getString(index));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Integer> getAllId() {
        ArrayList<Integer> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        res.moveToFirst();
        int index = 0;

        while (!res.isAfterLast()) {
            array_list.add(res.getInt(index));
            res.moveToNext();
        }
        return array_list;
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