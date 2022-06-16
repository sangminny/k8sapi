package com.sangminny.k8sapi.nds.mvc.controller;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import com.sangminny.k8sapi.nds.mvc.domain.Echo;

@RestController
@RequestMapping("/nds/echo")
public class EchoController {
    
    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Autowired
    Environment env;

    @GetMapping("")
    public Echo getEcho() {

        String ip = "Unknown";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        Echo echo = new Echo();
        echo.setIp(ip);
        echo.setProfile(activeProfile);
        return echo;
    }
}
