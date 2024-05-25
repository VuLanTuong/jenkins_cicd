package com.example.authservice.service;



import com.example.authservice.authen.UserPrincipal;
import com.example.authservice.entity.Role;
import com.example.authservice.entity.User;
import com.example.authservice.repositories.RoleRepository;
import com.example.authservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void createUser(User user) {
        System.out.println("saved");
        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setRoleName("CUSTOMER");
        role.setRoleKey("CUSTOMER");
        System.out.println("role" + role);
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public UserPrincipal findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        UserPrincipal userPrincipal = new UserPrincipal();

        if (null != user) {
            System.out.println("***USER" + user);

            Set<String> authorities = new HashSet<>();

            if (null != user.getRoles())
                user.getRoles().forEach(r -> {
                    authorities.add(r.getRoleKey());
                    r.getPermissions().forEach(
                            p -> authorities.add(p.getPermissionKey()));
                });

            userPrincipal.setUserId(user.getId());
            userPrincipal.setUsername(user.getUsername());
            userPrincipal.setPassword(user.getPassword());
            userPrincipal.setAuthorities(UserPrincipal.getAuthorities(user.getRoles()));

        }

        System.out.println(userPrincipal);
        return userPrincipal;

    }



    @Override
    public void createAdmin(User user) {
        Set<Role> roles = new HashSet<>();
        Role role1 = new Role();
        role1.setRoleName("CUSTOMER");
        role1.setRoleKey("CUSTOMER");

        Role role2 = new Role();
        role2.setRoleName("ADMIN");
        role2.setRoleKey("ADMIN");



        roleRepository.save(role1);
        roleRepository.save(role2);

        System.out.println("role " + role1);
        System.out.println("role" + role2);

        roles.add(role1);
        roles.add(role2);



        System.out.println("********** roles" + roles);
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public boolean isExistUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user != null;
    }

    @Override
    public User updateUser(User user) {
        userRepository.save(user);
        return user;


    }

    @Override
    public User deleteUser(User user) {
        User user1 = userRepository.findByUsername(user.getUsername());
        user1.setActive(false);
        userRepository.save(user1);
        return user1;
    }
}