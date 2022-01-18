package com.example.projectlivingtogether.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class MemberDto {

    @NotBlank(message = "이메일을 필수로 입력하십시오.")
    @Email(message = "이메일 형식으로 입력하십시오.")
    private String email;

    @NotBlank(message = "비밀번호를 필수로 입력하십시오.")
    @Length(min = 10, max = 16, message = "10자리 이상 16자리 이하의 비밀번호를 입력하십시오.")
    private String password;

    @NotBlank(message = "이름을 필수로 입력하십시오.")
    private String name;

    @NotEmpty(message = "주소를 필수로 입력하십시오.")
    private String address;
}
