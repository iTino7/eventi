package tinoborrelli.eventi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tinoborrelli.eventi.entities.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
