package tech.ntam.babiesandbeyond.model;

import java.util.List;

/**
 * Created by bassiouny on 22/02/18.
 */

public class SectionOrRowMidwife {
    private AvailableTimeMidwife row;
    private String section;
    private boolean isRow;

    public SectionOrRowMidwife (String section) {
        this.section = section;
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
}
