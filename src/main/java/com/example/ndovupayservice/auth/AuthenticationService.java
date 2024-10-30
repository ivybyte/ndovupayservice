package com.example.ndovupayservice.auth;


import com.example.ndovupayservice.config.JwtService;
import com.example.ndovupayservice.user.User;
import com.example.ndovupayservice.user.UserService;
import com.example.ndovupayservice.user.UsrStatus;
import com.example.ndovupayservice.user.token.Token;
import com.example.ndovupayservice.user.token.TokenRepository;
import com.example.ndovupayservice.user.token.TokenType;
import com.example.ndovupayservice.utils.StandardJsonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    public StandardJsonResponse authenticate(AuthenticationRequest request) {
        StandardJsonResponse response = new StandardJsonResponse();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserEmail(), request.getPassword()));

        var user = userService.findByOrgCodeAndUsrEmail(request.getUsrOrgCode(), request.getUserEmail());

        revokeAllUserTokens(user);

        if (user.getUsrStatus().equals(UsrStatus.ACTIVE)) {
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            saveUserToken(user, jwtToken);

            response.setSuccess(true);
//            response.setStatus(HttpStatus.SC_OK);
            response.setMessage("Authentication successful", request, response);

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("accessToken", jwtToken);
            dataMap.put("refreshToken", refreshToken);
            dataMap.put("userEmail", user.getUsrEmail());

            response.setData("result", dataMap, response);
        } else {
            response.setSuccess(false);
//            response.setStatus(HttpStatus.SC_FORBIDDEN);
            response.setMessage("User is not active", request, response);
            response.setData("userStatus", user.getUsrStatus().toString(), response);
        }


        return response;
    }


//    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserEmail(), request.getPassword()));
//
//        var user = userService.findByOrgCodeAndUsrEmail(request.getUsrOrgCode(), request.getUserEmail());
//
//        revokeAllUserTokens(user);
//        if (user.getUsrStatus().equals(UsrStatus.ACTIVE)) {
//            var jwtToken = jwtService.generateToken(user);
//            var refreshToken = jwtService.generateRefreshToken(user);
//            saveUserToken(user, jwtToken);
//            return AuthenticationResponse.builder()
//                    .accessToken(jwtToken)
//                    .refreshToken(refreshToken)
//                    .success(true)
//                    .userEmail(user.getUsrEmail())
//                    .build();
//        }
//        return AuthenticationResponse.builder().accessToken("USER STATUS IS "+user.getUsrStatus()).success(false).build();
//    }

    public void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUsrId());
        if (validUserTokens.isEmpty()) return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder().usrId(user.getUsrId()).token(jwtToken).tokenType(TokenType.BEARER).expired(false).revoked(false).build();
        tokenRepository.save(token);
    }
}
