package tech.ntam.adminapp.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.adminapp.model.Client;

/**
 * Created by bassiouny on 20/03/18.
 */

public class ClientsResponse extends ParentResponse {

    @SerializedName(DATA_KEY)
    private List<Client> clients;

    public List<Client> getClients() {
        if (clients == null)
            clients = new ArrayList<>();
        return clients;
    }
}
