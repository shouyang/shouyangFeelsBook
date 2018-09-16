package space.shouyang.shouyang_feelsbook.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import space.shouyang.shouyang_feelsbook.models.Feel;
import space.shouyang.shouyang_feelsbook.models.FeelingRecord;

public class EditFeelingActivity extends AppCompatActivity {

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

        Button delete_button  = findViewById(R.id.submit_delete);
        delete_button.setVisibility(View.VISIBLE);
        delete_button.setOnClickListener( new View.OnClickListener() {

            public void onClick(View v) {

                deleteFeelingRecord(v);


            }
            });



    }

    @Override
    protected void onResume() {
        super.onResume();
        this.updateDatetimeForms();

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            this.initArrayData();
            this.refreshInputFieldsFromRecords(bundle.getInt("pos"));
        }

    }

    private void refreshInputFieldsFromRecords(int pos) {

        FeelingRecord record = this.records.get(pos);


        Date record_date  = record.getRecord_time();

        String formatted_date = new SimpleDateFormat("yyyy-mm-dd").format(record_date);
        String formatted_time = new SimpleDateFormat("HH:mm:ss").format(record_date);



        this.input_date.setText(formatted_date);
        this.input_time.setText(formatted_time);
        this.input_comment.setText(record.getComment().toString());

        Button button;
        switch (record.getFeeling())
        {

            case ANGER:
                button = findViewById(R.id.submit_angry);
                button.setTypeface(button.getTypeface(), Typeface.BOLD);
                break;

            case JOY:
                button = findViewById(R.id.submit_joyful);
                button.setTypeface(button.getTypeface(), Typeface.BOLD);
                break;

            case FEAR:
                button = findViewById(R.id.submit_fear);
                button.setTypeface(button.getTypeface(), Typeface.BOLD);
                break;

            case SADNESS:
                button = findViewById(R.id.submit_sad);
                button.setTypeface(button.getTypeface(), Typeface.BOLD);
                break;

            case SURPRISE:
                button = findViewById(R.id.submit_suprised);
                button.setTypeface(button.getTypeface(), Typeface.BOLD);
                break;

            case LOVE:
                button = findViewById(R.id.submit_love);
                button.setTypeface(button.getTypeface(), Typeface.BOLD);
                break;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
    }



    public void updateDatetimeForms() {
        Date now = Calendar.getInstance().getTime();

        this.input_date.setText(new SimpleDateFormat("yyyy-MM-dd").format(now));
        this.input_time.setText(new SimpleDateFormat("HH:mm:ss").format(now));
    }


    public void deleteFeelingRecord(View view) {
        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null) {
            this.records.remove(bundle.getInt("pos"));
            this.saveArrayDate();
            this.showFeelingListActivity();
        }
    }

    public void updateFeelingRecord(Feel feel) {
        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null) {
            this.records.remove(bundle.getInt("pos"));
            this.createFeelingRecord(feel);
        }
        else {
            createFeelingRecord(feel);
        }
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

    public void createAngerRecord(View view) {
        updateFeelingRecord(Feel.ANGER);
    }

    public void createFearRecord(View view) {
        updateFeelingRecord(Feel.FEAR);
    }

    public void createJoyFulRecord(View view) {
        updateFeelingRecord(Feel.JOY);
    }

    public void createLoveRecord(View view) {
        updateFeelingRecord(Feel.LOVE);
    }

    public void createSadRecord(View view) {
        updateFeelingRecord(Feel.SADNESS);
    }

    public void createSupriseRecord(View view) {
        updateFeelingRecord(Feel.SURPRISE);
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
