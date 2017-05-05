package com.stratio.jirakpis;


import com.stratio.jirakpis.model.Issue;
import com.stratio.jirakpis.model.JiraSearch;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
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
}
