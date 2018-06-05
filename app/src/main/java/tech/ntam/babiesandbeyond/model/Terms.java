package tech.ntam.babiesandbeyond.model;

/**
 * Created by Developer on 28/01/18.
 */

public class Terms {

    private static String terms;

    public static String getTerms() {
        if(terms == null)
            terms = "";
        return terms;
    }

    public static void setTerms(String terms) {
        Terms.terms = terms;
    }
}
