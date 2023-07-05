package com.cristian.lil.learningspring.web;

import com.cristian.lil.learningspring.business.ReservationService;
import com.cristian.lil.learningspring.data.Guest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/guests")
public class GuestController {
    private final ReservationService reservationService;

    public GuestController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getGuests(Model model) {
        List<Guest> guestList = reservationService.getGuests();
        guestList.sort((g1, g2) -> {
            if (g1.getLastName().equals(g2.getLastName())) {
                return g1.getFirstName().compareTo(g2.getFirstName());
            }
            return g1.getLastName().compareTo(g2.getLastName());
        });
        model.addAttribute("guestList", guestList);
        return "guests";
    }
}
