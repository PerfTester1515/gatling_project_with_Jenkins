package acetoys.pages;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static acetoys.session.UserSession.*;


public class Products {

    private static final FeederBuilder<Object> ProductFeeder =
        jsonFile("data/ProductDetails.json").random();

    public static ChainBuilder LoadProductsDetailsPage =
            feed(ProductFeeder)
//            .exec(session -> {
//                    System.out.println("*****ProductInfo -  " + session.getString("dName") +
//                        ": " + session.getString("dSlug"));
//                    return session;
//                }
//            )
            .exec(
                http("LoadProductsDetailsPage")
                .get("/product/#{dSlug}")
                .check(css("#ProductDescription").isEL("#{dDescription}"))
            );

    public static ChainBuilder AddProductToCart =
             exec(IncreaseItemsInBasketForSession)
             .exec(
                http("AddProductToCart")
                .get("/cart/add/#{dId}")
                .check(substring("You have <span>#{sItemsInBasket}</span>"))   //
                    .check(bodyString().saveAs("sResponseBody"))
            )
             .exec(IncreaseSessionBasketTotal);
}
