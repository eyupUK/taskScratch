package com.scratchpay.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                //"pretty",
                "json:target/cucumber.json",
                "html:target/cucumber-report.html",
                "rerun:target/rerun.txt",
                "me.jvt.cucumber.report.PrettyReports:target/cucumberPrettyHTML",
        },
        features = "src/test/resources/Features",
        glue = "com/scratchpay/stepdefinitions",
        dryRun = false,
        tags = ""
)
public class CukesRunner {
}
