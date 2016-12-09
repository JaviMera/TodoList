package todo.javier.mera.todolist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by javie on 12/4/2016.
 */

public class TodoListSQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "todo_lists.db";
    private static final int DB_VERSION = 9;

    public static final String TABLE_TODO_LISTS = "TODO_LISTS";
    public static final String COLUMN_TODO_LIST_NAME = "NAME";
    public static final String COLUMN_CREATION_DATE = "CREATED_DATE";
    public static final String COLUMN_DUE_DATE = "DUE_DATE";
    public static final String COLUMN_TODO_LIST_POSITION = "POSITION";
    private String CREATE_TODO_LISTS = "CREATE TABLE "
        + TABLE_TODO_LISTS
        + "("
        + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + COLUMN_TODO_LIST_POSITION + " INTEGER, "
        + COLUMN_TODO_LIST_NAME + " TEXT, "
        + COLUMN_CREATION_DATE + " INTEGER, "
        + COLUMN_DUE_DATE + " INTEGER"
        + ")";

    public static final String TABLE_TODO_LIST_ITEMS = "TODO_LIST_ITEMS";
    public static final String COLUMN_ITEMS_FOREIGN_KEY = "TODO_LIST_ID";
    public static final String COLUMN_ITEMS_POSITION = "POSITION";
    public static final String COLUMN_ITEMS_DESCRIPTION = "DESCRIPTION";
    public static final String COLUMN_ITEMS_COMPLETED = "COMPLETED";
    public static final String COLUMN_ITEMS_TIMESTAMP = "TIMESTAMP";
    private static final String CREATE_TODO_LIST_ITEMS = "CREATE TABLE "
        + TABLE_TODO_LIST_ITEMS
        + "("
        + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + COLUMN_ITEMS_FOREIGN_KEY + " INTEGER, "
        + COLUMN_ITEMS_POSITION + " INTEGER, "
        + COLUMN_ITEMS_DESCRIPTION + " TEXT, "
        + COLUMN_ITEMS_COMPLETED + " INTEGER, "
        + COLUMN_ITEMS_TIMESTAMP +  " INTEGER, "
        + "FOREIGN KEY " + "(" + COLUMN_ITEMS_FOREIGN_KEY + ") REFERENCES " + TABLE_TODO_LISTS + "(" + BaseColumns._ID + ")"
        + ")";

    public TodoListSQLiteHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_TODO_LISTS);
        sqLiteDatabase.execSQL(CREATE_TODO_LIST_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        switch(oldVersion) {

            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO_LIST_ITEMS);
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO_LISTS);
                onCreate(sqLiteDatabase);
                break;
        }
    }
}
