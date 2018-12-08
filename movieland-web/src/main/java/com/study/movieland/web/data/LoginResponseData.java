package com.study.movieland.web.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseData {
    private String uuid;
    private String nickname;
}

