package tinoborrelli.eventi.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    private String location;
    private LocalDate date;
    @Column(name = "available_tickets")
    private int availableTickets;
    @ManyToMany
    private List<User> users;

    public Event(String name, String description, String location, LocalDate date, int availableTickets) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.date = date;
        this.availableTickets = availableTickets;
    }
}
