package com.example.vladislavsvasiljevs.pchub;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class stats2 {
    public String Value;
    public String Min;

    public stats2() {
    }

    public stats2(String value, String min) {
        this.Value = value;
        this.Min = min;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Max", Value);
        result.put("Min", Min);

        return result;
    }

}
