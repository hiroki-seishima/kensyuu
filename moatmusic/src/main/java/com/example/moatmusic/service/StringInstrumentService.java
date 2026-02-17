package com.example.moatmusic.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.moatmusic.entity.StringInstrument;
import com.example.moatmusic.form.StringInstrumentEditForm;
import com.example.moatmusic.form.StringInstrumentRegisterForm;
import com.example.moatmusic.repository.StringInstrumentRepository;

@Service
public class StringInstrumentService {
    private final StringInstrumentRepository stringInstrumentRepository;

    public StringInstrumentService(StringInstrumentRepository stringInstrumentRepository) {
        this.stringInstrumentRepository = stringInstrumentRepository;
    }

    @Transactional
    public void create(StringInstrumentRegisterForm stringInstrumentRegisterForm) { // 書新規登録メソッド
        StringInstrument stringInstrument = new StringInstrument(); // 楽器エンティティ生成
        MultipartFile imageFile = stringInstrumentRegisterForm.getImageFile(); // 登録した楽器の画像ファイル

        if (!imageFile.isEmpty()) {// 画像ファイルをストレージに保存
            String imageName = imageFile.getOriginalFilename();
            String hashedImageName = generateNewFileName(imageName);
            Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
            copyImageFile(imageFile, filePath);
            stringInstrument.setImageName(hashedImageName);
        }

        stringInstrument.setName(stringInstrumentRegisterForm.getName());
        stringInstrument.setDescription(stringInstrumentRegisterForm.getDescription());
        stringInstrument.setPrice(stringInstrumentRegisterForm.getPrice());
        stringInstrument.setCapacity(stringInstrumentRegisterForm.getCapacity());
        stringInstrument.setHandedness(stringInstrumentRegisterForm.getHandedness());

        stringInstrumentRepository.save(stringInstrument);// DBに保存
    }

    @Transactional
    public void update(StringInstrumentEditForm stringInstrumentEditForm) {  //楽器詳細編集メソッド
        StringInstrument stringInstrument = stringInstrumentRepository
                .getReferenceById(stringInstrumentEditForm.getId());
        MultipartFile imageFile = stringInstrumentEditForm.getImageFile();

        if (!imageFile.isEmpty()) {
            String imageName = imageFile.getOriginalFilename();
            String hashedImageName = generateNewFileName(imageName);
            Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
            copyImageFile(imageFile, filePath);
            stringInstrument.setImageName(hashedImageName);
        }

        stringInstrument.setName(stringInstrumentEditForm.getName());
        stringInstrument.setDescription(stringInstrumentEditForm.getDescription());
        stringInstrument.setPrice(stringInstrumentEditForm.getPrice());
        stringInstrument.setCapacity(stringInstrumentEditForm.getCapacity());
        stringInstrument.setHandedness(stringInstrumentEditForm.getHandedness());

        stringInstrumentRepository.save(stringInstrument);
    }

    // UUIDを使って生成したファイル名を返す
    public String generateNewFileName(String fileName) {
        String[] fileNames = fileName.split("\\.");
        for (int i = 0; i < fileNames.length - 1; i++) {
            fileNames[i] = UUID.randomUUID().toString();
        }
        String hashedFileName = String.join(".", fileNames);
        return hashedFileName;
    }

    // 画像ファイルを指定したファイルにコピーする
    public void copyImageFile(MultipartFile imageFile, Path filePath) {
        try {
            Files.copy(imageFile.getInputStream(), filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
