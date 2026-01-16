package com.example.moattravel.controller;

import java.time.LocalDate;

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
// import org.springframework.web.bind.annotation.PostMapping;  //35章で追加
import org.springframework.web.servlet.mvc.support.RedirectAttributes;  //34章で追加

import com.example.moattravel.entity.House; //34章で追加
import com.example.moattravel.entity.Reservation;
import com.example.moattravel.entity.User;
import com.example.moattravel.form.ReservationInputForm; //34章で追加
import com.example.moattravel.form.ReservationRegisterForm;  //34章で追加
import com.example.moattravel.repository.HouseRepository;  //34章で追加
import com.example.moattravel.repository.ReservationRepository;
import com.example.moattravel.security.UserDetailsImpl;
import com.example.moattravel.service.ReservationService;  //34章で追加
import com.example.moattravel.service.StripeService; //37章で追加

import jakarta.servlet.http.HttpServletRequest;  //37章で追加

@Controller
public class ReservationController {
    private final ReservationRepository reservationRepository;    
    private final HouseRepository houseRepository;  //34章で追加
    private final ReservationService reservationService;   //34章で追加
    private final StripeService stripeService;  //37章で追加

    public ReservationController(ReservationRepository reservationRepository, HouseRepository houseRepository, ReservationService reservationService, StripeService stripeService) {        //34章で, HouseRepository houseRepository, ReservationService reservationServiceを追加　37章で, StripeService stripeServiceを追加
        this.reservationRepository = reservationRepository; 
        this.houseRepository = houseRepository;  //34章で追加
        this.reservationService = reservationService;      //34章で追加
        this.stripeService = stripeService;  //37章で追加

    }    

    @GetMapping("/reservations")
    public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, Model model) {
        User user = userDetailsImpl.getUser();
        Page<Reservation> reservationPage = reservationRepository.findByUserOrderByCreatedAtDesc(user, pageable);
        
        model.addAttribute("reservationPage", reservationPage);         

        return "reservations/index";
    }

    
    @GetMapping("/houses/{id}/reservations/input")  //34章で追加
    public String input(@PathVariable(name = "id") Integer id,@ModelAttribute @Validated ReservationInputForm reservationInputForm,BindingResult bindingResult,RedirectAttributes redirectAttributes,Model model) {

        House house = houseRepository.getReferenceById(id);
        Integer numberOfPeople = reservationInputForm.getNumberOfPeople();   
        Integer capacity = house.getCapacity();

        if (numberOfPeople != null) {
            if (!reservationService.isWithinCapacity(numberOfPeople, capacity)) {
                FieldError fieldError = new FieldError(bindingResult.getObjectName(), "numberOfPeople", "宿泊人数が定員を超えています。");
                bindingResult.addError(fieldError);                
            }            
        }         
        if (bindingResult.hasErrors()) {            
            model.addAttribute("house", house);            
            model.addAttribute("errorMessage", "予約内容に不備があります。"); 
            return "houses/show";
        }
        redirectAttributes.addFlashAttribute("reservationInputForm", reservationInputForm);           
        
        return "redirect:/houses/{id}/reservations/confirm";
    }
    
    @GetMapping("/houses/{id}/reservations/confirm")  //34章で追加
    public String confirm(@PathVariable(name = "id") Integer id,  //35章で追加
    
    @ModelAttribute ReservationInputForm reservationInputForm,   //35章で追加
    @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,HttpServletRequest httpServletRequest,Model model) {      //35章で追加  37章でHttpServletRequest httpServletRequest, を追加
        House house = houseRepository.getReferenceById(id);  //35章で追加
        User user = userDetailsImpl.getUser();   //35章で追加
        //チェックイン日とチェックアウト日を取得する
        LocalDate checkinDate = reservationInputForm.getCheckinDate();
        LocalDate checkoutDate = reservationInputForm.getCheckoutDate();
        // 宿泊料金を計算する
        Integer price = house.getPrice();        
        Integer amount = reservationService.calculateAmount(checkinDate, checkoutDate, price);
        ReservationRegisterForm reservationRegisterForm = new ReservationRegisterForm(house.getId(), user.getId(), checkinDate.toString(), checkoutDate.toString(), reservationInputForm.getNumberOfPeople(), amount);
        String sessionId = stripeService.createStripeSession(house.getName(), reservationRegisterForm, httpServletRequest);  //37章で追加
        model.addAttribute("house", house);  
        model.addAttribute("reservationRegisterForm", reservationRegisterForm);   
        model.addAttribute("sessionId", sessionId);   //37章で追加
        return "reservations/confirm";
    }

    // @PostMapping("/houses/{id}/reservations/create")  //35章で追加  37章で使わなくなったため削除
    // public String create(@ModelAttribute ReservationRegisterForm reservationRegisterForm) {    //35章で追加     37章で使わなくなったため削除       
        // reservationService.create(reservationRegisterForm);      //35章で追加　37章で使わなくなったため削除

        // return "redirect:/reservations?reserved";  //35章で追加　37章で使わなくなったため削除
    // }
}
    