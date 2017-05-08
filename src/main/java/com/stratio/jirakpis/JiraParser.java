package com.stratio.jirakpis;


import com.stratio.jirakpis.model.Issue;
import com.stratio.jirakpis.model.JiraSearch;
import com.stratio.jirakpis.model.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JiraParser {

    public List<Issue> parseBugs(String jsonBugs) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        JiraSearch jiraSearch = mapper.readValue(jsonBugs.getBytes(), JiraSearch.class);

        List<Issue> issueList = jiraSearch.getIssues()
                .stream()
                .collect(Collectors.toList());
        return issueList;
    }

    public List<Version> parseVersions(String jsonVersions) {
        ObjectMapper mapper = new ObjectMapper();

        List<Version> result = new ArrayList<>();
        try {
            result = mapper.readValue(jsonVersions.getBytes(), new TypeReference<List<Version>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
