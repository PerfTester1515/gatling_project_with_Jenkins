package acetoys.pages;

import io.gatling.javaapi.core.ChainBuilder;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static acetoys.session.UserSession.*;

public class Cart {
    public static  ChainBuilder ViewCart =
        doIf(session -> !session.getBoolean("sCustomerLoggedIn"))
        .then(exec(Customer.LoginCustomer))
        .exec(
            http("12_ViewCart")
            .get("/cart/view")
            .check(css("#CategoryHeader").is("Cart Overview"))
        );

    public static  ChainBuilder IncreaseQuantityInCart =
        exec(IncreaseItemsInBasketForSession)
        .exec(IncreaseSessionBasketTotal)
        .exec(
            http("14_AddQuantityInCart - Product Name: #{dName}")
            .get("/cart/add/#{dId}?cartPage=true")
            .check(css("#grandTotal").isEL("$#{sBasketTotal}"))
        )
        .exec(IncreaseSessionBasketTotal);


    public static  ChainBuilder DecreaseQuantityInCart =
        exec(DecreaseItemsInBasketForSession)
        .exec(
            http("16_SubtractQuantityInCart - Product Name: #{dName}")
            .get("/cart/subtract/#{dId}")
            .check(css("#grandTotal").isEL("$#{sBasketTotal}"))
        );

    public static  ChainBuilder CheckoutCart =
        exec(
            http("17_CheckOut")
            .get("/cart/checkout")
            .check(substring("products are on their way to you"))
        );
}

