package acetoys.pages;

import io.gatling.javaapi.core.ChainBuilder;;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;


public class Products {
    public static ChainBuilder LoadProductsDetailsPage_Dartboards =
            exec(
                http("07_LoadProductsDetailsPage - Product: DartsBoard")
                .get("/product/darts-board")
                .check(css("#ProductDescription").is("Get all your mates round for a few drinks and throw sharp objects at this darts board"))
            );

    public static ChainBuilder AddProductToCart_Product19 =
            exec(
                http("08_AddProductToCart - Product 19")
                .get("/cart/add/19")
                .check(substring("You have <span>1</span> products in your Basket."))
            );
    public static ChainBuilder AddProductToCart_Product4 =
            exec(
                http("08_AddProductToCart - Product 4")
                .get("/cart/add/4")
                .check(substring("products in your Basket"))
            );
    public static ChainBuilder AddProductToCart_Product5 =
            exec(
                http("08_AddProductToCart - Product 5")
                .get("/cart/add/5")
                .check(substring("products in your Basket"))
            );
}
