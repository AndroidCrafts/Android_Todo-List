package com.hasib.todo;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.hasib.todo.Data.MyViewModel;
import com.hasib.todo.Data.TaskViewModel;
import com.hasib.todo.Fragments.BottomSheetFragment;
import com.hasib.todo.Model.Task;
import com.hasib.todo.Util.Sugar;
import com.hasib.todo.Util.TaskClickListener;
import com.hasib.todo.Util.TaskRecyclerViewAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskClickListener {

    private RecyclerView recyclerView;
    private TaskRecyclerViewAdapter taskAdapter;
    private TaskViewModel taskViewModel;
    private LiveData<List<Task>> tasks;
    private BottomSheetFragment bottomSheetFragment;

    private MyViewModel myViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.mainActivityToolbar);
        setSupportActionBar(toolbar);
        taskViewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this.getApplication()).create(TaskViewModel.class);
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        this.tasks = taskViewModel.getTasks();
        recyclerView = findViewById(R.id.mainActivityRecyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        taskViewModel.getTasks().observe(this, tasks -> {
            taskAdapter = new TaskRecyclerViewAdapter(tasks, this);
            recyclerView.setAdapter(taskAdapter);
        });

//        BottomSheet fragment hidden process
        bottomSheetFragment = new BottomSheetFragment();
        ConstraintLayout constraintLayout = findViewById(R.id.bottom_sheet_id);
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior = BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);
    }

    public void floatingBtnClicked(View view) {
//        BottomSheet Fragment display
        displayBottomSheetFragment();

    }

    private void displayBottomSheetFragment() {
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    @Override
    public void taskClicked(int id, Task task) {
        myViewModel.setMutableTask(task);
        myViewModel.setEditMode(true);
        displayBottomSheetFragment();
    }

    @Override
    public void currentTask(Task task) {
        Log.d(TAG, "currentTask: " + task.getTask());
        AlertDialog.Builder alert = Sugar.alertDialog("Confirm Deletion",
                "Delete " + task.getTask(), this);

        alert.setPositiveButton("Delete", (dialog, which) -> {
            TaskViewModel.delete(task);
            dialog.dismiss();
        });
        alert.setNegativeButton("Cancel", (dialog, which) -> {
           dialog.dismiss();
        });
        alert.show();
    }
}