package lorenz.prenogest.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import lorenz.prenogest.entities.Reservation;
import lorenz.prenogest.entities.ReservationDTO;
import lorenz.prenogest.entities.User;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByStartDateAndUser(LocalDateTime startDate, User user);

    List<Reservation> findByUser(User user);

    @Query("SELECT r FROM Reservation r WHERE r.user.id = ?1 AND DATE(r.startDate) >= DATE(?2) AND DATE(r.endDate) <= DATE(?3)")
    List<Reservation> findReservationsByUserAndDate(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT r FROM Reservation r WHERE r.placeOfWork.id = ?1 AND DATE(r.startDate) >= DATE(?2) AND DATE(r.endDate) <= DATE(?3)")
    List<Reservation> findReservationsByPOWAndDate(Long powId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT r FROM Reservation r WHERE r.user.id = ?1")
    List<Reservation> findAllReservationsByUserId(Long userId);

    List<Reservation> findByPlaceOfWorkId(Long placeOfWorkId);

    List<Reservation> findByUserId(Long userId);

    @Query(value = "SELECT r.id as reservationId, r.start_date as startDate, r.end_date as endDate, p.description as placeOfWorkDescription, b.name as buildingName, u.username as username FROM reservations r JOIN places_of_work p ON r.place_of_work_id = p.id JOIN buildings b ON p.building_id = b.id JOIN users u ON r.user_id = u.id WHERE r.user_id = ?1 OR r.place_of_work_id = ?2", nativeQuery = true)
    List<Object[]> findExpandedReservationByUserIdOrPlaceOfWorkId(Long userId, Long placeOfWorkId);
}
