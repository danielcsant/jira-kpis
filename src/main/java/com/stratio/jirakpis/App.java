package com.stratio.jirakpis;

import java.io.*;
import java.util.logging.Logger;

public class App {

    public static final String BASE_URL = "https://stratio.atlassian.net/rest/api/latest/";
    private static Logger logger = Logger.getLogger("com.stratio.jirakpis.App");

    public static void main(String[] args) throws IOException {
        try {
           JiraManager jiraManager = new JiraManager
                    (BASE_URL,"jmgomez","SC1cPA2pt");
            jiraManager.getAllBugs("DCS").forEach(s-> System.out.println(s));
            UserInterface ui = new UserInterface();
           JiraManager jiraManager = new JiraManager
                    (BASE_URL,ui.getUser(),ui.getPassword());
            System.out.println(jiraManager.getAllBugs("DCS").toString());
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // read this input
    }


}
