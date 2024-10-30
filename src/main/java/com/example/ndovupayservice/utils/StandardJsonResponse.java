package com.example.ndovupayservice.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Setter
@Getter
@RequiredArgsConstructor
public class StandardJsonResponse  extends ResponseHelper{
    private boolean success = true;
    private HashMap<String, Object> messages = new HashMap<>();
    private HashMap<String, Object> data = new HashMap<>();
    private int total = 0;
    private String targetUrl;
    private int status = 200;
    private Object result;

}
