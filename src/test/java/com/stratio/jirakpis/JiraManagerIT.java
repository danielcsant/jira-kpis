package com.stratio.jirakpis;

import com.stratio.jirakpis.model.Issue;
import com.stratio.jirakpis.model.JiraSearch;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class JiraManagerIT {

    private String user = "";
    private String pass = "";

    @Test
    public void modelMappingTest() throws IOException {
        JiraManager jiraManager = new JiraManager
                (App.BASE_URL,user,pass);
        ObjectMapper mapper = new ObjectMapper();

        List<Issue> issueList = jiraManager.getAllBugs("DCS");

        Assert.assertEquals("Bug", issueList.get(0).getFields().getIssueType().getName());
    }

}
