package com.hasib.todo.Util;

import com.hasib.todo.Model.Task;

public interface TaskClickListener {
    void taskClicked(int id, Task task);
    void currentTask(Task task);
}
