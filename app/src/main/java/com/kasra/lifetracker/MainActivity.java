package com.kasra.lifetracker;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kasra.lifetracker.models.Task;
import com.kasra.lifetracker.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private RecyclerView mRecyclerView;
    private TaskAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("All Tasks");

        mRecyclerView = (RecyclerView)findViewById(R.id.main_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Let's make some fake tasks
        Task task = new Task("Took anti-anxiety pills", 0xFF03A9F4, null);
        Task task2 = new Task("Got to work", 0xFF4CAF50, new double[] { 40.712784, 74.0059413 });
        ArrayList<Task> taskList = new ArrayList<>();

        taskList.add(task);
        taskList.add(task2);

        mAdapter = new TaskAdapter(taskList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "Touched " + position, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
        private List<Task> mTasks;

        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
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
