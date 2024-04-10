package com.plans.core.response;

import java.util.Set;
import com.plans.core.model.EndUser;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RDeviceRecordDetailedByClient {
    private RUser client;
    private Set<RClientRecords> records;

    public RDeviceRecordDetailedByClient(EndUser endUser, Set<RClientRecords> records) {
        this.client = new RUser(endUser.getUser());
        this.records = records;
    }
}
