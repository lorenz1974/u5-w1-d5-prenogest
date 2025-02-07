package lorenz.prenogest.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lorenz.prenogest.entities.Reservation;
import lorenz.prenogest.exceptions.EntityException;
import lorenz.prenogest.repositories.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation findById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public void createReservation(Reservation reservation) throws EntityException {

        // Check if the user already has a reservation in the same date
        List<Reservation> userReservations = reservationRepository.findReservationsByUserAndDate(
                reservation.getUser().getId(),
                reservation.getStartDate(),
                reservation.getEndDate());
        if (userReservations.size() > 0) {
            String[] args = {
                    "Reservation",
                    "Already exists",
                    reservation.getUser().getUsername(),
                    reservation.getStartDate().toString(),
            };
            throw new EntityException(args);
        }

        // Check if the POW already has a reservation in the same date
        List<Reservation> powReservations = reservationRepository.findReservationsByPOWAndDate(
                reservation.getPlaceOfWork().getId(),
                reservation.getStartDate(),
                reservation.getEndDate());
        if (powReservations.size() > 0) {
            String[] args = {
                    "Place Of Work",
                    "Already Taken",
                    String.valueOf(reservation.getPlaceOfWork().getId()),
                    reservation.getStartDate().toString(),
            };
            throw new EntityException(args);
        }

        reservationRepository.save(reservation);
    }

}
