package com.example.project.config.filter;

import lombok.Getter;

@Getter
public enum ExcludeAuthenticationUrl {

    KEYS("/ks/**"), Login("/login"), TOKEN("/token/refresh-token"), H2("/h2-console/**");

    private final String url;

    ExcludeAuthenticationUrl(String url) {

        this.url = url;
    }
}
