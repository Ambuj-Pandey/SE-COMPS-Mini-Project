package com.codegama.todolistapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.codegama.todolistapplication.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

// implements onClickListener for the onclick behaviour of button
public class scanner extends AppCompatActivity implements View.OnClickListener {
    Button Scan;
    EditText GPD;
    TextView messageText, messageFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        // referencing and initializing
        // the button and textviews
        Scan = findViewById(R.id.Scan);
        messageText = findViewById(R.id.getContents);
        messageFormat = findViewById(R.id.getFormatName);

        // adding listener to the button
        Scan.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // we need to create the object
        // of IntentIntegrator class
        // which is the class of QR library
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setPrompt("Scan a barcode or QR Code");
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.initiateScan();
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
        String Pid = messageText.getText().toString();
        switch (Pid)
        {
            case "696969":
                GPD = (EditText)findViewById(R.id.getContentsPD);
                GPD.setText("Masala");
                GPD = (EditText)findViewById(R.id.getContentsMD);
                GPD.setText("10-04-2021");
                GPD = (EditText)findViewById(R.id.getContentsED);
                GPD.setText("10-04-2022");
                break;

            case "8008":
                GPD = (EditText)findViewById(R.id.getContentsPD);
                GPD.setText("Milk");
                GPD = (EditText)findViewById(R.id.getContentsMD);
                GPD.setText("10-04-2020");
                GPD = (EditText)findViewById(R.id.getContentsED);
                GPD.setText("18-11-2022");


        }


    }
}