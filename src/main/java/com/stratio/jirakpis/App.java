package com.stratio.jirakpis;

import com.atlassian.jira.bc.user.ApplicationUserBuilder;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.codec.binary.Base64;
import java.util.logging.Logger;

public class App {

    private static Logger logger = Logger.getLogger("com.stratio.jirakpis.App");

    public static void main(String[] args) {
//        JerseyJiraRestClientFactory factory = new JerseyJiraRestClientFactory();
//        URI jiraServerUri = new URI("http://localhost:8090/jira");
//        JiraRestClient restClient = factory.create(jiraServerUri, myAuthenticationHandler);

//        final AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
//        URI url = new URI(link);
//        JiraRestClient restClient = factory.createWithBasicHttpAuthentication(url, user, pw);


        String stringUrl = "https://stratio.atlassian.net/rest/api/2/issue/PLAT-138";
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection uc = null;
        try {
            uc = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        uc.setRequestProperty("X-Requested-With", "Curl");

        String userpass = "username" + ":" + "password";
        String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));
        uc.setRequestProperty("Authorization", basicAuth);

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(uc.getInputStream());
            final BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // read this input
    }

}
