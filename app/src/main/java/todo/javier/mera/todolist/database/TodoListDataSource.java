package todo.javier.mera.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.LinkedList;
import java.util.List;

import todo.javier.mera.todolist.adapters.TodolistViewHolder;
import todo.javier.mera.todolist.model.TodoList;
import todo.javier.mera.todolist.model.TodoListItem;
import todo.javier.mera.todolist.model.TodoListStatus;

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

    public long createTodoList(String todoListTitle, long creationDate) {

        mDb.beginTransaction();

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

    public long createTodoListItem(long id, String description, TodoListStatus status, long timeStamp) {

        mDb.beginTransaction();

        ContentValues itemValues = new ContentValues();
        itemValues.put(TodoListSQLiteHelper.COLUMN_ITEMS_FOREIGN_KEY, id);
        itemValues.put(TodoListSQLiteHelper.COLUMN_ITEMS_DESCRIPTION, description);
        itemValues.put(TodoListSQLiteHelper.COLUMN_ITEMS_COMPLETED, status.ordinal());
        itemValues.put(TodoListSQLiteHelper.COLUMN_ITEMS_TIMESTAMP, timeStamp);

        long newId = mDb.insert(TodoListSQLiteHelper.TABLE_TODO_LIST_ITEMS, null, itemValues);

        mDb.setTransactionSuccessful();
        mDb.endTransaction();
        return newId;
    }

    public List<TodoListItem> readAllTodoListItems(long todoListId) {

        List<TodoListItem> items = new LinkedList<>();

        Cursor cursor = mDb.query(
            TodoListSQLiteHelper.TABLE_TODO_LIST_ITEMS,
            new String[]{
                BaseColumns._ID,
                TodoListSQLiteHelper.COLUMN_ITEMS_FOREIGN_KEY,
                TodoListSQLiteHelper.COLUMN_ITEMS_DESCRIPTION,
                TodoListSQLiteHelper.COLUMN_ITEMS_COMPLETED,
                TodoListSQLiteHelper.COLUMN_ITEMS_TIMESTAMP
                },
            TodoListSQLiteHelper.COLUMN_ITEMS_FOREIGN_KEY + "=?",
            new String[]{String.valueOf(todoListId)},
            null,
            null,
            null
        );

        if(cursor.moveToFirst()) {

            do {

                int id = getInt(cursor, BaseColumns._ID);
                int itemId = getInt(cursor, TodoListSQLiteHelper.COLUMN_ITEMS_FOREIGN_KEY);
                String description = getString(cursor, TodoListSQLiteHelper.COLUMN_ITEMS_DESCRIPTION);
                TodoListStatus status = TodoListStatus.values()[
                    getInt(cursor, TodoListSQLiteHelper.COLUMN_ITEMS_COMPLETED)];

                int timeStamp = getInt(cursor, TodoListSQLiteHelper.COLUMN_ITEMS_TIMESTAMP);

                TodoListItem item = new TodoListItem(id, itemId, description, status, timeStamp);
                items.add(item);
            }while(cursor.moveToNext());
        }
        return items;
    }

    private long getLong(Cursor cursor, String columnName) {

        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getLong(columnIndex);
    }
}
