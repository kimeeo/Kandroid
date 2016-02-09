package com.kimeeo.library.listDataView.dataManagers.sqlLite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kimeeo.library.listDataView.dataManagers.PageData;
import com.kimeeo.library.listDataView.dataManagers.IListProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by bhavinpadhiyar on 1/29/16.
 */
abstract public class SQLiteDataHelper extends SQLiteOpenHelper implements IListProvider {
    abstract public List<?> getList(PageData data,Map<String, Object> param);

    public SQLiteDataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super( context,name,  factory,  version);
    }
    public SQLiteDataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version,
                            DatabaseErrorHandler errorHandler) {
      super( context,  name, factory,version,errorHandler);
    }
}
