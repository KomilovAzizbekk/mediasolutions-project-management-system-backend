package uz.prod.backcrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.prod.backcrm.entity.Language;

public interface LanguageRepository extends JpaRepository<Language, Long> {
}
