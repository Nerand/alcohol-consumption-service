package ru.data.alcohol.alcoholconsumptionservice.security;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// Начальная загрузка пользователей в БД для Л7
@Component
@Profile("jpa")
public class AppUserDataLoader {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserDataLoader(AppUserRepository appUserRepository,
                             PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void loadUsers() {
        if (appUserRepository.count() > 0) {
            return;
        }

        AppUser user = new AppUser();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user123"));
        user.setRole("USER");

        AppUser admin = new AppUser();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole("ADMIN");

        appUserRepository.save(user);
        appUserRepository.save(admin);
    }
}
