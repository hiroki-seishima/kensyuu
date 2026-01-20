package com.example.moattravel2.controller; //7-4

// import java.util.List;  // コレクションのリスト 8-2でpageableインターフェイスを作成したためlistは不要
import org.springframework.data.domain.Page; //8-2で追加　pageインターフェイス（ページデータ）
import org.springframework.data.domain.Pageable; //8-2で追加　pageableインターフェイス（ページ情報）
import org.springframework.data.domain.Sort.Direction; //8-3で追加　ソート（昇順、降順）を指定するもの
import org.springframework.data.web.PageableDefault; //8-3で追加　　Pageableのデフォルト値設定アノテーション
import org.springframework.stereotype.Controller; //コントローラのフレームワーク
import org.springframework.ui.Model; //モデルのフレームワーク
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; //10-3 URLパスの一部をメソッド引数にバインドするためのアノテーションを使うためのインポート
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam; //クエリパラメータ取得アノテーション
import com.example.moattravel2.entity.House; // Houseにアクセス
import com.example.moattravel2.repository.HouseRepository; //HouseRepositoryにアクセス

@Controller
@RequestMapping("/admin/houses")
public class AdminHouseController {
    private final HouseRepository houseRepository;

    public AdminHouseController(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
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

    @GetMapping("/{id}")  //10-3で追加
    public String show(@PathVariable(name = "id") Integer id, Model model) {
        House house = houseRepository.getReferenceById(id);
        model.addAttribute("house", house);
        return "admin/houses/show";
    }
}
