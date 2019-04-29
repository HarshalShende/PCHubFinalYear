package com.example.vladislavsvasiljevs.pchub.Models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class cpuFreqReading {
    public String Value;
    public String Max;

    public cpuFreqReading() {
    }

    public cpuFreqReading(String value, String max) {
        this.Value = value;
        this.Max = max;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Value", Value);
        result.put("Max", Max);

        return result;
    }
}