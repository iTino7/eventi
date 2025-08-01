package tinoborrelli.eventi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tinoborrelli.eventi.entities.Event;
import tinoborrelli.eventi.exceptions.RecordNotFoundException;
import tinoborrelli.eventi.payloads.EventDTO;
import tinoborrelli.eventi.repositories.EventRepository;

import java.util.UUID;


@Service
public class EventService {

    @Autowired
    private UserService userService;

    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(EventDTO body) {
        Event event = new Event();
        event.setName(body.name());
        event.setDescription(body.description());
        event.setDate(body.date());
        event.setLocation(body.location());
        event.setAvailableTickets(body.availableTickets());
        return eventRepository.save(event);
    }

    public Event getEvent(UUID id) {
        return eventRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(Event.class.getSimpleName(), id));
    }

    public Page<Event> getEvents(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    public Event updateEvent(UUID id, EventDTO dto) {
        Event event = this.getEvent(id);
        event.setName(dto.name());
        event.setDescription(dto.description());
        event.setDate(dto.date());
        event.setLocation(dto.location());
        event.setAvailableTickets(dto.availableTickets());
        return eventRepository.save(event);
    }

    public void deleteEvent(UUID id) {
        eventRepository.deleteById(id);
    }
}
