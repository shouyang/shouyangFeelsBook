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
import java.util.Date;
import java.util.List;
import java.util.Vector;

import space.shouyang.shouyang_feelsbook.R;
import space.shouyang.shouyang_feelsbook.models.Feel;
import space.shouyang.shouyang_feelsbook.models.FeelingRecord;

public class CreateFeelingActivity extends CRUDFeelingActivity {

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

    @Override
    protected void onPause() {
        super.onPause();
    }



    public void updateDatetimeForms() {
        Date now = Calendar.getInstance().getTime();

        this.input_date.setText(new SimpleDateFormat("yyyy-mm-dd").format(now));
        this.input_time.setText(new SimpleDateFormat("HH:mm:ss").format(now));
    }



    public void createFeelingRecord(Feel feel) {

        DateFormat dateBuilder = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
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
    }

    public void submitAngerRecord(View view) {
        createFeelingRecord(Feel.ANGER);
    }

    public void submitFearRecord(View view) {
        createFeelingRecord(Feel.FEAR);
    }

    public void submitJoyFulRecord(View view) {
        createFeelingRecord(Feel.JOY);
    }

    public void submitLoveRecord(View view) {
        createFeelingRecord(Feel.LOVE);
    }

    public void submitSadRecord(View view) {
        createFeelingRecord(Feel.SADNESS);
    }

    public void submitSupriseRecord(View view) {
        createFeelingRecord(Feel.SURPRISE);
    }


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

    private void initArrayData() {

        try {

            File records_file = new File(getApplicationContext().getFilesDir(), this.save_path);
            if (!records_file.exists()) {
                records_file.createNewFile();
            }
            else {
                FileReader reader = new FileReader(records_file);

                Gson gson = new Gson();
                FeelingRecord records[];
                records = gson.fromJson(reader, FeelingRecord[].class );


                if (records != null) {
                    this.records = new Vector(Arrays.asList(records));
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


    private void saveArrayDate() {
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
