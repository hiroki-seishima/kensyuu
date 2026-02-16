package com.example.moatmusic.controller; //管理者用弦楽器一覧のコントローラー

//import java.util.List;

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
@RequestMapping("/admin/stringInstrument") // このページで行うメソッド
public class AdminStringInstrumentController {

    private final StringInstrumentRepository stringInstrumentRepository;

    public AdminStringInstrumentController(StringInstrumentRepository stringInstrumentRepository, HomeController homeController) { // コンストラクタ
        this.stringInstrumentRepository = stringInstrumentRepository; // コンストラクタ
    }

    @GetMapping
    public String index(Model model,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
            @RequestParam(name = "keyword", required = false) String keyword) { // ページ番号がデフォ。10ページずつ表示、ID順、昇順
        // List<StringInstrument> stringInstrument =
        // stringInstrumentRepository.findAll(); //検索したものをすべてリスト ページネーションをつけたため削除
        // Page<StringInstrument> stringInstrumentPage =
        // stringInstrumentRepository.findAll(pageable); //ページネーションでDBから取得  検索付きページネーション表示をさせるため削除
    
        Page<StringInstrument> stringInstrumentPage;
        if (keyword != null && !keyword.isEmpty()) {
            stringInstrumentPage = stringInstrumentRepository.findByNameLike("%" + keyword+ "%", pageable); //キーワードがからでない時に部分一致検索でかかったものを取得
        } else {
            stringInstrumentPage = stringInstrumentRepository.findAll(pageable); //それ以外がすべて表示（キーワードが空や、部分一致検索に引っ掛からなかったものについてはページネーション内ですべて表示）
        }

        model.addAttribute("stringInstrumentPage", stringInstrumentPage); // 画面に受け渡す
        model.addAttribute("keyword" , keyword); //入力したrキーワードを画面に受け渡す

        return "admin/stringInstrument/index"; // 弦楽器リストを表示
    }

}
