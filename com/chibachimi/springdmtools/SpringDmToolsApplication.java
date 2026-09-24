package com.chibachimi.springdmtools;

import com.chibachimi.springdmtools.createdfiles.Defaults;
import com.chibachimi.springdmtools.createdfiles.OsChecker;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.ColorScheme;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

@ColorScheme(ColorScheme.Value.DARK)
@SpringBootApplication
public class SpringDmToolsApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        //SpringApplication.run(SpringDmToolsApplication.class, args);
        SpringApplicationBuilder builder = new SpringApplicationBuilder(SpringDmToolsApplication.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);

        OsChecker checker = new OsChecker();
        // TODO Something feels weird about this
        // TODO In the future I think we should get this info from a file we create, and if its not there
        // Or something goes wrong, we can just redo it. But doing this on every start up feels annoying and slow
        Defaults defaultsCreator = new Defaults(checker);
    }
}
