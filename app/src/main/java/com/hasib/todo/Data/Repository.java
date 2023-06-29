package com.hasib.todo.Data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.hasib.todo.Model.Task;
import com.hasib.todo.Util.TaskRoomDatabase;

import java.util.List;

public class Repository {
    private LiveData<List<Task>> tasks;
    private TaskDao dao;
    public Repository(Application application){
        TaskRoomDatabase db = TaskRoomDatabase.getDatabase(application);
        dao = db.taskDao();
        tasks = dao.getAllTasks();
    }

    public LiveData<List<Task>> getTasks(){
        return this.tasks;
    }

    public void insert(Task task){
        TaskRoomDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(task);
            }
        });
    }

    public void deleteAllTasks(){
        TaskRoomDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteAll();
            }
        });
    }

    public void deleteTask(Task task){
        TaskRoomDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(task);
            }
        });
    }

    public void updateTask(Task task){
        TaskRoomDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                dao.update(task);
            }
        });
    }
}
