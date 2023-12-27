package acetoys.pages;

import io.gatling.javaapi.core.ChainBuilder;
import org.checkerframework.checker.units.qual.C;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;


public class Category {
    public static ChainBuilder ProductListByCategory_AllProducts =
        exec(
            http("04_LoadProductsListPage - Category: AllProducts")
            .get("/category/all")
            .check(css("#CategoryName").is("All Products"))
        );

    public static ChainBuilder LoadSecondPageOfProducts =
        exec(
            http("05_LoadNextPage - Page 1")
            .get("/category/all?page=1")
            .check(css(".page-item.active").is("2"))
        );
    public static ChainBuilder LoadThirdPageOfProducts =
        exec(
            http("05_LoadNextPage - Page 2")
            .get("/category/all?page=2")
            .check(css(".page-item.active").is("3"))
        );

    public static ChainBuilder ProductListByCategory_BabiesToys =
        exec(
            http("04_LoadProductsListPage - Category: BabiesToys")
            .get("/category/babies-toys")
            .check(css("#CategoryName").is("Babies Toys"))
        );


}
