package space.shouyang.shouyang_feelsbook.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import space.shouyang.shouyang_feelsbook.R;
import space.shouyang.shouyang_feelsbook.exceptions.CommentTooLongException;
import space.shouyang.shouyang_feelsbook.models.Feel;
import space.shouyang.shouyang_feelsbook.models.FeelingRecord;

public abstract class CRUDFeelingActivity extends AppCompatActivity {

    String save_path = "Feels.json";

    List<FeelingRecord> records;

    EditText input_date;
    EditText input_time;
    EditText input_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_crud_feeling);

        this.initInputFields();
        this.initArrayData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.updateDatetimeForms();
    }

    public void updateDatetimeForms() {
        Date now = Calendar.getInstance().getTime();

        this.input_date.setText(new SimpleDateFormat("yyyy-MM-dd").format(now));
        this.input_time.setText(new SimpleDateFormat("hh:mm:ss aa").format(now));
    }



    public void createFeelingRecord(Feel feel) {

        DateFormat dateBuilder = new SimpleDateFormat("yyyy-MM-ddhh:mm:ss aa");
        Date datetime;


        String user_date = input_date.getText().toString();
        String user_time = input_time.getText().toString();
        String user_comment = input_comment.getText().toString();

        try {
            datetime = dateBuilder.parse(user_date + user_time);
            FeelingRecord record = new FeelingRecord(feel, user_comment, datetime);

            this.records.add(record);
            this.saveArrayDate();


            this.showFeelingListActivity();
        }
        catch (ParseException e) {
            Toast.makeText(this, "Invalid Input ... Try Again (Check Date Input)", Toast.LENGTH_SHORT).show();
        }

        catch (CommentTooLongException e) {
            Toast.makeText(this, "Invalid Input ... Comment Over 100 Chars ", Toast.LENGTH_SHORT).show();
        }
    }

    public abstract void submitAngerRecord(View view);

    public abstract void submitFearRecord(View view);

    public abstract void submitJoyFulRecord(View view);

    public abstract void submitLoveRecord(View view);

    public abstract void submitSadRecord(View view);

    public abstract void submitSupriseRecord(View view);


    public void showFeelingListActivity() {
        Intent intent = new Intent(this, ListFeelingsActivity.class);
        startActivity(intent);
    }

    public void showFeelingListActivity(View view) {
        Intent intent = new Intent(this, ListFeelingsActivity.class);
        startActivity(intent);
    }

    private void initInputFields() {
        this.input_date = findViewById(R.id.user_date);
        this.input_time = findViewById(R.id.user_time);
        this.input_comment = findViewById(R.id.user_comment);
    }

    protected void initArrayData() {

        try {

            File records_file = new File(getApplicationContext().getFilesDir(), this.save_path);

            if (!records_file.exists()) {
                records_file.createNewFile();
                this.records = new Vector<>();
            }


            else {
                FileReader reader = new FileReader(records_file);
                Gson gson = new Gson();


                FeelingRecord records[];
                records = gson.fromJson(reader, FeelingRecord[].class );


                if (records != null) {
                    this.records = new Vector(Arrays.asList(records));
                    Collections.sort(this.records, Collections.reverseOrder());
                }

                else {
                    this.records = new Vector<>();
                }
                reader.close();
            }

        }


        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    protected void saveArrayDate() {
        try {
            File records_file = new File(getApplicationContext().getFilesDir(), this.save_path);
            if (!records_file.exists()) {
                records_file.createNewFile();
            }

            FileWriter writer = new FileWriter(records_file, false);

            Gson gson = new Gson();

            writer.write(gson.toJson(this.records.toArray()).toString());
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}
