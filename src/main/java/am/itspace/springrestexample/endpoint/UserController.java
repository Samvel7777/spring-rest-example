package am.itspace.springrestexample.endpoint;

import am.itspace.springrestexample.dto.AuthRequest;
import am.itspace.springrestexample.dto.AuthRespanse;
import am.itspace.springrestexample.dto.UserDto;
import am.itspace.springrestexample.exception.DuplicateEntityException;
import am.itspace.springrestexample.exception.ResourceNotFoundException;
import am.itspace.springrestexample.model.User;
import am.itspace.springrestexample.repository.UserRepository;
import am.itspace.springrestexample.service.SmsService;
import am.itspace.springrestexample.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Autowired
    @Qualifier("SMSCSmsService")
    private SmsService smsService;

    @PostMapping("/user/auth")
    public ResponseEntity auth(@RequestBody AuthRequest authRequest) {
        Optional<User> byEmail = userRepository.findByEmail(authRequest.getEmail());
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
                String token = jwtTokenUtil.generateToken(user.getEmail());
                return ResponseEntity.ok(AuthRespanse.builder()
                        .token(token)
                        .name(user.getName())
                        .surname(user.getSurname())
                        .build());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }

    @PostMapping("/user/add")
    public User save(@RequestBody UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isEmpty()) {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            User user = modelMapper.map(userDto, User.class);
            User save = userRepository.save(user);
            if (userDto.getPhoneNumber() != null && !userDto.getPhoneNumber().isEmpty()) {
                smsService.send(userDto.getPhoneNumber(), "Hello" + userDto.getName());
            }
            return save;
        } else {
            throw new DuplicateEntityException("Username already exists!");
        }
    }

    @PutMapping("/user/add/{userId}/image")
    public void uploadImage(@PathVariable("userId") int userId, @RequestParam("image") MultipartFile file) {
        Optional<User> byId = userRepository.findById(userId);
        if (byId.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        System.out.println(file.getOriginalFilename());
    }
}
