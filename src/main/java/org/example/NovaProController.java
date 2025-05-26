package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class NovaProController {

    @Autowired
    private NovaProClientService novaProClientService;
    @Autowired
    private UserLoginRepository userLoginRepository;

    @PostMapping("/analyze")
    @CrossOrigin(origins = {
            "http://localhost:3000",
            "http://10.20.85.61:3000"
    },
            allowCredentials = "true")
    public ResponseEntity<String> analyzeBase64Images(@RequestBody NovaProRequest request) {

        String response = null;
        Optional<UserDetail> optionalUser = userLoginRepository.findByPhone(request.getPhoneNumber());
        boolean isUserExist = optionalUser.isPresent();
        try {
             response = novaProClientService.invokeNovaPro(request.getPrompt(), request.getImages());
        }catch (Exception e) {
                System.out.println("{\"error\": \"Processing failed for an image\"}");
        }

        if (isUserExist && response != null) {
            UserDetail user = optionalUser.get();
            user.setUserWebData(response);
            userLoginRepository.save(user);
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
    }
    @PostMapping("/login")
    @CrossOrigin(origins = {
            "http://localhost:3000",
            "http://10.20.85.61:3000"
    },
            allowCredentials = "true")
    public boolean loginUser(@RequestBody UserLoginRequest request) {
        if (userLoginRepository.findByPhone(request.getPhoneNumber()).isPresent()) {
            return false; // User already exists
        } else {
            userLoginRepository.save(new UserDetail(request.getPhoneNumber()));
            return true; // New user saved
        }
    }


}
