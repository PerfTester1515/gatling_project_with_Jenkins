package acetoys.simulation;

import acetoys.session.UserSession;
import io.gatling.javaapi.core.Choice;
import io.gatling.javaapi.core.ScenarioBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;

public class TestScenario {
    public static ScenarioBuilder DefaultLoadTest =
        scenario("DefaultLoadTest")
            .during(30)
            .on(
                randomSwitch()
                    .on(
                        Choice.withWeight(60, exec(UserJourney.BrowseStore)),
                        Choice.withWeight(30, exec(UserJourney.AbandonBasket)),
                        Choice.withWeight(10, exec(UserJourney.CompletePurchase))
                    )
            );

    public static ScenarioBuilder HighPurchaseLoadTest =
        scenario("DefaultLoadTest")
            .during(30)
            .on(
                randomSwitch()
                    .on(
                        Choice.withWeight(30, exec(UserJourney.BrowseStore)),
                        Choice.withWeight(30, exec(UserJourney.AbandonBasket)),
                        Choice.withWeight(40, exec(UserJourney.CompletePurchase))


                    )
            );
}
