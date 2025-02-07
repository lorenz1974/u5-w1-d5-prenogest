package lorenz.prenogest.entities;

import java.time.LocalDateTime;

public class ReservationDTO {
    private Long reservationId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String placeOfWorkDescription;
    private String buildingName;

    public ReservationDTO(Long reservationId, LocalDateTime startDate, LocalDateTime endDate,
            String placeOfWorkDescription, String buildingName) {
        this.reservationId = reservationId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.placeOfWorkDescription = placeOfWorkDescription;
        this.buildingName = buildingName;
    }
}
