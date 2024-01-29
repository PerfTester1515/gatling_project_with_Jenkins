package acetoys.simulation;

import io.gatling.javaapi.core.PopulationBuilder;
import static io.gatling.javaapi.core.CoreDsl.*;
import java.time.Duration;

public class TestPopulation {
    private static final int USER_COUNT = Integer.parseInt(System.getProperty("Users", "5"));
    private static final Duration RAMP_DURATION = Duration.ofSeconds(Integer.parseInt(System.getProperty("RAMP_DURATION", "10")));
    public static PopulationBuilder InstantUsers =
        TestScenario.DefaultLoadTest
            .injectOpen(
                nothingFor(5),
                atOnceUsers(USER_COUNT)
            );

    public static PopulationBuilder RampUsers =
        TestScenario.DefaultLoadTest
            .injectOpen(
                nothingFor(5),
                rampUsers(USER_COUNT).during(RAMP_DURATION)    //Ramp 10 users during a period of 20 seconds
            );

    public static PopulationBuilder ComplexInjection =
        TestScenario.DefaultLoadTest
            .injectOpen(
                nothingFor(5),
                constantUsersPerSec(10).during(20), //.randomized(),
                rampUsersPerSec(10).to(20).during(20) //.randomized()
            );

    public static PopulationBuilder ClosedModel =
        TestScenario.HighPurchaseLoadTest
            .injectClosed(
                constantConcurrentUsers(10).during(10),
                rampConcurrentUsers(10).to(20).during(20)

            );

}
