package acetoys;

//import acetoys.pages.Category;
//import acetoys.pages.StaticPages;
//import acetoys.pages.Products;
//import acetoys.pages.Cart;
//import acetoys.pages.Customer;



import acetoys.simulation.TestPopulation;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class AceToysSimulation extends Simulation {
  private static final String TEST_TYPE = System.getProperty("TEST_TYPE", "INSTANT_USERS");

  private static final String DOMAIN = "acetoys.uk";

  private final HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://" + DOMAIN)
    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("en-US,en;q=0.9");

  //cbg - remove userAgent header, acceptHeader
  //cbg - Remove Header Maps

  {
    if (TEST_TYPE == "INSTANT_USERS") {
      setUp((TestPopulation.InstantUsers)).protocols(httpProtocol);
    } else if (TEST_TYPE == "RAMP_USERS") {
      setUp((TestPopulation.RampUsers)).protocols(httpProtocol);
    } else if (TEST_TYPE == "COMPLEX_INJECTION") {
      setUp((TestPopulation.RampUsers)).protocols(httpProtocol);
    } else {
      setUp((TestPopulation.InstantUsers)).protocols(httpProtocol);
    }
  }
}
