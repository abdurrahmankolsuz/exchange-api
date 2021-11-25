package com.integratedfinance.exchangerate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "/application.properties")
class SpringBootPropertySourceTest {

    @Value("${currency.api.base.uri}")
    String api;

    @Value("${api.key}")
    String apiKey;

    @Test
    void testApi() {
        assertThat(api).isEqualTo("http://api.currencylayer.com/");
    }

    @Test
    void testApiKey() {
        assertThat(apiKey).isEqualTo("513b48624c5e45c8824a77ad4a58be1c");
    }
}