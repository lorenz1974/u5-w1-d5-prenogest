package lorenz.prenogest.runners;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.boot.CommandLineRunner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lorenz.prenogest.entities.*;
import lorenz.prenogest.exceptions.EntityException;
import lorenz.prenogest.repositories.*;
import lorenz.prenogest.services.ReservationService;
import lorenz.prenogest.services.UserService;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(1)
public class CreateDBRunner implements CommandLineRunner {
    private final Faker faker;

    private final UserRepository userRepository;
    private final BuildingRepository buildingRepository;
    private final PlaceOfWorkRepository placeOfWorkRepository;

    private final UserService userService;
    private final ReservationService reservationService;

    private Random random = new Random();

    private final int numberOfFakeUsers = 15;
    private final int numberOfFakeBuildings = 10;
    private final int numberOfFakePlacesOfWork = 25;
    private final int numberOfFakeReservations = 300;

    @Override
    @Transactional
    public void run(String... args) throws Exception, EntityException {

        System.out.println("\n".repeat(50));

        log.info("Generate {} fake users...", numberOfFakeUsers);
        for (int i = 0; i < numberOfFakeUsers; i++) {
            User user = new User();

            String username = faker.internet().emailAddress();

            user.setUsername(username);
            user.setEmail(username);
            user.setPassword(faker.internet().password());

            log.debug(user.toString());

            userService.save(user);
        }

        log.info("Generating Place of Works and Buildings...");
        for (int i = 0; i < numberOfFakeBuildings; i++) {

            log.info("Generate the Building...");
            Building building = new Building();
            building.setName("Building " + faker.superhero().name());
            building.setAddress(faker.address().streetAddress());
            building.setCity(faker.address().city());

            // Save the building with places of work
            // !!! Devo salvare il building altrimenti quando vado a salvare il PlaceOfWork
            // non riesce a creare correttamente la selazione !!!
            buildingRepository.save(building);

            List<PlaceOfWork> placesOfWork = new ArrayList<>();

            log.info("Generate {} Places of Work...", numberOfFakePlacesOfWork);

            // Generate the places of work
            for (int j = 0; j < numberOfFakePlacesOfWork; j++) {

                PlaceOfWork placeOfWork = new PlaceOfWork();

                int rndm = random.nextInt(1, 4);
                switch (rndm) {
                    case 1:
                        placeOfWork.setType(PlaceType.PRIVAT);
                        placeOfWork.setPeopleCapacity(1);
                        placeOfWork.setDescription("POW n. " + j);
                        break;
                    case 2:
                        placeOfWork.setType(PlaceType.OPENSPACE);
                        placeOfWork.setPeopleCapacity(faker.number().numberBetween(2, 100));
                        placeOfWork.setDescription(
                                "Open Space n. " + j + " for " + placeOfWork.getPeopleCapacity() + " people");
                        break;
                    case 3:
                        placeOfWork.setType(PlaceType.MEETING_ROOM);
                        placeOfWork.setPeopleCapacity(faker.number().numberBetween(2, 12));
                        placeOfWork.setDescription(
                                "Meeting Room n. " + j + " for " + placeOfWork.getPeopleCapacity() + " people");
                        break;
                    default:
                        break;

                }

                // !!! Devo associare il buonging al placeOfWork di riferimento in modo che
                // quando lo va a salvare crea correttamente la relazione nel db !!!
                placeOfWork.setBuilding(building);

                // Save the place of work
                placeOfWorkRepository.save(placeOfWork);
            }

            log.debug(building.toString());

        }

        log.info("Generating Reservations...");

        List<User> users = userRepository.findAll();
        List<PlaceOfWork> placesOfWorks = placeOfWorkRepository.findAll();

        for (int i = 0; i < numberOfFakeReservations; i++) {
            Reservation reservation = new Reservation();

            reservation.setUser(users.get(random.nextInt(users.size())));
            reservation.setPlaceOfWork(placesOfWorks.get(random.nextInt(placesOfWorks.size())));

            LocalDateTime start = LocalDateTime.now().plusDays(random.nextInt(20)).withHour(8).withMinute(0)
                    .withSecond(0).withNano(0);

            LocalDateTime end = start.plusHours(random.nextInt(8) + 1);

            reservation.setStartDate(start);
            reservation.setEndDate(end);

            reservation.setNotes(faker.lorem().sentence());

            reservation.setUser(users.get(random.nextInt(users.size())));
            reservation.setPlaceOfWork(placesOfWorks.get(random.nextInt(placesOfWorks.size())));

            log.debug(reservation.toString());

            // Save the reservation using the service
            // try/catch block to avoid exceptions
            try {
                reservationService.createReservation(reservation);
            } catch (EntityException e) {
                log.error(e.getMessage()); // Message created by the exception
            } catch (Exception e) {
                log.error("Unexpected error creating reservation: {}", e.getMessage());
            }
        }
    }
}
