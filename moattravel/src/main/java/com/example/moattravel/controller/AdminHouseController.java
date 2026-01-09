package com.example.moattravel.controller;

// import java.util.List;  ページ毎表示にするために削除(8.3)

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute; // 13章で追加
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;  // 13章で追加
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;  // 13章で追加

import com.example.moattravel.entity.House;
import com.example.moattravel.form.HouseEditForm;
import com.example.moattravel.form.HouseRegisterForm; // 14章で追加
import com.example.moattravel.repository.HouseRepository;
import com.example.moattravel.service.HouseService;  //13章で追加

@Controller
@RequestMapping("/admin/houses")
public class AdminHouseController {
    private final  HouseRepository houseRepository;
    private final HouseService houseService;  //13章で追加

    public AdminHouseController(HouseRepository houseRepository, HouseService houseService) {  // 13章で第２引数HouseService houseServiceを追加
        this.houseRepository  = houseRepository;
        this.houseService = houseService;  // 13章で追加
    }

    @GetMapping
    public String index(Model model,@PageableDefault(page  = 0, size = 10,sort = "id", direction = Direction.ASC) Pageable pageable,@RequestParam(name = "keyword",required = false)String keyword){
        //  List<House> houses = houseRepository.findAll();  //リスト宣言 8.3で不要
        // Page<House> housePage = houseRepository.findAll(pageable);  //ページ宣言
        Page<House>housePage;
        if(keyword != null && !keyword.isEmpty()){
            housePage = houseRepository.findByNameLike("%" + keyword + "%" , pageable);
        }else{
            housePage = houseRepository.findAll(pageable);
        }

        // model.addAttribute("houses",houses);　//8.3で不要
        model.addAttribute("housePage",housePage);
        model.addAttribute("keyword",keyword);

        return  "admin/houses/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id,Model model){
        House house = houseRepository.getReferenceById(id);
        model.addAttribute("house",house);
        return "admin/houses/show";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("houseRegisterForm", new HouseRegisterForm());
        return "admin/houses/register";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute @Validated HouseRegisterForm houseRegisterForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "admin/houses/register";
        }
        houseService.create(houseRegisterForm);
        redirectAttributes.addFlashAttribute("successMessage", "民宿を登録しました。");

        return "redirect:/admin/houses";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(name = "id") Integer id, Model model) {
        House house = houseRepository.getReferenceById(id);
        String imageName = house.getImageName();
        HouseEditForm houseEditForm = new HouseEditForm(house.getId(), house.getName(), null, house.getDescription(), house.getPrice(), house.getCapacity(), house.getPostalCode(), house.getAddress(), house.getPhoneNumber());

        model.addAttribute("imageName", imageName);
        model.addAttribute("houseEditForm", houseEditForm);

        return "admin/houses/edit";
    }
    @PostMapping("/{id}/update")
    public String update(@ModelAttribute @Validated HouseEditForm houseEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "admin/houses/edit";
        }

        houseService.update(houseEditForm);
        redirectAttributes.addFlashAttribute("successMessage", "民宿情報を編集しました。");

        return "redirect:/admin/houses";
    }

    @PostMapping("/{id}/delete") //16章で追加
    public String delete(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) { //16章で追加
        houseRepository.deleteById(id); //16章で追加

        redirectAttributes.addFlashAttribute("successMessage", "民宿を削除しました。"); //16章で追加

        return "redirect:/admin/houses"; //16章で追加
    }
}
    
