package com.crm.app.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.*;

public class ApiClient {

    private final static Logger logger = LoggerFactory.getLogger(ApiClient.class);

    //static final String URL = "http://localhost:8082/api/";
    static final String URL = "http://157.90.55.120:8082/api/";
    static final RestTemplate restTemplate = new RestTemplate();
    static ObjectMapper mapper = new ObjectMapper();


    public static String[] getAllTables() {
        ResponseEntity<String[]> response = restTemplate.getForEntity(URL + "get-tables", String[].class);
        return response.getBody();
    }

    public static String[] getTableSchema(String tableName) {
        ResponseEntity<String[]> response = restTemplate.getForEntity(URL + "get-table-schema/" + tableName, String[].class);
        return response.getBody();
    }

    public static void createTable(String tableName, Set<String> columns) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, String> tableNameRequest = new HashMap<>() {{
                put("name", tableName);
            }};
            String[] tableColumnRequest = Arrays.copyOf(columns.stream().sorted().toArray(), columns.size(), String[].class);
            HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(tableNameRequest), headers);
            ResponseEntity<String> result = restTemplate.postForEntity(URL + "create-table", request, String.class);
            System.out.println(result.getStatusCode());
            request = new HttpEntity<>(mapper.writeValueAsString(tableColumnRequest), headers);
            result = restTemplate.postForEntity(URL + "add-header/" + tableName, request, String.class);
            if (result.getStatusCode() == HttpStatus.OK) {
                logger.info(result.getBody());
            }
        } catch (JsonProcessingException jsonProcessingException) {
            logger.error(jsonProcessingException.getMessage());
        }
    }

    public static List<Map<String, String>> getTableData(String tableName) {
        try {
            //ResponseEntity<?extends List<Map<String,String>>> result = restTemplate.getForEntity(URL+"get-all-data/"+tableName, (Class<?  extends List<Map<String,String>>>) List.class);
            ResponseEntity<List<Map<String, String>>> result = restTemplate.exchange(URL + "get-all-data/" + tableName, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
            });
            if (result.getStatusCode() == HttpStatus.OK) {
                return result.getBody();
            }
        } catch (HttpClientErrorException httpClientErrorException) {
            logger.error(httpClientErrorException.getMessage());
        }
        return null;
    }
}
