package com.example.moattravel2.controller; //7-4

// import java.util.List;  // コレクションのリスト 8-2でpageableインターフェイスを作成したためlistは不要
import org.springframework.data.domain.Page; //8-2で追加　pageインターフェイス（ページデータ）
import org.springframework.data.domain.Pageable; //8-2で追加　pageableインターフェイス（ページ情報）
import org.springframework.data.domain.Sort.Direction; //8-3で追加　ソート（昇順、降順）を指定するもの
import org.springframework.data.web.PageableDefault; //8-3で追加　　Pageableのデフォルト値設定アノテーション
import org.springframework.stereotype.Controller; //コントローラのフレームワーク
import org.springframework.ui.Model; //モデルのフレームワーク
import org.springframework.validation.BindingResult; //13-3 Spring MVCでフォーム検証エラーの結果を受け取るためのインターフェース。 @Valid （または @Validated ）で検証した結果（エラー情報）を格納します。必ず @Valid dto の直後に記述しないと例外が発生します。
import org.springframework.validation.annotation.Validated; //13-3　Spring FrameworkでBean Validationをトリガーし、グループ検証をサポートするアノテーション
import org.springframework.web.bind.annotation.GetMapping; //HTTP GETリクエストを処理するコントローラーメソッドを定義するショートカットアノテーション.データの取得・表示（一覧表示、フォーム表示、検索など）に使用
import org.springframework.web.bind.annotation.ModelAttribute; //13-3 Spring MVCでフォームデータやモデルオブジェクトを自動バインドするためのアノテーションをインポート
import org.springframework.web.bind.annotation.PathVariable; //10-3 URLパスの一部をメソッド引数にバインドするためのアノテーションを使うためのインポート
import org.springframework.web.bind.annotation.PostMapping; //13-3  HTTP POSTリクエストを処理するコントローラーメソッドを定義するショートカットアノテーション。データの作成・送信（予約登録、ファイルアップロード、ログインなど）に使用
import org.springframework.web.bind.annotation.RequestMapping; //HTTPリクエストをコントローラーメソッドにマッピングする基本アノテーション
import org.springframework.web.bind.annotation.RequestParam; //クエリパラメータ取得アノテーション
import org.springframework.web.servlet.mvc.support.RedirectAttributes; //13-3 リダイレクト時にデータを安全に引き継ぐためのSpring MVC専用のインターフェース
import com.example.moattravel2.entity.House; // Houseにアクセス
import com.example.moattravel2.form.HouseEditForm; //14-4 form.HouseEditFormにアクセス
import com.example.moattravel2.form.HouseRegisterForm; //12-4で追加　HouseRegisterFormにアクセス
import com.example.moattravel2.repository.HouseRepository; //HouseRepositoryにアクセス
import com.example.moattravel2.service.HouseService; //13-3 service.HouseServiceにアクセス

@Controller
@RequestMapping("/admin/houses")
public class AdminHouseController {
    private final HouseRepository houseRepository;
    private final HouseService houseService; // 13-3で追加

    public AdminHouseController(HouseRepository houseRepository, HouseService houseService) { // 13-3で,HouseService
                                                                                              // houseServiceを追加
        this.houseRepository = houseRepository;
        this.houseService = houseService; // 13-3で追加
    }

    @GetMapping
    public String index(Model model,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
            @RequestParam(name = "keyword", required = false) String keyword) { // 9-2で,@RequestParam(name =
                                                                                // "keyword",required = false) String
                                                                                // keyword を追加
        // public String index(Model model,Pageable pageable) { //8-2で第2引数にPageable
        // pageableを追加、8-3でPageableDefaultを追加したため削除
        // List<House> houses = houseRepository.findAll();
        // 8-2でpageableインターフェイスを作成したためlistは不要
        // Page<House> housePage = houseRepository.findAll(pageable); // 8-2で追加
        // リストの宣言に似ているが、別物のため、page,pageableのフレームワークスが必要となる。 ９−２で削除
        Page<House> housePage; // 9-2で追加
        if (keyword != null && !keyword.isEmpty()) { // 9-2で追加
            housePage = houseRepository.findByNameLike("%" + keyword + "%", pageable);
        } else {
            housePage = houseRepository.findAll(pageable);

        }
        // model.addAttribute("houses", houses); //model.addAttribute はSpring
        // MVCのModelオブジェクトのメソッドで、コントローラからThymeleafテンプレートにデータを渡すために使う。
        // 8-2でpageableインターフェイスを作成したためlistは不要
        model.addAttribute("housePage", housePage); // 8-2で追加 引数を入れてhousepageについてコントローラからThymeleafテンプレートにデータを渡す
        model.addAttribute("keyword", keyword); // 9-2で追加
        return "admin/houses/index";
    }

    @GetMapping("/{id}") // 10-3で追加
    public String show(@PathVariable(name = "id") Integer id, Model model) {
        House house = houseRepository.getReferenceById(id);
        model.addAttribute("house", house);
        return "admin/houses/show";
    }

    @GetMapping("/register") // 12-4で追加
    public String register(Model model) {
        model.addAttribute("houseRegisterForm", new HouseRegisterForm());
        return "admin/houses/register";
    }

    @PostMapping("/create") // 13-3で追加
    public String create(@ModelAttribute @Validated HouseRegisterForm houseRegisterForm, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "admin/houses/register";
        }
        houseService.create(houseRegisterForm);
        redirectAttributes.addFlashAttribute("successMessage", "民宿を登録しました。");
        return "redirect:/admin/houses";
    }

    @GetMapping("/{id}/edit") // 14-4 民宿編集ページ用のメソッド
    public String edit(@PathVariable(name = "id") Integer id, Model model) {
        House house = houseRepository.getReferenceById(id);
        String imageName = house.getImageName();
        HouseEditForm houseEditForm = new HouseEditForm(house.getId(), house.getName(), null, house.getDescription(),
                house.getPrice(), house.getCapacity(), house.getPostalCode(), house.getAddress(),
                house.getPhoneNumber());

        model.addAttribute("imageName", imageName);
        model.addAttribute("houseEditForm", houseEditForm);
        return "admin/houses/edit";

    }

    @PostMapping("/{id}/update")  //15-3更新メソッド
    public String update(@ModelAttribute @Validated HouseEditForm houseEditForm, BindingResult bindingResult,RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "admin/houses/edit";
        }
        houseService.update(houseEditForm);
        redirectAttributes.addFlashAttribute("successMessage", "民宿情報を編集しました。");
        return "redirect:/admin/houses";
    }
}
