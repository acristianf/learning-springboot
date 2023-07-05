package com.cristian.lil.learningspring.webservice;

import com.cristian.lil.learningspring.business.ReservationService;
import com.cristian.lil.learningspring.business.RoomReservation;
import com.cristian.lil.learningspring.util.DateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
