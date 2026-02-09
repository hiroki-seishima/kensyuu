package com.example.moattravel3.controller;  //民宿検索一覧ページのコントローラ


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.moattravel3.entity.House;
import com.example.moattravel3.repository.HouseRepository;

@Controller
@RequestMapping("/houses")
public class HouseController {
    private final HouseRepository houseRepository;

    public HouseController(HouseRepository houseRepository){  //コンスラクタ
        this.houseRepository = houseRepository;
    }

    @GetMapping
    public String index(@RequestParam(name = "keyword",required = false) String keyword,@RequestParam(name = "area",required = false) String area,@RequestParam(name = "price",required = false) Integer price,@PageableDefault(page = 0,size = 10,sort = "id",direction = Direction.ASC)Pageable pageable,Model model){
        Page<House> housePage;
        if (keyword !=null && !keyword.isEmpty()){  //キーワードがnullでないかつ空でないの時
            housePage = houseRepository.findByNameLikeOrAddressLike("%" + keyword + "%", "%" + keyword + "%" ,pageable);  //ページング内でホテル名または住所を部分検索
        }else if (area !=null && !area.isEmpty()){  //住所がnullでないかつ空でない時
            housePage = houseRepository.findByAddressLike("%" + area + "%",pageable);  //ページング内で住所を部分検索
        }else if (price !=null){  //金額がnullでないとき
            housePage = houseRepository.findByPriceLessThanEqual(price,pageable); //ページング内で指定した金額以下のホテルを表示
        }else{
            housePage = houseRepository.findAll(pageable);  //それ以外はページング内で全て表示
        }
        model.addAttribute("housePage",housePage);  //ページング付きのハウスページのデータを画面に渡す
        model.addAttribute("keyword",keyword);  //検索キーワードのデータを画面に渡す
        model.addAttribute("area",area);  //検索した住所のデータを画面に渡す
        model.addAttribute("price",price);  //指定した金額を画面に受け渡し

        return "houses/index";  //民宿一覧を表示
    }

}
