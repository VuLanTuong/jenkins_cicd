package com.example.adminservice.service;

import com.example.adminservice.model.Admin;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AdminService {
    void addAdmin(Admin admin);
    void updateAdmin(Admin admin);
    void deleteAdmin(Admin admin);

    List<Admin> getAllAdmins();
}
