package acetoys.pages;

import io.gatling.javaapi.core.ChainBuilder;;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class Cart {
    public static  ChainBuilder ViewCart =
        exec(
            http("12_ViewCart")
            .get("/cart/view")
 //           .check(css("#CategoryHeader").is("Cart Overview"))
        );

    public static  ChainBuilder IncreaseQuantityInCart =
        exec(
            http("14_AddQuantityInCart - Product 19")
            .get("/cart/add/19?cartPage=true")
        );


    public static  ChainBuilder DecreaseeQuantityInCart =
        exec(
            http("16_SubtractQuantityInCart - Product 19")
            .get("/cart/subtract/19")
        );

    public static  ChainBuilder CheckoutCart =
        exec(
            http("17_CheckOut")
            .get("/cart/checkout")
            .check(substring("products are on their way to you"))
        );
}

