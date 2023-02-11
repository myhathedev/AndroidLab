//package com.example.androidlabs;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//public class MyOpener extends SQLiteOpenHelper {
//    protected final static String DATABASE_NAME = "ToDoDB";
//    protected final static  int VERSION_NUM = 1;
//    public final static String TABLE_NAME = "ToDo_Table";
//    public final static String COL_ID= "_id";
//    public final static String COL_TASK = "Task";
//    public final static String COL_URGENT = "Urgent";
//
//    public MyOpener(Context ctx){ super(ctx,DATABASE_NAME,null,VERSION_NUM);}
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
//                + COL_TASK + " text,"
//                + COL_URGENT + " text);" );
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        onCreate(db);
//    }
//
//    @Override
//    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        onCreate(db);
//    }
//
//}
