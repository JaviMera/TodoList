package todo.javier.mera.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import todo.javier.mera.todolist.model.Reminder;
import todo.javier.mera.todolist.model.Task;
import todo.javier.mera.todolist.model.Priority;
import todo.javier.mera.todolist.model.TaskStatus;
import todo.javier.mera.todolist.model.TodoList;

/**
 * Created by javie on 12/4/2016.
 */

public class TodoListDataSource {

    private TodoListSQLiteHelper mSqliteHelper;
    private SQLiteDatabase mDb;

    public TodoListDataSource(Context context) {

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

    public long createTodoList(TodoList newList, int position) {

        mDb = openWriteable();
        mDb.beginTransaction();

        ContentValues todoListValues = new ContentValues();
        todoListValues.put(TodoListSQLiteHelper.COLUMN_TODO_LIST_ID, newList.getId());
        todoListValues.put(TodoListSQLiteHelper.COLUMN_TODO_LIST_POSITION, position);
        todoListValues.put(TodoListSQLiteHelper.COLUMN_TODO_LIST_DESCRIPTION, newList.getDescription());
        todoListValues.put(TodoListSQLiteHelper.COLUMN_TODO_LIST_DUE_DATE, newList.getDueDate());
        todoListValues.put(TodoListSQLiteHelper.COLUMN_TODO_LIST_PRIORITY, newList.getPriority().ordinal());

        long newId = mDb.insert(TodoListSQLiteHelper.TABLE_TODO_LISTS, null, todoListValues);

        mDb.setTransactionSuccessful();
        mDb.endTransaction();
        close(mDb);

        return newId;
    }

    public List<TodoList> readTodoLists() {

        mDb = openReadable();

        Cursor cursor = getCursor(
            mDb,
            TodoListSQLiteHelper.TABLE_TODO_LISTS,
            getTodoListColumns(),
            null,
            null,
            TodoListSQLiteHelper.COLUMN_TODO_LIST_POSITION + " ASC"
        );

        List<TodoList> todoLists = new LinkedList<>();
        if(cursor.moveToFirst()) {

            do {

                TodoList todoList = createTodoList(cursor);
                List<Task> tasks = readTask(todoList.getId());
                todoList.setTaskNumber(tasks.size());
                todoLists.add(todoList);

            }while(cursor.moveToNext());
        }

        cursor.close();
        close(mDb);

        return todoLists;
    }

    public TodoList readTodoList(String listId) {

        mDb = openReadable();

        Cursor cursor = getCursor(mDb,
            TodoListSQLiteHelper.TABLE_TODO_LISTS,
            getTodoListColumns(),
            TodoListSQLiteHelper.COLUMN_TODO_LIST_ID + "=?",
            new String[]{String.valueOf(listId)},
            null
        );

        TodoList todoList = null;
        if(cursor.moveToFirst()) {

            todoList = createTodoList(cursor);
            List<Task> tasks = readTask(todoList.getId());
            todoList.setTaskNumber(tasks.size());
        }

        cursor.close();
        close(mDb);

        return todoList;
    }

    public List<TodoList> readTodoLists(String sortByColumn, String order) {

        mDb = openReadable();

        Cursor cursor = getCursor(
            mDb,
            TodoListSQLiteHelper.TABLE_TODO_LISTS,
            getTodoListColumns(),
            null,
            null,
            sortByColumn + " " + order
        );

        List<TodoList> todoLists = new LinkedList<>();
        if(cursor.moveToFirst()) {

            do {
                TodoList todoList = createTodoList(cursor);
                List<Task> tasks = readTask(todoList.getId());
                todoList.setTaskNumber(tasks.size());
                todoLists.add(todoList);

            }while(cursor.moveToNext());
        }

        cursor.close();
        close(mDb);

        return todoLists;
    }

    private TodoList createTodoList(Cursor cursor) {

        String id = getString(cursor, TodoListSQLiteHelper.COLUMN_TODO_LIST_ID);
        String name = getString(cursor, TodoListSQLiteHelper.COLUMN_TODO_LIST_DESCRIPTION);
        long dueDate = getLong(cursor, TodoListSQLiteHelper.COLUMN_TODO_LIST_DUE_DATE);
        Priority priority = Priority.values()[getInt(cursor, TodoListSQLiteHelper.COLUMN_TODO_LIST_PRIORITY)];

        return new TodoList(id, name, dueDate, priority);
    }

    public long createTask(Task newTask, int position) {

        mDb = openWriteable();
        mDb.beginTransaction();

        ContentValues itemValues = new ContentValues();
        itemValues.put(TodoListSQLiteHelper.COLUMN_ITEMS_ID, newTask.getId());
        itemValues.put(TodoListSQLiteHelper.COLUMN_TODO_LIST_ID, newTask.getTodoListId());
        itemValues.put(TodoListSQLiteHelper.COLUMN_ITEMS_POSITION, position);
        itemValues.put(TodoListSQLiteHelper.COLUMN_ITEMS_DESCRIPTION, newTask.getDescription());
        itemValues.put(TodoListSQLiteHelper.COLUMN_ITEMS_STATUS, newTask.getStatus().ordinal());
        itemValues.put(TodoListSQLiteHelper.COLUMN_ITEMS_CREATED_ON, newTask.getCreationDate());
        itemValues.put(TodoListSQLiteHelper.COLUMN_ITEMS_DUE_DATE, newTask.getDueDate());
        itemValues.put(TodoListSQLiteHelper.COLUMN_ITEMS_DUE_TIME, newTask.getDueDate());
        itemValues.put(TodoListSQLiteHelper.COLUMN_ITEMS_PRIORITY, newTask.getPriority().ordinal());
        itemValues.put(TodoListSQLiteHelper.COLUMN_ITEMS_REMINDER, newTask.getReminder());

        long newId = mDb.insert(TodoListSQLiteHelper.TABLE_TASKS, null, itemValues);

        mDb.setTransactionSuccessful();
        mDb.endTransaction();
        close(mDb);

        return newId;
    }

    public List<Task> readTask(String todoListId) {

        mDb = openReadable();
        List<Task> items = new LinkedList<>();

        Cursor cursor = getCursor(
            mDb,
            TodoListSQLiteHelper.TABLE_TASKS,
            getTaskColumns(),
            TodoListSQLiteHelper.COLUMN_TODO_LIST_ID + "=?",
            new String[]{String.valueOf(todoListId)},
            TodoListSQLiteHelper.COLUMN_ITEMS_POSITION + " ASC"
        );

        if(cursor.moveToFirst()) {

            do {

                Task item = createTask(cursor);
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

            List<Task> tasks = readTask(tl.getId());
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
                TodoListSQLiteHelper.TABLE_TASKS,
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
        mDb.delete(TodoListSQLiteHelper.TABLE_TASKS, null, null);
        mDb.delete(TodoListSQLiteHelper.TABLE_TODO_LISTS, null, null);

        close(mDb);
    }

    public List<Task> readTask(String todoListId, String sortByColumn, String order) {
        mDb = openReadable();

        List<Task> items = new LinkedList<>();
        Cursor cursor = getCursor(
            mDb,
            TodoListSQLiteHelper.TABLE_TASKS,
            getTaskColumns(),
            TodoListSQLiteHelper.COLUMN_TODO_LIST_ID + "=?",
            new String[]{String.valueOf(todoListId)},
            sortByColumn + " " + order
        );

        if(cursor.moveToFirst()) {

            do {

                Task item = createTask(cursor);
                items.add(item);

            }while(cursor.moveToNext());
        }

        cursor.close();
        close(mDb);

        return items;
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

    private String[] getTodoListColumns() {

        return new String[] {

            TodoListSQLiteHelper.COLUMN_TODO_LIST_ID,
            TodoListSQLiteHelper.COLUMN_TODO_LIST_POSITION,
            TodoListSQLiteHelper.COLUMN_TODO_LIST_DESCRIPTION,
            TodoListSQLiteHelper.COLUMN_TODO_LIST_DUE_DATE,
            TodoListSQLiteHelper.COLUMN_TODO_LIST_PRIORITY
        };
    }

    private String[] getTaskColumns() {

        return new String[]{

            TodoListSQLiteHelper.COLUMN_ITEMS_ID,
            TodoListSQLiteHelper.COLUMN_TODO_LIST_ID,
            TodoListSQLiteHelper.COLUMN_ITEMS_POSITION,
            TodoListSQLiteHelper.COLUMN_ITEMS_DESCRIPTION,
            TodoListSQLiteHelper.COLUMN_ITEMS_STATUS,
            TodoListSQLiteHelper.COLUMN_ITEMS_CREATED_ON,
            TodoListSQLiteHelper.COLUMN_ITEMS_DUE_DATE,
            TodoListSQLiteHelper.COLUMN_ITEMS_DUE_TIME,
            TodoListSQLiteHelper.COLUMN_ITEMS_PRIORITY,
            TodoListSQLiteHelper.COLUMN_ITEMS_REMINDER
        };
    }

    private Cursor getCursor(
        SQLiteDatabase db,
        String table,
        String[] columns,
        String select,
        String[] selectArgs,
        String sortBy) {

        return db.query(
            table,
            columns,
            select,
            selectArgs,
            null,
            null,
            sortBy
        );
    }

    private Task createTask(Cursor cursor) {

        String itemId = getString(cursor, TodoListSQLiteHelper.COLUMN_ITEMS_ID);
        String id = getString(cursor, TodoListSQLiteHelper.COLUMN_TODO_LIST_ID);
        String description = getString(cursor, TodoListSQLiteHelper.COLUMN_ITEMS_DESCRIPTION);
        TaskStatus status = TaskStatus.values()[
                getInt(cursor, TodoListSQLiteHelper.COLUMN_ITEMS_STATUS)];

        long creationDate = getLong(cursor, TodoListSQLiteHelper.COLUMN_ITEMS_CREATED_ON);
        long dueDate = getLong(cursor, TodoListSQLiteHelper.COLUMN_ITEMS_DUE_DATE);

        Priority priority = Priority.values()[getInt(cursor, TodoListSQLiteHelper.COLUMN_ITEMS_PRIORITY)];
        long reminder = getLong(cursor, TodoListSQLiteHelper.COLUMN_ITEMS_REMINDER);

        return new Task(
            itemId,
            id,
            description,
            status,
            creationDate,
            dueDate,
            priority,
            reminder
        );
    }
}
