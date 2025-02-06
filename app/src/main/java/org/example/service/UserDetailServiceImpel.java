package org.example.service;

import lombok.AllArgsConstructor;
import org.example.entities.UserInfo;
import org.example.models.UserInfoDto;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;
@AllArgsConstructor
public class UserDetailServiceImpel implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserInfo user =userRepository.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("could not found user");
        }
        return new CustomUserDetails(user);
    }
    public UserInfo checkIfUserAlreadyExists(UserInfoDto userInfoDto){
        return userRepository.findByUsername(userInfoDto.getUsername());
    }

    public Boolean signupUser(UserInfoDto userInfoDto){
        userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
        if(Objects.nonNull(checkIfUserAlreadyExists(userInfoDto))){
            return false;
        }
        String userId= UUID.randomUUID().toString();
        userRepository.save(new UserInfo(userId,userInfoDto.getUsername(),userInfoDto.getPassword(),
                new HashSet<>()));
        return true;
    }
}
