package com.hasib.todo.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.hasib.todo.Model.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Task task);
    @Delete
    void delete(Task task);
    @Query("DELETE FROM Tasks")
    void deleteAll();
    @Query("SELECT * FROM tasks")
    LiveData<List<Task>> getAllTasks();
    @Update
    void update(Task task);
}
