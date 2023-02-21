package eu.asgardschmiede.petregister.controller;


import eu.asgardschmiede.petregister.exception.OwnerAlreadyExistsException;
import eu.asgardschmiede.petregister.exception.OwnerNotFoundException;
import eu.asgardschmiede.petregister.model.AuthRequest;
import eu.asgardschmiede.petregister.model.AuthResponse;
import eu.asgardschmiede.petregister.model.Owner;
import eu.asgardschmiede.petregister.model.RegisterRequest;
import eu.asgardschmiede.petregister.repository.OwnerRepository;
import eu.asgardschmiede.petregister.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
//@CrossOrigin(origins = {"","",""}) // Bei mehreren Werten
//@CrossOrigin(origins = "") // bei einem Wert
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {

    public final AuthenticationManager authenticationManager;

    public final OwnerRepository ownerRepository;

    public final JwtService jwtService;

    public final PasswordEncoder passwordEncoder;

    @PostMapping("login")
    public AuthResponse authenticate(@RequestBody AuthRequest request) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        }
        catch(Exception ex) {
            throw new OwnerNotFoundException();
        }

        Owner owner = ownerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtService.generateToken(owner);

        return new AuthResponse(token);
    }

    @PostMapping("register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {

        var opt = ownerRepository.findByEmail(request.getEmail());
        if(opt.isPresent()) {
            throw new OwnerAlreadyExistsException();
        }

        Owner owner = Owner.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFistname())
                .lastname(request.getLastname())
                .build();

        ownerRepository.save(owner);
        String token = jwtService.generateToken(owner);

        return new AuthResponse(token);
    }
}
