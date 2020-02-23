package lt.sdacademy.autoads.repository;

import lt.sdacademy.autoads.model.entity.UserEntity;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<UserEntity, Long> {

  UserEntity findOneById(Long id);

  UserEntity save(UserEntity userEntity);

  Boolean existsByEmail(String email);

  UserEntity findOneByEmail(String email);
}
