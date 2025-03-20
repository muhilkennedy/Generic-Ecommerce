package com.platform.api;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.api.client.auth.openidconnect.IdToken.Payload;
import com.platform.configurations.ConfigTypes;
import com.platform.entity.BaseEntity;
import com.platform.entity.ConfigType;
import com.platform.logging.Log;
import com.platform.messages.GenericResponse;
import com.platform.messages.Response;
import com.platform.model.SocialUser;
import com.platform.service.ConfigTypeService;
import com.platform.util.JWTUtil;
import com.platform.util.PlatformOauthUtil;
import com.platform.util.PlatformUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Muhil
 *
 */
@RestController
@RequestMapping("social")
public class SocialController
{
    
    @Autowired
    private ConfigTypeService configService;
    
    @PostMapping(value = "/login/google")
    public GenericResponse<BaseEntity> loginGoogleUser(@RequestBody SocialUser body, HttpServletRequest httpRequest,
            HttpServletResponse httpResponse) throws IOException, GeneralSecurityException {
        GenericResponse<BaseEntity> response = new GenericResponse<BaseEntity>();
        // validate google token here
        List<ConfigType> configTypes = configService.findConfigsByType(ConfigTypes.GOOGLEOAUTH.name());
        Assert.isTrue(!configTypes.isEmpty(), "Update Oauth configuration");
        Payload payload = PlatformOauthUtil.verifyAndParseGoogleToken(
            configTypes.stream().filter(
                type -> type.getName().equals("CLIENTID")).findFirst().get().getValue(),
            body.getIdToken());
        Log.platform.debug("Google token information : {}", payload);
       // User user = empService.findBySecondaryEmailId((String) payload.get("email"));
//        if (user == null) {
//            throw new RuntimeException();
//        }
//        httpResponse.addHeader(PlatformUtil.TOKEN_HEADER, JWTUtil.generateToken(user.getUniquename(),
//                String.valueOf(user.getObjectId()), JWTUtil.USER_TYPE_EMPLOYEE, httpRequest.getRemoteAddr(), false));
//        return response.setStatus(Response.Status.OK).setData(user).build();
        return null;
    }

}
