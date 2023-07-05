package com.cristian.lil.learningspring.business;

import com.cristian.lil.learningspring.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReservationService {
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RoomReservation> getRoomReservationForDate(Date date) {
        Iterable<Room> rooms = this.roomRepository.findAll();
        Map<Long, RoomReservation> roomReservationMap = new HashMap<>();
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getId());
            roomReservation.setRoomName(room.getName());
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservationMap.put(room.getId(), roomReservation);
        });
        Iterable<Reservation> reservations = this.reservationRepository.findReservationByResDate(new java.sql.Date(date.getTime()));
        reservations.forEach(reservation -> {
            RoomReservation roomReservation = roomReservationMap.get(reservation.getRoomId());
            roomReservation.setDate(date);
            Guest guest = this.guestRepository.findById(reservation.getGuestId()).get();
            roomReservation.setFirstName(guest.getFirstName());
            roomReservation.setGuestId(guest.getId());
            roomReservation.setLastName(guest.getLastName());
        });
        List<RoomReservation> roomReservations = new ArrayList<>();
        for (Long id : roomReservationMap.keySet()) {
            roomReservations.add(roomReservationMap.get(id));
        }
        roomReservations.sort((reservation1, reservation2) -> {
            if (reservation1.getRoomName().equals(reservation2.getRoomName())) {
                return reservation1.getRoomNumber().compareTo(reservation2.getRoomNumber());
            }
            return reservation1.getRoomName().compareTo(reservation2.getRoomName());
        });
        return roomReservations;
    }
    public List<Guest> getGuests() {
        return (List<Guest>) this.guestRepository.findAll();
    }
    public Guest saveGuest(Guest newGuest) {
        if (newGuest == null) {
            throw new RuntimeException("Guest cannot be null");
        }
        return this.guestRepository.save(newGuest);
    }
    public List<Room> getRooms() {
        List<Room> rooms = (List<Room>) this.roomRepository.findAll();
        rooms.sort(Comparator.comparing(Room::getRoomNumber));
        return rooms;
    }
}
