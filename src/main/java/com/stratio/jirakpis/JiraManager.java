package com.stratio.jirakpis;

import com.stratio.jirakpis.model.Issue;
import com.stratio.jirakpis.model.JiraSearch;
import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.stream.Collectors;


public class JiraManager {

    private final String baseURL;
    private String user;
    private String password;

    public JiraManager(String baseURL, String user, String password){
        this.user = user;
        this.password = password;
        this.baseURL = baseURL;
    }

    public List<Issue> getAllBugs(String project) throws IOException {
        String searchURL = baseURL+"search";

        URL url = null;
        URLConnection uc = null;
        StringBuilder output = null;
        List<Issue> issueList = null;
        try {
            Integer maxResult = stractMaxResult(jiraSearch(project, searchURL,new String[]{} , 1));
            output = jiraSearch(project, searchURL, new String[]{"\"summary\"", "\"status\"", "\"assignee\"",
                            "\"issuetype\""},
                    maxResult);

            ObjectMapper mapper = new ObjectMapper();

            JiraSearch jiraSearch = mapper.readValue(output.toString().getBytes(), JiraSearch.class);

            issueList = jiraSearch.getIssues()
                    .stream()
                    .filter(issue -> issue.getFields().getIssueType().getName().equals("Bug"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return issueList;
    }

    private Integer stractMaxResult(StringBuilder stringBuilder) {
        String json = stringBuilder.toString();
        String magicWorld = "\"total\":";
        return Integer.parseInt(json.substring(json.indexOf(magicWorld)+magicWorld.length()).split(",")[0]);
    }

    private StringBuilder jiraSearch(String project, String searchURL,
                                     String[] arrayFields, int maxResult) throws IOException {

        URL url;
        URLConnection uc;
        url = new URL(searchURL);

        uc = url.openConnection();


        String userpass = user + ":" + password;
        System.out.println(userpass);
        String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));

        uc.setRequestProperty("X-Requested-With", "Curl");
        uc.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        uc.setRequestProperty("Authorization", basicAuth);

        uc.setDoOutput(true);
        String fields = String.join(",", arrayFields);
        String json = generateJson(project, maxResult, fields);
        OutputStream os = uc.getOutputStream();
        os.write(json.getBytes("UTF-8"));
        os.close();

        InputStreamReader inputStreamReader = new InputStreamReader(uc.getInputStream());
        final BufferedReader reader = new BufferedReader(inputStreamReader);

        StringBuilder output = generateOutput(reader);
        reader.close();
        return output;
    }

    private StringBuilder  generateOutput(BufferedReader reader) throws IOException {
        StringBuilder  output = new StringBuilder("");
        String line = null;
        while ((line = reader.readLine()) != null) {
            output.append(line);
        }
        return output;
    }

    private String generateJson(String project, final int maxResult, String fields) {
        return "{\n" +
                "    \"jql\": \"project = " + project + "\",\n" +
                "    \"startAt\": 0,\n" +
                "    \"maxResults\": " + maxResult + ",\n" +
                "    \"fields\": [\n" +
                fields +
                "    ],\n" +
                "    \"fieldsByKeys\": false\n" +
                "}";
    }
}
