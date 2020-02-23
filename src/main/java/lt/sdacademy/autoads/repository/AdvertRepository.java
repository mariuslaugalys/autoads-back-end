package lt.sdacademy.autoads.repository;

import java.util.List;
import lt.sdacademy.autoads.model.entity.AdvertEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

public interface AdvertRepository extends Repository<AdvertEntity, Long>, JpaSpecificationExecutor {

  AdvertEntity save(AdvertEntity advert);

  AdvertEntity findOneById(Long id);

  List<AdvertEntity> findAll();

  void deleteById(Long id);
}
