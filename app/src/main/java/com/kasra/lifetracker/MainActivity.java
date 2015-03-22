package com.kasra.lifetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.kasra.lifetracker.db.DatabaseManager;
import com.kasra.lifetracker.models.Task;
import com.kasra.lifetracker.utils.Constants;
import com.kasra.lifetracker.utils.RecyclerItemClickListener;
import com.kasra.lifetracker.widget.LTRecyclerView;

import java.util.List;

public class MainActivity extends ActionBarActivity {
    // UI
    private LTRecyclerView mRecyclerView;
    private TaskAdapter mAdapter;
    private FloatingActionButton mAddButton;

    // Logic
    private final static int REQUEST_CODE_ADD_TASK = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("All Tasks");

        mRecyclerView = (LTRecyclerView)findViewById(R.id.main_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Task> taskList = DatabaseManager.getInstance(this).getAllTasks();

        mAdapter = new TaskAdapter(taskList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setOnItemClickListener(onListItemClicked);

        mAddButton = (FloatingActionButton)findViewById(R.id.main_fab);
        mAddButton.setOnClickListener(onAddButtonClicked);
    }

    private View.OnClickListener onAddButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivityForResult(new Intent(MainActivity.this, AddTaskActivity.class), REQUEST_CODE_ADD_TASK);
        }
    };

    private RecyclerItemClickListener.OnItemClickListener onListItemClicked = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            // #TODO
        }
    };

    private void addTaskToList(long taskId ) {
        if (taskId == -1)
            return;

        Task t = DatabaseManager.getInstance(this).getTaskById(taskId);
        mAdapter.addTask(t);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ADD_TASK && resultCode == RESULT_OK && data != null) {
            addTaskToList(data.getLongExtra(Constants.TASK_ID, -1));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
        private List<Task> mTasks;

        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        public void addTask(Task task) {
            if (mTasks.add(task))
                notifyItemInserted(mTasks.size());
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_task, parent, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder vh, int position) {
            Task task = mTasks.get(position);

            vh.mIndicator.setBackgroundColor(task.color);
            vh.mTextView.setText(task.name);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public View mIndicator;
            public TextView mTextView;

            public ViewHolder(View v) {
                super(v);

                mIndicator = v.findViewById(R.id.row_task_indicator);
                mTextView = (TextView) v.findViewById(R.id.row_task_name);
            }
        }
    }
}
