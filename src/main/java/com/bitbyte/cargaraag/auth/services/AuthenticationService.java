package com.bitbyte.cargaraag.auth.services;

import com.bitbyte.cargaraag.auth.entities.Roles;
import com.bitbyte.cargaraag.auth.entities.User;
import com.bitbyte.cargaraag.auth.exceptionhandlers.WrongCredentialsException;
import com.bitbyte.cargaraag.auth.models.Credentials;
import com.bitbyte.cargaraag.auth.models.Role;
import com.bitbyte.cargaraag.auth.repository.RolesRepository;
import com.bitbyte.cargaraag.auth.repository.UserRepository;
import com.bitbyte.cargaraag.auth.securityutils.PasswordUtils;
import com.bitbyte.cargaraag.auth.securityutils.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Value("${hashing.algorithm}")
    private String hashingAlgorithm;

    public String authenticateUser(Credentials userCreds) throws NoSuchAlgorithmException {
        User authenticatedUser = validateUserCredentials(userCreds);
        String token = TokenGenerator.generateToken(authenticatedUser);
        return token;
    }

    public String signUpNewUser(User user) throws NoSuchAlgorithmException {
        byte[] salt = PasswordUtils.createSalt();
        byte[] saltedHashPassword = PasswordUtils.generateSaltedHash(user.getPassword(), hashingAlgorithm, salt);
        user.setPassword(PasswordUtils.convertByteArrayToHexString(saltedHashPassword));
        user.setSalt(salt);
        user.setHashingAlgorithm(hashingAlgorithm);

        Roles role = rolesRepository
                .findByRole(Role.USER.name())
                .orElseGet(() -> rolesRepository.save(new Roles(Role.USER.name())));

        role.addUser(userRepository.save(user));
        rolesRepository.save(role);
        User savedUser = userRepository.findByUserName(user.getUserName()).get();
        return TokenGenerator.generateToken(savedUser);
    }

    private User validateUserCredentials(Credentials userCreds) throws NoSuchAlgorithmException {
        Optional<User> optionalUser = userRepository.findByUserName(userCreds.getUserName());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            boolean isValidUser = PasswordUtils.comparePasswords(userCreds.getPassword(), user.getPassword(), user.getSalt(), user.getHashingAlgorithm());
            if (isValidUser)
                return user;
            else
                throw new WrongCredentialsException("Invalid credentials");
        } else
            throw new WrongCredentialsException("User Name doesn't exist");
    }
}
