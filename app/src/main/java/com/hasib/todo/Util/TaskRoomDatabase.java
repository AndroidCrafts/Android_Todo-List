package com.hasib.todo.Util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hasib.todo.Data.TaskDao;
import com.hasib.todo.Model.Priority;
import com.hasib.todo.Model.Task;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class TaskRoomDatabase extends RoomDatabase {
    public static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static TaskRoomDatabase INSTANCE;

    public static TaskRoomDatabase getDatabase(Context context){
        if(INSTANCE == null){
            synchronized (TaskRoomDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TaskRoomDatabase.class, "Task Database").addCallback(callback).build();
            }
        }
        return INSTANCE;
    }

    public static final RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            executor.execute(() -> {
                TaskDao dao = INSTANCE.taskDao();
                dao.deleteAll();

                Task task = new Task("Android dev", Priority.HIGH, Calendar.getInstance().getTime(),
                        Calendar.getInstance().getTime(), false);
                dao.insert(task);
            });



        }
    };

    public abstract TaskDao taskDao();

}
