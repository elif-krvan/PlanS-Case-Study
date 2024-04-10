package com.plans.core.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.plans.core.enums.Role;
import com.plans.core.exception.AlreadyFoundException;
import com.plans.core.exception.SomethingWentWrongException;
import com.plans.core.model.EndUser;
import com.plans.core.model.User;
import com.plans.core.repository.IEndUserRepository;
import com.plans.core.request.QAddClient;
import com.plans.core.request.QUpdateUser;
import com.plans.core.request.QUser;
import com.plans.core.response.RRegisterClient;
import com.plans.core.response.RUser;
import com.plans.core.utils.PasswordGenerator;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ClientService {

    private final AccountService accountService;
    private final IEndUserRepository endUserRepository;

    @Transactional
    public RRegisterClient createClient(QAddClient client) throws Exception {
        try {
            // Generate a password for the client and create QUser using client data
            String generatedPassword = PasswordGenerator.generate();
            QUser newUser = QUser.builder()
                            .email(client.getEmail())
                            .username(client.getUsername())
                            .password(generatedPassword)
                            .build();

            User registeredUser = accountService.register(newUser, Role.END_USER);
            System.out.println("user email:" +registeredUser.getEmail());
            System.out.println("user id:" +registeredUser.getId());

            // Save user to enduser table
            endUserRepository.save(
                EndUser.builder()
                .user(registeredUser)
                // .devices(new ArrayList<>())
                .build());

            log.info("Client registered with email {}.", registeredUser.getEmail());

            return new RRegisterClient(registeredUser.getUsername(), registeredUser.getEmail(), generatedPassword);          
        } catch (AlreadyFoundException e) {
            log.error("User register fail since email already exists for email {}", client.getEmail());
            throw e;
        } catch (Exception e) {
            log.error("User register failed for email {}", client.getEmail(), e);
            throw new SomethingWentWrongException();
        }
    }

    public List<RUser> getClients() throws SomethingWentWrongException {
        return accountService.getUsers(Role.END_USER);
    }
    
    public User getClientByUsername(String username) throws Exception {
        return accountService.getUserByUsername(username);
    }

    @Transactional // TODO is this stupid? should i just call account service in the controller 
    public void removeClient(String username) throws Exception {
        accountService.removeUserByUsername(username);
    }

    @Transactional
    public RUser updateClient(QUpdateUser updateUser) throws Exception { // TODO remove this and call directly from account service?
        return accountService.updateUser(updateUser);
    }
}
