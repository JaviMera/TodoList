package todo.javier.mera.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.LinkedList;
import java.util.List;

import todo.javier.mera.todolist.model.TodoList;

/**
 * Created by javie on 12/4/2016.
 */

public class TodoListDataSource {

    private Context mContext;
    private TodoListSQLiteHelper mSqliteHelper;
    private SQLiteDatabase mDb;

    public TodoListDataSource(Context context) {

        mContext = context;
        mSqliteHelper = new TodoListSQLiteHelper(context);
    }

    public void openWriteable() {

        mDb = mSqliteHelper.getWritableDatabase();
    }

    public void openReadable() {

        mDb = mSqliteHelper.getReadableDatabase();
    }

    public void close() {

        mDb.close();
    }

    public boolean isOpen() {

        return mDb.isOpen();
    }

    public long create(String todoListTitle, long creationDate) {

        mDb.beginTransaction();

        TodoList newTodoList;
        ContentValues todoListValues = new ContentValues();
        todoListValues.put(TodoListSQLiteHelper.COLUMN_TODO_LIST_NAME, todoListTitle);
        todoListValues.put(TodoListSQLiteHelper.COLUMN_TODO_LIST_TIMESTAMP, creationDate);

        long id = mDb.insert(TodoListSQLiteHelper.TABLE_TODO_LISTS, null, todoListValues);

        mDb.setTransactionSuccessful();
        mDb.endTransaction();

        return id;
    }

    public List<TodoList> readTodoLists() {

        Cursor cursor = mDb.query(

            TodoListSQLiteHelper.TABLE_TODO_LISTS,
            new String[] {BaseColumns._ID, TodoListSQLiteHelper.COLUMN_TODO_LIST_NAME, TodoListSQLiteHelper.COLUMN_TODO_LIST_TIMESTAMP},
            null,
            null,
            null,
            null,
            null
        );

        List<TodoList> todoLists = new LinkedList<>();
        if(cursor.moveToFirst()) {

            do {

                int id = getInt(cursor, BaseColumns._ID);
                String name = getString(cursor, TodoListSQLiteHelper.COLUMN_TODO_LIST_NAME);
                int creationDate = getInt(cursor, TodoListSQLiteHelper.COLUMN_TODO_LIST_TIMESTAMP);

                TodoList todoList = new TodoList(id, name, creationDate);
                todoLists.add(todoList);

            }while(cursor.moveToNext());
        }

        cursor.close();

        return todoLists;
    }

    private int getInt(Cursor cursor, String columnName) {

        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getInt(columnIndex);
    }

    private String getString(Cursor cursor, String columnName) {

        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getString(columnIndex);
    }

    public void clear() {

        mDb.delete(TodoListSQLiteHelper.TABLE_TODO_LIST_ITEMS, null, null);
        mDb.delete(TodoListSQLiteHelper.TABLE_TODO_LISTS, null, null);
    }
}
