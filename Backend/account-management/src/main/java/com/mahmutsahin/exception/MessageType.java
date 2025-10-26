package com.mahmutsahin.exception;

import lombok.Getter;

@Getter
public enum MessageType {

    NO_RECORD_EXIST("1000","Kayıt bulunamadı"),
    GENERAL_EXCEPTION("2000","Genel bir hata oluştu");


    private String code;

    private String message;

    MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
