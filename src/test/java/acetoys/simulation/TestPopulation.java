package acetoys.simulation;

import io.gatling.javaapi.core.PopulationBuilder;
import static io.gatling.javaapi.core.CoreDsl.*;

public class TestPopulation {
    public static PopulationBuilder InstantUsers =
        TestScenario.DefaultLoadTest
            .injectOpen(
                nothingFor(5),
                atOnceUsers(10)
            );

    public static PopulationBuilder RampUsers =
        TestScenario.DefaultLoadTest
            .injectOpen(
                nothingFor(5),
                rampUsers(10).during(20)    //Ramp 10 users during a period of 20 seconds
            );

    public static PopulationBuilder ComplexInjection =
        TestScenario.DefaultLoadTest
            .injectOpen(
                nothingFor(5),
                rampUsers(10).during(20)
//                constantUsersPerSec(10).during(20), //.randomized(),
//                rampUsersPerSec(10).to(20).during(20) //.randomized()
            );

    public static PopulationBuilder ClosedModel =
        TestScenario.HighPurchaseLoadTest
            .injectClosed(
                constantConcurrentUsers(10).during(10),
                rampConcurrentUsers(10).to(20).during(20)

            );

}
