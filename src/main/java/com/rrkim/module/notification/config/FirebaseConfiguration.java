package com.rrkim.module.notification.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.rrkim.core.common.exception.UnhandledExecutionException;
import com.rrkim.core.common.util.ApplicationContextProvider;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Configuration
public class FirebaseConfiguration {

    private FirebaseApp firebaseApp;

    @Value("${framework-setting.firebase.admin-sdk-file-path}")
    private String firebaseAdminSdkFilePath;

    @PostConstruct
    public FirebaseApp initializeFcm() throws IOException {
        File file = new File(firebaseAdminSdkFilePath);
        if(!file.exists()) {
            return null;
        }

        InputStream serviceAccount = new FileInputStream(file);
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            firebaseApp = FirebaseApp.initializeApp(options);
        }

        return firebaseApp;
    }

    @Bean
    public FirebaseMessaging initFirebaseMessaging() {
        if (firebaseApp == null) {
            return null;
        }

        return FirebaseMessaging.getInstance(firebaseApp);
    }
}