package com.hasib.todo.Util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.hasib.todo.Model.Task;
import com.hasib.todo.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {
    private final List<Task> tasks;
    private final TaskClickListener taskClickListener;

    public TaskRecyclerViewAdapter(List<Task> tasks, TaskClickListener taskClickListener) {
        this.tasks = tasks;
        this.taskClickListener = taskClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.getTextView().setText(task.getTask());
//        String dateFormat = DateFormat.getDateInstance().format(task.getDueDate());
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        simpleDateFormat.applyPattern("EEE, MMM d");
        holder.getChip().setText(simpleDateFormat.format(task.getDueDate()));
    }


    @Override
    public int getItemCount() {
        return tasks.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textView;
        private final Chip chip;
        private final TaskClickListener taskClick;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             textView = itemView.findViewById(R.id.task_id);
             chip = itemView.findViewById(R.id.chip_id);
             taskClick = taskClickListener;
             itemView.setOnClickListener(this);
        }

        public Chip getChip() {
            return chip;
        }

        public TextView getTextView() {
            return textView;
        }

        @Override
        public void onClick(View v) {
            Task currentTask = tasks.get(getAdapterPosition());
            if(v.getId() == R.id.task_row_layout){
                taskClick.taskClicked(getAdapterPosition(), currentTask);
            }
        }
    }
}
