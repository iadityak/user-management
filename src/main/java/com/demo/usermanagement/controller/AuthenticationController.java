package com.demo.usermanagement.controller;

import static com.demo.usermanagement.model.Constants.TOKEN_PREFIX;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.usermanagement.config.JwtTokenUtil;
import com.demo.usermanagement.model.ApiResponse;
import com.demo.usermanagement.model.AuthToken;
import com.demo.usermanagement.model.LoginUser;
import com.demo.usermanagement.model.User;
import com.demo.usermanagement.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ApiResponse<AuthToken> register(@RequestBody LoginUser loginUser) throws AuthenticationException {

    	final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        final User user = userService.findOne(loginUser.getUsername());
        final String token = jwtTokenUtil.generateToken(authentication);
        return new ApiResponse<>(200, "success",new AuthToken(token));
    }
    
    @PostMapping(value="/logout")
    public ApiResponse<Object> logout(@RequestHeader String Authorization){
    	String authToken = null;
    	if (Authorization != null && Authorization.startsWith(TOKEN_PREFIX)) {
            authToken = Authorization.replace(TOKEN_PREFIX,"");
            System.out.println(authToken);
            return new ApiResponse<>(200, "success", "User Logged Out Successfully!");
    	}
    	else
    		return new ApiResponse<>(200, "success", "User Logged Out Successfully!");
    }
}
