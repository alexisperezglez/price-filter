package es.project.pricefilter.cucumberglue;

import io.cucumber.spring.CucumberTestContext;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(CucumberTestContext.SCOPE_CUCUMBER_GLUE)
public class BaseStepDefs {

    @LocalServerPort
    int port;
}
