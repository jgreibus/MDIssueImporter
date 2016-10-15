package issue_tracker_integration.issues.issueServices;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
/**
 * Created by justinasg on 2016-10-08.
 */
public class getIssues {


    Client client = ClientBuilder.newClient();
    Response response = client.target("http://www.easyredmine.com/undefined")
            .request(MediaType.TEXT_PLAIN_TYPE)
            .get();

    System.out.println("status: " + response.getStatus());
    System.out.println("headers: " + response.getHeaders());
    System.out.println("body:" + response.readEntity(String.class));
}
