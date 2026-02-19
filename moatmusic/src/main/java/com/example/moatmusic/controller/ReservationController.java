package com.example.moatmusic.controller; //予約一覧のコントローラー

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.moatmusic.entity.StringInstrument;
import com.example.moatmusic.entity.Reservation;
import com.example.moatmusic.entity.User;
import com.example.moatmusic.form.ReservationInputForm;
import com.example.moatmusic.form.ReservationRegisterForm;
import com.example.moatmusic.repository.StringInstrumentRepository;
import com.example.moatmusic.repository.ReservationRepository;
import com.example.moatmusic.security.UserDetailsImpl;
import com.example.moatmusic.service.ReservationService;

@Controller
public class ReservationController {
    private final ReservationRepository reservationRepository;
    private final StringInstrumentRepository stringInstrumentRepository;
    private final ReservationService reservationService;

    public ReservationController(ReservationRepository reservationRepository,
            StringInstrumentRepository stringInstrumentRepository,
            ReservationService reservationService) {
        this.reservationRepository = reservationRepository;
        this.stringInstrumentRepository = stringInstrumentRepository;
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
            Model model) {
        User user = userDetailsImpl.getUser();
        Page<Reservation> reservationPage = reservationRepository.findByUserOrderByCreatedAtDesc(user, pageable);

        model.addAttribute("reservationPage", reservationPage);

        return "reservations/index";
    }

    @GetMapping("/stringInstrument/{id}/reservations/input")
    public String input(@PathVariable(name = "id") Integer id,
            @ModelAttribute @Validated ReservationInputForm reservationInputForm, BindingResult bindingResult,
            RedirectAttributes redirectAttributes, Model model) {
        StringInstrument stringInstrument = stringInstrumentRepository.getReferenceById(id);
        Integer rentalQuantity = reservationInputForm.getRentalQuantity();
        Integer capacity = stringInstrument.getCapacity();

        if (rentalQuantity != null) {
            if (!reservationService.isWithinCapacity(rentalQuantity, capacity)) {
                FieldError fieldError = new FieldError(bindingResult.getObjectName(), "rentalQuantity",
                        "台数が最大貸出数を超えています。");
                bindingResult.addError(fieldError);
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("stringInstrument", stringInstrument);
            model.addAttribute("errorMessage", "予約内容に不備があります。");
            return "stringInstrument/show";
        }

        redirectAttributes.addFlashAttribute("reservationInputForm", reservationInputForm);

        return "redirect:/stringInstrument/{id}/reservations/confirm";
    }

    @GetMapping("/stringInstrument/{id}/reservations/confirm")
    public String confirm(@PathVariable(name = "id") Integer id,
            @ModelAttribute ReservationInputForm reservationInputForm,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            Model model) {
        StringInstrument stringInstrument = stringInstrumentRepository.getReferenceById(id);
        User user = userDetailsImpl.getUser();

        // レンタル開始日と終了日を取得する
        LocalDateTime rentalStartTimeDate = reservationInputForm.getRentalStartTimeDate();
        LocalDateTime rentalEndTimeDate = reservationInputForm.getRentalEndTimeDate();

        // レンタル料金を計算する
        Integer rentalQuantity = reservationInputForm.getRentalQuantity();
        Integer price = stringInstrument.getPrice();
        Integer amount = reservationService.calculateAmount(rentalStartTimeDate, rentalEndTimeDate, price,
                rentalQuantity);

        ReservationRegisterForm reservationRegisterForm = new ReservationRegisterForm(stringInstrument.getId(),
                user.getId(),
                rentalStartTimeDate.toString(), rentalEndTimeDate.toString(),
                reservationInputForm.getRentalQuantity(), amount);

        model.addAttribute("stringInstrument", stringInstrument);
        model.addAttribute("reservationRegisterForm", reservationRegisterForm);

        return "reservations/confirm";
    }

    @PostMapping("/stringInstrument/{id}/reservations/create")  //予約情報をDBに作成するメソッド
    public String create(@ModelAttribute ReservationRegisterForm reservationRegisterForm) {
        reservationService.create(reservationRegisterForm);

        return "redirect:/reservations?reserved";
    }
}