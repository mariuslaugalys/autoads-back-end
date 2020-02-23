package lt.sdacademy.autoads.services;

import lt.sdacademy.autoads.converter.AdvertDtoConverter;
import lt.sdacademy.autoads.converter.UserDtoConverter;
import lt.sdacademy.autoads.converter.UserFormConverter;
import lt.sdacademy.autoads.model.dto.UserAdvertsDto;
import lt.sdacademy.autoads.model.dto.UserForm;
import lt.sdacademy.autoads.model.entity.UserEntity;
import lt.sdacademy.autoads.model.exception.InvalidValuesException;
import lt.sdacademy.autoads.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final UserFormConverter userFormConverter;
  private final UserDtoConverter userDtoConverter;
  private final PasswordEncoder passwordEncoder;
  private final AdvertDtoConverter advertDtoConverter;

  public UserService(
      UserRepository userRepository,
      UserFormConverter userFormConverter,
      UserDtoConverter userDtoConverter,
      PasswordEncoder passwordEncoder,
      AdvertDtoConverter advertDtoConverter
  ) {
    this.userRepository = userRepository;
    this.userFormConverter = userFormConverter;
    this.userDtoConverter = userDtoConverter;
    this.passwordEncoder = passwordEncoder;
    this.advertDtoConverter = advertDtoConverter;
  }

  public UserAdvertsDto getUser(Long id) {
    return userDtoConverter
        .convertWithAdverts(
            userRepository.findOneById(id),
            (advert) -> advertDtoConverter.convertWithUser(advert, userDtoConverter::convert)
        );
  }

  public UserAdvertsDto saveUser(UserForm userForm) {
    if (!userRepository.existsByEmail(userForm.getEmail())) {
      UserEntity userEntity = userFormConverter.convert(userForm);
      userEntity.setPassword(passwordEncoder.encode(userForm.getPassword()));
      userEntity = userRepository.save(userEntity);
      return userDtoConverter.convertWithAdverts(
          userEntity,
          (advert) -> advertDtoConverter.convertWithUser(advert, userDtoConverter::convert)
      );
    }
    throw new InvalidValuesException().addError("email", "User with email already exists");
  }

  @Transactional
  public UserAdvertsDto updateUser(UserForm userForm, Long authorizedUserId) {
    UserEntity userEntity = userRepository.findOneById(authorizedUserId);

    if (!userEntity.getEmail().equals(userForm.getEmail())
    ) {
      if (userRepository.existsByEmail(userForm.getEmail())) {
        throw new InvalidValuesException().addError("email", "User with email already exists");
      }
      userEntity.setEmail(userForm.getEmail());
    }

    userEntity.setPassword(passwordEncoder.encode(userForm.getPassword()));
    userEntity.setFirstName(userForm.getFirstName());
    userEntity.setLastName(userForm.getLastName());
    userEntity.setName(userForm.getName());
    userEntity.setPhone(userForm.getPhone());

    return userDtoConverter.convertWithAdverts(
        userEntity,
        (advert) -> advertDtoConverter.convertWithUser(advert, userDtoConverter::convert)
    );
  }
}
