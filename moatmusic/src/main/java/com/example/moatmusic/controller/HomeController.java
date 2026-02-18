package com.example.moatmusic.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.moatmusic.entity.StringInstrument;
import com.example.moatmusic.repository.StringInstrumentRepository;

@Controller
public class HomeController {

    private final StringInstrumentRepository stringInstrumentRepository;

    public HomeController(StringInstrumentRepository stringInstrumentRepository) {
        this.stringInstrumentRepository = stringInstrumentRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<StringInstrument> newStringInstrument = stringInstrumentRepository.findTop10ByOrderByCreatedAtDesc();
        model.addAttribute("newStringInstrument", newStringInstrument);
        return "index";
    }
}