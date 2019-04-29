package com.example.vladislavsvasiljevs.pchub.Models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class settings {
    public String CPUTempAlert;
    public String GPUTempAlert;
    public int FrequencyOfNotifications;

    public settings() {
    }

    public settings(String cPUTempAlert, String gPUTempAlert,int frequencyOfNotifications) {
        this.CPUTempAlert = cPUTempAlert;
        this.GPUTempAlert = gPUTempAlert;
        this.FrequencyOfNotifications = frequencyOfNotifications;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Value", CPUTempAlert);
        result.put("Max", GPUTempAlert);
        result.put("Max", FrequencyOfNotifications);

        return result;
    }
}