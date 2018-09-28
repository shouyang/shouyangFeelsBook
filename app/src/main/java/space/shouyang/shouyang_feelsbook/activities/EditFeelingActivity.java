package space.shouyang.shouyang_feelsbook.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;

import space.shouyang.shouyang_feelsbook.R;
import space.shouyang.shouyang_feelsbook.models.Feel;
import space.shouyang.shouyang_feelsbook.models.FeelingRecord;


/**
 *  Purpose:
 *      Activity for user to edit or delete existing feeling activities.
 *      Previous activity should have supplied the index of the target feeling record to modify.
 *
 *  Design Rationale:
 *      Reuse implementations supplied by CRUD super class. Adds functionality specific for
 *      editing and deleting. In this case populate generic feeling record detail view with
 *      data stored about a single feel record instance. Makes the delete button visible.
 *
 */
public class EditFeelingActivity extends CRUDFeelingActivity {


    /**
     *  On create, make delete button visible from CRUD activity common UI which is generally
     *  hidden.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button delete_button  = findViewById(R.id.submit_delete);
        delete_button.setVisibility(View.VISIBLE);
        delete_button.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                deleteFeelingRecord(v);
            }
        });
    }


    /**
     *  Ensure that on resume we update and populate UI based on selected feeling record.
     */
    @Override
    protected void onResume() {
        super.onResume();

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            this.initArrayData();
            this.refreshInputFieldsFromRecords(bundle.getInt("pos"));
        }
    }

    /**
     *  Populate generic CRUD activity input and text fields with data related to the target
     *  feeling record instance.
     */
    private void refreshInputFieldsFromRecords(int pos) {

        FeelingRecord record = this.records.get(pos);


        Date record_date  = record.getRecord_time();

        String formatted_date = new SimpleDateFormat("yyyy-MM-dd").format(record_date);
        String formatted_time = new SimpleDateFormat("hh:mm:ss aa").format(record_date);

        this.input_date.setText(formatted_date);
        this.input_time.setText(formatted_time);
        this.input_comment.setText(record.getComment());

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

    public void submitAngerRecord(View view) {
        updateFeelingRecord(Feel.ANGER);
    }

    public void submitFearRecord(View view) {
        updateFeelingRecord(Feel.FEAR);
    }

    public void submitJoyFulRecord(View view) {
        updateFeelingRecord(Feel.JOY);
    }

    public void submitLoveRecord(View view) {
        updateFeelingRecord(Feel.LOVE);
    }

    public void submitSadRecord(View view) {
        updateFeelingRecord(Feel.SADNESS);
    }

    public void submitSupriseRecord(View view) {
        updateFeelingRecord(Feel.SURPRISE);
    }
}
