package acetoys.simulation;

import io.gatling.javaapi.core.Choice;
import io.gatling.javaapi.core.ScenarioBuilder;
import static io.gatling.javaapi.core.CoreDsl.*;

import java.time.Duration;

public class TestScenario {
    private static final Duration TEST_DURATION = Duration.ofSeconds(Integer.parseInt(System.getProperty("DURATION", "60")));


    public static ScenarioBuilder DefaultLoadTest =
        scenario("DefaultLoadTest")
            .during(TEST_DURATION)
            .on(
                randomSwitch()
                    .on(
                        Choice.withWeight(60, exec(UserJourney.BrowseStore)),
                        Choice.withWeight(30, exec(UserJourney.AbandonBasket)),
                        Choice.withWeight(10, exec(UserJourney.CompletePurchase))
                    )
            );

    public static ScenarioBuilder HighPurchaseLoadTest =
        scenario("HighPurchaseLoadTest")
            .during(TEST_DURATION)
            .on(
                randomSwitch()
                    .on(
                        Choice.withWeight(30, exec(UserJourney.BrowseStore)),
                        Choice.withWeight(30, exec(UserJourney.AbandonBasket)),
                        Choice.withWeight(40, exec(UserJourney.CompletePurchase))


                    )
            );
}
