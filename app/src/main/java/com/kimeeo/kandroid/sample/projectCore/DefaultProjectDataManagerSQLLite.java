package com.kimeeo.kandroid.sample.projectCore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kimeeo.kandroid.sample.model.SampleModel;
import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.dataManagers.sqlLite.SQLLiteDataManager;
import com.kimeeo.library.listDataView.dataManagers.sqlLite.SQLiteDataHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bhavinpadhiyar on 1/11/16.
 */
public class DefaultProjectDataManagerSQLLite extends SQLLiteDataManager
{
    public DefaultProjectDataManagerSQLLite(Context context)
    {
        super(context,new SQLiteDataHelper1(context));
        //setRefreshEnabled(true);
    }

    public static class SQLiteDataHelper1 extends SQLiteDataHelper
    {
        private static final int DATABASE_VERSION = 3;
        private static final String DATABASE_NAME = "sampleManager";
        private static final String TABLE_SAMPLE = "sample";

        private static final String KEY_ID = "id";
        private static final String KEY_NAME = "name";
        private static final String KEY_DETAILS = "details";

        public SQLiteDataHelper1(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SAMPLE + "("
                    + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                    + KEY_DETAILS + " TEXT" + ")";
            db.execSQL(CREATE_CONTACTS_TABLE);
        }

        private SampleModel getSample(String name, String phone) {
            SampleModel o = new SampleModel();
            o.name =name;
            o.details = phone;
            return o;
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAMPLE);

            // Create tables again
            onCreate(db);
        }

        void addEntry(SampleModel contact) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_NAME, contact.name); // Contact Name
            values.put(KEY_DETAILS, contact.details); // Contact Phone

            // Inserting Row
            db.insert(TABLE_SAMPLE, null, values);
            db.close(); // Closing database connection
        }
        boolean isAdded=false;
        public List<?> getList(PageData data,Map<String, Object> param)
        {
            /*
            if(isAdded==false) {
                addEntry(getSample("B1", "534534"));
                addEntry(getSample("B2", "534534"));
                addEntry(getSample("B3", "534534"));
                addEntry(getSample("B4", "534534"));
                addEntry(getSample("B5", "534534"));
                addEntry(getSample("B6", "534534"));
                addEntry(getSample("B7", "534534"));
                addEntry(getSample("B8", "534534"));
                addEntry(getSample("B9", "534534"));
                addEntry(getSample("B10", "534534"));
                isAdded=true;
            }*/


            List<SampleModel> contactList = new ArrayList<>();
            String selectQuery = "SELECT  * FROM " + TABLE_SAMPLE;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    SampleModel contact = new SampleModel();
                    contact.id = Integer.parseInt(cursor.getString(0))+"";
                    contact.name = cursor.getString(1);
                    contact.details = cursor.getString(2);
                    contactList.add(contact);
                } while (cursor.moveToNext());
            }
            return contactList;
        };

    }

}