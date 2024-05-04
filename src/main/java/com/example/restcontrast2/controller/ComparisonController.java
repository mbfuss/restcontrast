package com.example.restcontrast2.controller;

import com.example.restcontrast2.model.ComparisonRequest;
import com.example.restcontrast2.component.ComparisonResponse;
import com.example.restcontrast2.service.ComparisonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;


@RestController
public class ComparisonController {

    ComparisonResponse comparisonResponse;
    ComparisonService comparisonService;

    public ComparisonController(ComparisonResponse comparisonResponse, ComparisonService comparisonService) {
        this.comparisonResponse = comparisonResponse;
        this.comparisonService = comparisonService;
    }

    @PostMapping("/delta")
    public ComparisonResponse compareJsonObjects(@RequestBody ComparisonRequest request) throws IOException {
        // Извлечение старой и новой версии данных из запроса
        String oldVersion = request.getOldVersion();
        String newVersion = request.getNewVersion();

        // Десериализация JSON из base64 в объекты Map<String, Object>
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> oldObject = objectMapper.readValue(Base64.getDecoder().decode(oldVersion), Map.class);
        Map<String, Object> newObject = objectMapper.readValue(Base64.getDecoder().decode(newVersion), Map.class);

        // Вызов метода для сравнения объектов
        comparisonService.compareObjects(oldObject, newObject, comparisonResponse);

        return comparisonResponse;
    }


}
