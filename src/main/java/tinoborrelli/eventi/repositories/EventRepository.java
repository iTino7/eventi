package tinoborrelli.eventi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tinoborrelli.eventi.entities.Event;
import tinoborrelli.eventi.entities.User;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
    Page<Event> findAllByUsers(User user, Pageable pageable);
}
