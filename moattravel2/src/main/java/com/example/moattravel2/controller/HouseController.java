package com.example.moattravel2.controller; //27-3

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; //30-2で追加　URLパス内の変数部分（ /{id} ）をコントローラーメソッドの引数に自動バインドするアノテーション
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.moattravel2.entity.House;
import com.example.moattravel2.form.ReservationInputForm; //33-4で追加
import com.example.moattravel2.repository.HouseRepository;

@Controller
@RequestMapping("/houses")

public class HouseController {
    private final HouseRepository houseRepository;

    public HouseController(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @GetMapping
    public String index(@RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "area", required = false) String area,
            @RequestParam(name = "price", required = false) Integer price,
            @RequestParam(name = "order", required = false) String order, // 28-3で追加
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
            Model model) {
        Page<House> housePage;

        if (keyword != null && !keyword.isEmpty()) {
            housePage = houseRepository.findByNameLikeOrAddressLike("%" + keyword + "%", "%" + keyword + "%", pageable);
            if (order != null && order.equals("priceAsc")) {
                housePage = houseRepository.findByNameLikeOrAddressLikeOrderByPriceAsc("%" + keyword + "%",
                        "%" + keyword + "%", pageable); // 28-3で追加
            } else {
                housePage = houseRepository.findByNameLikeOrAddressLikeOrderByCreatedAtDesc("%" + keyword + "%",
                        "%" + keyword + "%", pageable);
            }
        } else if (area != null && !area.isEmpty()) {
            housePage = houseRepository.findByAddressLike("%" + area + "%", pageable);
            if (order != null && order.equals("priceAsc")) { // 28-3
                housePage = houseRepository.findByAddressLikeOrderByPriceAsc("%" + area + "%", pageable);
            } else { // 28-3
                housePage = houseRepository.findByAddressLikeOrderByCreatedAtDesc("%" + area + "%", pageable);
            }
        } else if (area != null && !area.isEmpty()) { // 28-3
            housePage = houseRepository.findByAddressLike("%" + area + "%", pageable);
            if (order != null && order.equals("priceAsc")) { // 28-3
                housePage = houseRepository.findByAddressLikeOrderByPriceAsc("%" + area + "%", pageable);
            } else { // 28-3
                housePage = houseRepository.findByAddressLikeOrderByCreatedAtDesc("%" + area + "%", pageable);
            }
        } else if (price != null) {
            housePage = houseRepository.findByPriceLessThanEqual(price, pageable);
            if (order != null && order.equals("priceAsc")) { // 28-3

                housePage = houseRepository.findByPriceLessThanEqualOrderByPriceAsc(price, pageable);
            } else { // 28-3
                housePage = houseRepository.findByPriceLessThanEqualOrderByCreatedAtDesc(price, pageable);
            }
        } else {
            // housePage = houseRepository.findAll(pageable); 33-4で削除
            if (order != null && order.equals("priceAsc")) { // 33-4で追加
                housePage = houseRepository.findAllByOrderByPriceAsc(pageable);
            } else { // 33-4で追加
                housePage = houseRepository.findAllByOrderByCreatedAtDesc(pageable);
            }
        }

        model.addAttribute("housePage", housePage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("area", area);
        model.addAttribute("price", price);
        model.addAttribute("order", order); // 28-3

        return "houses/index";

    }

    @GetMapping("/{id}") // 30-2で追加
    public String show(@PathVariable(name = "id") Integer id, Model model) {
        House house = houseRepository.getReferenceById(id);
        model.addAttribute("house", house);
        model.addAttribute("reservationInputForm", new ReservationInputForm());  //33-4で追加
        return "houses/show";
    }
}
