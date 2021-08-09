package com.vaadin.app.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class ApiClient {

    static final String URL = "https://localhost:8082/api/";

    public static String getTableData(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);
        return "";
    }
}
