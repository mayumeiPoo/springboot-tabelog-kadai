package com.example.nagoyameshi.form;

import org.springframework.web.multipart.MultipartFile;

import com.example.nagoyameshi.entity.Category;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShopRegisterForm {
	@NotBlank(message = "店舗名を入力してください。")
    private String name;
        
    private MultipartFile imageFile;
    
    @NotBlank(message = "説明を入力してください。")
    private String description;   
    
    @NotNull(message = "予算を入力してください。")
    @Min(value = 1, message = "予算は1円以上に設定してください。")
    private Integer price;  
    
    @NotNull(message = "１テーブルあたり人数を入力してください。")
    @Min(value = 1, message = "1人以上に設定してください。")
    private Integer capacity;     
    
    @NotBlank(message = "郵便番号を入力してください。")
    private String postalCode;
    
    @NotBlank(message = "住所を入力してください。")
    private String address;
    
    @NotBlank(message = "電話番号を入力してください。")
    private String phoneNumber;
    
    @NotBlank(message = "最寄駅を入力してください。")
    private  String station;
    
    
    private String timeStart;
    
    
    private String timeEnd;
    
    @NotNull(message = "カテゴリを選択してください。")
    @ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

    private Double lat;
    private Double lng;
   
}
