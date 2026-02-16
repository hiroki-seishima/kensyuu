package com.example.moatmusic.controller; //管理者用弦楽器一覧のコントローラー

//import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.moatmusic.entity.StringInstrument;
import com.example.moatmusic.form.StringInstrumentEditForm;
import com.example.moatmusic.form.StringInstrumentRegisterForm;
import com.example.moatmusic.repository.StringInstrumentRepository;
import com.example.moatmusic.service.StringInstrumentService;

@Controller
@RequestMapping("/admin/stringInstrument") // このページで行うメソッド
public class AdminStringInstrumentController {

    private final StringInstrumentRepository stringInstrumentRepository;
    private final StringInstrumentService stringInstrumentService;

    public AdminStringInstrumentController(StringInstrumentRepository stringInstrumentRepository,
            StringInstrumentService stringInstrumentService) { // コンストラクタ
        this.stringInstrumentRepository = stringInstrumentRepository;
        this.stringInstrumentService = stringInstrumentService;
    }

    @GetMapping
    public String index(Model model,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
            @RequestParam(name = "keyword", required = false) String keyword) { // ページ番号がデフォ。10ページずつ表示、ID順、昇順
        // List<StringInstrument> stringInstrument =
        // stringInstrumentRepository.findAll(); //検索したものをすべてリスト ページネーションをつけたため削除
        // Page<StringInstrument> stringInstrumentPage =
        // stringInstrumentRepository.findAll(pageable); //ページネーションでDBから取得
        // 検索付きページネーション表示をさせるため削除

        Page<StringInstrument> stringInstrumentPage;
        if (keyword != null && !keyword.isEmpty()) {
            stringInstrumentPage = stringInstrumentRepository.findByNameLike("%" + keyword + "%", pageable); // キーワードがからでない時に部分一致検索でかかったものを取得
        } else {
            stringInstrumentPage = stringInstrumentRepository.findAll(pageable); // それ以外がすべて表示（キーワードが空や、部分一致検索に引っ掛からなかったものについてはページネーション内ですべて表示）
        }

        model.addAttribute("stringInstrumentPage", stringInstrumentPage); // 画面に受け渡す
        model.addAttribute("keyword", keyword); // 入力したrキーワードを画面に受け渡す

        return "admin/stringInstrument/index"; // 弦楽器リストを表示
    }

    @GetMapping("/{id}") // 各楽器の詳細ページ
    public String show(@PathVariable(name = "id") Integer id, Model model) {
        StringInstrument stringInstrument = stringInstrumentRepository.getReferenceById(id); // DBから任意のIDを検索しそのデータを取得

        model.addAttribute("stringInstrument", stringInstrument); // 任意のIDをもった楽器の情報を画面に受け渡し

        return "admin/stringInstrument/show"; // 楽器詳細ページへ
    }

    @GetMapping("/register") // 登録ページの情報を取得
    public String register(Model model) {
        model.addAttribute("stringInstrumentRegisterForm", new StringInstrumentRegisterForm());
        return "admin/stringInstrument/register";
    }

    @PostMapping("/create") // 新規登録した楽器のページでエラーになれば登録ページへ、成功すればリダイレクトで成功メッセ＋楽器一覧ページへ
    public String create(@ModelAttribute @Validated StringInstrumentRegisterForm stringInstrumentRegisterForm,
            BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "admin/stringInstrument/register";
        }

        stringInstrumentService.create(stringInstrumentRegisterForm);
        redirectAttributes.addFlashAttribute("successMessage",
                "楽器を登録しました。");

        return "redirect:/admin/stringInstrument";
    }

    @GetMapping("/{id}/edit")  //任意のIDから情報を取得したものを編集して保存するメソッド
    public String edit(@PathVariable(name = "id") Integer id, Model model) {
        StringInstrument stringInstrument = stringInstrumentRepository.getReferenceById(id);
        String imageName = stringInstrument.getImageName();
        StringInstrumentEditForm stringInstrumentEditForm = new StringInstrumentEditForm(stringInstrument.getId(),
                stringInstrument.getName(), null,
                stringInstrument.getDescription(), stringInstrument.getPrice(), stringInstrument.getCapacity(),
                stringInstrument.getHandedness());

        model.addAttribute("imageName", imageName);
        model.addAttribute("stringInstrumentEditForm", stringInstrumentEditForm);

        return "admin/stringInstrument/edit";  //編集ページへ
    }
}
