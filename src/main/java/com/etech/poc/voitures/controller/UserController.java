package com.etech.poc.voitures.controller;

import com.etech.poc.voitures.config.security.jwt.AuthRequest;
import com.etech.poc.voitures.config.security.jwt.JwtProperties;
import com.etech.poc.voitures.config.security.jwt.JwtUtil;
import com.etech.poc.voitures.data.dto.user.LoginDTO;
import com.etech.poc.voitures.services.applicatif.user.UserSA;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author benja
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserSA userSA;


    @CrossOrigin
	@PreAuthorize("permitAll()")
    @ApiOperation(value = "Login", notes = "Generate token for login")
    @PostMapping("/login")
    public ResponseEntity<LoginDTO> generateToken(@RequestBody AuthRequest authRequest, HttpServletResponse response) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("Invalid username/password");
        }
        String token = jwtUtil.generateToken(authRequest.getUsername());
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + token);
        return new ResponseEntity<>(new LoginDTO(token), HttpStatus.OK);
    }
}
