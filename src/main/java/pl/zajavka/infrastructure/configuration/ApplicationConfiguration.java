package pl.zajavka.infrastructure.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.zajavka.ComponentScanMarker;

@Configuration
@ComponentScan(basePackageClasses = ComponentScanMarker.class)
@Import(PersistenceJpaConfiguration.class)
public class ApplicationConfiguration {
}
