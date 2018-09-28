package space.shouyang.shouyang_feelsbook.activities;

import android.view.View;

import space.shouyang.shouyang_feelsbook.models.Feel;


/**
 *  Purpose:
 *      Activity for user to create new feeling records.
 *
 *  Design Rationale:
 *      Reuse implementations supplied by CRUD super class. In this case, object creation
 *      is straightforward, no additional "prep" is conducted. See the edit view for how
 *      these concrete views provide additional functionality to the implementations in
 *      the CRUD super class.
 *
 */
public class CreateFeelingActivity extends CRUDFeelingActivity {

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

}
