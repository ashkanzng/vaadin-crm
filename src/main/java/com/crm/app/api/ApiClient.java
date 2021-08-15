package com.crm.app.api;

import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Set;

public class ApiClient {

    static final String URL = "http://localhost:8082/api/";
    static final RestTemplate restTemplate = new RestTemplate();

    public static String[] getAllTables() {
        //ResponseEntity<String[]> response = restTemplate.getForEntity(URL, String[].class);
        System.out.println(URL + "get-tables");
        return new String[]{"authority",
                "hibernate_sequence",
                "users",
                "measurement-1",
                "configuration",
                "table-1",
                "table-1",
                "table-1",
                "table-2",
                "table-3",
                "table-3",
                "table-4"};
    }

    public static void createTable(String tableName, Set<String> columns) {
        System.out.println(URL + "create-table");
        System.out.println(URL + "add-header/"+tableName);
        System.out.println(Arrays.toString(columns.toArray()));
    }
}
