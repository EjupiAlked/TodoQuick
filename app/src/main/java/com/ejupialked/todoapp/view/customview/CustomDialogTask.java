package com.ejupialked.todoapp.view.customview;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.ejupialked.todoapp.R;
import com.ejupialked.todoapp.domain.model.Task;
import com.ejupialked.todoapp.view.activity.TasksActivity;
import com.ejupialked.todoapp.view.presenter.TasksPresenter;

import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomDialogTask extends AppCompatDialogFragment {

    @BindView(R.id.edit_taskdescription) EditText editDescription;
    @BindView(R.id.radiogroup_task) RadioGroup radioGroup;
    @BindView(R.id.date_button) ImageButton dateButton;
    @BindView(R.id.txt_date) TextView txtDate;
    @BindView(R.id.cancel_button) ImageButton buttonClear;
    @BindView(R.id.radio_high) RadioButton high;
    @BindView(R.id.radio_medium) RadioButton medium;
    @BindView(R.id.radio_low) RadioButton low;


    private RadioButton radioButton;
    private CustomDialogTask.CustomDialogListener listener;
    private Context context;


    private String title;
    private String positiveButton;

    private String taskDescription;
    private String taskPriority;
    private String taskDate;


    private Task t;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();

        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.layout_dialogtask, null);

        ButterKnife.bind(this, view);

        buttonClear.setOnClickListener(view1 -> txtDate.setText("Pick a date"));


        if(t != null){
            title = "Edit Task";
            positiveButton = "Edit";
            editDescription.setText(t.getDescription());
            txtDate.setText(t.getDate());

            if(t.getPriority().equalsIgnoreCase("high")){
                high.setSelected(true);

            }else if(t.getPriority().equalsIgnoreCase("medium")){
                medium.setSelected(true);

            }else if(t.getPriority().equalsIgnoreCase("low")){
                low.setSelected(true);
            }
        }else{
            title = "Create a task";
            positiveButton = "Create";
        }

        builder.setView(view)
                .setTitle(title)
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                })
                .setPositiveButton(positiveButton, (dialogInterface, i) -> {
                    if(t == null) {
                        int ID = radioGroup.getCheckedRadioButtonId();
                        radioButton = view.findViewById(ID);

                        taskDescription = editDescription.getText().toString();
                        taskPriority = radioButton.getText().toString();
                        taskDate = txtDate.getText().toString();

                        listener.createTask(taskDescription, taskPriority, taskDate);
                    }else{
                        int ID = radioGroup.getCheckedRadioButtonId();
                        radioButton = view.findViewById(ID);

                        taskDescription = editDescription.getText().toString();
                        taskPriority = radioButton.getText().toString();
                        taskDate = txtDate.getText().toString();

                        t.setDate(taskDate);
                        t.setDescription(taskDescription);
                        t.setPriority(taskPriority);

                        listener.editTask(t);
                    }
                });


        dateButton.setOnClickListener(view1 -> {
            chooseDate();
        });
        return builder.create();
    }



    private void chooseDate(){
        Calendar calendar = Calendar.getInstance();

        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (datePicker, i, i1, i2) -> {
            taskDate =  i + "/" + i2 + "/" + i2;
            chooseTime();

        }, YEAR, MONTH, DATE);
        datePickerDialog.show();
    }

    private void chooseTime() {
        Calendar calendar = Calendar.getInstance();

        int MINUTE = calendar.get(Calendar.MINUTE);
        int HOUR = calendar.get(Calendar.HOUR);

        TimePickerDialog timePickerDialog = new TimePickerDialog(context, (timePicker, i, i1) -> {
            String time = i+ ":" + i1;
            taskDate = taskDate + " " + time;
            txtDate.setText(taskDate);
        }, HOUR, MINUTE, true);

        timePickerDialog.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        try{
            listener = (CustomDialogTask.CustomDialogListener) context;
        }catch (ClassCastException c){
            throw new ClassCastException(context.toString() + "Must implement listener ");
        }
    }

    public void setTask(Task t) {
        this.t = t;
    }

    public interface CustomDialogListener{
        void createTask(String description, String priority, String date);
        void editTask(Task t);
    }
}
