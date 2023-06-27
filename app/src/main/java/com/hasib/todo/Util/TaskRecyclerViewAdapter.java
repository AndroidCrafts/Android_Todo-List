package com.hasib.todo.Util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    public TaskRecyclerViewAdapter(List<Task> tasks) {
        this.tasks = tasks;
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final Chip chip;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             textView = itemView.findViewById(R.id.task_id);
             chip = itemView.findViewById(R.id.chip_id);
        }

        public Chip getChip() {
            return chip;
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
