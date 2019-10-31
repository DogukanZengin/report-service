package com.dz.financial.reporting.controller;

import com.dz.financial.reporting.model.rest.JwtRequest;
import com.dz.financial.reporting.model.rest.JwtResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

public abstract class AbstractControllerTest {

    @LocalServerPort
    private int port;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${server.ssl.key-store}")
    private String keystoreName;

    @Value("${server.ssl.key-store-password}")
    private String keystorePassword;

    @Bean
    protected RestTemplate restTemplate() {
        try {
            InputStream keyStoreInputStream = getClass().getResourceAsStream(keystoreName);
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(keyStoreInputStream, null);

            SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                    .loadKeyMaterial(keyStore, "keystorePassword".toCharArray())
                    .loadTrustMaterial(keyStore, new TrustAllStrategy()).build();

            HttpClient httpClient = HttpClients.custom().setSSLContext(sslContext)
                    .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();

            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);

            return new RestTemplate(requestFactory);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    protected JwtResponse login(String username, String password) throws URISyntaxException, HttpStatusCodeException {
        RestTemplate restTemplate = restTemplate();
        URI uri = new URI(createURLWithPort("/merchant/user/login"));
        JwtRequest jwtRequest = new JwtRequest(username, password);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<JwtRequest> httpRequest = new HttpEntity<>(jwtRequest, headers);

        return restTemplate.postForEntity(uri, httpRequest, JwtResponse.class).getBody();
    }

    protected String createURLWithPort(String uri) {
        return "https://localhost:" + port + contextPath + uri;
    }
}
