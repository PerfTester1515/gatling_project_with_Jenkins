package acetoys.pages;

import io.gatling.javaapi.core.ChainBuilder;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class StaticPages {

    public static ChainBuilder HomePage =
        exec(
            http("LoadHomePage")
            .get("/")
            .check(css("#_csrf", "content").saveAs("pCSRF_Token"))
            .check(status().is(200))
            .check(status().not(404), status().not(500), status().not(405))
            .check(substring("<title>Ace Toys Online Shop</title>"))
        );

    public static ChainBuilder OurStory =
        exec(
            http("LoadStoryPage")
            .get("/our-story")
            .check(regex("founded online in \\d{4}"))
        );

    public static ChainBuilder GetInTouch =
        exec(
            http("LoadGetInTouchPage")
            .get("/get-in-touch")
            .check(substring("we are not actually a real store"))
        );


}
