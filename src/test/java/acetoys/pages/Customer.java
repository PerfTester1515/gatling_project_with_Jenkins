package acetoys.pages;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.Choice;
import io.gatling.javaapi.core.FeederBuilder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class Customer {
    private static Iterator<Map<String,Object>> LoginFeeder =
        Stream.generate((Supplier<Map<String, Object>>) () -> {
            Random rand = new Random();
            int vUserId = rand.nextInt(3)  + 1;

            HashMap<String, Object> hmap = new HashMap<String, Object>();
            hmap.put("sUserId", "user" + vUserId);
            hmap.put("sPassword", "pass");
            return hmap;
        }).iterator();

    public static ChainBuilder LoginCustomer =
        feed(LoginFeeder)
        .exec(session -> {
            System.out.println("*****UserInfo -  " + session.getString("sUserId") +
                ": " + session.getString("sPassword"));
            return session;
            }
        )
        .exec (
            http("13_LoginUser")
            .post("/login")
            .formParam("_csrf", "#{pCSRF_Token}")
            .formParam("username", "#{sUserId}")
            .formParam("password", "#{sPassword}")
            .check(css("#_csrf", "content").saveAs("pCSRF_Token_LoggedIn"))
        )
        .exec(session -> session.set("sCustomerLoggedIn", true)

    );

    public static ChainBuilder LogoutCustomer =
        randomSwitch().on(
            Choice.withWeight(10, exec (
                http("18_Logout")
                .post("/logout")
                .formParam("_csrf", "#{pCSRF_Token_LoggedIn}")
                .check(css("#LoginLink").is("Login"))
            ))
        );
}
