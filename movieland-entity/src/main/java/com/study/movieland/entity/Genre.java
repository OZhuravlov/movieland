package com.study.movieland.entity;

import lombok.Getter;

@Getter
public class Genre {
    private int id;
    private String name;

    private Genre(){}

    public static Builder newBuilder() {
        return new Genre().new Builder();
    }

    public class Builder{
        private Builder(){}

        public Builder setId(int id){
            Genre.this.id = id;
            return this;
        }

        public Builder setName(String name){
            Genre.this.name = name;
            return this;
        }

        public Genre build() {
            return Genre.this;
        }
    }
}
