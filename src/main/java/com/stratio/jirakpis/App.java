package com.stratio.jirakpis;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.security.JiraAuthenticationContext;

import java.net.URI;
import java.util.logging.Logger;

public class App {

    private static Logger logger = Logger.getLogger("com.stratio.jirakpis.App");

    public static void main(String[] args) {
//        JerseyJiraRestClientFactory factory = new JerseyJiraRestClientFactory();
//        URI jiraServerUri = new URI("http://localhost:8090/jira");
//        JiraRestClient restClient = factory.create(jiraServerUri, myAuthenticationHandler);

        User user = new Usert
        ComponentAccessor.getJiraAuthenticationContext().setLoggedInUser(user);
        JiraAuthenticationContext context = ComponentAccessor.getJiraAuthenticationContext();
//        if (context == null) logger.warn("No JIRA auth context: maybe it is a plugin system 2 plugin now? Text will be formatted in the JIRA default locale.");
        context.getLoggedInUser();
    }

}
