package es.project.pricefilter.cucumberglue;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue = {"es.project.pricefilter.cucumberglue"},
        plugin = {"pretty", "html:target/cucumber.html", "json:target/cucumber.json"}
)
public class CucumberRunnerTest {
}
