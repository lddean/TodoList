package com.example.lddean.tab_to_do_list;

/**
 * Created by lddean on 15-3-20.
 */

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.example.lddean.tab_to_do_list.TaskContract;
import com.example.lddean.tab_to_do_list.TaskDBHelper;

public class todolistActivity extends ListActivity {
    private ListAdapter listAdapter;
    private TaskDBHelper helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist);
        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Add a task");
                builder.setMessage("What do you want to do?");
                final EditText inputField = new EditText(this);
                builder.setView(inputField);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String task = inputField.getText().toString();

                        helper = new TaskDBHelper(todolistActivity.this);
                        SQLiteDatabase db = helper.getWritableDatabase();
                        ContentValues values = new ContentValues();

                        values.clear();
                        values.put(TaskContract.Columns.TASK,task);

                        db.insertWithOnConflict(TaskContract.TABLE,null,values,SQLiteDatabase.CONFLICT_IGNORE);
                        updateUI();
                    }
                });

                builder.setNegativeButton("Cancel",null);

                builder.create().show();
                return true;

            default:
                return false;
        }
    }

    private void updateUI() {
        helper = new TaskDBHelper(todolistActivity.this);
        SQLiteDatabase sqlDB = helper.getReadableDatabase();
        Cursor cursor = sqlDB.query(TaskContract.TABLE,
                new String[]{TaskContract.Columns._ID, TaskContract.Columns.TASK},
                null, null, null, null, null);

        listAdapter = new SimpleCursorAdapter(
                this,
                R.layout.task_view,
                cursor,
                new String[]{TaskContract.Columns.TASK},
                new int[]{R.id.taskTextView},
                0
        );

        this.setListAdapter(listAdapter);
    }

    public void onDoneButtonClick(View view) {
        View v = (View) view.getParent();
        TextView taskTextView = (TextView) v.findViewById(R.id.taskTextView);
        String task = taskTextView.getText().toString();

        String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
                TaskContract.TABLE,
                TaskContract.Columns.TASK,
                task);


        helper = new TaskDBHelper(todolistActivity.this);
        SQLiteDatabase sqlDB = helper.getWritableDatabase();
        sqlDB.execSQL(sql);
        updateUI();
    }
}