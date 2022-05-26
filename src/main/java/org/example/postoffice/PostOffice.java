package org.example.postoffice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.departure.Departure;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="post_offices")
@Getter
@Setter
@NoArgsConstructor
public class PostOffice {

    @Id
    @SequenceGenerator(name = "offices_seq", sequenceName =
            "offices_sequence", allocationSize = 1)
    @GeneratedValue(generator = "offices_seq", strategy =
            GenerationType.SEQUENCE)
    private Long id;

    @Column(name="office_name")
    private String OfficeName;
    @Column(name="city_name")
    private String CityName;

    @OneToMany(mappedBy = "office")
    private List<Departure> departures;

    public PostOffice(String officeName, String cityName) {
        OfficeName = officeName;
        CityName = cityName;
        this.departures=new ArrayList<>();
    }
}
