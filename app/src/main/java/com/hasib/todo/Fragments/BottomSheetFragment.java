package com.hasib.todo.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.hasib.todo.Data.MyViewModel;
import com.hasib.todo.Data.TaskViewModel;
import com.hasib.todo.Model.Priority;
import com.hasib.todo.Model.Task;
import com.hasib.todo.R;
import com.hasib.todo.Util.Sugar;
import com.hasib.todo.databinding.FragmentBottomSheetBinding;

import java.util.Calendar;
import java.util.Date;


public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private FragmentBottomSheetBinding binding;
    private Date dueDate;
    private Calendar calendar = Calendar.getInstance();
    private Context context;
    private MyViewModel myViewModel;
    private Priority priority;
    private int selectedPriorityId;
    private RadioButton selectedPriority;
    public BottomSheetFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.context = inflater.getContext();
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Get date from calendar View
        binding.calendarViewId.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                calendar.clear();
                calendar.set(year, month, dayOfMonth);
                dueDate = calendar.getTime();
            }
        });

        // Calendar btn handle
        binding.calendarBtnId.setOnClickListener(this);
        //Save btn handle
        binding.saveBtnId.setOnClickListener(this);
        //today chip
        binding.todayDateId.setOnClickListener(this);
        // Tomorrow chip
        binding.tomorrowDateId.setOnClickListener(this);
        //Next Week chip
        binding.nextWeekDateId.setOnClickListener(this);
        //Priority btn
        binding.priorityBtnId.setOnClickListener(this);


//        Get Task from selected task
        myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
    }

    // Get Task from selected task
    @Override
    public void onResume() {
        super.onResume();
        Task task = myViewModel.getMutableTask().getValue();
        if(task != null){
            binding.taskDetailId.setText(task.getTask());
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        // Calendar Btn
        if(id == binding.calendarBtnId.getId()){
            Sugar.hideKeyboard(v);
            binding.calendarGroupId.setVisibility(
                    binding.calendarGroupId.getVisibility() == Group.GONE ? Group.VISIBLE : Group.GONE
            );
        }

        // Save Btn
        else if(id == binding.saveBtnId.getId() && dueDate != null && priority != null){
            String text = binding.taskDetailId.getText().toString().trim();
            if(!TextUtils.isEmpty(text)){
                Task task = new Task(text, priority, dueDate, Calendar.getInstance().getTime(), false);
                //TODO: Update the task
                if(myViewModel.getEditMode()){
                    Task newTask = myViewModel.getMutableTask().getValue();
                    assert newTask != null;
                    newTask.setTask(text);
                    newTask.setPriority(priority);
                    newTask.setDateCreated(Calendar.getInstance().getTime());
                    newTask.setDueDate(Calendar.getInstance().getTime());
                    TaskViewModel.updateTask(newTask);
                    myViewModel.setEditMode(false);
                    Snackbar.make(v, "Updated", Snackbar.LENGTH_SHORT).show();
                    Sugar.hideKeyboard(v);
                    this.dismiss();
                }else {
                    TaskViewModel.insert(task);
                    calendar = Calendar.getInstance();
                    dueDate = calendar.getTime();
                    Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
                    Sugar.hideKeyboard(v);
                    this.dismiss();
                }

            }else {
                binding.taskDetailId.setError(getString(R.string.text_field_error_message));
            }
        }

        // Today Chip date set
        else if(id == binding.todayDateId.getId()) {
            calendar.add(Calendar.DAY_OF_YEAR, 0);
            dueDate = calendar.getTime();
        }

        // Tomorrow Chip date set
        else if(id == binding.tomorrowDateId.getId()){
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            dueDate = calendar.getTime();
        }

        // Next week chip date set
        else if(id == binding.nextWeekDateId.getId()){
            calendar.add(Calendar.DAY_OF_YEAR, 7);
            dueDate = calendar.getTime();
        }

        //Priority Radio group
        else if(id == binding.priorityBtnId.getId()){
            binding.radioGroup2.setVisibility(
                    binding.radioGroup2.getVisibility() == View.GONE ? View.VISIBLE : View.GONE
            );

            binding.radioGroup2.setOnCheckedChangeListener((group, checkedId) -> {
                if(binding.radioGroup2.getVisibility() == View.VISIBLE){
                    selectedPriorityId = checkedId;
                    selectedPriority = group.findViewById(selectedPriorityId);
                    if(selectedPriority.getId() == R.id.high_priority_id){
                        priority = Priority.HIGH;
                    }else if(selectedPriority.getId() == R.id.medium_priority_id){
                        priority = Priority.MEDIUM;
                    }else if(selectedPriority.getId() == R.id.low_priority_id){
                        priority = Priority.LOW;
                    }else {
                        priority = Priority.LOW;
                    }
                }else {
                    priority = Priority.LOW;
                }
            });
        }

    }
}