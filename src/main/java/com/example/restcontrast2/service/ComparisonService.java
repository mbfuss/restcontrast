package com.example.restcontrast2.service;

import com.example.restcontrast2.component.ComparisonResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ComparisonService {
    public void compareObjects(Map<String, Object> oldObject, Map<String, Object> newObject, ComparisonResponse response) {
        // Проход по ключам старого объекта для сравнения значений
        for (String key : oldObject.keySet()) {
            Object oldValue = oldObject.get(key);
            Object newValue = newObject.get(key);

            // Если значение - это вложенный объект Map, вызываем рекурсивное сравнение
            if (oldValue instanceof Map && newValue instanceof Map) {
                compareObjects((Map<String, Object>) oldValue, (Map<String, Object>) newValue, response);
            }
            // Если значение - это список, вызываем метод для сравнения списков
            else if (oldValue instanceof List && newValue instanceof List) {
                compareLists((List<Object>) oldValue, (List<Object>) newValue, key, response);
            }
            // В противном случае, сравниваем простые значения
            else {
                if (!oldValue.equals(newValue)) {
                    addComparisonResult(key, oldValue, newValue, response);
                }
            }
        }
    }

    private void compareLists(List<Object> oldList, List<Object> newList, String key, ComparisonResponse response) {
        // Создание списков для хранения удаленных и вставленных элементов
        List<Object> deleted = new ArrayList<>();
        List<Object> inserted = new ArrayList<>();

        // Сравнение списков и заполнение списков deleted и inserted
        for (Object oldItem : oldList) {
            if (!newList.contains(oldItem)) {
                deleted.add(oldItem);
            }
        }

        for (Object newItem : newList) {
            if (!oldList.contains(newItem)) {
                inserted.add(newItem);
            }
        }

        // Если есть изменения в списке, добавляем их в объект ComparisonResponse
        if (!deleted.isEmpty() || !inserted.isEmpty()) {
            Map<String, List<Object>> changes = new HashMap<>();
            changes.put("del", deleted);
            changes.put("ins", inserted);
            response.addComparison(key, changes);
        }
    }

    private void addComparisonResult(String key, Object oldValue, Object newValue, ComparisonResponse response) {
        // Создание Map для хранения измененных значений
        Map<String, Object> fieldChanges = new HashMap<>();
        fieldChanges.put("old", oldValue);
        fieldChanges.put("new", newValue);

        // Создание Map для хранения результата сравнения
        Map<String, Map<String, Object>> comparison = new HashMap<>();
        comparison.put("upd", fieldChanges);

        // Добавление результата сравнения в объект ComparisonResponse
        response.addComparison(key, comparison);
    }
}
