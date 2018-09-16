package space.shouyang.shouyang_feelsbook.activities;

import android.view.View;

import space.shouyang.shouyang_feelsbook.models.Feel;


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
