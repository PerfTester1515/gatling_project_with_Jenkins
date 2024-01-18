package acetoys.pages;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import java.util.*;

public class Category {
    private static final FeederBuilder<String> CategoryFeeder =
        csv("data/CategoryDetails.csv").circular();
    public static ChainBuilder ProductListByCategory =
        feed(CategoryFeeder)
        .exec(
            http("LoadProductsListPage")
            .get("/category/#{dCategorySlug}")
            .check(css("#CategoryName").isEL("#{dCategoryName}")));

    public static ChainBuilder CyclePagesOfProducts =
        exec(session -> {
            int vCurrentPageNumber = session.getInt("sProductsListPageNumber");
            int vTotalPages = session.getInt("dCategoryPages");
            boolean vMorePages = vCurrentPageNumber < vTotalPages;
            return session.setAll(Map.of(
                "sCurrentPageNumber", vCurrentPageNumber,
                "sNextPageNumber", (vCurrentPageNumber + 1),
                "sMorePages", vMorePages));
        })
        .asLongAs("#{sMorePages}").on(
            exec(http("Load Page #{sCurrentPageNumber} of Products")
                .get("/category/#{dCategorySlug}?page=#{sCurrentPageNumber}")
                .check(bodyString().saveAs("sResponseBody"))
                .check(css(".page-item.active").isEL("#{sNextPageNumber}")))
            .exec(session -> {
                int vCurrentPageNumber = session.getInt("sCurrentPageNumber") + 1;
                int vTotalPages = session.getInt("dCategoryPages");
                boolean vMorePages = (vCurrentPageNumber < vTotalPages);
//            System.out.println("Response Body:");
//            System.out.println(session.getString("sResponseBody"));
                return session.setAll(Map.of(
                    "sCurrentPageNumber", vCurrentPageNumber,
                    "sNextPageNumber", (vCurrentPageNumber + 1),
                    "sMorePages", vMorePages));
            })
        );

}
