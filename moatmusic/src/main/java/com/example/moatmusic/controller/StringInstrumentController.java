package com.example.moatmusic.controller;  //楽器一覧のコントローラー

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.moatmusic.entity.StringInstrument;
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
 @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, Model model) {
 Page<StringInstrument> stringInstrumentPage;

 if (nameKeyword != null && !nameKeyword.isEmpty()) {
 stringInstrumentPage =
stringInstrumentRepository.findByNameLike("%" + nameKeyword + "%", pageable);
 } else if (handedness != null && !handedness.isEmpty()) {
 stringInstrumentPage = stringInstrumentRepository.findByHandedness("%" + handedness + "%", pageable);
 } else if (price != null) {
 stringInstrumentPage = stringInstrumentRepository.findByPriceLessThanEqual(price, pageable);
 } else {
 stringInstrumentPage = stringInstrumentRepository.findAll(pageable);
 }

 model.addAttribute("stringInstrumentPage", stringInstrumentPage);
 model.addAttribute("nameKeyword", nameKeyword);
 model.addAttribute("area", handedness);
 model.addAttribute("price", price);

 return "stringInstrument/index";
 }
}
