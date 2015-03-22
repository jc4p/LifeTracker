package com.kasra.lifetracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.kasra.lifetracker.db.DatabaseManager;
import com.kasra.lifetracker.utils.Constants;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AddTaskActivity extends ActionBarActivity {
    // UI
    @InjectView(R.id.add_task_name) EditText mEditTextTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_task);
        ButterKnife.inject(this);
    }

    private void createTask() {
        String name = mEditTextTitle.getText().toString().trim();
        int color = 0xFF3F51B5;
        double[] geoFence = null;

        long taskId = DatabaseManager.getInstance(this).createTask(name, color, geoFence);
        Intent data = new Intent();
        data.putExtra(Constants.TASK_ID, taskId);

        setResult(Activity.RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_task, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_add_task_create:
                createTask();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
