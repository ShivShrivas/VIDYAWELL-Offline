package vidyawell.infotech.bsn.admin.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;




public class MyDatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "school_result";


    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Note.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public void insertNote(String code, String name, String theory, String practical, String notebook, String enrichment, String lock,String maxtheory,String maxenrichment,String maxnote,String maxpractical,String rowcount,String total,String test) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_CODE,code);
        values.put(Note.COLUMN_NAME,name);
        values.put(Note.COLUMN_THEORY,theory);
        values.put(Note.COLUMN_PRACTICAL,practical);
        values.put(Note.COLUMN_NOTEBOOK,notebook);
        values.put(Note.COLUMN_ENRICHMENT,enrichment);
        values.put(Note.COLUMN_LOCK,lock);
        values.put(Note.COLUMN_MAXTHEORY,maxtheory);
        values.put(Note.COLUMN_MAXENRICHMENT,maxenrichment);
        values.put(Note.COLUMN_MAXNOTE,maxnote);
        values.put(Note.COLUMN_MAXPRACTICAL,maxpractical);
        values.put(Note.COLUMN_ROWCOUNT,rowcount);
        values.put(Note.COLUMN_GRANDTOTAL,total);
        values.put(Note.COLUMN_TEST,test);
        db.insert(Note.TABLE_NAME, null, values);
        db.close();
    }

    public Note getNote(String id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Note.TABLE_NAME,
                new String[]{Note.COLUMN_ID,Note.COLUMN_CODE, Note.COLUMN_NAME, Note.COLUMN_THEORY, Note.COLUMN_PRACTICAL, Note.COLUMN_NOTEBOOK, Note.COLUMN_ENRICHMENT, Note.COLUMN_LOCK, Note.COLUMN_ROWCOUNT,Note.COLUMN_GRANDTOTAL,Note.COLUMN_TEST},
                Note.COLUMN_CODE + "=?",
                new String[]{id}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Note note = new Note(
                cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_CODE)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_THEORY)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_PRACTICAL)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTEBOOK)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_ENRICHMENT)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_LOCK)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_MAXTHEORY)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_MAXENRICHMENT)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_MAXNOTE)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_ROWCOUNT)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_MAXPRACTICAL)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_GRANDTOTAL)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_TEST))

        );
        cursor.close();
        return note;
    }
    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
                note.setCode(cursor.getString(cursor.getColumnIndex(Note.COLUMN_CODE)));
                note.setName(cursor.getString(cursor.getColumnIndex(Note.COLUMN_NAME)));
                note.settheory(cursor.getString(cursor.getColumnIndex(Note.COLUMN_THEORY)));
                note.setpractical(cursor.getString(cursor.getColumnIndex(Note.COLUMN_PRACTICAL)));
                note.setnotebook(cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTEBOOK)));
                note.setenrichment(cursor.getString(cursor.getColumnIndex(Note.COLUMN_ENRICHMENT)));
                note.setlock(cursor.getString(cursor.getColumnIndex(Note.COLUMN_LOCK)));
                note.setmaxtheory(cursor.getString(cursor.getColumnIndex(Note.COLUMN_MAXTHEORY)));
                note.setmaxenrichment(cursor.getString(cursor.getColumnIndex(Note.COLUMN_MAXENRICHMENT)));
                note.setmaxnote(cursor.getString(cursor.getColumnIndex(Note.COLUMN_MAXNOTE)));
                note.setmaxpractical(cursor.getString(cursor.getColumnIndex(Note.COLUMN_MAXPRACTICAL)));
                note.setrowcount(cursor.getString(cursor.getColumnIndex(Note.COLUMN_ROWCOUNT)));
                note.settotal(cursor.getString(cursor.getColumnIndex(Note.COLUMN_GRANDTOTAL)));
                note.settest(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TEST)));
                notes.add(note);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        return notes;
    }

    public int getNotesCount(String code) {
      //  String countQuery = "SELECT  * FROM " + Note.TABLE_NAME+" WHERE "+;
        SQLiteDatabase db = this.getReadableDatabase();
       // Cursor cursor = db.rawQuery(countQuery, null);
       // String query2="SELECT * FROM "+ Note.TABLE_NAME+" WHERE ("+Note.COLUMN_USERID+" = '"+userid+"' AND "+Note.COLUMN_SCHOOLCODE+" = '"+id+"')"+" OR ("+Note.COLUMN_PASSWORD+" = '"+password+"')";
        String query2="SELECT * FROM "+ Note.TABLE_NAME+" WHERE ("+Note.COLUMN_CODE+" = '"+code+"')";
        Cursor cursor = db.rawQuery(query2,null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }

    public int getUnlockCount(String unlock) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query2="SELECT * FROM "+ Note.TABLE_NAME+" WHERE ("+Note.COLUMN_LOCK+" = '"+unlock+"')";
        Cursor cursor = db.rawQuery(query2,null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

  /*  public Note getNotematch(String id,String userid,String password) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        *//*Cursor cursor = db.query(Note.TABLE_NAME,
                new String[]{Note.COLUMN_ID,Note.COLUMN_SCHOOLCODE, Note.COLUMN_BRANCHNAME, Note.COLUMN_BRANCHLOGO, Note.COLUMN_USERID, Note.COLUMN_PASSWORD, Note.COLUMN_BRANCHCODE},
                Note.COLUMN_SCHOOLCODE + "=?",
                new String[]{id}, null, null, null, null);*//*

        *//*String query="SELECT *\n" +
                "                FROM employees\n" +
                "        WHERE (last_name = 'Smith' AND first_name = 'Jane')\n" +
                "        OR (employee_id = 1);";*//*
        String query2="SELECT * FROM "+ Note.TABLE_NAME+" WHERE ("+Note.COLUMN_CODE+" = '"+userid+"' AND "+Note.COLUMN_SCHOOLCODE+" = '"+id+"')"+" OR ("+Note.COLUMN_PASSWORD+" = '"+password+"')";
        Cursor cursor = db.rawQuery(query2,null);




        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Note note = new Note(
                cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_SCHOOLCODE)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_BRANCHNAME)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_BRANCHLOGO)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_USERID)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_PASSWORD)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_BRANCHCODE))

        );

        // close the db connection
        cursor.close();

        return note;
    }*/



    public int updateNote(String code,String theorymark,String enrichmark,String notemark,String islock,String practical,String total,String test) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_THEORY, theorymark);
        values.put(Note.COLUMN_PRACTICAL, practical);
        values.put(Note.COLUMN_NOTEBOOK, notemark);
        values.put(Note.COLUMN_ENRICHMENT, enrichmark);
        values.put(Note.COLUMN_LOCK,islock);
        values.put(Note.COLUMN_GRANDTOTAL,total);
        values.put(Note.COLUMN_TEST,test);
        // updating row
        return db.update(Note.TABLE_NAME, values, Note.COLUMN_CODE + " = ?",
                new String[]{String.valueOf(code)});
    }

    public void deleteNote() {
        SQLiteDatabase db = this.getWritableDatabase();
       /* db.delete(Note.TABLE_NAME, Note.COLUMN_CODE + " = ?",
                new String[]{note});*/
        db.delete(Note.TABLE_NAME,null,null);
        db.close();
    }
}
