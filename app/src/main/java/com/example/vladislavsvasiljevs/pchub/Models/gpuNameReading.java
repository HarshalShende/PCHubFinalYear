package com.example.vladislavsvasiljevs.pchub.Models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class gpuNameReading {
    public String Text;


    public gpuNameReading() {
    }

    public gpuNameReading(String text) {
        this.Text = text;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Text", Text);

        return result;
    }
}