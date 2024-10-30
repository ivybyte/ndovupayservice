package com.example.ndovupayservice.user;


import com.example.ndovupayservice.config.JwtService;
import com.example.ndovupayservice.organisation.Organisation;
import com.example.ndovupayservice.organisation.OrganisationDto;
import com.example.ndovupayservice.user.token.Token;
import com.example.ndovupayservice.user.token.TokenRepository;
import com.example.ndovupayservice.utils.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {


    private final UserRepository userRepository;
    //    private final SmartGate smartGate;
    private final TokenRepository tokenRepository;
    private final JavaEmailService javaEmailService;
    private final PasswordEncoder passwordEncoder;
    //    private final ResellerRepo resellerRepo;
    private final GlobalUtils gu;
//    private final StandardJsonResponse standardJsonResponse;


//    @Value("${server.my-server-url}")
//    private String coreGatewayUrl;

    public User save(@Valid User usr) {
        return userRepository.saveAndFlush(usr);
    }


    public User getCurrentUser(HttpServletRequest request) {
        String tokenFromRequest = JwtService.getTokenFromRequest(request);
        Token token = tokenRepository.findByTokenAndRevokedAndExpired(tokenFromRequest, false, false).orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Token invalid"));
        return findById(token.getUsrId());
    }

    public User findById(UUID id) {
        return userRepository.findByUsrId(id).orElseThrow(() -> new IllegalStateException("User not found with id : " + id));
    }


    private void sendPhonePassword(String xMsisdn, String xMessage) {
//        new Thread(() -> smartGate.sendSMS(xMsisdn, xMessage)).start();
    }

    private void sendMailPassword(String xEmail, String xMessage) {
        if (TextUtils.isEmpty(xEmail) || TextUtils.isEmpty(xMessage)) return;
        String xSubject = " EMAIL OTP";
        javaEmailService.sendMail(xEmail, xSubject, xMessage);
    }

    public StandardJsonResponse getAllUsers(User user, UserDto filterDto) {
        StandardJsonResponse resp = new StandardJsonResponse();
        if (filterDto.getLimit() == 0) filterDto.setLimit(10);
        filterDto.setSortColumn("created_date");
        Pageable pageable = PageRequest.of(filterDto.getStart(), filterDto.getLimit(), Sort.by(filterDto.getSortColumn()).descending());

//        if (!TextUtils.isEmpty(filterDto.getUsrMobileNumber()))
//            filterDto.getUsrMobileNumber("%" + filterDto.getUsrMobileNumber() + "%");
//        if (!TextUtils.isEmpty(filterDto.getUsrFirstname())) filterDto.getUsrFirstname("%" + filterDto.getUsrFirstname() + "%");
//        if (!TextUtils.isEmpty(filterDto.getUsrLastname())) filterDto.getUsrLastname("%" + filterDto.getUsrLastname() + "%");
        if (!TextUtils.isEmpty(filterDto.getUsrEmail())) filterDto.setUsrEmail("%" + filterDto.getUsrEmail() + "%");
        if (!TextUtils.isEmpty(filterDto.getUsrNationalId()))
            filterDto.setUsrNationalId("%" + filterDto.getUsrNationalId() + "%");
//        Layers layer = user.getLayer();
//        if (layer.equals(Layers.TOP)) filterDto.setLayer("TOP");
//        if (layer.equals(Layers.ACCOUNT)) filterDto.setAccId(user.getUsrAccId());
//        if (layer.equals(Layers.RESELLER)) filterDto.setResellerId(user.getUsrResellerId());
        String status = null;
        if (filterDto.getUsrStatus() != null) status = filterDto.getUsrStatus().name();


        Page<User> list = userRepository.getAllUsersFiltered(filterDto.getLayer(), filterDto.getUsrFirstname(), filterDto.getUsrLastname(), filterDto.getUsrEmail(), status, filterDto.getUsrNationalId(), filterDto.getUsrMobileNumber(), pageable);
        resp.setMessage("message", "ok", resp);
        resp.setData("result", list.getContent(), resp);
        resp.setTotal((int) list.getTotalElements());
        return resp;
    }


//    public StandardJsonResponse assignPermissionsToUser(UUID userId, Set<Permission> permissions) {
//        StandardJsonResponse resp = new StandardJsonResponse();
//        User user = findById(userId);
//        user.getPermissions().clear();
//        user.getPermissions().addAll(permissions);
//        save(user);
//        resp.setData("result", user, resp);
//        resp.setMessage("message", "Permission Assigned Successfully", resp);
//        return resp;
//    }


    public User findByOrgCodeAndUsrEmail(String usrOrgCode, String usrEmail) {
        return userRepository.findByUsrOrgCodeAndUsrEmail(usrOrgCode, usrEmail).orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("User not found with org code: " + usrOrgCode + " and email: " + usrEmail));

    }

    public User findByUsrNationalId(String email) {
        return userRepository.findByUsrNationalId(email).orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("User not found with email : " + email));
    }

    public StandardJsonResponse sendEmailOtp(User usr,String xPlainCode) {
        StandardJsonResponse response = new StandardJsonResponse();
        String xMessage = "Your verification code is " + xPlainCode;
        String xEmail = usr.getUsrEmail();
        String xSubject = "NDOVUPAY EMAIL OTP";

        log.error("---{}---{}", xEmail, xMessage);

        javaEmailService.sendMail(xEmail, xSubject, xMessage);

        usr.setUsrOtpStatus("EMAIL_OTP_SENT");
        usr.setUsrEmailOtp(passwordEncoder.encode(xPlainCode));

        response.setMessage("message", "Email OTP sent successfully", response);
        response.setData("result", save(usr), response);
        return response;
    }

    public StandardJsonResponse sendPhoneOtp(User usr, String xPlainCode) {
        StandardJsonResponse response = new StandardJsonResponse();
        String xMessage = "Your SMS system verification code is " + xPlainCode;

        String xMsisdn = usr.getUsrMobileNumber();

        new Thread(() -> {
            SmartGate smartGate = new SmartGate();
            smartGate.sendSMS(xMsisdn, xMessage);
        }).start();

        usr.setUsrOtpStatus("SMS_OTP_SENT");
        usr.setUsrPhoneOtp(passwordEncoder.encode(xPlainCode));
        response.setMessage("message", "SMS OTP sent successfully", response);
        response.setData("result", save(usr), response);
        return response;
    }

    public StandardJsonResponse validateEmailOtp(DraftSecret draftSecret) {
        StandardJsonResponse response = new StandardJsonResponse();
//        User stpUser = findById(draftOtps.getUsrId());
        User stpUser = findByOrgCodeAndUsrEmail(draftSecret.getUsrOrgCode(), draftSecret.getUserEmail());
        boolean optMatched = passwordEncoder.matches(draftSecret.getUsrSecret(), stpUser.getUsrEmailOtp());
        if (!optMatched) {
            response.setSuccess(false);
            response.setStatus(HttpStatus.SC_NOT_ACCEPTABLE);
            response.setMessage("message", "INVALID EMAIL OTP", response);
            return response;
        }
        response.setSuccess(true);
        stpUser.setUsrOtpStatus("EMAIL_OTP_VERIFIED");
        response.setMessage("message", "Email OTP matched successfully", response);
        response.setData("result", save(stpUser), response);
        return response;
    }

    public StandardJsonResponse validatePhoneOtp(DraftSecret draftSecret) {
        StandardJsonResponse response = new StandardJsonResponse();
//        User stpUser = findById(draftOtps.getUsrId());
        User stpUser = findByOrgCodeAndUsrEmail(draftSecret.getUsrOrgCode(), draftSecret.getUserEmail());
        boolean optMatched = passwordEncoder.matches(draftSecret.getUsrSecret(), stpUser.getUsrPhoneOtp());
        if (!optMatched) {
            response.setSuccess(false);
            response.setStatus(HttpStatus.SC_NOT_ACCEPTABLE);
            response.setMessage("message", "INVALID PHONE OTP", response);
            return response;
        }
        response.setSuccess(true);
        stpUser.setUsrOtpStatus("PHONE_OTP_VERIFIED");
        response.setMessage("message", "Phone OTP matched successfully", response);
        response.setData("result", save(stpUser), response);
        return response;
    }

    public StandardJsonResponse updatePasssword(DraftSecret draftSecret) {
        StandardJsonResponse response = new StandardJsonResponse();
//        User user = findById(draftSecret.getUsrId());
        User user = findByOrgCodeAndUsrEmail(draftSecret.getUsrOrgCode(), draftSecret.getUserEmail());
        user.setPassword(passwordEncoder.encode(draftSecret.getUsrSecret()));
        user.setUsrChangePassword(false);
        response.setSuccess(true);
        response.setData("result", save(user), response);
        response.setMessage("message", "Paasword Updated successfully", response);
        return response;
    }

    public StandardJsonResponse newEmailOtp(UUID id, String newEmail) {

        User usr = findById(id);
        StandardJsonResponse response = new StandardJsonResponse();
        UniqueCodeGenerator ug = new UniqueCodeGenerator();
        String xPlainCode = ug.getUniqueCode();
        String xMessage = "Your E-citizen verification code is " + xPlainCode;
        String xSubject = "E-CITIZEN EMAIL OTP";
        javaEmailService.sendMail(newEmail, xSubject, xMessage);
        usr.setUsrOtpStatus("EMAIL_OTP_SENT");
        usr.setUsrEmailOtp(passwordEncoder.encode(xPlainCode));
        usr = save(usr);
        usr.setUsrEmail(newEmail); // cache the email
        response.setMessage("message", "Email OTP sent successfully", response);
        response.setData("result", usr, response);
        return response;
    }

    public StandardJsonResponse newPhoneOtp(UUID id, String newmobile) {
        User usr = findById(id);
        StandardJsonResponse response = new StandardJsonResponse();
        UniqueCodeGenerator ug = new UniqueCodeGenerator();
        String xPlainCode = ug.getUniqueCode();
        String xMessage = "Your E-citizen verification code is " + xPlainCode;
        new Thread(() -> {
            SmartGate smartGate = new SmartGate();
            smartGate.sendSMS(newmobile, xMessage);
        }).start();
        usr.setUsrOtpStatus("SMS_OTP_SENT");
        usr.setUsrPhoneOtp(passwordEncoder.encode(xPlainCode));
        usr = save(usr);
        usr.setUsrMobileNumber(newmobile);//cache the new number
        response.setMessage("message", "SMS OTP sent successfully", response);
        response.setData("result", usr, response);
        return response;
    }

//    public StandardJsonResponse getRoles(User user) {
//        StandardJsonResponse resp = new StandardJsonResponse();
//
//        Set<String> acceptableRoles = new HashSet<>();
//        acceptableRoles.add(Role.ADMIN.name());
//        acceptableRoles.add(Role.VIEWER.name());
//
//
//        if (user.getLayer().equals(Layers.ACCOUNT)) {
//            acceptableRoles.add(Role.VIEWER.name());
//            acceptableRoles.add(Role.CAMPAIGN_ADMIN.name());
//        } else if (user.getLayer().equals(Layers.RESELLER)) {
//            acceptableRoles.add(Role.SALE.name());
//            acceptableRoles.add(Role.MANAGER.name());
//            acceptableRoles.add(Role.ACCOUNTANT.name());
//        } else {
//            acceptableRoles.add(Role.SUPER_ADMIN.name());
//
//        }
//
//        List<RoleDto> roles = Arrays.stream(Role.values()).filter(role -> acceptableRoles.contains(role.name())).map(r -> RoleDto.builder().rlName(r.name()).build()).collect(Collectors.toList());
//
//        resp.setTotal(roles.size());
//        resp.setData("result", roles, resp);
//
//        return resp;
//    }

//    private String getResellerDomain(UUID usrResellerId, String coreGatewayUrl) {
//        if (usrResellerId == null) return coreGatewayUrl;
//        Reseller rs = resellerRepo.findById(usrResellerId).orElse(null);
//        if (rs == null) return coreGatewayUrl;
//        String domain = rs.getRsDomain();
//        return TextUtils.isEmpty(domain) ? coreGatewayUrl : domain;
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

    public StandardJsonResponse updateOnBoarding(UserStatusDTO userStatusDTO, User user) {
        StandardJsonResponse response = new StandardJsonResponse();
        try {
            if (user == null) {
                throw new GlobalExceptionHandler.ResourceNotFoundException("user not found");
            }
            user.setUsrIsOnboarded(userStatusDTO.isBoarded());
            userRepository.save(user);
            response.setSuccess(true);
            response.setMessage("message", "updated successfully", response);
            return response;

        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("message", "update failed", response);
            return response;
        }
    }

    public StandardJsonResponse getUserByPhone(String phone) {
        StandardJsonResponse response = new StandardJsonResponse();
        try {
            User user = userRepository.findByUsrMobileNumber(phone);
            userRepository.save(user);
            response.setSuccess(true);
            response.setMessage("message", "Request successfully", response);
            return response;


        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("message", "update failed", response);
            return response;
        }
    }


    public void createDefaultOrgAdmin(OrganisationDto userDto, Organisation org) {
        User user = User.builder().usrOrgId(org.getOrgUid()).usrOrgCode(org.getOrgCode()).usrFirstname(userDto.getUsrFirstName()).usrLastname(userDto.getUsrLastName()).usrEmail(userDto.getUsrEmail()).usrMobileNumber(userDto.getUsrMobileNumber()).password(passwordEncoder.encode(userDto.getUsrEncryptedPassword())).usrNationalId(userDto.getUsrNationalId()).usrStatus(UsrStatus.ACTIVE).usrCreatedDate(LocalDateTime.now()).build();
        userRepository.saveAndFlush(user);

    }

}
