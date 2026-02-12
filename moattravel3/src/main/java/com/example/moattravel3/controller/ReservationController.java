package com.example.moattravel3.controller;  //ログイン済みユーザーの予約一覧を表示するためのコントローラー

import java.time.LocalDate;

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
//import org.springframework.web.bind.annotation.PostMapping; 不要となったため削除 
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.moattravel3.entity.House;
import com.example.moattravel3.entity.Reservation;
import com.example.moattravel3.entity.User;
import com.example.moattravel3.form.ReservationInputForm;
import com.example.moattravel3.form.ReservationRegisterForm;
import com.example.moattravel3.repository.HouseRepository;
import com.example.moattravel3.repository.ReservationRepository;
import com.example.moattravel3.security.UserDetailsImpl;
import com.example.moattravel3.service.ReservationService;
import com.example.moattravel3.service.StripeService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ReservationController {
    private final ReservationRepository reservationRepository;
    private final HouseRepository houseRepository;
    private final ReservationService reservationService;
    private final StripeService stripeService;

    public ReservationController(ReservationRepository reservationRepository,HouseRepository houseRepository,ReservationService reservationService,StripeService stripeService){ //コンストラクタ
        this.reservationRepository = reservationRepository;
        this.houseRepository = houseRepository;
        this.reservationService = reservationService;
        this.stripeService = stripeService;
    }

    @GetMapping("/reservations")
    public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,@PageableDefault(page = 0, size = 10,sort ="id",direction = Direction.ASC) Pageable pageable,Model model) {//ログインユーザーの予約一覧をページネーションで表示するメソッド
        User user = userDetailsImpl.getUser();  //spring securityからログイン中のUserエンティティを取得
        Page<Reservation> reservationPage = reservationRepository.findByUserOrderByCreatedAtDesc(user,pageable);  //ログインユーザーの予約一覧をDBから検索してページネーションで取得
        model.addAttribute("reservationPage",reservationPage);  //予約一覧情報を画面に受けわたし
        return "reservations/index";  //予約一覧を表示
    }

    
    @GetMapping("/houses/{id}/reservations/input")  //予約入力
    public String input(@PathVariable(name="id")Integer id,@ModelAttribute @Validated ReservationInputForm reservationInputForm,BindingResult bindingResult,RedirectAttributes redirectAttributes,Model model){

        House house = houseRepository.getReferenceById(id);  //idをDBから取得
        Integer numberOfPeople = reservationInputForm.getNumberOfPeople();  //予約人数を取得して代入
        Integer capacity = house.getCapacity();  //定員を取得して代入
        if (numberOfPeople != null){  //もし予約人数がnullでなければ
            if (!reservationService.isWithinCapacity(numberOfPeople,capacity)){//もし定員を超えていれば
                FieldError fieldError = new FieldError(bindingResult.getObjectName(),"numberOfPeople", "宿泊人数が定員を超えています。");  //宿泊人数が定員を超えているエラーを作成
                bindingResult.addError(fieldError);
            }
        }
        if(bindingResult.hasErrors()) {  //もしエラーがあったら
            model.addAttribute("house",house);  //houseの情報を画面に受け渡す
            model.addAttribute("errorMessage", "予約内容に不備があります。"); //エラーメッセージを画面に渡す

            return "houses/show";  //民宿詳細画面へ
        }

        redirectAttributes.addFlashAttribute("reservationInputForm",reservationInputForm);  //リダイレクト先の画面にreservationInputFormのデータを１回だけ渡す。※入力に失敗したらフォームデータが空になるためaddAttributeでなくaddFlashAttributeをつかう。
        return "redirect:/houses/{id}/reservations/confirm";  //リダイレクトでconfirmに移動
    }
    @GetMapping("/houses/{id}/reservations/confirm")
    public String confirm(@PathVariable(name = "id") Integer id,@ModelAttribute ReservationInputForm reservationInputForm,@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,HttpServletRequest httpServletRequest,Model model){
        House house = houseRepository.getReferenceById(id);
        User user = userDetailsImpl.getUser();

        //チェックイン日とチェックアウト日を取得する
        LocalDate checkinDate = reservationInputForm.getCheckinDate();  //checkinを取得
        LocalDate checkoutDate = reservationInputForm.getCheckoutDate();  //checkoutを取得
    

        //宿泊料金を計算する
        Integer price = house.getPrice();  //houseエンティティから
        Integer amount = reservationService.calculateAmount(checkinDate,checkoutDate,price);  //チェックイン、チェックアウトと宿泊料金から金額を算出したものを呼び出す

        ReservationRegisterForm reservationRegisterForm = new ReservationRegisterForm(house.getId(),user.getId(),checkinDate.toString(),checkoutDate.toString(),reservationInputForm.getNumberOfPeople(),amount);  //入力した情報をフォームから取得

        String sessionId = stripeService.createStripeSession(house.getName(),reservationRegisterForm,httpServletRequest);  //stripe決済画面へのリダイレクト用のセッションIDを生成
        model.addAttribute("house",house);  //houseの情報を画面に受け渡す
        model.addAttribute("reservationRegisterForm",reservationRegisterForm);  //reservationRegisterFormの情報を画面に渡す
        model.addAttribute("sessionId", sessionId); //sessionidを画面に渡す
        
        return "reservations/confirm";  //confirmを表示
    }

    // @PostMapping("/houses/{id}/reservations/create")
    // public String create(@ModelAttribute ReservationRegisterForm reservationRegisterForm) {
    //     reservationService.create(reservationRegisterForm);
    //     return "redirect:/reservations?reserved";
    // } 不要となったため削除
}

