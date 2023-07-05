package com.cristian.lil.learningspring.web;

import com.cristian.lil.learningspring.business.ReservationService;
import com.cristian.lil.learningspring.business.RoomReservation;
import com.cristian.lil.learningspring.util.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class RoomReservationController {
    private final DateUtils dateUtils;
    private final ReservationService reservationService;

    public RoomReservationController(DateUtils dateUtils, ReservationService reservationService) {
        this.dateUtils = dateUtils;
        this.reservationService = reservationService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getReservations(String date, Model model) {
        Date parsedDate = this.dateUtils.createDateFromDateString(date);
        List<RoomReservation> roomReservations = this.reservationService.getRoomReservationForDate(parsedDate);
        model.addAttribute("roomReservations", roomReservations);
        return "roomres";
    }
}
