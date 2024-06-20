package com.xenakil.security.service;

import com.xenakil.security.model.Role;
import com.xenakil.security.model.User;
import com.xenakil.security.repository.RoleRepository;
import com.xenakil.security.repository.UserRepository;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("USER");
        if (userRole == null) {
            userRole = new Role();
            userRole.setName("USER");
            roleRepository.save(userRole);
        }

        user.setRoles(new HashSet<Role>() {
            @Override
            public boolean add(Role role) {
                return super.add(role);
            }
        });
        return userRepository.save(user);
    }
}
