package com.example.project.config.filter;

import lombok.Getter;

@Getter
public enum ExcludeAuthenticationUrl {

    KEYS("/api/ks/**"), LOGIN("/api/login"), TOKEN("/api/token/refresh-token"), H2("/api/h2-console/**");

    private final String url;

    ExcludeAuthenticationUrl(String url) {

        this.url = url;
    }
}
