package com.example.demo.service;

import com.example.demo.authority.UserAuthority;
import com.example.demo.dto.UserDetailsDTO;
import com.example.demo.dto.UserInfoDTO;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserCustomRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserCustomRepository userCustomRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입 구현 기능
     *
     * @param dto
     * @return
     */
    public String save(UserInfoDTO dto) {

        // 비밀번호 암호화 및 권한 부여
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        dto.setAuthority("USER");

        UserInfo userInfo = dtoToEntity(dto);
        userRepository.save(userInfo);

        return "Success";

    }

//    public Collection<UserInfoDTO> findAll() {
//        return userRepository.findAll();
//    }
//
//    public String deleteBySeq(long seq) {
//        userRepository.deleteBySeq(seq);
//        return "delete!";
//    }
//


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        // 아이디가 없을 때
        if(id == null || id.equals("")) {
            return userCustomRepository.findById(id)
                    .map(u -> new UserDetailsDTO(u, Collections.singleton(new SimpleGrantedAuthority(u.getAuthority()))))
                    .orElseThrow(() -> new AuthenticationServiceException(id));
        }
        // 비밀번호가 맞지 않는 경우
        else {
            return userCustomRepository.findById(id)
                    .map(u -> new UserDetailsDTO(u, Collections.singleton(new SimpleGrantedAuthority(u.getAuthority()))))
                    .orElseThrow(() -> new BadCredentialsException(id));
        }
    }


    /**
     * DTO -> Entity 변환
     * @param userInfoDTO
     * @return
     */
    UserInfo dtoToEntity(UserInfoDTO userInfoDTO){
        UserInfo userInfo = UserInfo.builder()
                .seq(userInfoDTO.getSeq())
                .id(userInfoDTO.getId())
                .name(userInfoDTO.getName())
                .password(userInfoDTO.getPassword())
                .phoneNumber(userInfoDTO.getPhoneNumber())
                .homeNumber(userInfoDTO.getHomeNumber())
                .email(userInfoDTO.getEmail())
                .birthdate(userInfoDTO.getBirthdate())
                .address(userInfoDTO.getAddress())
                .authority(userInfoDTO.getAuthority())
                .build();

        return userInfo;
    }

    /**
     * Entity -> DTO 변환
     * @param userInfo
     * @return
     */
    UserInfoDTO entityToDTO(UserInfo userInfo){
        UserInfoDTO dto = UserInfoDTO.builder()
                .seq(userInfo.getSeq())
                .id(userInfo.getId())
                .name(userInfo.getName())
                .password(userInfo.getPassword())
                .phoneNumber(userInfo.getPhoneNumber())
                .homeNumber(userInfo.getHomeNumber())
                .email(userInfo.getEmail())
                .birthdate(userInfo.getBirthdate())
                .address(userInfo.getAddress())
                .build();

        return dto;
    }


    /**
     * 중복 아이디 체크
     * @param id
     * @return
     */
    public boolean findByUserId(String id) {
        Optional<UserInfo> userInfo = userCustomRepository.findById(id);
        return userInfo.isEmpty();

    }
}
