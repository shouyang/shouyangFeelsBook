package space.shouyang.shouyang_feelsbook.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import space.shouyang.shouyang_feelsbook.R;
import space.shouyang.shouyang_feelsbook.models.FeelingRecord;


/**
 *  Purpose:
 *      Base class for activities that require the persistent feelings recorded in app.
 *
 *  Design Rationale:
 *      Reuse and make consistent how persistent data is shared in the app's concrete activities.
 *      Many activities will interact with saved data. Changes related to saved data should
 *      standardized throughout the app.
 */
public abstract class PersistentFeelingRecordsActivity extends AppCompatActivity {

    private static final  String save_path = "Feels.json";
    protected List<FeelingRecord> records;


    /**
     *  Add data population to activity life cycle on start.
     *  Data should be populated at least once on creation for earliest use possible.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initArrayData();
    }



    /**
     *  Add data population to activity life cycle on resume.
     *  Ensures user sees updated data for use.
     */
    @Override
    protected void onResume() {
        super.onResume();
        this.initArrayData();
    }

    /**
     *  Initializes stored array data for use. Loads JSON data from save_path and deserialize the
     *  data for use.
     */
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

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Serialize records into JSON format and save to app internal storage pointed by save_path.
     */
    protected void saveArrayData() {
        try {
            File records_file = new File(getApplicationContext().getFilesDir(), this.save_path);
            if (!records_file.exists()) {
                records_file.createNewFile();
            }

            FileWriter writer = new FileWriter(records_file, false);
            Gson gson = new Gson();

            writer.write(gson.toJson(this.records.toArray()));
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
