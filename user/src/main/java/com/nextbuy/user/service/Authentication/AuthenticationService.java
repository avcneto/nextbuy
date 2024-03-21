package com.nextbuy.user.service.Authentication;


import com.nextbuy.user.dto.authentication.JwtAuthenticationDTO;
import com.nextbuy.user.dto.authentication.SigningDTO;

public interface AuthenticationService {
    JwtAuthenticationDTO signing(SigningDTO request);
}
