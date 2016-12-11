package todo.javier.mera.todolist.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by javie on 12/4/2016.
 */

public class TodoListSQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "todo_lists.db";
    private static final int DB_VERSION = 16;

    public static final String TABLE_TODO_LISTS = "TODO_LISTS";
    public static final String COLUMN_TODO_LIST_ID = "TODOLIST_ID";
    public static final String COLUMN_TODO_LIST_NAME = "NAME";
    public static final String COLUMN_CREATION_DATE = "CREATED_DATE";
    public static final String COLUMN_TODO_LIST_POSITION = "POSITION";
    private String CREATE_TODO_LISTS = "CREATE TABLE "
        + TABLE_TODO_LISTS
        + "("
        + COLUMN_TODO_LIST_ID + " TEXT PRIMARY KEY, "
        + COLUMN_TODO_LIST_POSITION + " INTEGER, "
        + COLUMN_TODO_LIST_NAME + " TEXT, "
        + COLUMN_CREATION_DATE + " INTEGER"
        + ")";

    public static final String TABLE_TODO_LIST_ITEMS = "TODO_LIST_ITEMS";
    public static final String COLUMN_ITEMS_ID = "ITEM_ID";
    public static final String COLUMN_ITEMS_POSITION = "POSITION";
    public static final String COLUMN_ITEMS_DESCRIPTION = "DESCRIPTION";
    public static final String COLUMN_ITEMS_STATUS = "COMPLETED";
    public static final String COLUMN_ITEMS_CREATED_ON = "CREATED_ON";
    public static final String COLUMN_ITEMS_DUE_DATE = "DUE_DATE";
    private static final String CREATE_TODO_LIST_ITEMS = "CREATE TABLE "
        + TABLE_TODO_LIST_ITEMS
        + "("
        + COLUMN_ITEMS_ID + " TEXT PRIMARY KEY, "
        + COLUMN_TODO_LIST_ID + " TEXT, "
        + COLUMN_ITEMS_POSITION + " INTEGER, "
        + COLUMN_ITEMS_DESCRIPTION + " TEXT, "
        + COLUMN_ITEMS_STATUS + " INTEGER, "
        + COLUMN_ITEMS_CREATED_ON +  " INTEGER, "
        + COLUMN_ITEMS_DUE_DATE + " INTEGER, "
        + "FOREIGN KEY " + "(" + COLUMN_TODO_LIST_ID + ") REFERENCES " + TABLE_TODO_LISTS + "(" + COLUMN_TODO_LIST_ID + ")"
        + ")";

    public TodoListSQLiteHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {

            sqLiteDatabase.execSQL(CREATE_TODO_LISTS);
            sqLiteDatabase.execSQL(CREATE_TODO_LIST_ITEMS);
        }
        catch(SQLException ex) {

            String message = ex.getMessage();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        if(oldVersion < newVersion) {

            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO_LIST_ITEMS);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO_LISTS);
            onCreate(sqLiteDatabase);
        }
    }
}
