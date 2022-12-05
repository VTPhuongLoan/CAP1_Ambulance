package com.example.restapi.model.mapper.impl;

import com.example.restapi.model.dto.AccountDTO;
import com.example.restapi.model.dto.RoleDTO;
import com.example.restapi.model.mapper.AccountMapper;
import com.example.restapi.service.RoleService;
import com.example.restapi.model.entity.Account;
import com.example.restapi.model.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountMapperImpl implements AccountMapper {

    @Autowired
    private RoleService roleService;

    @Override
    public AccountDTO toDTO(Account account) {
        if (account == null) {
            return null;
        }

        // Account
        AccountDTO accountDTO = new AccountDTO();
        if (account.getId() != null) {
            accountDTO.setId(account.getId());
        }
//        accountDTO.setAvatar(account.getAvatar());
        accountDTO.setFullName(account.getFullName());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setUsername(account.getUsername());
        accountDTO.setPassword("******");
        accountDTO.setPhone(account.getPhone());
        accountDTO.setAddress(account.getAddress());
        accountDTO.setStatus(account.isStatus());

        // Role
        if (account.getRole() != null) {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setId(account.getRole().getId());
            roleDTO.setName(account.getRole().getName());
            roleDTO.setDescription(account.getRole().getDescription());
            roleDTO.setStatus(account.getRole().isStatus());
            accountDTO.setRole(roleDTO);
            accountDTO.setRoleId(roleDTO.getId());
            accountDTO.setRoleName(account.getRole().getName());
        }

        return accountDTO;
    }

    @Override
    public List<AccountDTO> toListDTO(List<Account> accounts) {
        if (accounts == null) {
            return null;
        }

        List<AccountDTO> list = new ArrayList<>(accounts.size());
        for (Account account : accounts) {
            AccountDTO accountDTO = toDTO(account);
            if (accountDTO != null) {
                list.add(accountDTO);
            }
        }

        return list;
    }

    @Override
    public Account toEntity(Account account, AccountDTO accountDTO) {
        if (accountDTO == null) {
            return null;
        }

        if (account == null) {
            account = new Account();
        }
        account.setAddress(accountDTO.getAddress());
        account.setStatus(accountDTO.isStatus());
        String email = accountDTO.getEmail();
        account.setEmail(email);
        account.setUsername(email);
        account.setFullName(accountDTO.getFullName());

        // set role
        if (accountDTO.getRoleId() > 0) {
            Role role = roleService.findById(accountDTO.getRoleId());
            if (role != null) {
                account.setRole(role);
            }
        }

        return account;
    }
}
