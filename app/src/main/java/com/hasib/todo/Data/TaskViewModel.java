package com.hasib.todo.Data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hasib.todo.Model.Task;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private LiveData<List<Task>> tasks;
    private static Repository repository;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        tasks = repository.getTasks();
    }

    public LiveData<List<Task>> getTasks(){
        return repository.getTasks();
    }

    public static void insert(Task task){
        repository.insert(task);
    }

    public static void deleteAll(){
        repository.deleteAllTasks();
    }

    public static void delete(Task task){
        repository.deleteTask(task);
    }

    public static void updateTask(Task task){
        repository.updateTask(task);
    }



}
