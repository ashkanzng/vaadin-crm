package com.crm.app.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class ApiClient {

    static final String URL = "http://localhost:8082/api/";
    static final RestTemplate restTemplate = new RestTemplate();
    static ObjectMapper mapper = new ObjectMapper();


    public static String[] getAllTables() {
        ResponseEntity<String[]> response = restTemplate.getForEntity(URL + "get-tables", String[].class);
        return response.getBody();
    }

    public static String[] getTableSchema(String tableName) {
        ResponseEntity<String[]> response = restTemplate.getForEntity(URL+"get-table-schema/"+tableName, String[].class);
        return response.getBody();
//        return new String[]{"id",
//                "created_at",
//                "update_at",
//                "target_Port",
//                "condition",
//                "notes"};
    }

    public static void createTable(String tableName, Set<String> columns){
//        System.out.println(URL + "create-table");
//        System.out.println(URL + "add-header/"+tableName);
//        System.out.println(Arrays.toString(columns.toArray()));
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String,String> dataRequest = new HashMap<>(){{ put("name",tableName);}};
            HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(dataRequest), headers);
            ResponseEntity<String> result = restTemplate.postForEntity(URL + "create-table", request, String.class);
            System.out.println(result.getStatusCode());
        }catch (JsonProcessingException jsonProcessingException){
            System.out.println(jsonProcessingException.getMessage());
        }
    }

    public static List<Map<String, String>> getTableData(String tableName){
//        System.out.println(URL+"get-all-data/"+tableName);
        return List.of(new HashMap<>(){{
            put("id","1");
            put("target_Port","1345");
            put("condition","451");
            put("notes","341");
        }},new HashMap<>(){{
            put("id","2");
            put("target_Port","13425");
            put("condition","451");
            put("notes","3411");
        }});
    }
}
