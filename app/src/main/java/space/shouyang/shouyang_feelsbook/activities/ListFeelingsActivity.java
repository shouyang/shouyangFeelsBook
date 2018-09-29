package space.shouyang.shouyang_feelsbook.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import space.shouyang.shouyang_feelsbook.R;
import space.shouyang.shouyang_feelsbook.models.Feel;
import space.shouyang.shouyang_feelsbook.models.FeelingRecord;




/**
 *  Purpose:
 *      Display a list of persisted feeling records.
 *      Displays a count of the type of feeling records present.
 *
 *  Design Rationale:
 *      Use list view to display records persisted. Do this on a separate activity for more space.
 *      Allow the user to select a record for editing. Display counts as complementary item to
 *      the list of records presented.
 *
 *      Extends PersistentFeelingRecordsActivity to reuse how data is saved and loaded from
 *      internal storage.
 */
public class ListFeelingsActivity extends PersistentFeelingRecordsActivity {

    ListAdapter recordsAdapter;
    ListView form;

    TextView angry_count;
    TextView fear_count;
    TextView joyful_count;
    TextView love_count;
    TextView sad_count;
    TextView suprise_count;


    /**
     *  Bind UI and update counts from loaded records.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_feelings);

        this.initForms();
        this.initCounts();
    }

    /**
     *  Update counts for any updates to loaded records.
     */
    @Override
    protected void onResume() {
        super.onResume();
        this.initForms();
        this.updateCounts();
    }


    /**
     *  Binds the records adapter from to the list view.
     */
    private void initForms() {
        this.initArrayData();

        this.recordsAdapter = new ArrayAdapter<FeelingRecord>(this, R.layout.list_feeling, this.records);

        this.form = findViewById(R.id.feelings_log);
        this.form.setAdapter(this.recordsAdapter);

        this.form.setOnItemClickListener( new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                Toast.makeText(ListFeelingsActivity.this, "" + position, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ListFeelingsActivity.this, EditFeelingActivity.class);
                intent.putExtra("pos",  position);
                startActivity(intent);
            }
        }
        );
    }

    /**
     *  Binds the UI fields used to store the different counts of emotions.
     */
    private void initCounts() {
        this.angry_count = findViewById(R.id.anger_count);
        this.fear_count = findViewById(R.id.fear_count);
        this.joyful_count = findViewById(R.id.joy_count);
        this.love_count = findViewById(R.id.love_count);
        this.sad_count = findViewById(R.id.sad_count);
        this.suprise_count = findViewById(R.id.suprise_count);

        this.updateCounts();
    }

    /**
     *  Updates the counts of different emotions.
     */
    public void updateCounts() {

        Map<Feel, Integer> counts_of = new HashMap<>();

        for (Feel feeling : Feel.values()) {
            counts_of.put(feeling, 0);
        }

        for (FeelingRecord record : this.records) {
            counts_of.put(record.getFeeling(), counts_of.get(record.getFeeling()) + 1);
        }

        this.angry_count.setText("Anger: " + counts_of.get(Feel.ANGER).toString());
        this.fear_count.setText("Fear: " + counts_of.get(Feel.FEAR).toString());
        this.joyful_count.setText("Joy: " + counts_of.get(Feel.JOY).toString());
        this.love_count.setText("Love: " + counts_of.get(Feel.LOVE).toString());
        this.sad_count.setText("Sad:" + counts_of.get(Feel.SADNESS).toString());
        this.suprise_count.setText("Suprise: " + counts_of.get(Feel.SURPRISE).toString());
    }
}
