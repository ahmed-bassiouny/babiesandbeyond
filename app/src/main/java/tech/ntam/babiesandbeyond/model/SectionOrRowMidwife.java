package tech.ntam.babiesandbeyond.model;

import java.util.List;

/**
 * Created by Developer on 22/02/18.
 */

public class SectionOrRowMidwife {
    private AvailableTimeMidwife row;
    private String section;
    private String date;
    private boolean isRow;

    public SectionOrRowMidwife (String section,String date) {
        this.section = section;
        this.date = date;
        this.isRow = false;

    }

    public SectionOrRowMidwife (AvailableTimeMidwife row) {
        this.row = row;
        this.isRow = true;
    }

    public AvailableTimeMidwife getRow() {
        return row;
    }

    public String getSection() {
        return section;
    }

    public boolean isRow() {
        return isRow;
    }

    public String getDate() {
        return date;
    }
}
