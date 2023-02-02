package com.example.demo.service;

import com.example.demo.authority.UserAuthority;
import com.example.demo.dto.UserInfoDTO;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserCustomRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserCustomRepository userCustomRepository;

    private final PasswordEncoder passwordEncoder;

    public String save(UserInfoDTO dto) {
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
            dto.setAuthority(UserAuthority.USER);
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
//    public Optional<UserInfoDTO> findByUserId(String id) {
//        return entityToDTO(userCustomRepository.findById(id));
//    }
//
//    public Optional<UserInfoDTO> login(Map<String, Object> userInfo) {
//        return userCustomRepository.login(userInfo);
//    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        System.out.println("아아악");
        System.out.println(id);
        UserInfo userInfo = userCustomRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("등록되지 않은 사용자입니다."));
        return toUserDetails(userInfo);
    }

    private UserDetails toUserDetails(UserInfo userInfo) {
        return User.builder()
                .username(userInfo.getId())
                .password(userInfo.getPassword())
                .authorities(new SimpleGrantedAuthority(userInfo.getAuthority().getName())).build();
    }

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
                .build();

        return userInfo;
    }

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

    public boolean findByUserId(String id) {
        Optional<UserInfo> userInfo = userCustomRepository.findById(id);
        return userInfo.isEmpty();

    }
}
