package es.project.pricefilter.cucumberglue;

import es.project.pricefilter.PriceFilterApplication;
import groovy.util.logging.Slf4j;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@SpringBootTest(classes = PriceFilterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Profile("test")
@Slf4j
public class CucumberSpringConfiguration {
}
