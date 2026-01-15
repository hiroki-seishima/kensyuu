package com.example.moattravel.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;  //30章で追加
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.moattravel.entity.House;
import com.example.moattravel.form.ReservationInputForm;  //33章で追加
import com.example.moattravel.repository.HouseRepository;

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
        @RequestParam(name = "order", required = false) String order,    //28章で追加                 
        @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,Model model) {
            Page<House> housePage;

            if (keyword != null && !keyword.isEmpty()) {
                housePage = houseRepository.findByNameLikeOrAddressLike("%" + keyword + "%", "%" + keyword + "%", pageable);
                if (order != null && order.equals("priceAsc")) {  //28章で追加
                    housePage = houseRepository.findByNameLikeOrAddressLikeOrderByPriceAsc("%" + keyword + "%", "%" + keyword + "%", pageable);  //28章で追加
                } else {  ////28章で追加
                    housePage = houseRepository.findByNameLikeOrAddressLikeOrderByCreatedAtDesc("%" + keyword + "%", "%" + keyword + "%", pageable);  //28章で追加
                }
            } else if (area != null && !area.isEmpty()) {
                housePage = houseRepository.findByAddressLike("%" + area + "%", pageable);
                if (order != null && order.equals("priceAsc")) {  //28章で追加
                    housePage = houseRepository.findByAddressLikeOrderByPriceAsc("%" + area + "%", pageable);  //28章で追加
                } else {  //28章で追加
                    housePage = houseRepository.findByAddressLikeOrderByCreatedAtDesc("%" + area + "%", pageable);  //28章で追加
                }
            } else if (price != null) {
                housePage = houseRepository.findByPriceLessThanEqual(price, pageable);
                if (order != null && order.equals("priceAsc")) {  //28章で追加
                    housePage = houseRepository.findByPriceLessThanEqualOrderByPriceAsc(price, pageable);  //28章で追加
                } else {  //28章で追加
                    housePage = houseRepository.findByPriceLessThanEqualOrderByCreatedAtDesc(price, pageable);  //28章で追加
                }
            } else {
                housePage = houseRepository.findAll(pageable);
                if (order != null && order.equals("priceAsc")) {  //28章で追加
                    housePage = houseRepository.findAllByOrderByPriceAsc(pageable);  //28章で追加
                } else {  //28章で追加
                    housePage = houseRepository.findAllByOrderByCreatedAtDesc(pageable);    //28章で追加
                }
            }               
            model.addAttribute("housePage", housePage);
            model.addAttribute("keyword", keyword);
            model.addAttribute("area", area);
            model.addAttribute("price", price);       
            model.addAttribute("order", order);    //28章で追加                     
            return "houses/index";
        }

        @GetMapping("/{id}")  //30章で追加
        public String show(@PathVariable(name = "id") Integer id, Model model) {  //30章で追加
            House house = houseRepository.getReferenceById(id);  //30章で追加

            model.addAttribute("house", house);    //30章で追加
            model.addAttribute("reservationInputForm", new ReservationInputForm()); //33章で追加
            return "houses/show";  //30章で追加
        }     
}
