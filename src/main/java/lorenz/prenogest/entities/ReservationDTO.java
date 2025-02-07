package lorenz.prenogest.entities;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReservationDTO {
    private Long reservationId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String placeOfWorkDescription;
    private String buildingName;
    private String username;

    public ReservationDTO(Long reservationId, LocalDateTime startDate, LocalDateTime endDate,
            String placeOfWorkDescription, String buildingName, String username) {
        this.reservationId = reservationId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.placeOfWorkDescription = placeOfWorkDescription;
        this.buildingName = buildingName;
        this.username = username;
    }

}
