package uz.online.study_sets.sevice;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.online.study_sets.common.util.DateUtil;
import uz.online.study_sets.common.util.SecurityUtils;
import uz.online.study_sets.dto.UserDto;
import uz.online.study_sets.entity.UserEntity;
import uz.online.study_sets.repository.UserRepository;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final Logger log = LoggerFactory.getLogger(getClass().getName());

    public List<UserDto> getUserAll() {
        log.atInfo().log("!Получение... Все пользователи");
        return userRepository.getAllUser().stream().map(user->{
            UserDto userDto = user.toDto();
            userDto.setBirtDate(user.getBirtDate() == null ? null : user.getBirtDate().toString());
            return userDto;
        }).collect(Collectors.toList());
    }

    public UserDto getUserInformation(Long id) {
        UserEntity userInformation = userRepository.getUserInformation(id);

        log.atInfo().log("!Получение... Информация о пользователе по ИД");

        UserDto responseInformationUser = userInformation.toDto();
        responseInformationUser.setBirtDate(userInformation.getBirtDate() == null ? null : userInformation.getBirtDate().toString());

        return responseInformationUser;
    }

    @Transactional
    public Boolean updateUser(UserDto userDto) {
        UserEntity user = userRepository.findByPhoneNumber(SecurityUtils
                .getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("curren user username not found!"));

        log.atInfo().log("!Обновление... пользователя");

        user.forUpdate();

        if (!StringUtils.isEmpty(userDto.getFirstname())) user.setFirstname(userDto.getFirstname());
        if (!StringUtils.isEmpty(userDto.getLastname())) user.setLastname(userDto.getLastname());
        if (!StringUtils.isEmpty(userDto.getPhoneNumber())) user.setPhoneNumber(userDto.getPhoneNumber());
        if (!StringUtils.isEmpty(userDto.getMiddleName())) user.setMiddleName(userDto.getMiddleName());
        if (!StringUtils.isEmpty(userDto.getPassword()))
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if (!StringUtils.isEmpty(userDto.getBirtDate())) {
            try {
                user.setBirtDate(DateUtils.parseDate(userDto.getBirtDate(), DateUtil.PATTERN14));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        userRepository.save(user);
        return true;
    }

    @Transactional
    public Boolean userDelete(Long id) {

        Integer userDeleteIsSuccess = userRepository.userDelete(id);

        return userDeleteIsSuccess > 0;
    }
}
