package com.crm.app.api;

import org.springframework.web.client.RestTemplate;

import java.util.*;

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

    public static String[] getTableSchema(String tableName) {
        System.out.println(URL+"get-table-schema/"+tableName);
        return new String[]{"id",
                "created_at",
                "update_at",
                "target_Port",
                "condition",
                "notes"};
    }

    public static void createTable(String tableName, Set<String> columns) {
        System.out.println(URL + "create-table");
        System.out.println(URL + "add-header/"+tableName);
        System.out.println(Arrays.toString(columns.toArray()));
    }

    public static List<Map<String, String>> getTableData(String tableName){
        System.out.println(URL+"get-all-data/"+tableName);
        return List.of(new HashMap<>(){{
            put("id","1");
            put("target_Port","1");
            put("condition","1");
            put("notes","1");
        }});
    }
}
