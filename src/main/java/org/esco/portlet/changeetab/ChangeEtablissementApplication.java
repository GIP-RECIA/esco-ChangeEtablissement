package org.esco.portlet.changeetab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@SpringBootApplication
@ImportResource(locations = {"classpath:context/*.xml"})
public class ChangeEtablissementApplication {

    @Autowired
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer;

    public static void main(String[] args) {
        SpringApplication.run(ChangeEtablissementApplication.class, args);
    }

}
