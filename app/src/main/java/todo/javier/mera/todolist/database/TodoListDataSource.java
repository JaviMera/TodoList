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

    public TodoListDataSource(Context context) {

        mContext = context;
        mSqliteHelper = new TodoListSQLiteHelper(context);
    }

    public SQLiteDatabase openWriteable() {

        return mSqliteHelper.getWritableDatabase();
    }

    public SQLiteDatabase openReadable() {

        return mSqliteHelper.getReadableDatabase();
    }

    public void close(SQLiteDatabase database) {

        database.close();
    }

    public TodoList create(String todoListTitle, long creationDate) {

        SQLiteDatabase database = openWriteable();
        database.beginTransaction();

        TodoList newTodoList;
        ContentValues todoListValues = new ContentValues();
        todoListValues.put(TodoListSQLiteHelper.COLUMN_TODO_LIST_NAME, todoListTitle);
        todoListValues.put(TodoListSQLiteHelper.COLUMN_TODO_LIST_TIMESTAMP, creationDate);

        long id = database.insert(TodoListSQLiteHelper.TABLE_TODO_LISTS, null, todoListValues);
        newTodoList = new TodoList(id, todoListTitle, creationDate);

        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);

        return newTodoList;
    }

    public List<TodoList> readTodoLists() {

        SQLiteDatabase database = openReadable();

        Cursor cursor = database.query(

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
        close(database);

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
}
