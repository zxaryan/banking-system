package com.spring.bank_system.user;

import com.spring.bank_system.customer.Customer;
import com.spring.bank_system.customer.CustomerRepository;
import com.spring.bank_system.user.dto.UserRequest;
import com.spring.bank_system.user.dto.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository userRepository;
    private final CustomerRepository customerRepository;

    public AppUserServiceImpl(
            AppUserRepository userRepository,
            CustomerRepository customerRepository
    ) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public UserResponse createUser(UserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User email already exists");
        }

        Customer customer = null;

        if (request.getCustomerId() != null) {
            customer = customerRepository.findById(request.getCustomerId())
                    .orElseThrow(() ->
                            new RuntimeException("Customer not found"));
        }

        AppUser user = new AppUser();

        user.setEmail(request.getEmail());
        user.setPasswordHash(request.getPassword());
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setRole(
                request.getRole() != null
                        ? request.getRole()
                        : UserRole.CUSTOMER
        );
        user.setCustomer(customer);
        user.setEnabled(true);

        AppUser savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    @Override
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public UserResponse getUserById(UUID id) {

        AppUser user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return mapToResponse(user);
    }

    @Override
    public UserResponse updateUser(
            UUID id,
            UserRequest request
    ) {

        AppUser user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());

        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }

        AppUser updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }

    @Override
    public void deleteUser(UUID id) {

        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }

        userRepository.deleteById(id);
    }

    private UserResponse mapToResponse(AppUser user) {

        UUID customerId = user.getCustomer() != null
                ? user.getCustomer().getId()
                : null;

        return new UserResponse(
                user.getId(),
                customerId,
                user.getEmail(),
                user.getFullName(),
                user.getPhone(),
                user.getRole(),
                user.isEnabled(),
                user.getLastLoginAt(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}