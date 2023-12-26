package acetoys;

import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class AceToysSimulation extends Simulation {

  private static final String DOMAIN = "acetoys.uk";

  private final HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://" + DOMAIN)
    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("en-US,en;q=0.9");

  //cbg - remove userAgent header, acceptHeader
  //cbg - Remove Header Maps

  private final ScenarioBuilder scn = scenario("AceToysSimulation")
    .exec(
      http("01_LoadHomePage")
        .get("/")
              .check(css("#_csrf", "content").saveAs("pCSRF_Token"))
              .check(status().is(200))
              .check(status().not(404), status().not(500), status().not(405))
              .check(substring("<title>Ace Toys Online Shop</title>"))
    )
    .exec(session -> {
        System.out.println("*****CSRF_Token: " + session.getString("pCSRF_Token"));
        return session;
      }
    )
    .pause(1)
    .exec(
      http("02_LoadStoryPage")
        .get("/our-story")
              .check(regex("founded online in \\d{4}"))
    )
    .pause(1)
    .exec(
      http("03_LoadGetInTouchPage")
        .get("/get-in-touch")
    )
    .pause(1)
    .exec(
      http("04_LoadProductsListPage - Category: AllProducts")
        .get("/category/all")
    )
    .pause(1)
    .exec(
      http("05_LoadNextPage - Page 1")
        .get("/category/all?page=1")
    )
    .pause(1)
    .exec(
      http("06_LoadNextPage - Page 2")
        .get("/category/all?page=2")
    )
    .pause(1)
    .exec(
      http("07_LoadProductsDetailsPage - Product: DartsBoard")
        .get("/product/darts-board")
    )
    .pause(1)
    .exec(
      http("08_AddProductToCart - Product 19")
        .get("/cart/add/19")
    )
    .pause(1)
    .exec(
      http("09_LoadProductsListPage - Category: BabiesToys")
        .get("/category/babies-toys")
    )
    .pause(1)
    .exec(
      http("10_AddProductToCart - Product 4")
        .get("/cart/add/4")
    )
    .pause(1)
    .exec(
      http("11_AddProductToCart - Product 4")
        .get("/cart/add/4")
    )
    .pause(1)
    .exec(
      http("12_ViewCart")
        .get("/cart/view")
    )
    .pause(1)
    .exec(
      http("13_LoginUser")
        .post("/login").check(css("#_csrf", "content").saveAs("pCSRF_Token_LoggedIn"))
        .formParam("_csrf", "#{pCSRF_Token}")
        .formParam("username", "user1")
        .formParam("password", "pass")
    )

    .exec(session -> {
              System.out.println("*****CSRF_Token_LoggedIn: " + session.getString("pCSRF_Token_LoggedIn"));
              return session;
            }
    )
    .pause(1)
    .exec(
      http("14_AddQuantityInCart - Product 19")
        .get("/cart/add/19?cartPage=true")
    )
    .pause(1)
    .exec(
      http("15_AddQuantityInCart - Product 19")
        .get("/cart/add/19?cartPage=true")
    )
    .pause(1)
    .exec(
      http("16_SubtractQuantityInCart - Product 19")
        .get("/cart/subtract/19")
    )
    .pause(1)
    .exec(
      http("17_CheckOut")
        .get("/cart/checkout")
              .check(substring("products are on their way to you"))
    )
    .pause(1)
    .exec(
      http("18_Logout")
        .post("/logout")
        .formParam("_csrf", "#{pCSRF_Token_LoggedIn}")
    );

  {
	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}
