package com.example.moattravel3.service;  //民宿の登録、編集のコアサービス

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.moattravel3.entity.House;
import com.example.moattravel3.form.HouseEditForm;
import com.example.moattravel3.form.HouseRegisterForm;
import com.example.moattravel3.repository.HouseRepository;

@Service
public class HouseService {
    private final HouseRepository houseRepository;

    public HouseService(HouseRepository houseRepository){  //コンストラクタ
        this.houseRepository = houseRepository;
    }

    @Transactional
    public void create(HouseRegisterForm houseRegisterForm){  //新規民宿登録メソッド
        House house = new House();  //houseエンティティを作成
        MultipartFile imageFile = houseRegisterForm.getImageFile();  //画像を登録

        if (!imageFile.isEmpty()){  //画像ファイルの中が空でなければ
            String imageName = imageFile.getOriginalFilename();  //アップロード画像の元ファイル名を取得
            String hashedImageName = generateNewFileName(imageName);  //画像ファイルに名前をつける
            Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);//storageに保存
            copyImageFile(imageFile,filePath);  //画像をコピーして
            house.setImageName(hashedImageName); //DBにファイル名を保存


            //houseエンティティにフォームで入力した情報を設定
            house.setName(houseRegisterForm.getName());  
            house.setDescription(houseRegisterForm.getDescription());
            house.setPrice(houseRegisterForm.getPrice());
            house.setCapacity(houseRegisterForm.getCapacity());
            house.setPostalCode(houseRegisterForm.getPostalCode());
            house.setAddress(houseRegisterForm.getAddress());
            house.setPhoneNumber(houseRegisterForm.getPhoneNumber());

            houseRepository.save(house);  //houseテーブルDBに保存
        }
    }
    @Transactional
    public void update(HouseEditForm houseEditForm){  //民宿情報を編集するメソッド
        House house = houseRepository.getReferenceById(houseEditForm.getId());  //編集したい民宿をDBから呼び出す
        MultipartFile imageFile = houseEditForm.getImageFile();  //新規画像のみ処理
        if (!imageFile.isEmpty()){
            String imageName = imageFile.getOriginalFilename();
            String hashedImageName = generateNewFileName(imageName);
            Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
            copyImageFile(imageFile,filePath);
            house.setImageName(hashedImageName);

            //編集フォームで入力した情報を取得
            house.setName(houseEditForm.getName());
            house.setDescription(houseEditForm.getDescription());
            house.setPrice(houseEditForm.getPrice());
            house.setCapacity(houseEditForm.getCapacity());
            house.setPostalCode(houseEditForm.getPostalCode());
            house.setAddress(houseEditForm.getAddress());
            house.setPhoneNumber(houseEditForm.getPhoneNumber());

            houseRepository.save(house);  //DBに保存
        }
    }

        //UUIDをつかって生成したファイル名を返す（Storageのimagesの中にフォーム経由で追加された画像ファイル）
    public String generateNewFileName(String fileName){
        String[] fileNames = fileName.split("\\.");  //ファイルの拡張子
        for (int i =0; i<fileNames.length -1; i++) {  //forループ（最後以外）
            fileNames[i] = UUID.randomUUID().toString();//ファイル名部分をランダムでUUIDに
        }
        String hashedFileName = String.join(".",fileNames);//ファイル名とUUID生成したものを結合
        return hashedFileName;  //生成したファイル名を返す
    }
    

        //画像ファイルを指定したファイルにコピーする
    public void copyImageFile(MultipartFile imageFile,Path filePath) {
        try {
            Files.copy(imageFile.getInputStream(),filePath);  //画像データを読み込み指定したファイルへコピー
        }catch (IOException e){
            e.printStackTrace();  //ファイル読み書きエラー
        }
    }
}
