package com.example.restcontrast2.component;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ComparisonResponse {
    private Map<String, Map<String, Object>> comparison;

    public ComparisonResponse() {
        this.comparison = new HashMap<>();
    }

    public Map<String, Map<String, Object>> getComparison() {
        return comparison;
    }

    public void setComparison(Map<String, Map<String, Object>> comparison) {
        this.comparison = comparison;
    }

    public void addComparison(String key, Map<String, ?> changes) {
        comparison.put(key, (Map<String, Object>) changes);
    }
}
