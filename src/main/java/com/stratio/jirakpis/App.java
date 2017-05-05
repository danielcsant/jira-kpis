package com.stratio.jirakpis;

import com.stratio.jirakpis.model.Issue;

import java.io.*;
import java.util.List;
import java.util.logging.Logger;

public class App {

    public static final String BASE_URL = "https://stratio.atlassian.net/rest/api/latest/";
    private static Logger logger = Logger.getLogger("com.stratio.jirakpis.App");

    public static void main(String[] args) throws IOException {
        try {
           UserInterface ui = new UserInterface();
           JiraManager jiraManager = new JiraManager
                    (BASE_URL,ui.getUser(),ui.getPassword());
            StringBuilder output = jiraManager.getAllBugs(ui.getProject());

            JiraParser jiraParser = new JiraParser();
            List<Issue> issueList = jiraParser.parseBugs(output.toString());
            System.out.println(issueList);
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
