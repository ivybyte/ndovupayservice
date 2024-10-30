package com.example.ndovupayservice.user;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum UsrStatus{
    ACTIVE,INACTIVE,DELETED,DISABLED
}
