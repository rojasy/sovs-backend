package com.uautso.sovs.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "tz.go.pmo")
public class ApplicationProperties {

    private String jwtKey;

    private String defaultPassword;

    private int maxFailedLoginCount =3;

    private int cacheMaxLimit = 100;

}
