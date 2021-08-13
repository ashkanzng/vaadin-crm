package com.crm.app.api;


public class ApiClient {

    static final String URL = "http://localhost:8082/api/";

    public static String[] getAllTables(){
        //RestTemplate restTemplate = new RestTemplate();
        //ResponseEntity<String[]> response = restTemplate.getForEntity(URL, String[].class);

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
}
