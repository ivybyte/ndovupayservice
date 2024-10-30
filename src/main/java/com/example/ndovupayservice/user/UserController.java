package com.example.ndovupayservice.user;


import com.example.ndovupayservice.utils.StandardJsonResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PostMapping("/get")
    public StandardJsonResponse getAllUsers(HttpServletRequest request , @RequestBody UserDto userFilter) {
            User user = userService.getCurrentUser(request);
        return userService.getAllUsers(user, userFilter);
    }

    @GetMapping("me")
    public StandardJsonResponse getUserById(HttpServletRequest request) {
        StandardJsonResponse response = new StandardJsonResponse();
        User user = userService.getCurrentUser(request);
        response.setData("response", List.of(user), response);
        return response;
    }

    @GetMapping("phone/{phone}")
    public StandardJsonResponse getUserByPhone(HttpServletRequest request,@PathVariable String phone ) {
        return userService.getUserByPhone(phone);
    }







    @PostMapping("/new-email-otp/{id}")
    public StandardJsonResponse newEmailOtp(@PathVariable UUID id, @RequestParam(required = true) String newEmail) {
        return userService.newEmailOtp(id, newEmail);
    }



    @PostMapping("/update-password")
    public StandardJsonResponse updatePasssword(@RequestBody UserSecret userSecret, HttpServletRequest request) {
        var user = userService.getCurrentUser(request);
//        DraftSecret draftSecret = DraftSecret.builder()
//                .usrSecret(userSecret.getUserSecret())
//                .usrId(user.getUsrId()).build();
        DraftSecret draftSecret = DraftSecret.builder()
                .usrOrgCode(user.getUsrOrgCode())
                .userEmail(user.getUsrEmail())
                .usrSecret(userSecret.getUserSecret())
                .build();
        return userService.updatePasssword(draftSecret);
    }



    @GetMapping("distinct-status")
    public StandardJsonResponse setDistinctUsrStatus() {
        StandardJsonResponse resp = new StandardJsonResponse();
        List<StatusDto> statusDto = Arrays.stream(UsrStatus.values())
                .map(i -> StatusDto.builder().usrStatus(i.name()).build())
                .collect(Collectors.toList());
        resp.setTotal(statusDto.size());
        resp.setData("result", statusDto, resp);
        return resp;
    }

    @PostMapping("/update-onBoarding")
    public StandardJsonResponse updateOnBoarding(@RequestBody UserStatusDTO userStatusDTO, HttpServletRequest request) {
        User user = userService.getCurrentUser(request);
        return userService.updateOnBoarding(userStatusDTO, user);
    }


}


