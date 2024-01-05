package uz.prod.backcrm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.prod.backcrm.entity.User;
import uz.prod.backcrm.exceptions.RestException;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.mapper.RoleMapper;
import uz.prod.backcrm.mapper.UserMapper;
import uz.prod.backcrm.payload.ProfileDTO;
import uz.prod.backcrm.payload.RoleDTO;
import uz.prod.backcrm.payload.UserDTO;
import uz.prod.backcrm.repository.UserRepository;
import uz.prod.backcrm.service.abs.UserService;
import uz.prod.backcrm.utills.CommonUtils;
import uz.prod.backcrm.utills.constants.Message;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleMapper roleMapper;

    @Override
    public ApiResult<UserDTO> me(){
        User user = CommonUtils.getUserFromRequest();
        return ApiResult.success(userMapper.toDTO(user));
    }

    @Override
    public ApiResult<List<UserDTO>> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResult.success(userMapper.toDTOList(userRepository.findAll(pageable)));
    }

    @Override
    public ApiResult<?> editProfile(ProfileDTO dto) {
        User user = CommonUtils.getUserFromRequest();
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());
        userRepository.save(user);
        return ApiResult.success(Message.UPDATED_SUCCESSFULLY);
    }

    @Override
    public ApiResult<?> editByAdmin(UUID id, RoleDTO roleDTO) {
        User user = userRepository.findById(id).orElseThrow(
                () -> RestException.restThrow(Message.ID_NOT_FOUND, HttpStatus.BAD_REQUEST));
        user.setRole(roleMapper.toEntity(roleDTO));
        userRepository.save(user);
        return ApiResult.success(Message.UPDATED_SUCCESSFULLY);
    }

    @Override
    public ApiResult<?> deleteProfile() {
        try {
            userRepository.deleteById(CommonUtils.getUserFromRequest().getId());
            return ApiResult.success(Message.DELETED_SUCCESSFULLY);
        } catch (Exception e) {
            throw RestException.restThrow(Message.DELETE_FAILED, HttpStatus.CONFLICT);
        }
    }


}
