/*
 * Copyright (c) 2021-2022, gaoweixuan (breeze-cloud@foxmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.breeze.boot.security.config;

import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

/**
 * jwt配置
 * 过滤器
 *
 * @author breeze
 * @date 2022-08-31
 */
@Configuration
public class JwtConfig {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录jwt译码器
     *
     * @return {@link JwtDecoder}
     */
    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.jwtProperties.getRsaPublicKey()).build();
    }

    /**
     * 登录jwt编码器
     *
     * @return {@link JwtEncoder}
     */
    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.jwtProperties.getRsaPublicKey()).privateKey(this.jwtProperties.getRsaPrivateKey()).build();
        return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(jwk)));
    }

    public SignedJWT sign(SignedJWT jwt) {
        try {
            jwt.sign(new RSASSASigner(this.jwtProperties.getRsaPrivateKey()));
            return jwt;
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}
