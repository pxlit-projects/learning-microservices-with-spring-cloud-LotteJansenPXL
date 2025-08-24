package be.pxl.services;

import be.pxl.services.model.dto.LoginRequest;
import be.pxl.services.model.dto.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @GetMapping("/data")
    public ResponseEntity<String> getData(@RequestHeader("X-ROLE") String role) {
        if ("ADMIN".equals(role)) {
            return ResponseEntity.ok("Hello Admin, here is your dashboard data.");
        } else if ("USER".equals(role)) {
            return ResponseEntity.ok("Hello User, here is your personal data.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }
    }
}

