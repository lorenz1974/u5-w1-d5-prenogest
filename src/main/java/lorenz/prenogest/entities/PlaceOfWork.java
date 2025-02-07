package lorenz.prenogest.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "places_of_work")
public class PlaceOfWork {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String description;

    private int peopleCapacity;

    @Enumerated(EnumType.STRING)
    private PlaceType type;

    @ManyToOne
    @ToString.Exclude
    private Building building;

    public boolean hasCapacityFor(int numberOfPeople) {
        return numberOfPeople <= peopleCapacity;
    }
}
