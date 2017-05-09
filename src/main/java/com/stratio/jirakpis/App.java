package com.stratio.jirakpis;

import com.stratio.jirakpis.model.Issue;
import com.stratio.jirakpis.model.Version;

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

            String project = ui.getProject();
            StringBuilder output = jiraManager.getAllBugs(project);

            List<Version> bugsByReleaseInJira = jiraManager.getAllBugsByRelease(project);
            List<Version> bugsByReleaseInPlat = jiraManager.getAllBugsByRelease("PLAT");

            generateReport(bugsByReleaseInJira, bugsByReleaseInPlat);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateReport(List<Version> bugsByRelease, List<Version> bugsByReleaseInPlat) {
        System.out.println();
        System.out.println("###### Bugs by version in project (found inside Stratio)");
        for (Version version : bugsByRelease) {
            System.out.println("Version: " + version.getName() + " has " + version.getIssueList().size() + " bugs");
        }

        System.out.println();
        System.out.println("###### Bugs by version in project PLAT (found by the clients)");
        for (Version version : bugsByReleaseInPlat) {
            System.out.println("Version: " + version.getName() + " has " + version.getIssueList().size() + " bugs");
        }
    }


}
