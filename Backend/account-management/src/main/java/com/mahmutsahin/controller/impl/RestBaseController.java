package com.mahmutsahin.controller.impl;

import com.mahmutsahin.model.RootEntity;
import org.springframework.http.ResponseEntity;

public class RestBaseController {

    public <T> RootEntity <T> ok(T data) {
        return RootEntity.ok(data);
    }
    public <T> RootEntity <T> error(String errorMessage) {
        return RootEntity.error(errorMessage);
    }
}
