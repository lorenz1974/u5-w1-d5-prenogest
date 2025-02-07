package lorenz.prenogest.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lorenz.prenogest.entities.PlaceOfWork;
import lorenz.prenogest.entities.PlaceType;
import org.springframework.context.annotation.Scope;

@Configuration
public class PlaceOfWorkConfig {

    @Bean
    @Scope("prototype")
    public PlaceOfWork genericPOW() {
        PlaceOfWork placeOfWork = new PlaceOfWork();
        placeOfWork.setDescription("Generic Place of Work");
        placeOfWork.setType(PlaceType.PRIVAT);
        placeOfWork.setPeopleCapacity(1);
        return placeOfWork;
    }

    @Bean
    @Scope("prototype")
    public PlaceOfWork genericMeetingRoom() {
        PlaceOfWork placeOfWork = new PlaceOfWork();
        placeOfWork.setDescription("Generic Meeting Room");
        placeOfWork.setType(PlaceType.MEETING_ROOM);
        placeOfWork.setPeopleCapacity(10);
        return placeOfWork;
    }

    @Bean
    @Scope("prototype")
    public PlaceOfWork genericOpenSpace() {
        PlaceOfWork placeOfWork = new PlaceOfWork();
        placeOfWork.setDescription("Generic Open Space");
        placeOfWork.setType(PlaceType.OPENSPACE);
        placeOfWork.setPeopleCapacity(60);
        return placeOfWork;
    }
}
