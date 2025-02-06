package org.example.controller;

import org.example.Response.JwtResponseDTO;
import org.example.entities.RefreshToken;
import org.example.models.UserInfoDto;
import org.example.service.JwtService;
import org.example.service.RefreshTokenService;
import org.example.service.UserDetailServiceImpel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserDetailServiceImpel userDetailServiceImpel;

    @PostMapping("/auth/v1/signup")
    public ResponseEntity SignUp(@RequestBody UserInfoDto userInfoDto)
    {
        try{
            Boolean isSignedUp=userDetailServiceImpel.signupUser(userInfoDto);
            if(Boolean.FALSE.equals(isSignedUp)){
                return new ResponseEntity<>("Already Exists", HttpStatus.BAD_REQUEST);
            }
            RefreshToken refreshToken=refreshTokenService.createRefreshToken(userInfoDto.getUsername());
            String jwtToken=jwtService.GenerateToken(userInfoDto.getUsername());
            return new ResponseEntity<>(JwtResponseDTO.builder().accessToken(jwtToken)
                    .token(refreshToken.getToken()).build(),HttpStatus.OK);

        }catch (Exception ex){
            return new ResponseEntity<>("Exception in User Service",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
