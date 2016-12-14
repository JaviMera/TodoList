package todo.javier.mera.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import todo.javier.mera.todolist.model.TaskPriority;
import todo.javier.mera.todolist.model.TodoList;
import todo.javier.mera.todolist.model.Task;
import todo.javier.mera.todolist.model.TaskStatus;

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

    private SQLiteDatabase openWriteable() {

        return mSqliteHelper.getWritableDatabase();
    }

    private SQLiteDatabase openReadable() {

        return mSqliteHelper.getReadableDatabase();
    }

    private void close(SQLiteDatabase db) {

        db.close();
    }

    public long createTodoList(TodoList newList) {

        mDb = openWriteable();
        mDb.beginTransaction();

        ContentValues todoListValues = new ContentValues();
        todoListValues.put(TodoListSQLiteHelper.COLUMN_TODO_LIST_ID, newList.getId());
        todoListValues.put(TodoListSQLiteHelper.COLUMN_TODO_LIST_POSITION, newList.getPosition());
        todoListValues.put(TodoListSQLiteHelper.COLUMN_TODO_LIST_NAME, newList.getTitle());
        todoListValues.put(TodoListSQLiteHelper.COLUMN_CREATION_DATE, newList.getCreationDate());

        long newId = mDb.insert(TodoListSQLiteHelper.TABLE_TODO_LISTS, null, todoListValues);

        mDb.setTransactionSuccessful();
        mDb.endTransaction();
        close(mDb);

        return newId;
    }

    public List<TodoList> readTodoLists() {

        mDb = openReadable();

        Cursor cursor = mDb.query(

            TodoListSQLiteHelper.TABLE_TODO_LISTS,
            new String[] {
                TodoListSQLiteHelper.COLUMN_TODO_LIST_ID,
                TodoListSQLiteHelper.COLUMN_TODO_LIST_POSITION,
                TodoListSQLiteHelper.COLUMN_TODO_LIST_NAME,
                TodoListSQLiteHelper.COLUMN_CREATION_DATE},
            null,
            null,
            null,
            null,
            TodoListSQLiteHelper.COLUMN_TODO_LIST_POSITION + " ASC"
        );

        List<TodoList> todoLists = new LinkedList<>();
        if(cursor.moveToFirst()) {

            do {

                String id = getString(cursor, TodoListSQLiteHelper.COLUMN_TODO_LIST_ID);
                int position = getInt(cursor, TodoListSQLiteHelper.COLUMN_TODO_LIST_POSITION);
                String name = getString(cursor, TodoListSQLiteHelper.COLUMN_TODO_LIST_NAME);
                long creationDate = getLong(cursor, TodoListSQLiteHelper.COLUMN_CREATION_DATE);

                TodoList todoList = new TodoList(id, name, creationDate, position);

                List<Task> tasks = readTodoListTasks(id);
                todoList.setItems(tasks);
                todoLists.add(todoList);

            }while(cursor.moveToNext());
        }

        cursor.close();
        close(mDb);

        return todoLists;
    }

    public long createTodoListTask(Task newTask) {

        mDb = openWriteable();
        mDb.beginTransaction();

        ContentValues itemValues = new ContentValues();
        itemValues.put(TodoListSQLiteHelper.COLUMN_ITEMS_ID, newTask.getId());
        itemValues.put(TodoListSQLiteHelper.COLUMN_TODO_LIST_ID, newTask.getTodoListId());
        itemValues.put(TodoListSQLiteHelper.COLUMN_ITEMS_POSITION, newTask.getPosition());
        itemValues.put(TodoListSQLiteHelper.COLUMN_ITEMS_DESCRIPTION, newTask.getDescription());
        itemValues.put(TodoListSQLiteHelper.COLUMN_ITEMS_STATUS, newTask.getStatus().ordinal());
        itemValues.put(TodoListSQLiteHelper.COLUMN_ITEMS_CREATED_ON, newTask.getCreationDate());
        itemValues.put(TodoListSQLiteHelper.COLUMN_ITEMS_DUE_DATE, newTask.getDueDate());
        itemValues.put(TodoListSQLiteHelper.COLUMN_ITEMS_PRIORITY, newTask.getPriority().ordinal());

        long newId = mDb.insert(TodoListSQLiteHelper.TABLE_TODO_LIST_ITEMS, null, itemValues);

        mDb.setTransactionSuccessful();
        mDb.endTransaction();
        close(mDb);

        return newId;
    }

    public List<Task> readTodoListTasks(String todoListId) {

        mDb = openReadable();
        List<Task> items = new LinkedList<>();

        Cursor cursor = mDb.query(
            TodoListSQLiteHelper.TABLE_TODO_LIST_ITEMS,
            new String[]{
                TodoListSQLiteHelper.COLUMN_ITEMS_ID,
                TodoListSQLiteHelper.COLUMN_TODO_LIST_ID,
                TodoListSQLiteHelper.COLUMN_ITEMS_POSITION,
                TodoListSQLiteHelper.COLUMN_ITEMS_DESCRIPTION,
                TodoListSQLiteHelper.COLUMN_ITEMS_STATUS,
                TodoListSQLiteHelper.COLUMN_ITEMS_CREATED_ON,
                TodoListSQLiteHelper.COLUMN_ITEMS_DUE_DATE,
                TodoListSQLiteHelper.COLUMN_ITEMS_PRIORITY
                },
            TodoListSQLiteHelper.COLUMN_TODO_LIST_ID + "=?",
            new String[]{String.valueOf(todoListId)},
            null,
            null,
            TodoListSQLiteHelper.COLUMN_ITEMS_POSITION + " ASC"
        );

        if(cursor.moveToFirst()) {

            do {

                String itemId = getString(cursor, TodoListSQLiteHelper.COLUMN_ITEMS_ID);
                String id = getString(cursor, TodoListSQLiteHelper.COLUMN_TODO_LIST_ID);
                int position = getInt(cursor, TodoListSQLiteHelper.COLUMN_ITEMS_POSITION);
                String description = getString(cursor, TodoListSQLiteHelper.COLUMN_ITEMS_DESCRIPTION);
                TaskStatus status = TaskStatus.values()[
                    getInt(cursor, TodoListSQLiteHelper.COLUMN_ITEMS_STATUS)];

                long creationDate = getLong(cursor, TodoListSQLiteHelper.COLUMN_ITEMS_CREATED_ON);
                long dueDate = getLong(cursor, TodoListSQLiteHelper.COLUMN_ITEMS_DUE_DATE);
                TaskPriority priority = TaskPriority.values()[getInt(cursor, TodoListSQLiteHelper.COLUMN_ITEMS_PRIORITY)];

                Task item = new Task(itemId, id, position, description, status, creationDate, dueDate, priority);
                items.add(item);
            }while(cursor.moveToNext());
        }

        cursor.close();
        close(mDb);

        return items;
    }

    public int removeTodoLists(TodoList... lists) {

        int rowsAffected = 0;
        for(TodoList tl : lists) {

            List<Task> tasks = readTodoListTasks(tl.getId());
            removeTodoListTasks(tasks.toArray(new Task[tasks.size()]));

            mDb = openWriteable();
            rowsAffected += mDb.delete(
                TodoListSQLiteHelper.TABLE_TODO_LISTS,
                TodoListSQLiteHelper.COLUMN_TODO_LIST_ID + "=?",
                new String []{String.valueOf(tl.getId())}
            );
        }

        close(mDb);

        return rowsAffected;
    }

    public int removeTodoListTasks(Task... tasks) {

        mDb = openWriteable();
        int rowsAffected = 0;

        for(Task task : tasks) {

            rowsAffected += mDb.delete(
                TodoListSQLiteHelper.TABLE_TODO_LIST_ITEMS,
                TodoListSQLiteHelper.COLUMN_ITEMS_ID + "=?",
                new String[]{task.getId()}
            );
        }

        close(mDb);
        return rowsAffected;
    }

    public int update(String tableName, String columnName, String id, ContentValues values){

        mDb = openWriteable();

        int affectedRows = mDb.update(
            tableName,
            values,
            columnName + "=?",
            new String[]{id}
        );

        close(mDb);

        return affectedRows;
    }

    public void clear() {

        mDb = openWriteable();
        mDb.delete(TodoListSQLiteHelper.TABLE_TODO_LIST_ITEMS, null, null);
        mDb.delete(TodoListSQLiteHelper.TABLE_TODO_LISTS, null, null);

        close(mDb);
    }

    private int getInt(Cursor cursor, String columnName) {

        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getInt(columnIndex);
    }

    private String getString(Cursor cursor, String columnName) {

        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getString(columnIndex);
    }

    private long getLong(Cursor cursor, String columnName) {

        int columnIndex = cursor.getColumnIndex(columnName);
        return cursor.getLong(columnIndex);
    }

    public List<Task> readTodoListTasks(String todoListId, String sortByColumn) {
        mDb = openReadable();
        List<Task> items = new LinkedList<>();

        Cursor cursor = mDb.query(
                TodoListSQLiteHelper.TABLE_TODO_LIST_ITEMS,
                new String[]{
                        TodoListSQLiteHelper.COLUMN_ITEMS_ID,
                        TodoListSQLiteHelper.COLUMN_TODO_LIST_ID,
                        TodoListSQLiteHelper.COLUMN_ITEMS_POSITION,
                        TodoListSQLiteHelper.COLUMN_ITEMS_DESCRIPTION,
                        TodoListSQLiteHelper.COLUMN_ITEMS_STATUS,
                        TodoListSQLiteHelper.COLUMN_ITEMS_CREATED_ON,
                        TodoListSQLiteHelper.COLUMN_ITEMS_DUE_DATE,
                        TodoListSQLiteHelper.COLUMN_ITEMS_PRIORITY
                },
                TodoListSQLiteHelper.COLUMN_TODO_LIST_ID + "=?",
                new String[]{String.valueOf(todoListId)},
                null,
                null,
                sortByColumn + " ASC"
        );

        if(cursor.moveToFirst()) {

            do {

                String itemId = getString(cursor, TodoListSQLiteHelper.COLUMN_ITEMS_ID);
                String id = getString(cursor, TodoListSQLiteHelper.COLUMN_TODO_LIST_ID);
                int position = getInt(cursor, TodoListSQLiteHelper.COLUMN_ITEMS_POSITION);
                String description = getString(cursor, TodoListSQLiteHelper.COLUMN_ITEMS_DESCRIPTION);
                TaskStatus status = TaskStatus.values()[
                        getInt(cursor, TodoListSQLiteHelper.COLUMN_ITEMS_STATUS)];

                long creationDate = getLong(cursor, TodoListSQLiteHelper.COLUMN_ITEMS_CREATED_ON);
                long dueDate = getLong(cursor, TodoListSQLiteHelper.COLUMN_ITEMS_DUE_DATE);
                TaskPriority priority = TaskPriority.values()[getInt(cursor, TodoListSQLiteHelper.COLUMN_ITEMS_PRIORITY)];

                Task item = new Task(itemId, id, position, description, status, creationDate, dueDate, priority);
                items.add(item);
            }while(cursor.moveToNext());
        }

        cursor.close();
        close(mDb);

        return items;
    }
}