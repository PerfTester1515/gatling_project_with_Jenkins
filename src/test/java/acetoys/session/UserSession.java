package acetoys.session;

import io.gatling.javaapi.core.ChainBuilder;

import java.text.DecimalFormat;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class UserSession {

    private static final DecimalFormat df = new DecimalFormat("0.00");
    public static ChainBuilder InitSession =
        exec(flushCookieJar())
            .exec(session -> session.set("sProductsListPageNumber", 1))
            .exec(session -> session.set("sItemsInBasket", 0))
            .exec(session -> session.set("sBasketTotal", 0.00))
            .exec(session -> session.set("sCustomerLoggedIn", false));


    public static ChainBuilder IncreaseItemsInBasketForSession =
        exec(session -> {
            int vItemsInBasket = session.getInt("sItemsInBasket");
            return session.set("sItemsInBasket", (vItemsInBasket + 1));
        });

    public static ChainBuilder IncreaseSessionBasketTotal  =
        exec(session -> {
            double vCurrentBasketTotal = session.getDouble("sBasketTotal");
            double vItemPrice = session.getDouble("dPrice");
            double vUpdatedBasketTotal = vCurrentBasketTotal + vItemPrice;
            return session.set("sBasketTotal", df.format(vUpdatedBasketTotal));
        });

    public static ChainBuilder DecreaseItemsInBasketForSession =
        exec(session -> {
            int vItemsInBasket = session.getInt("sItemsInBasket");
            return session.set("sItemsInBasket", (vItemsInBasket - 1));
        });

    public static ChainBuilder DecreaseSessionBasketTotal  =
        exec(session -> {
            double vCurrentBasketTotal = session.getDouble("sBasketTotal");
            double vItemPrice = session.getDouble("dPrice");
            double vUpdatedBasketTotal = vCurrentBasketTotal - vItemPrice;
            return session.set("sBasketTotal", df.format(vUpdatedBasketTotal));
        });
}
