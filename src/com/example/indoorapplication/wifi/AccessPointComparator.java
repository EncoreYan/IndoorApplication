package com.example.indoorapplication.wifi;

import java.util.Comparator;

public class AccessPointComparator implements Comparator<AccessPoint> {
    public int compare(AccessPoint ap1, AccessPoint ap2) {
        return ap2.level - ap1.level;
    }
}