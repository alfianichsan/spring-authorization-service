package com.authorization.authorization.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authorization.authorization.entity.BaseResponse;
import com.authorization.authorization.entity.Roles;
import com.authorization.authorization.service.RoleService;
import com.authorization.authorization.utils.ResponseHelper;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final ResponseHelper responseHelper;
    private final RoleService roleService;

    public RoleController(RoleService roleService, ResponseHelper responseHelper) {
        this.roleService = roleService;
        this.responseHelper = responseHelper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Roles>> getRoleById(@PathVariable Long id) {
        Roles roleDetail = roleService.getRoleById(id);
        return responseHelper.createSuccessResponse("Data retrieved successfully", roleDetail);
    }

    @PostMapping("")
    public ResponseEntity<BaseResponse<Roles>> createRole(@Validated @RequestBody Roles roles) {
        Roles createdRole = roleService.creatRoles(roles);
        return responseHelper.createCreatedResponse(createdRole);
    }

    @GetMapping("")
    public ResponseEntity<BaseResponse<List<Roles>>> getAllRoles() {
        List<Roles> roles = roleService.listRoles();
        return responseHelper.createSuccessResponse("Data retrieved successfully", roles);
    }

}
