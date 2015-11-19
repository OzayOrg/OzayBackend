package com.ozay.backend.service;

import com.ozay.backend.model.Role;
import com.ozay.backend.model.RolePermission;
import com.ozay.backend.repository.RolePermissionRepository;
import com.ozay.backend.repository.RoleRepository;
import com.ozay.backend.web.rest.form.RoleFormDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by naofumiezaki on 11/18/15.
 */
@Service
@Transactional
public class RoleService {

    @Inject
    RoleRepository roleRepository;

    @Inject
    RolePermissionRepository rolePermissionRepository;

    public void create(RoleFormDTO roleFormDTO){
        roleRepository.create(roleFormDTO.getRole());
        processRolePermission(roleFormDTO.getRole());
    }

    public void update(RoleFormDTO roleFormDTO){
        roleRepository.update(roleFormDTO.getRole());
        processRolePermission(roleFormDTO.getRole());
    }

    private void processRolePermission(Role role){
        rolePermissionRepository.deleteAllByRoleId(role.getId());
        for(RolePermission rolePermission : role.getRolePermissions()){
            rolePermission.setRoleId(role.getId());
            rolePermissionRepository.create(rolePermission);
        }
    }
}
