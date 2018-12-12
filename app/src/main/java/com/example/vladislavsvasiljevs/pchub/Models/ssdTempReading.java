package com.example.vladislavsvasiljevs.pchub.Models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class ssdTempReading {
    public String Value;
    public String Max;

    public ssdTempReading() {
    }

    public ssdTempReading(String value, String max) {
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