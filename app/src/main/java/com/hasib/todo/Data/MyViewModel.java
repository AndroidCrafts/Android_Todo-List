package com.hasib.todo.Data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hasib.todo.Model.Task;

public class MyViewModel extends androidx.lifecycle.ViewModel {
    private final MutableLiveData<Task> mutableTask = new MutableLiveData<>();
    private Boolean editMode;
    public LiveData<Task> getMutableTask() {
        return mutableTask;
    }

    public void setMutableTask(Task mutableTask) {
        this.mutableTask.setValue(mutableTask);
    }

    public Boolean getEditMode() {
        return editMode;
    }

    public void setEditMode(Boolean editMode) {
        this.editMode = editMode;
    }
}
