package com.hasib.todo.Fragments;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.opengl.Visibility;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.hasib.todo.Data.TaskViewModel;
import com.hasib.todo.Model.Priority;
import com.hasib.todo.Model.Task;
import com.hasib.todo.R;
import com.hasib.todo.databinding.ActivityMainBinding;
import com.hasib.todo.databinding.FragmentBottomSheetBinding;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.Inflater;


public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private FragmentBottomSheetBinding binding;
    private Date dueDate;
    private Calendar calendar = Calendar.getInstance();
    private Context context;
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
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        // Calendar Btn
        if(id == binding.calendarBtnId.getId()){
            binding.calendarGroupId.setVisibility(
                    binding.calendarGroupId.getVisibility() == Group.GONE ? Group.VISIBLE : Group.GONE
            );
        }

        // Save Btn
        else if(id == binding.saveBtnId.getId() && dueDate != null){
            String text = binding.taskDetailId.getText().toString().trim();
            if(!TextUtils.isEmpty(text)){
                Task task = new Task(text, Priority.HIGH, dueDate, Calendar.getInstance().getTime(), false);
                TaskViewModel.insert(task);
                calendar = Calendar.getInstance();
                dueDate = calendar.getTime();
                Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
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





    }
}