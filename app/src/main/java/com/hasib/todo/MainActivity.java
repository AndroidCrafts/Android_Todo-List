package com.hasib.todo;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.hasib.todo.Data.TaskViewModel;
import com.hasib.todo.Fragments.BottomSheetFragment;
import com.hasib.todo.Model.Priority;
import com.hasib.todo.Model.Task;
import com.hasib.todo.Util.TaskClickListener;
import com.hasib.todo.Util.TaskRecyclerViewAdapter;
import com.hasib.todo.databinding.ActivityMainBinding;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskClickListener {

    private RecyclerView recyclerView;
    private TaskRecyclerViewAdapter taskAdapter;
    private TaskViewModel viewModel;
    private LiveData<List<Task>> tasks;
    private BottomSheetFragment bottomSheetFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.mainActivityToolbar);
        setSupportActionBar(toolbar);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this.getApplication()).create(TaskViewModel.class);
        this.tasks = viewModel.getTasks();
        recyclerView = findViewById(R.id.mainActivityRecyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        viewModel.getTasks().observe(this, tasks -> {
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
        Log.d(TAG, "taskClicked: " + task.getTask());
    }
}