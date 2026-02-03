package com.example.moattravel3.controller;

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

import com.example.moattravel3.entity.House;
import com.example.moattravel3.form.HouseEditForm;
import com.example.moattravel3.form.HouseRegisterForm;
import com.example.moattravel3.repository.HouseRepository;
import com.example.moattravel3.service.HouseService;

@Controller
@RequestMapping("/admin/houses")
public class AdminHouseController {
    private final HouseRepository houseRepository;
    private final HouseService houseService;

    public AdminHouseController(HouseRepository houseRepository , HouseService houseService){
        this.houseRepository = houseRepository;
        this.houseService =houseService;
    }

    @GetMapping  //ページとキーワード検索
    public String index(Model model,@PageableDefault(page=0,size=10,sort="id",direction=Direction.ASC)Pageable pageable,@RequestParam(name="keyword",required=false)String keyword){
        //Page<House> housePage =houseRepository.findAll(pageable);
        Page<House> housePage;

        if(keyword !=null && !keyword.isEmpty()){
            housePage = houseRepository.findByNameLike("%" +keyword + "%",pageable);
        }else{
            housePage = houseRepository.findAll(pageable);
        }
        //model.addAttribute("houses",houses);
        model.addAttribute("housePage", housePage);
        model.addAttribute("keyword",keyword);

        return "admin/houses/index";
    }

    @GetMapping("/{id}")  //各民宿の詳細フォームの表示
    public String show(@PathVariable(name="id") Integer id ,Model model){
        House house = houseRepository.getReferenceById(id);  //getReferenceByIdはJpaRepositoryインターファイスの中のメソッド
        model.addAttribute("house",house);
        return "admin/houses/show";
    }

    @GetMapping("/register")  //民宿の新規登録フォームの表示
    public String register(Model model){
        model.addAttribute("houseRegisterForm",new HouseRegisterForm());
        return "admin/houses/register";
    }

    @PostMapping("/create")  //民宿の新規登録の実行。成功なら登録しましたが表示され、失敗（未入力等）なら新規登録フォームを再表示
    public String create(@ModelAttribute @Validated HouseRegisterForm houseRegisterForm,BindingResult bindingResult,RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            return "admin/houses/register";

        }
        houseService.create(houseRegisterForm);
        redirectAttributes.addFlashAttribute("successMessage","民宿を登録しました。");

        return "redirect:/admin/houses";  //housesに移動を指示するためにredirectをつける
    }

    @GetMapping("/{id}/edit")  //民宿の編集フォームの表示
    public String edit(@PathVariable(name="id") Integer id,Model model){
        House house = houseRepository.getReferenceById(id);
        String imageName = house.getImageName();
        HouseEditForm houseEditForm = new HouseEditForm(house.getId(),house.getName(),null,house.getDescription(),house.getPrice(),house.getCapacity(),house.getPostalCode(),house.getAddress(),house.getPhoneNumber());
        model.addAttribute("imageName",imageName);
        model.addAttribute("houseEditForm",houseEditForm);

        return "admin/houses/edit";
    }

    @PostMapping("/{id}/update") //民宿の編集実行から成功なら編集しました。失敗（未入力等）なら再度編集フォームの表示
    public String update(@ModelAttribute @Validated HouseEditForm houseEditForm,BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "admin/houses/edit";
        }
        houseService.update(houseEditForm);
        redirectAttributes.addFlashAttribute("successMessage","民宿情報を編集しました。");

        return "redirect:/admin/houses";
    }

    @PostMapping("/{id}/delete") //民宿の削除実行から成功なら削除しましたが表示
    public String delete(@PathVariable(name = "id")Integer id,RedirectAttributes redirectAttributes){
        houseRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage","民宿を削除しました。");
        return "redirect:/admin/houses";
    }

}
    