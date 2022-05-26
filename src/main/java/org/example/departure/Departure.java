package org.example.departure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.postoffice.PostOffice;

import javax.persistence.*;

@Entity
@Table(name="departures")
@NoArgsConstructor
@Getter
@Setter
public class Departure {
    @Id
    @SequenceGenerator(name = "departures_seq", sequenceName =
            "departures_sequence", allocationSize = 1)
    @GeneratedValue(generator = "departures_seq", strategy =
            GenerationType.SEQUENCE)
    private Long id;
    @Column(name="departure_type")
    private String type;
    @Column(name="departure_date")
    private String departureDate;

    @ManyToOne
    @JsonIgnore
    private PostOffice office;
    public Departure(String type, String departureDate) {
        this.type = type;
        this.departureDate = departureDate;

    }
}
