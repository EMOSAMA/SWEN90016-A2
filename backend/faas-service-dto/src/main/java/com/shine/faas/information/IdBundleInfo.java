package com.shine.faas.information;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IdBundleInfo {

    private List<UUID > ids = new ArrayList<>();

    //region get set

    public List<UUID> getIds() {
        return ids;
    }

    public void setIds(List<UUID> ids) {
        this.ids = ids;
    }

    //endregion
}
