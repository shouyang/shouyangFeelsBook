package space.shouyang.shouyang_feelsbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import space.shouyang.shouyang_feelsbook.R;
import space.shouyang.shouyang_feelsbook.exceptions.CommentTooLongException;
import space.shouyang.shouyang_feelsbook.models.Feel;
import space.shouyang.shouyang_feelsbook.models.FeelingRecord;

/**
 *  Purpose:
 *      Base class for activities that require create, read, update, delete functionality
 *      related to a single Feeling object.
 *
 *  Design Rationale:
 *      Reuse and make consistent how common model persistent model functions are implemented.
 *      CRUD functionality interrelate and share common functionality. CRUD views should be
 *      slices of the functionality CRUD activity.
 *
 *      Some use cases:
 *          * Discriminate user permissions and supply differing CRUD views
 *          * Supply differing CRUD GUIs with consistent backend.
 *
 *      Provides abstract methods tied to UI buttons to be defined by concrete CRUD activities.
 *      For create and edit activities to share buttons.
 *
 */
public abstract class CRUDFeelingActivity extends PersistentFeelingRecordsActivity {

    protected EditText input_date;
    protected EditText input_time;
    protected EditText input_comment;

    /**
     *  Binds the UI for FeelingRecords CRUD activities on create.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_crud_feeling);
        this.initInputFields();
    }

    /**
     * Update UI datetime fields whenever user resumes CRUD activities.
     */
    @Override
    protected void onResume() {
        super.onResume();
        this.updateDatetimeForms();
    }

    /**
     * Creates new feeling record and store to persistent data array.
     * Moves user to list feeling activity so they may look at their new record.
     * @param feel new feeling type
     */
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
            this.saveArrayData();


            this.showFeelingListActivity();
        }
        catch (ParseException e) {
            Toast.makeText(this, "Invalid Input ... Try Again (Check Date Input)", Toast.LENGTH_SHORT).show();
        }
        catch (CommentTooLongException e) {
            Toast.makeText(this, "Invalid Input ... Comment Over 100 Chars ", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *  Deletes a feeling record via its location in the records array.
     *
     *  For read, update, delete views wherein a position in the records array will be passed.
     */
    public void deleteFeelingRecord(View view) {
        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null) {
            this.records.remove(bundle.getInt("pos"));
            this.saveArrayData();
            this.showFeelingListActivity();
        }
    }


    /**
     *  Updates a feeling record in the records array via creating a new record and deleting the
     *  existing one.
     *
     *  For read, update, delete views wherein a position in the records array will be passed.
     */
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

    public void updateDatetimeForms() {
        Date now = Calendar.getInstance().getTime();

        this.input_date.setText(new SimpleDateFormat("yyyy-MM-dd").format(now));
        this.input_time.setText(new SimpleDateFormat("hh:mm:ss aa").format(now));
    }
}
