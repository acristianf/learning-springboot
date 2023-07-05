package com.cristian.lil.learningspring.webservice;

import com.cristian.lil.learningspring.business.ReservationService;
import com.cristian.lil.learningspring.business.RoomReservation;
import com.cristian.lil.learningspring.data.Guest;
import com.cristian.lil.learningspring.data.Room;
import com.cristian.lil.learningspring.util.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WebserviceController {
    private final DateUtils dateUtils;
    private final ReservationService reservationService;

    public WebserviceController(DateUtils dateUtils, ReservationService reservationService) {
        this.dateUtils = dateUtils;
        this.reservationService = reservationService;
    }

    @RequestMapping(path = "/reservations", method = RequestMethod.GET)
    public List<RoomReservation> getReservations(@RequestParam(value="date", required = false)String date) {
        Date parsedDate = dateUtils.createDateFromDateString(date);
        return this.reservationService.getRoomReservationForDate(parsedDate);
    }
    @RequestMapping(path = "/guests", method = RequestMethod.GET)
    public List<Guest> getGuests() {
        return this.reservationService.getGuests();
    }
    @PostMapping(path="/guests")
    @ResponseStatus(HttpStatus.CREATED)
    public Guest newGuest(@RequestBody Guest newGuest) {
        return this.reservationService.saveGuest(newGuest);
    }
    @GetMapping(path="/rooms")
    public List<Room> getRooms(){
        return this.reservationService.getRooms();
    }
}
