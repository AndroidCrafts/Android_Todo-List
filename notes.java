1- Create the room database for Task

2- When Floating-A-B clicked add new Task to the database

3- Retrieve data -> observer

4- format the date and add it to the view

5- display bottomSheet fragment

6- save a todo from bottomSheet data

7- 
            - make visible and gone the hidden calendar section
            - get the dueDate from calendarView.
            --> TIP: calendarView.setOnDateChangeListener(...) --> get date from the calendarView
            --> TIP: Calender.getInstance().set(Year,month,day) --> to set the date to the selected calendarView date
            
8-
            - get dueDate from chips
            --> TIP: Calender.getInstance().add(Calendar.DAY_OF_YEAR, 0); -> set up today. if we give it 1 value it will give it tomorrow date and so on.
                
9-          set Listen to the clicks on  each todo row to get its                             proper position and task
            TIP: using an interface is a better way for this job.    
                
                
10-         When Radio button clicked delete the todo

11-         Share data between Activity and fragment -> in this case                          take the current Task and share it to the bottomsheet                              fragment

12         NOT_DONE --> when a task clicked enter new task and update it
13         NOT_DONE --> Hide keyboard and bottomsheet in specific states
14         NOT_DONE --> Visible priority group and make it dynamic
15         NOT_DONE --> Change task color state based on priority
                
