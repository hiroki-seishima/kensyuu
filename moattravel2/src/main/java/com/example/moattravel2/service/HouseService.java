package com.example.moattravel2.service; //13-2

import java.io.IOException; //Java標準ライブラリの入出力関連の例外クラスをインポート
import java.nio.file.Files; //Java NIO.2（New I/O）パッケージの静的ユーティリティクラスをインポート。ファイル・ディレクトリの作成、読み書き、コピー、移動、存在確認などをPathオブジェクトに対して実行
import java.nio.file.Path; //ファイルシステム上のパス（絶対パス・相対パス）をオブジェクトとして操作
import java.nio.file.Paths; //静的ファクトリーメソッドで文字列からPathインスタンスを作成します。PathとFilesクラス使用時の必須インポート
import java.util.UUID; //Java標準ライブラリのUUID（Universally Unique Identifier）クラスをインポート。128ビットのグローバル唯一識別子を生成・操作します。ランダムUUID（Type 4）が主で、重複確率は実質ゼロ。ファイル名、セッションID、データベース主キーなどに最適
import org.springframework.stereotype.Service; //Spring Frameworkのステレオタイプアノテーションで、ビジネスロジック層のクラスをSpring Beanとして自動登録 
import org.springframework.transaction.annotation.Transactional; //Spring Frameworkのトランザクション管理アノテーションをインポート.メソッドまたはクラスに適用すると、Springが自動的にデータベーストランザクションを開始・コミット・ロールバックを管理します。例外発生時は自動ロールバックでデータ整合性を保証。
import org.springframework.web.multipart.MultipartFile; //Spring MVC/Spring Boot で「アップロードされたファイル」を表すインターフェースを使うためのインポート
import com.example.moattravel2.entity.House; //entity.Houseにアクセスするためのもの
import com.example.moattravel2.form.HouseEditForm; //form.HouseEditFormへアクセス
import com.example.moattravel2.form.HouseRegisterForm; //form.HouseRegisterFormにアクセスするためのもの
import com.example.moattravel2.repository.HouseRepository; //repository.HouseRepositoryにアクセスするためのもの

@Service // サービスクラスとして機能する。
public class HouseService {
    private final HouseRepository houseRepository;

    public HouseService(HouseRepository houseRepository) { // コンストラクタ
        this.houseRepository = houseRepository;
    }

    @Transactional // トランザクションとは、データベースに対する一連の操作（INSERT、UPDATE、DELETEなど）を1つの論理的な処理単位として扱う仕組み
                   // メソッドにつけることでトランザクション化する。
    public void create(HouseRegisterForm houseRegisterForm) {
        House house = new House(); // インスタンス生成
        MultipartFile imageFile = houseRegisterForm.getImageFile();
        if (!imageFile.isEmpty()) {
            String imageName = imageFile.getOriginalFilename();
            String hashedImageName = generateNewFileName(imageName);
            Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
            copyImageFile(imageFile, filePath);
            house.setImageName(hashedImageName);
        }
        house.setName(houseRegisterForm.getName());
        house.setDescription(houseRegisterForm.getDescription());
        house.setPrice(houseRegisterForm.getPrice());
        house.setCapacity(houseRegisterForm.getCapacity());
        house.setPostalCode(houseRegisterForm.getPostalCode());
        house.setAddress(houseRegisterForm.getAddress());
        house.setPhoneNumber(houseRegisterForm.getPhoneNumber());
        houseRepository.save(house);
    }

    @Transactional  //15-2
    public void update(HouseEditForm houseEditForm) {  //更新メソッド
        House house = houseRepository.getReferenceById(houseEditForm.getId());
        MultipartFile imageFile = houseEditForm.getImageFile();
        if (!imageFile.isEmpty()) {
            String imageName = imageFile.getOriginalFilename();
            String hashedImageName = generateNewFileName(imageName);
            Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
            copyImageFile(imageFile, filePath);
            house.setImageName(hashedImageName);
        }
        house.setName(houseEditForm.getName());
        house.setDescription(houseEditForm.getDescription());
        house.setPrice(houseEditForm.getPrice());
        house.setCapacity(houseEditForm.getCapacity());
        house.setPostalCode(houseEditForm.getPostalCode());
        house.setAddress(houseEditForm.getAddress());
        house.setPhoneNumber(houseEditForm.getPhoneNumber());
        houseRepository.save(house);
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
