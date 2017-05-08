package com.stratio.jirakpis;

import com.stratio.jirakpis.model.Issue;
import com.stratio.jirakpis.model.Version;
import org.apache.commons.codec.binary.Base64;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class JiraManager {

    private final String baseURL;
    private String user;
    private String password;

    public JiraManager(String baseURL, String user, String password) {
        this.user = user;
        this.password = password;
        this.baseURL = baseURL;
    }

    public StringBuilder getAllBugs(String project) throws IOException {
        String searchURL = baseURL + "search";

        URL url = null;
        URLConnection uc = null;
        StringBuilder output = null;
        try {
            String jql = "\"issuetype = bug AND project = " + project + "\"";
            Integer maxResult = stractMaxResult(
                    jiraSearch(searchURL,
                            new String[]{},
                            1,
                            jql));
            output = jiraSearch(searchURL, new String[]{
                            "\"summary\"",
                            "\"status\"",
                            "\"assignee\"",
                            "\"issuetype\""
            },maxResult, jql);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    public List<Version> getAllBugsByRelease(String project) throws IOException {
        String versionsURL = baseURL + "project/" + project + "/versions";
        String searchURL = baseURL + "search";

        URL url = null;
        URLConnection uc = null;
        StringBuilder output = null;
        List<Version> bugsByVersions = new ArrayList<>();
        try {
            output = jiraSearch(versionsURL);
            List<Version> versionsInProject = new JiraParser().parseVersions(output.toString());
            for (Version version : versionsInProject) {
                String bugsByReleaseJQL =
                        "\"issuetype=bug AND project=" + project + " AND affectedVersion='" + version.getName() + "'\"";
                Integer maxResult = stractMaxResult(
                        jiraSearch(searchURL,
                                new String[]{},
                                1,
                                bugsByReleaseJQL));
                StringBuilder bugsByReleaseRAW = jiraSearch(searchURL, new String[]{
                        "\"summary\"",
                        "\"status\"",
                        "\"assignee\"",
                        "\"issuetype\""
                },maxResult, bugsByReleaseJQL);
                List<Issue> issueList = new JiraParser().parseBugs(bugsByReleaseRAW.toString());
                version.setIssueList(issueList);
                bugsByVersions.add(version);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bugsByVersions;
    }



    private Integer stractMaxResult(StringBuilder stringBuilder) {
        String json = stringBuilder.toString();
        String magicWorld = "\"total\":";
        return Integer.parseInt(json.substring(json.indexOf(magicWorld) + magicWorld.length()).split(",")[0]);
    }

    private StringBuilder jiraSearch(String searchURL,
                                     String[] arrayFields,
                                     int maxResult,
                                     String jql) throws IOException {

        URL url;
        URLConnection uc;
        url = new URL(searchURL);

        uc = url.openConnection();


        String userpass = user + ":" + password;
        String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));

        uc.setRequestProperty("X-Requested-With", "Curl");
        uc.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        uc.setRequestProperty("Authorization", basicAuth);

        uc.setDoOutput(true);
        String fields = String.join(",", arrayFields);
        String json = generateJson(maxResult, fields, jql);
        OutputStream os = uc.getOutputStream();
        os.write(json.getBytes("UTF-8"));
        os.close();

        InputStreamReader inputStreamReader = new InputStreamReader(uc.getInputStream());
        final BufferedReader reader = new BufferedReader(inputStreamReader);

        StringBuilder output = generateOutput(reader);
        reader.close();
        return output;
    }

    private StringBuilder jiraSearch(String searchURL) throws IOException {

        URL url;
        URLConnection uc;
        url = new URL(searchURL);

        uc = url.openConnection();


        String userpass = user + ":" + password;
        String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));

        uc.setRequestProperty("X-Requested-With", "Curl");
        uc.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        uc.setRequestProperty("Authorization", basicAuth);

        uc.setDoOutput(true);

        InputStreamReader inputStreamReader = new InputStreamReader(uc.getInputStream());
        final BufferedReader reader = new BufferedReader(inputStreamReader);

        StringBuilder output = generateOutput(reader);
        reader.close();
        return output;
    }

    private StringBuilder generateOutput(BufferedReader reader) throws IOException {


        StringBuilder output = new StringBuilder("");
        String line = null;
        while ((line = reader.readLine()) != null) {
            output.append(line);
        }
        return output;
    }

    private String generateJson(final int maxResult, String fields, String jql) {
        return "{\n" +
                "    \"jql\":" + jql + ",\n" +
                "    \"startAt\": 0,\n" +
                "    \"maxResults\": " + maxResult + ",\n" +
                "    \"fields\": [\n" + fields + "    ],\n" +
                "    \"fieldsByKeys\": false\n" +
                "}";
    }
}


