package acetoys;

import acetoys.pages.Category;
import acetoys.pages.StaticPages;
import acetoys.pages.Products;
import acetoys.pages.Cart;
import acetoys.pages.Customer;


import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

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
    .exec(StaticPages.HomePage)
    .exec(session -> {
        System.out.println("*****CSRF_Token: " + session.getString("pCSRF_Token"));
        return session;
      }
    )
    .pause(1)
    .exec(StaticPages.OurStory)
    .pause(1)
    .exec(StaticPages.GetInTouch)
    .pause(1)
    .exec(Category.ProductListByCategory_AllProducts)
    .pause(1)
    .exec(Category.LoadSecondPageOfProducts)
    .pause(1)
    .exec(Category.LoadThirdPageOfProducts)
    .pause(1)
    .exec(Products.LoadProductsDetailsPage_Dartboards)
    .pause(1)
    .exec(Products.AddProductToCart_Product19)
    .pause(1)
    .exec(Category.ProductListByCategory_BabiesToys)
    .pause(1)
    .exec(Products.AddProductToCart_Product4)
    .pause(1)
    .exec(Products.AddProductToCart_Product5)
    .pause(1)
    .exec(Cart.ViewCart)
    .pause(1)
    .exec(Customer.LoginCustomer)
    .exec(session -> {
              System.out.println("*****CSRF_Token_LoggedIn: " + session.getString("pCSRF_Token_LoggedIn"));
              return session;
            }
    )
    .pause(1)
    .exec(Cart.IncreaseQuantityInCart)
    .pause(1)
    .exec(Cart.DecreaseeQuantityInCart)
    .pause(1)
    .exec(Cart.DecreaseeQuantityInCart)
    .pause(1)
    .exec(Cart.CheckoutCart)
    .pause(1)
    .exec(Customer.LogoutCustomer);


  {
	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}
