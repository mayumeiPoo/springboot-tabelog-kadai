package com.example.nagoyameshi.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class UserEditForm {
	@NotNull
    private Integer id;
    
    @NotBlank(message = "氏名を入力してください。")
    private String name;
    
    @NotBlank(message = "フリガナを入力してください。")
    private String furigana;
    
    @NotBlank(message = "郵便番号を入力してください。")
    private String postalCode;
    
    @NotBlank(message = "住所を入力してください。")
    private String address;
    
    @NotBlank(message = "電話番号を入力してください。")
    private String phoneNumber;
    
    @NonNull
    private String birthmonth;
    
    @NonNull
    private String birthday;
    
    private String gender;
    
    @NotBlank(message = "メールアドレスを入力してください。")
    private String email;

    

	
    

}
