package tech.ntam.babiesandbeyond.database;

import java.util.ArrayList;
import java.util.List;

import tech.ntam.babiesandbeyond.model.Group;

/**
 * Created by bassiouny on 05/01/18.
 */

public class GroupsDatabase {

    private static List<Group> groups;

    public static List<Group> getGroups() {
        if (groups == null)
            groups = new ArrayList<>();
        return groups;
    }

    public static void setGroups(List<Group> groups) {
        GroupsDatabase.groups = groups;
    }
}
