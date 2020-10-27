package cn.tedu.straw.portal.controller;

import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private IUserService userService;

    @GetMapping("/user.current/details")
    public UserDetails getUserDetails(@AuthenticationPrincipal UserDetails userDetails){
        return userDetails;
    }
    // http://localhost:8080/test/user/current/principal
    @GetMapping("/user/current/principal")
    public Principal getPrincipal(Principal principal) {
        return principal;
    }

    @GetMapping("/user/current/authentication")
    public Authentication getAuthentication(Authentication authentication){
        return authentication;
    }


    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyAuthority('test:user:info')")
    public User getUserById(@PathVariable("id") Integer id){

        return userService.getById(id);
    }

}
