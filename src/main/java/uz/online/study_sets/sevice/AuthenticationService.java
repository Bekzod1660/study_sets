package uz.online.study_sets.sevice;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.online.study_sets.common.util.DateUtil;
import uz.online.study_sets.common.util.SecurityUtils;
import uz.online.study_sets.config.token.JwtService;
import uz.online.study_sets.dto.LoginRequestDto;
import uz.online.study_sets.dto.TokenResponseDto;
import uz.online.study_sets.dto.UserDto;
import uz.online.study_sets.entity.UserEntity;
import uz.online.study_sets.entity.role.RoleEnum;
import uz.online.study_sets.repository.UserRepository;

import java.text.ParseException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final Logger log = LoggerFactory.getLogger(getClass().getName());


    public TokenResponseDto register(UserDto request) {
        String jwtToken = jwtService.generateToken(saveUser(request));
        return TokenResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    public TokenResponseDto authenticate(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getPhoneNumber(),
                        request.getPassword()
                )
        );
        UserEntity user = repository.findByPhoneNumber(request.getPhoneNumber()).orElseThrow(() -> new UsernameNotFoundException("Not found!"));
        String jwtToken = jwtService.generateToken(user);
        return TokenResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    public UserEntity saveUser(UserDto userDto){
        Optional<UserEntity> byUsername = userRepository.findByPhoneNumber(userDto.getPhoneNumber());
        if (byUsername.isPresent()) {
            throw new IllegalArgumentException();
        }
        UserEntity user = userDto.toEntity("password","role");
        try {
            user.forCreate(SecurityUtils.getUserId());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setBirtDate(DateUtils.parseDate(userDto.getBirtDate(),DateUtil.PATTERN14));
            user.setRole(userDto.getRole() == RoleEnum.ADMIN ? RoleEnum.ADMIN : RoleEnum.USER);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return userRepository.save(user);
    }

}
