package acetoys.pages;

import io.gatling.javaapi.core.ChainBuilder;

import io.gatling.javaapi.core.ChainBuilder;
import org.checkerframework.checker.units.qual.C;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class StaticPages {

    public static ChainBuilder HomePage =
        exec(
            http("01_LoadHomePage")
            .get("/")
            .check(css("#_csrf", "content").saveAs("pCSRF_Token"))
            .check(status().is(200))
            .check(status().not(404), status().not(500), status().not(405))
            .check(substring("<title>Ace Toys Online Shop</title>"))
        );

    public static ChainBuilder OurStory =
        exec(
            http("02_LoadStoryPage")
            .get("/our-story")
            .check(regex("founded online in \\d{4}"))
        );

    public static ChainBuilder GetInTouch =
        exec(
            http("03_LoadGetInTouchPage")
            .get("/get-in-touch")
            .check(substring("we are not actually a real store"))
        );


}
