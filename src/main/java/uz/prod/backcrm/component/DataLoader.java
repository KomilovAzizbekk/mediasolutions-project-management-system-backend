package uz.prod.backcrm.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.prod.backcrm.entity.*;
import uz.prod.backcrm.enums.LanguageName;
import uz.prod.backcrm.enums.RoleName;
import uz.prod.backcrm.enums.StatusName;
import uz.prod.backcrm.repository.*;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final LanguageRepository languageRepository;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    private String mode;

    @Override
    public void run(String... args) throws Exception {
        if(mode.equals("always")){
            addRoles();
            addLanguages();
            addAdmin();
            addStatus();
        }
    }

    private void addRoles(){
        Role admin = Role.builder().roleName(RoleName.ROLE_ADMIN).build();
        Role pm = Role.builder().roleName(RoleName.ROLE_PM).build();
        Role programmer = Role.builder().roleName(RoleName.ROLE_PROGRAMMER).build();
        Role user = Role.builder().roleName(RoleName.ROLE_USER).build();
        roleRepository.saveAll(new ArrayList<>(List.of(admin, programmer, pm, user)));
    }

    private void addLanguages(){
        Language uz = Language.builder().languageName(LanguageName.UZ).build();
        Language ru = Language.builder().languageName(LanguageName.RU).build();
        Language en = Language.builder().languageName(LanguageName.EN).build();
        languageRepository.saveAll(new ArrayList<>(List.of(uz, en, ru)));
    }

    private void addAdmin(){
        User admin = User.builder()
                .username("admin")
                .phoneNumber("+998996280240")
                .firstName("Azizbek")
                .lastName("Komilov")
                .password(passwordEncoder.encode("Qwerty123@"))
                .role(roleRepository.findByRoleName(RoleName.ROLE_ADMIN))
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();
        userRepository.save(admin);
    }

    private void addStatus(){
        Status done = Status.builder().statusName(StatusName.DONE).build();
        Status accepted = Status.builder().statusName(StatusName.ACCEPTED).build();
        Status denied = Status.builder().statusName(StatusName.DENIED).build();
        Status inProgress = Status.builder().statusName(StatusName.IN_PROGRESS).build();
        statusRepository.saveAll(new ArrayList<>(List.of(done, accepted, denied, inProgress)));
    }

}
