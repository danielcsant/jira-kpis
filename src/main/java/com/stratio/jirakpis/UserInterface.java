package com.stratio.jirakpis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by jmgomez on 27/04/17.
 */
public class UserInterface {

    public String getUser(){
        String user = "";
        try {
            user= get("user");
        } catch (IOException e) {
            e.printStackTrace();

        }
        return user;
    }

    public String getPassword(){
        String password = "";
        try {
            password= get("password");
        } catch (IOException e) {
            e.printStackTrace();

        }
        return password;
    }


    private String get(String field) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter "+field+"> ");
        return  br.readLine();

    }
}
