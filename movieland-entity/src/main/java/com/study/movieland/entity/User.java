package com.study.movieland.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.study.movieland.view.Views;
import lombok.Data;

@Data
public class User {
    @JsonView(Views.Summary.class)
    private int id;
    @JsonView(Views.Summary.class)
    private String nickname;
}
