package lorenz.prenogest.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lorenz.prenogest.entities.Building;

@Configuration
public class BuildingConfig {

    @Bean
    public Building Building1() {

        Building building = new Building();
        building.setAddress("Via Roma 1");
        building.setCity("Milano");

        return building;
    }

    @Bean
    public Building Building2() {

        Building building = new Building();
        building.setAddress("Via Milano 2");
        building.setCity("Roma");

        return building;
    }
}
