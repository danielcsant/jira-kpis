package com.stratio.jirakpis;

import com.stratio.jirakpis.model.Issue;
import com.stratio.jirakpis.model.JiraSearch;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class JiraManagerIT {

        String jsonBugs = "{\"expand\":\"names,schema\",\"startAt\":0,\"maxResults\":1,\"total\":348,\"issues\":[{\"expand\":\"operations,versionedRepresentations,editmeta,changelog,renderedFields\",\"id\":\"46018\",\"self\":\"https://stratio.atlassian.net/rest/api/latest/issue/46018\",\"key\":\"DCS-2751\",\"fields\":{\"summary\":\"Marathon port is exposed without authentication\",\"issuetype\":{\"self\":\"https://stratio.atlassian.net/rest/api/2/issuetype/1\",\"id\":\"1\",\"description\":\"A problem which impairs or prevents the functions of the product that relates to a Story from previous Sprints\",\"iconUrl\":\"https://stratio.atlassian.net/secure/viewavatar?size=xsmall&avatarId=10303&avatarType=issuetype\",\"name\":\"Bug\",\"subtask\":false,\"avatarId\":10303},\"assignee\":null,\"status\":{\"self\":\"https://stratio.atlassian.net/rest/api/2/status/10200\",\"description\":\"\",\"iconUrl\":\"https://stratio.atlassian.net/images/icons/subtask.gif\",\"name\":\"Created\",\"id\":\"10200\",\"statusCategory\":{\"self\":\"https://stratio.atlassian.net/rest/api/2/statuscategory/2\",\"id\":2,\"key\":\"new\",\"colorName\":\"blue-gray\",\"name\":\"To Do\"}}}}]}\n";
    @Test
    public void modelMappingTest() throws IOException {


      JiraParser parser = new JiraParser();
        List<Issue> issueList =  parser.parseBugs(jsonBugs);

        Assert.assertEquals("Bug", issueList.get(0).getFields().getIssueType().getName());
    }

}
