package com.example.moattravel3.controller;

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

import com.example.moattravel3.entity.User;
import com.example.moattravel3.repository.UserRepository;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {   //コンストラクタ
    private final UserRepository userRepository;
    public AdminUserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @GetMapping
    public String index(@RequestParam(name = "keyword",required = false)String keyword,@PageableDefault(page = 0,size = 10,sort = "id", direction = Direction.ASC)Pageable pageable,Model model){
        Page<User> userPage;

        if (keyword !=null && !keyword.isEmpty()){  //キーワードがnullかつ空でないとき
            userPage = userRepository.findByNameLikeOrFuriganaLike("%" + keyword + "%","%" + keyword + "%" , pageable);  //ユーザー名（漢字またはフリガナ）検索
        }else {
            userPage = userRepository.findAll(pageable); //それ以外なら全てを表示
        }

        model.addAttribute("userPage",userPage); //ページング付きユーザーページデータを画面に受け渡し
        model.addAttribute("keyword",keyword);  //検索キーワードを画面に受けわたし

        return "admin/users/index";  //登録情報閲覧画面を表示
    }
    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id,Model model){  //管理者が登録したユーザー情報を確認するためのメソッド
        User user = userRepository.getReferenceById(id);  //idを指定してDBから特定のユーザーの情報を取得
        model.addAttribute("user",user);  //取得した情報を画面に渡す
        return "admin/users/show";  //管理者用登録情報を表示
    }


}
