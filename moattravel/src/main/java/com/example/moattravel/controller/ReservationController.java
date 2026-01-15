package com.example.moattravel.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;  //34章で追加
import org.springframework.validation.FieldError;  //34章で追加
import org.springframework.validation.annotation.Validated;  //34章で追加
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute; //34章で追加
import org.springframework.web.bind.annotation.PathVariable;  //34章で追加
import org.springframework.web.servlet.mvc.support.RedirectAttributes;  //34章で追加

import com.example.moattravel.entity.House; //34章で追加
import com.example.moattravel.entity.Reservation;
import com.example.moattravel.entity.User;
import com.example.moattravel.form.ReservationInputForm; //34章で追加
import com.example.moattravel.repository.HouseRepository;  //34章で追加
import com.example.moattravel.repository.ReservationRepository;
import com.example.moattravel.security.UserDetailsImpl;
import com.example.moattravel.service.ReservationService;  //34章で追加

@Controller
public class ReservationController {
    private final ReservationRepository reservationRepository;    
    private final HouseRepository houseRepository;  //34章で追加
    private final ReservationService reservationService;   //34章で追加

    public ReservationController(ReservationRepository reservationRepository, HouseRepository houseRepository, ReservationService reservationService) {        //34章で, HouseRepository houseRepository, ReservationService reservationServiceを追加
        this.reservationRepository = reservationRepository; 
        this.houseRepository = houseRepository;  //34章で追加
        this.reservationService = reservationService;      //34章で追加
    }    

    @GetMapping("/reservations")
    public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, Model model) {
        User user = userDetailsImpl.getUser();
        Page<Reservation> reservationPage = reservationRepository.findByUserOrderByCreatedAtDesc(user, pageable);
        
        model.addAttribute("reservationPage", reservationPage);         

        return "reservations/index";
    }

    
//     確認@GetMapping("/houses/{id}/reservations/input")  //34章で追加
//     public String input(@PathVariable(name = "id") Integer id,@ModelAttribute @Validated ReservationInputForm reservationInputForm,BindingResult bindingResult,RedirectAttributes redirectAttributes,Model model) {


//         House house = houseRepository.getReferenceById(id);
//         Integer numberOfPeople = reservationInputForm.getNumberOfPeople();   
//         Integer capacity = house.getCapacity();
// }
    

