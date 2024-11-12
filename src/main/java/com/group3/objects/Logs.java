package com.group3.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor

public class Logs {

    private LocalDateTime time;
    private String message;


}
