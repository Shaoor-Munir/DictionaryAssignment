package com.example.shaoo.assignment3;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Shaoor Munir on 05-Nov-16.
 */

public class DictionaryContent extends ContentProvider {
    private SQLiteDatabase db;
    static final String PROVIDER_NAME = "com.example.provider.Dictionary";
    static final String URL = "content://" + PROVIDER_NAME + "/words";
    static final Uri CONTENT_URI = Uri.parse(URL);
    static final String DATABASE_NAME = "Dictionary";
    static final String WORDS_TABLE_NAME = "words";
    static final int DATABASE_VERSION = 1;
    static int values = 0;
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + WORDS_TABLE_NAME +
                    " (wordID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " word TEXT NOT NULL, " +
                    " meaning TEXT NOT NULL);";

    static int hardcodeData = 0;

    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "words", 1);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + WORDS_TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */

        db = dbHelper.getWritableDatabase();


        String[] colnames = {"word"};
        Cursor cursor = query(Uri.parse("content://com.example.provider.Dictionary/words"), colnames, null, null, null);

        if(cursor.getCount() == 0)
        {

            ContentValues con = new ContentValues();

            con.put("word", "Hi");
            con.put("meaning", "Used as an alternate form of Hello");
            insert(Uri.parse(URL), con);


            con.put("word", "Hi");
            con.put("meaning", "Used to greet someone you have just met");
            insert(Uri.parse(URL), con);


            con.put("word", "Bye");
            con.put("meaning", "Used to bid farewell to someone while parting of ways. Can be used in place of GoodBye");
            insert(Uri.parse(URL), con);


            con.put("word", "Omar");
            con.put("meaning", "Koi bohat hi chawak banda");
            insert(Uri.parse(URL), con);


            con.put("word", "Haider");
            con.put("meaning", "Phattu");
            insert(Uri.parse(URL), con);


            con.put("word", "Saad");
            con.put("meaning", "Best Friend");
            insert(Uri.parse(URL), con);


            con.put("word", "Haseeb");
            con.put("meaning", "Ganja");
            insert(Uri.parse(URL), con);


            con.put("word", "Fatima");
            con.put("meaning", "Motu :P");
            insert(Uri.parse(URL), con);
        }
        return db != null;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(WORDS_TABLE_NAME);

        if (uriMatcher.match(uri) != 1) {
            return null;
        } else {
            return db.query(WORDS_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        long rowID = db.insert(WORDS_TABLE_NAME, "", values);

        /**
         * If record is added successfully
         */

        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }


}
