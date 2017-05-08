package com.stratio.jirakpis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by jmgomez on 27/04/17.
 */
public class UserInterface {

    public String getUser(){
        return get("user");
    }

    public String getPassword(){
        return get("password");
    }

    public String getProject() {
        return get("project");
    }

    private String get(String field) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter "+field+"> ");
        String value = "";
        try {
            value = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
}
