package com.example.ndovupayservice.auth;


import com.example.ndovupayservice.user.DraftSecret;
import com.example.ndovupayservice.user.UserService;
import com.example.ndovupayservice.utils.StandardJsonResponse;
import com.example.ndovupayservice.utils.UniqueCodeGenerator;
import lombok.RequiredArgsConstructor;
/*import org.apache.http.util.TextUtils;
import org.json.JSONObject;*/
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserService userService;


    @PostMapping("/login")
    public StandardJsonResponse authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        StandardJsonResponse response = new StandardJsonResponse();
        if (TextUtils.isEmpty(request.getUsrSecret())) {
            var user = userService.findByOrgCodeAndUsrEmail(request.getUsrOrgCode(), request.getUserEmail());
            UniqueCodeGenerator ug = new UniqueCodeGenerator();
            String xPlainCode = ug.getUniqueCode();
            //to send otp to email
            new Thread(()->userService.sendEmailOtp(user,xPlainCode)).start();
            new Thread(()->userService.sendPhoneOtp(user,xPlainCode)).start();

            response.setMessage("message", "SMS OTP sent successfully", response);
            response.setData("result", request, response);
            return response;
        }

        // todo validate otp
        // if valid - login else -- return invalid otp response

        DraftSecret draftSecret = DraftSecret.builder()
                .usrOrgCode(request.getUsrOrgCode())
                .userEmail(request.getUserEmail())
                .usrSecret(request.getUsrSecret())
                .build();

        JSONObject jsonResponse = new JSONObject(userService.validateEmailOtp(draftSecret));

        boolean success = jsonResponse.getBoolean("success");
//        System.out.println(success);
        if(success) {
            response = authenticationService.authenticate(request);
        }else {
            response.setMessage("message", "INVALID OTP!", response);
            response.setData("result", request, response);
        }

//        return ResponseEntity.ok(authenticationService.authenticate(request));
        return response;
    }


}


