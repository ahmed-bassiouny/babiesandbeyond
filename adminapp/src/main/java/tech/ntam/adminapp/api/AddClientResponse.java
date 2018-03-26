package tech.ntam.adminapp.api;

import com.google.gson.annotations.SerializedName;

import tech.ntam.adminapp.model.Client;

/**
 * Created by bassiouny on 26/03/18.
 */

public class AddClientResponse extends ParentResponse {

    @SerializedName(DATA_KEY)
    private Client client;

    public Client getClient() {
        if(client == null)
            client = new Client();
        return client;
    }
}
