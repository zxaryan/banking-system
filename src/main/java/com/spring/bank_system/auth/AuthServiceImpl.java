package com.spring.bank_system.auth;

import com.spring.bank_system.auth.dto.AuthResponse;
import com.spring.bank_system.auth.dto.LoginRequest;
import com.spring.bank_system.auth.dto.RegisterRequest;
import com.spring.bank_system.customer.Customer;
import com.spring.bank_system.customer.CustomerRepository;
import com.spring.bank_system.user.AppUser;
import com.spring.bank_system.user.AppUserRepository;
import com.spring.bank_system.user.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AppUserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(
            AppUserRepository userRepository,
            CustomerRepository customerRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        AppUser user = new AppUser();

        user.setEmail(request.getEmail());
        user.setPasswordHash(
                passwordEncoder.encode(request.getPassword())
        );
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setRole(UserRole.CUSTOMER);
        user.setCustomer(customer);
        user.setEnabled(true);

        AppUser savedUser = userRepository.save(user);

        String token = jwtService.generateToken(savedUser);

        return new AuthResponse(token, "Bearer");
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        AppUser user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        if (!user.isEnabled()) {
            throw new RuntimeException("User account is disabled");
        }

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPasswordHash()
        )) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);

        return new AuthResponse(token, "Bearer");
    }
}