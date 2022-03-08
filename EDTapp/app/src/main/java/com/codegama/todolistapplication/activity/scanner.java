package com.codegama.todolistapplication.activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.codegama.todolistapplication.R;
import com.codegama.todolistapplication.bottomSheetFragment.CreateTaskBottomSheetFragment;
import com.codegama.todolistapplication.database.DatabaseClient;
import com.codegama.todolistapplication.model.Task;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import butterknife.BindView;
import butterknife.Unbinder;

// implements onClickListener for the onclick behaviour of button
public class scanner extends AppCompatActivity implements View.OnClickListener {
     class inherit extends CreateTaskBottomSheetFragment {
         Unbinder unbinder;
         @BindView(R.id.addTaskTitle)
         EditText addTaskTitle;
         @BindView(R.id.addTaskDescription)
         EditText addTaskDescription;
         @BindView(R.id.taskDate)
         EditText taskDate;
         @BindView(R.id.taskTime)
         EditText taskTime;
         @BindView(R.id.taskEvent)
         EditText taskEvent;
         @BindView(R.id.addTask)
         Button addTask;
         boolean isEdit;
         int taskId;
         setRefreshListener setRefreshListener;

         void createTask2() {
         class saveTaskInBackend extends AsyncTask<Void, Void, Void> {
             @SuppressLint("WrongThread")
             @Override
             protected Void doInBackground(Void... voids) {
                 Task createTask2 = new Task();
                 createTask2.setTaskTitle("weewe");
                 createTask2.setTaskDescrption("ererer");
                 createTask2.setDate("8-03-2022");
                 createTask2.setLastAlarm("23:57");
                 createTask2.setEvent("rre");

                 if (!isEdit)
                     DatabaseClient.getInstance(getActivity()).getAppDatabase()
                             .dataBaseAction()
                             .insertDataIntoTaskList(createTask2);
                 else
                     DatabaseClient.getInstance(getActivity()).getAppDatabase()
                             .dataBaseAction()
                             .updateAnExistingRow(taskId, addTaskTitle.getText().toString(),
                                     addTaskDescription.getText().toString(),
                                     taskDate.getText().toString(),
                                     taskTime.getText().toString(),
                                     taskEvent.getText().toString());

                 return null;
             }

             @Override
             protected void onPostExecute(Void aVoid) {
                 super.onPostExecute(aVoid);
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                     createAnAlarm();
                 }
                 setRefreshListener.refresh();
                 Toast.makeText(getActivity(), "Your event is been added", Toast.LENGTH_SHORT).show();
                 dismiss();

             }
         }
         saveTaskInBackend st = new saveTaskInBackend();
        st.execute();


     }}
    Button Scan;

    TextView messageText, messageFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);



        // referencing and initializing
        // the button and textviews
        Scan = findViewById(R.id.Scan);

      //  messageText = findViewById(R.id.textContent);
      //  messageFormat = findViewById(R.id.textFormat);

        // adding listener to the button
        Scan.setOnClickListener(this);

        findViewById(R.id.setReminder).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                inherit obj = new inherit();
                obj.createTask2();



            }
        });


        }





    @Override
    public void onClick(View v)  {
        // we need to create the object
        // of IntentIntegrator class
        // which is the class of QR library
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setPrompt("Scan a barcode or QR Code");
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.initiateScan();

    //   inherit obj = new inherit();
      // obj.createTask2();


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                // if the intentResult is not null we'll set
                // the content and format of scan message
                messageText.setText(intentResult.getContents());
                messageFormat.setText(intentResult.getFormatName());

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}