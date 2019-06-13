package de.assentis.skype;

import de.assentis.skype.configuration.ApplicationConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        final AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
    }
}
