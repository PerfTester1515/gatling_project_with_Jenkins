package acetoys.simulation;

import acetoys.pages.*;
import io.gatling.javaapi.core.ChainBuilder;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static acetoys.session.UserSession.*;
import java.time.Duration;

public class UserJourney {
    private static final Duration LOW_PAUSE = Duration.ofMillis(1000);
    private static final Duration HIGH_PAUSE = Duration.ofMillis(3000);

    public static ChainBuilder BrowseStore =
        exec(InitSession)
        .exec(StaticPages.HomePage)
        .pause(HIGH_PAUSE)
        .exec(StaticPages.OurStory)
        .pause(LOW_PAUSE, HIGH_PAUSE)
        .exec(StaticPages.GetInTouch)
        .pause(LOW_PAUSE, HIGH_PAUSE)
        .repeat(3).on(
            exec(Category.ProductListByCategory)
            .pause(LOW_PAUSE, HIGH_PAUSE)
            .exec(Category.CyclePagesOfProducts)
            .pause(LOW_PAUSE, HIGH_PAUSE)
            .exec(Products.LoadProductsDetailsPage)
        );

    public static ChainBuilder AbandonBasket =
        exec(InitSession)
        .exec(StaticPages.HomePage)
        .pause(HIGH_PAUSE)
        .exec(Category.ProductListByCategory)
        .pause(LOW_PAUSE, HIGH_PAUSE)
        .exec(Products.LoadProductsDetailsPage)
        .pause(LOW_PAUSE, HIGH_PAUSE)
        .exec(Products.AddProductToCart);

    public static ChainBuilder CompletePurchase =
        exec(InitSession)
            .exec(StaticPages.HomePage)
            .pause(HIGH_PAUSE)
            .exec(Category.ProductListByCategory)
            .pause(LOW_PAUSE, HIGH_PAUSE)
            .exec(Products.LoadProductsDetailsPage)
            .pause(LOW_PAUSE, HIGH_PAUSE)
            .exec(Products.AddProductToCart)
            .pause(LOW_PAUSE, HIGH_PAUSE)
            .exec(Cart.ViewCart)
            .pause(LOW_PAUSE, HIGH_PAUSE)
            .exec(Cart.IncreaseQuantityInCart)
            .pause(LOW_PAUSE)
            .exec(Cart.DecreaseQuantityInCart)
            .pause(LOW_PAUSE)
            .exec(Cart.CheckoutCart)
            .pause(LOW_PAUSE, HIGH_PAUSE)
            .exec(Customer.LogoutCustomer);

}
