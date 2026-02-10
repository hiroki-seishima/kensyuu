package com.example.moattravel3.controller;  //民宿検索一覧ページのコントローラ


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.moattravel3.entity.House;
import com.example.moattravel3.form.ReservationInputForm;
import com.example.moattravel3.repository.HouseRepository;

@Controller
@RequestMapping("/houses")
public class HouseController {
    private final HouseRepository houseRepository;

    public HouseController(HouseRepository houseRepository){  //コンスラクタ
        this.houseRepository = houseRepository;
    }

    @GetMapping
    public String index(@RequestParam(name = "keyword",required = false) String keyword,@RequestParam(name = "area",required = false) String area,@RequestParam(name = "price",required = false) Integer price,@RequestParam(name = "order",required = false)String order,@PageableDefault(page = 0,size = 10,sort = "id",direction = Direction.ASC)Pageable pageable,Model model){
        Page<House> housePage;
        if (keyword !=null && !keyword.isEmpty()){  //キーワードがnullでないかつ空でないの時
            housePage = houseRepository.findByNameLikeOrAddressLike("%" + keyword + "%", "%" + keyword + "%" ,pageable);  //ページング内でホテル名または住所を部分検索
            if (order != null && order.equals("priceAsc")){  //さらにorderが送信されているかつ昇順であれば
                housePage=houseRepository.findByNameLikeOrAddressLikeOrderByPriceAsc("%" + keyword + "%" , "%" + keyword + "%" ,pageable);  //民宿名と住所を部分一致検索して料金の高い順番で降順（安い順で昇順）
            }else {
                housePage = houseRepository.findByNameLikeOrAddressLikeOrderByCreatedAtDesc("%" + keyword + "%", "%" + keyword + "%", pageable);  //さらにそれ以外は民宿、住所を部分一致検索して作成日時で降順
            }

        }else if (area !=null && !area.isEmpty()){  //住所がnullでないかつ空でない時
            housePage = houseRepository.findByAddressLike("%" + area + "%",pageable);  //ページング内で住所を部分検索
            if (order !=null && order.equals("priceAsc")){  //
                housePage = houseRepository.findByAddressLikeOrderByPriceAsc("%" + area + "%", pageable);  //住所の部分一致検索をし、安い順で昇順
            }else{
                housePage = houseRepository.findByAddressLikeOrderByCreatedAtDesc("%" + area + "%", pageable);  //住所の部分一致検索をし、作成日時新しい順で降順
            }
        }else if (price !=null){  //金額がnullでないとき
            housePage = houseRepository.findByPriceLessThanEqual(price,pageable); //ページング内で指定した金額以下のホテルを検索
            if(order !=null && order.equals("priceAsc")){  //さらにorderが送信されているかつ昇順であれば
                housePage = houseRepository.findByPriceLessThanEqualOrderByPriceAsc(price,pageable);  //指定した金額以下で検索し、安い順で昇順
            }else{
                housePage = houseRepository.findByPriceLessThanEqualOrderByCreatedAtDesc(price,pageable); //指定した料金以下を検索し作成日時の新しい順で降順
            }
        }else{
            housePage = houseRepository.findAllByOrderByCreatedAtDesc(pageable);  //それ以外はページング内で作成日時順で降順で表示
        }
        model.addAttribute("housePage",housePage);  //ページング付きのハウスページのデータを画面に渡す
        model.addAttribute("keyword",keyword);  //検索キーワードのデータを画面に渡す
        model.addAttribute("area",area);  //検索した住所のデータを画面に渡す
        model.addAttribute("price",price);  //指定した金額を画面に受け渡し
        model.addAttribute("order",order);  //指定したorderを画面に受け渡し
        

        return "houses/index";  //民宿一覧を表示
    }

    @GetMapping("/{id}")  
    public String show(@PathVariable(name = "id") Integer id,Model model){  //idを自動取得
        House house = houseRepository.getReferenceById(id);  //取得したidからDBにアクセスし、情報を取得
        
        model.addAttribute("house",house);  //houseの情報を画面に受け渡す
        model.addAttribute("reservationInputForm",new ReservationInputForm());  //予約一覧データを画面に受け渡す

        return "houses/show";  //showを表示
    }
}
