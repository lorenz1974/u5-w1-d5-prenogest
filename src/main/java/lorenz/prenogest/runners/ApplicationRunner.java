package lorenz.prenogest.runners;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lorenz.prenogest.entities.*;
import lorenz.prenogest.repositories.*;
import lorenz.prenogest.services.*;
import lorenz.prenogest.utils.*;
import lorenz.prenogest.exceptions.*;
import static lorenz.prenogest.utils.Utils.*;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(2)
public class ApplicationRunner implements CommandLineRunner {

    public Scanner scanner = new Scanner(System.in);

    private final BuildingRepository buildingRepository;
    private final PlaceOfWorkRepository placeOfWorkRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    private final ReservationService reservationService;
    private final UserService userService;

    public String scan() {
        return scanner.nextLine();
    }

    @Override
    public void run(String... args) throws Exception {
        _C();
        MainMenu();

    }

    public void MainMenu() throws Exception, EntityException {

        Logger logger = new Logger();

        System.out.print("""
                ----------------------------------------------------
                1. Lista degli edifici
                2. Lista delle postazioni di lavoro per edificio
                3. Lista delle prenotazioni per postazione di lavoro
                4. Lista degli utenti
                5. Lista delle prenotazioni per utente
                ----------------------------------------------------
                0. Esci
                ----------------------------------------------------
                ->\s""");

        switch (this.scan()) {
            case "1" -> {
                List<Building> buildings = buildingRepository.findAll();
                logger.log(buildings);
            }
            case "2" -> {
                try {
                    System.out.print("Inserisci l'ID dell'edificio: ");

                    Long buildingId = Long.parseLong(this.scan());

                    Building building = buildingRepository.findById(buildingId)
                            .orElseThrow(() -> new EntityException(
                                    new String[] { "Building", "Not found", buildingId.toString() }));
                    List<PlaceOfWork> placesOfWork = placeOfWorkRepository.findByBuilding(building);
                    logger.log(placesOfWork);
                } catch (EntityException e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    System.out.println("Errore generico: " + e.getMessage());
                }
            }
            case "3" -> {
                System.out.print("Inserisci l'ID della postazione di lavoro: ");
                Long placeOfWorkId = Long.parseLong(this.scan());

                List<Object[]> userReservations = reservationRepository
                        .findExpandedReservationByUserIdOrPlaceOfWorkId(null, placeOfWorkId);

                List<ReservationDTO> reservationDTOs = new ArrayList<>();
                for (Object[] reservation : userReservations) {
                    ReservationDTO dto = new ReservationDTO(
                            (Long) reservation[0],
                            LocalDateTime.parse(reservation[1].toString(),
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")),
                            LocalDateTime.parse(reservation[2].toString(),
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")),
                            (String) reservation[3],
                            (String) reservation[4],
                            (String) reservation[5]);
                    reservationDTOs.add(dto);
                }
                logger.log(reservationDTOs);
            }
            case "4" -> {
                List<User> users = userRepository.findAll();
                logger.log(users);
            }
            case "5" -> {
                System.out.print("Inserisci l'ID dell'utente: ");
                Long userId = Long.parseLong(this.scan());

                List<Object[]> userReservations = reservationRepository
                        .findExpandedReservationByUserIdOrPlaceOfWorkId(userId, null);

                List<ReservationDTO> reservationDTOs = new ArrayList<>();
                for (Object[] reservation : userReservations) {
                    ReservationDTO dto = new ReservationDTO(
                            (Long) reservation[0],
                            LocalDateTime.parse(reservation[1].toString(),
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")),
                            LocalDateTime.parse(reservation[2].toString(),
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")),
                            (String) reservation[3],
                            (String) reservation[4],
                            (String) reservation[5]);
                    reservationDTOs.add(dto);
                }
                logger.log(reservationDTOs);

                // userReservations.forEach(reservation -> {
                // System.out.println("ID: " + reservation[0]);
                // System.out.println("Building Name: " + reservation[4]);
                // System.out.println("Place of Work Description: " + reservation[3]);
                // System.out.println("Start Date: " + reservation[1]);
                // System.out.println("End Date: " + reservation[2]);
                // System.out.println();
                // });

            }
            case "0" -> {
                System.out.println("Uscita dal programma.");
                System.exit(0);
            }
            default -> {
                System.out.println("Invalid input");
                MainMenu();
            }
        }

        MainMenu();
    }
}
