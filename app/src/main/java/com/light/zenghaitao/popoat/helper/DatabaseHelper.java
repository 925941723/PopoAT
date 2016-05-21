package com.light.zenghaitao.popoat.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.light.zenghaitao.popoat.model.DatabaseBean;

import java.sql.SQLException;

/**
 * Created by ly-zenghaitao on 2016/4/20.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper{

    private static final String DATABASE_NAME = "database";
    private static final int DATABASE_VERSION = 1;
    private static DatabaseHelper databaseHelper;
    private Dao<DatabaseBean, Integer> databaseDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, DatabaseBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2, int arg3) {
        try {
            TableUtils.dropTable(connectionSource, DatabaseBean.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized DatabaseHelper getHelper(Context context){
        if (null==databaseHelper){
            synchronized (DatabaseHelper.class){
                if (null==databaseHelper){
                    databaseHelper = new DatabaseHelper(context);
                }
            }
        }
        return databaseHelper;
    }

    public Dao<DatabaseBean, Integer> getDatabaseDao() throws SQLException{
        if (databaseDao==null){
            databaseDao = getDao(DatabaseBean.class);
        }
        return databaseDao;
    }
}
