package uz.prod.backcrm.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.prod.backcrm.entity.*;
import uz.prod.backcrm.enums.LanguageName;
import uz.prod.backcrm.enums.ProjectTypeName;
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
    private final ProjectTypeRepository projectTypeRepository;
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
            addProjectType();
        }
    }

    private void addRoles(){
        Role admin = Role.builder().name(RoleName.ROLE_ADMIN).build();
        Role pm = Role.builder().name(RoleName.ROLE_PM).build();
        Role programmer = Role.builder().name(RoleName.ROLE_PROGRAMMER).build();
        roleRepository.saveAll(new ArrayList<>(List.of(admin, programmer, pm)));
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
                .role(roleRepository.findByName(RoleName.ROLE_ADMIN))
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();
        userRepository.save(admin);
    }

    private void addStatus(){
        Status completed = Status.builder().name(StatusName.COMPLETED).build();
        Status created = Status.builder().name(StatusName.CREATED).build();
        Status deadlineHasPassed = Status.builder().name(StatusName.DEADLINE_HAS_PASSED).build();
        Status inProgress = Status.builder().name(StatusName.IN_PROGRESS).build();
        statusRepository.saveAll(new ArrayList<>(List.of(completed, created, deadlineHasPassed, inProgress)));
    }

    private void addProjectType(){
        ProjectType telegramBot = ProjectType.builder().name(ProjectTypeName.TELEGRAM_BOT).build();
        ProjectType webSiteWordpress = ProjectType.builder().name(ProjectTypeName.WEB_SITE_WORDPRESS).build();
        ProjectType mobileApp = ProjectType.builder().name(ProjectTypeName.MOBILE_APP).build();
        ProjectType webSystem = ProjectType.builder().name(ProjectTypeName.WEB_SYSTEM).build();
        ProjectType designServices = ProjectType.builder().name(ProjectTypeName.DESIGN_SERVICES).build();
        ProjectType googleAds = ProjectType.builder().name(ProjectTypeName.GOOGLE_ADS).build();
        projectTypeRepository.saveAll(new ArrayList<>(List.of(telegramBot, webSiteWordpress, mobileApp,
                webSystem, designServices, googleAds)));
    }

}
