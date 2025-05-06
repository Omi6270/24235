package com.example.expt09_42441;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    private EditText dataInput, fileNameInput;
    private RadioButton internalStorageRadio, externalStorageRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataInput = findViewById(R.id.dataInput);
        fileNameInput = findViewById(R.id.fileNameInput);
        internalStorageRadio = findViewById(R.id.internalStorageRadio);
        externalStorageRadio = findViewById(R.id.externalStorageRadio);
    }

    public void saveData(View view) {
        String data = dataInput.getText().toString();
        String fileName = fileNameInput.getText().toString();

        if (data.isEmpty() || fileName.isEmpty()) {
            Toast.makeText(this, "Please enter data and filename", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            if (internalStorageRadio.isChecked()) {
                saveInternalStorage(fileName, data);
            } else if (externalStorageRadio.isChecked()) {
                saveExternalStorage(fileName, data);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error saving data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void loadData(View view) {
        String fileName = fileNameInput.getText().toString();

        if (fileName.isEmpty()) {
            Toast.makeText(this, "Please enter filename", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            String data;
            if (internalStorageRadio.isChecked()) {
                data = readInternalStorage(fileName);
            } else if (externalStorageRadio.isChecked()) {
                data = readExternalStorage(fileName);
            } else {
                Toast.makeText(this, "Select storage type", Toast.LENGTH_SHORT).show();
                return;
            }
            dataInput.setText(data);
        } catch (Exception e) {
            Toast.makeText(this, "Error reading data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveInternalStorage(String fileName, String data) {
        try (FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fos.write(data.getBytes(StandardCharsets.UTF_8));
            Toast.makeText(this, "Saved to Internal Storage", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Internal Storage Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveExternalStorage(String fileName, String data) {
        if (isExternalStorageWritable()) {
            File file = new File(getExternalFilesDir(null), fileName);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(data.getBytes(StandardCharsets.UTF_8));
                Toast.makeText(this, "Saved to External Storage", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "External Storage Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "External Storage Not Writable", Toast.LENGTH_SHORT).show();
        }
    }

    private String readInternalStorage(String fileName) throws Exception {
        try (FileInputStream fis = openFileInput(fileName);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(isr)) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            Toast.makeText(this, "Loaded from Internal Storage", Toast.LENGTH_SHORT).show();
            return sb.toString().trim();
        }
    }

    private String readExternalStorage(String fileName) throws Exception {
        if (isExternalStorageReadable()) {
            File file = new File(getExternalFilesDir(null), fileName);
            try (FileInputStream fis = new FileInputStream(file);
                 InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
                 BufferedReader bufferedReader = new BufferedReader(isr)) {

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                Toast.makeText(this, "Loaded from External Storage", Toast.LENGTH_SHORT).show();
                return sb.toString().trim();
            }
        } else {
            throw new Exception("External Storage Not Readable");
        }
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
}