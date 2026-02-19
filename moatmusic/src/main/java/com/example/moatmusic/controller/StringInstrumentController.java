package com.example.moatmusic.controller; //楽器一覧のコントローラー

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

import com.example.moatmusic.entity.StringInstrument;
import com.example.moatmusic.form.ReservationInputForm;
import com.example.moatmusic.repository.StringInstrumentRepository;

@Controller
@RequestMapping("/stringInstrument")
public class StringInstrumentController {
    private final StringInstrumentRepository stringInstrumentRepository;

    public StringInstrumentController(StringInstrumentRepository stringInstrumentRepository) {
        this.stringInstrumentRepository = stringInstrumentRepository;
    }

    @GetMapping
    public String index(@RequestParam(name = "nameKeyword", required = false) String nameKeyword,
            @RequestParam(name = "handedness", required = false) String handedness,
            @RequestParam(name = "price", required = false) Integer price,
            @RequestParam(name = "order", required = false) String order,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
            Model model) {
        Page<StringInstrument> stringInstrumentPage;

        if (nameKeyword != null && !nameKeyword.isEmpty()) {
            stringInstrumentPage = stringInstrumentRepository.findByNameLike("%" + nameKeyword + "%", pageable);
            if (order != null && order.equals("priceAsc")) {
                stringInstrumentPage = stringInstrumentRepository.findByNameLikeOrderByPriceAsc("%" +
                        nameKeyword + "%", pageable);
            } else {
                stringInstrumentPage = stringInstrumentRepository.findByNameLikeOrderByCreatedAtDesc("%" +
                        nameKeyword + "%", pageable);
            }
        } else if (handedness != null && !handedness.isEmpty()) {
            stringInstrumentPage = stringInstrumentRepository.findByHandedness(handedness, pageable); // "%"はLikeのときにつかう。前後にいれることでキーワードを含む意味を指してる。
            if (order != null && order.equals("priceAsc")) {
                stringInstrumentPage = stringInstrumentRepository.findByHandednessOrderByPriceAsc(handedness, pageable);
            } else {
                stringInstrumentPage = stringInstrumentRepository.findByHandednessOrderByCreatedAtDesc(handedness,
                        pageable);
            }
        } else if (price != null) {
            stringInstrumentPage = stringInstrumentRepository.findByPriceLessThanEqual(price, pageable);
            if (order != null && order.equals("priceAsc")) {
                stringInstrumentPage = stringInstrumentRepository.findByPriceLessThanEqualOrderByPriceAsc(price,
                        pageable);
            } else {
                stringInstrumentPage = stringInstrumentRepository.findByPriceLessThanEqualOrderByCreatedAtDesc(price,
                        pageable);
            }
        } else {
            stringInstrumentPage = stringInstrumentRepository.findAll(pageable);
            if (order != null && order.equals("priceAsc")) {
                stringInstrumentPage = stringInstrumentRepository.findAllByOrderByPriceAsc(pageable);
            } else {
                stringInstrumentPage = stringInstrumentRepository.findAllByOrderByCreatedAtDesc(pageable);
            }
        }
        model.addAttribute("stringInstrumentPage", stringInstrumentPage);
        model.addAttribute("nameKeyword", nameKeyword);
        model.addAttribute("area", handedness);
        model.addAttribute("price", price);
        model.addAttribute("order", order);

        return "stringInstrument/index";
    }

    @GetMapping("/{id}")  //詳細ページ表示
    public String show(@PathVariable(name = "id") Integer id, Model model) {
        StringInstrument stringInstrument = stringInstrumentRepository.getReferenceById(id);

        model.addAttribute("stringInstrument", stringInstrument);
        model.addAttribute("reservationInputForm", new ReservationInputForm());
        return "stringInstrument/show";
    }
}
