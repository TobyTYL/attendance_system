package edu.duke.ece651.team1.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import edu.duke.ece651.team1.client.service.*;
import org.springframework.stereotype.Controller;
import edu.duke.ece651.team1.client.model.*;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/api/qrcode")
public class QRCodeController {
    @Autowired
    private  QRCodeService qrCodeService;

   

    @GetMapping("/{sectionId}")
    public String getQRCode(@PathVariable int sectionId, Model model) {
        try {
            String targetUrl = "/student/markattendance/"+sectionId;
            String redirectUrl = "https://" + UserSession.getInstance().getHost() + ":" + "8081"
            + "/markattendance/authenticate";
            String qr = qrCodeService.generateQRCodeImage(redirectUrl);
            model.addAttribute("qr",qr);
            return "test";
        } catch (Exception e) {
            return "";
        }
    }
}

