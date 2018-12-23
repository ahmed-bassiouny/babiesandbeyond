package tech.ntam.babiesandbeyond.model;

import java.io.Serializable;
import java.util.Map;

public class FortRequest implements Serializable {
    private Map<String,Object> requestMap;
    private boolean showResponsePage;
    public Map<String, Object> getRequestMap() {
        return requestMap;
    }
    public void setRequestMap(Map<String, Object> requestMap) {
        this.requestMap = requestMap;
    }
    public boolean isShowResponsePage() {
        return showResponsePage;
    }
    public void setShowResponsePage(boolean showResponsePage) {
        this.showResponsePage = showResponsePage;
    }
}