package co.com.poli.authservice.service;


import co.com.poli.authservice.dto.*;
import co.com.poli.authservice.entity.AuthUser;
import co.com.poli.authservice.repository.AuthUserRepository;
import co.com.poli.authservice.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserService {

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    public AuthUser save(NewUserDto dto) {
        Optional<AuthUser> user = authUserRepository.findByEmail(dto.getEmail());
        if(user.isPresent())
            return null;
        String password = passwordEncoder.encode(dto.getPassword());
        AuthUser authUser = AuthUser.builder()
                .email(dto.getEmail())
                .password(password)
                .name(dto.getName())
                .lastName(dto.getLastName())
                .documentType(dto.getDocumentType())
                .documentNumber(dto.getDocumentNumber())
                .cellPhone(dto.getCellPhone())
                .address(dto.getAddress())
                .role(dto.getRole())
                .build();
        return authUserRepository.save(authUser);
    }

    public TokenDto login(AuthUserDto dto) {
        Optional<AuthUser> user = authUserRepository.findByEmail(dto.getEmail());
        if(!user.isPresent())
            return null;
        if(passwordEncoder.matches(dto.getPassword(), user.get().getPassword()))
            return new TokenDto(jwtProvider.createToken(user.get()),user.get().getLocked());
        return null;
    }

    public TokenDto validate(String token, RequestDto dto) {
        if(!jwtProvider.validate(token, dto))
            return null;
        String username = jwtProvider.getUserNameFromToken(token);
        if(!authUserRepository.findByEmail(username).isPresent())
            return null;
        return new TokenDto(token,authUserRepository.findByEmail(username).get().getLocked());
    }

    public AuthUser resetPassword(ResetPassWordUserDto dto) {
        Optional<AuthUser> user = authUserRepository.findByEmail(dto.getEmail());
        if(!user.isPresent())
            return null;
        String password = passwordEncoder.encode(user.get().getDocumentNumber());
        user.get().setPassword(password);
        user.get().setLocked(true);
        return authUserRepository.save(user.get());
    }

    public AuthUser changePassword(ChangePasswordUserDto dto) {
        Optional<AuthUser> user = authUserRepository.findByEmail(dto.getEmail());
        if(!user.isPresent() || !dto.getOldPassword().equals(user.get().getDocumentNumber()))
            return null;
        String password = passwordEncoder.encode(dto.getNewPassword());
        user.get().setPassword(password);
        user.get().setLocked(false);
        return authUserRepository.save(user.get());
    }

    public AuthUser getAuthUser(String email) {
        return authUserRepository.findByEmail(email).get();
    }

    public UserInfoDto getInfoUser(String email) {
        AuthUser user = authUserRepository.findByEmail(email).get();
        return UserInfoDto.builder()
                .name(user.getName())
                .lastName(user.getLastName())
                .documentType(user.getDocumentType())
                .documentNumber(user.getDocumentNumber())
                .cellPhone(user.getCellPhone())
                .address(user.getAddress())
                .email(user.getEmail())
                .build();
    }
}
