package com.plans.core.utils;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ZoneSelector {

    private static final List<ZoneId> ZONE_IDS = 
            new ArrayList<>(ZoneId.getAvailableZoneIds().stream().map(ZoneId::of).toList());
    private static final Random random = new Random();

    public static ZoneId getRandomZoneId() {
        int randomIndex = random.nextInt(ZONE_IDS.size());
        return ZONE_IDS.get(randomIndex);
    }
}
