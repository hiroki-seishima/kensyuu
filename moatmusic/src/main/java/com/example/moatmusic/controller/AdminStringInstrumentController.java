package com.example.moatmusic.controller;  //管理者用弦楽器一覧のコントローラー

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.moatmusic.entity.StringInstrument;
import com.example.moatmusic.repository.StringInstrumentRepository;

@Controller
@RequestMapping("/admin/stringInstrument")  //このページでのメソッド
public class AdminStringInstrumentController {
    private final StringInstrumentRepository stringInstrumentRepository;

    public AdminStringInstrumentController(StringInstrumentRepository stringInstrumentRepository) {  //コンストラクタ
        this.stringInstrumentRepository = stringInstrumentRepository;
    }

    @GetMapping
    public String index(Model model) {
        List<StringInstrument> stringInstrument = stringInstrumentRepository.findAll();  //検索したものをすべてリスト

        model.addAttribute("stringInstrument", stringInstrument);  //画面に受け渡す

        return "admin/stringInstrument/index";  //弦楽器リストを表示
    }

}
