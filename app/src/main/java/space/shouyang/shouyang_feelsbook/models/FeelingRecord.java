package space.shouyang.shouyang_feelsbook.models;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FeelingRecord implements Comparable<FeelingRecord> {

    private Feel feeling;
    private String comment;
    private Date   record_time;

    public FeelingRecord (Feel feeling, String comment, Date datetime) {

        this.comment = comment;
        this.feeling = feeling;
        this.record_time = datetime;
    }

    @Override
    public String toString() {
        return String.format("(%s), %s", new SimpleDateFormat("MM/dd/yyyy hh:mm:ss").format(this.record_time), this.feeling);
    }

    @Override
    public int compareTo(@NonNull FeelingRecord other) {
        return this.record_time.compareTo(other.record_time);
    }

    public String getComment() {
        return comment;
    }

    public Feel getFeeling() {
        return feeling;
    }

    public Date getRecord_time() {
        return record_time;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setFeeling(Feel feeling) {
        this.feeling = feeling;
    }

    public void setRecord_time(Date record_time) {
        this.record_time = record_time;
    }
}
