package acetoys.pages;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.Choice;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class Customer {
    public static ChainBuilder LoginCustomer =
        exec (
            http("13_LoginUser")
            .post("/login").check(css("#_csrf", "content").saveAs("pCSRF_Token_LoggedIn"))
            .formParam("_csrf", "#{pCSRF_Token}")
            .formParam("username", "user1")
            .formParam("password", "pass")
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
