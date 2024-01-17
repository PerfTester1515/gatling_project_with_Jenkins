package acetoys;

//import acetoys.pages.Category;
//import acetoys.pages.StaticPages;
//import acetoys.pages.Products;
//import acetoys.pages.Cart;
//import acetoys.pages.Customer;


import acetoys.session.UserSession;

import acetoys.simulation.TestPopulation;
import acetoys.simulation.TestScenario;
import acetoys.simulation.UserJourney;
import akka.actor.setup.Setup;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import io.gatling.javaapi.core.PopulationBuilder;

public class AceToysSimulation extends Simulation {

  private static final String DOMAIN = "acetoys.uk";

  private final HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://" + DOMAIN)
    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("en-US,en;q=0.9");

  //cbg - remove userAgent header, acceptHeader
  //cbg - Remove Header Maps

  {
    setUp(TestPopulation.ComplexInjection).protocols(httpProtocol).maxDuration(60)
  }
}
