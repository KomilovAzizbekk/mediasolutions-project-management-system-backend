package uz.prod.backcrm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.mapper.RoleMapper;
import uz.prod.backcrm.payload.RoleDTO;
import uz.prod.backcrm.repository.RoleRepository;
import uz.prod.backcrm.service.abs.RoleService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public ApiResult<List<RoleDTO>> getRoles() {
        return ApiResult.success(roleMapper.toDTOList(roleRepository.findAll()));
    }
}
