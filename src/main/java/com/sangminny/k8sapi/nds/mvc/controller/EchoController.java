package com.sangminny.k8sapi.nds.mvc.controller;

import java.net.DatagramSocket;
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

        Echo echo = new Echo();
        echo.setIp("Unknown");
        try {
            InetAddress host = InetAddress.getLocalHost();
            echo.setIp(host.getHostAddress());
            echo.setHostName(host.getHostName());

            // final DatagramSocket socket = new DatagramSocket();
            // socket.connect(InetAddress.getByName("192.168.219.1"), 10002);
            // ip = socket.getLocalAddress().getHostAddress();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (Exception e) {

        }

        

        // echo.setIp(ip);
        echo.setProfile(activeProfile);
        return echo;
    }
}
