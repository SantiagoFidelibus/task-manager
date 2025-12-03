package com.santech.mtm.service;

import com.santech.mtm.dto.LoginRequest;
import com.santech.mtm.dto.UserDTO;
import com.santech.mtm.exception.InvalidPasswordException;
import com.santech.mtm.exception.UserNotFoundException;
import com.santech.mtm.exception.UserAlreadyActiveException;
import com.santech.mtm.exception.UserAlreadyInactiveException;
import com.santech.mtm.model.UserApp;
import com.santech.mtm.model.mapper.UserMapper;
import com.santech.mtm.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDTO> findAllUsers() {
        log.info("Buscando lista de usuarios");
        return userRepository.findAllByActiveTrue()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    public UserDTO findUserById(Long id) throws UserNotFoundException, UserAlreadyInactiveException{
        return userMapper.toDTO(
                findActiveUserOrThrow(Optional.of(id),Optional.empty() )
        );
    }

    @Override
    public UserDTO findUserByEmail(String email) throws UserNotFoundException, UserAlreadyInactiveException{

        return userMapper.toDTO(
                findActiveUserOrThrow(Optional.empty(), Optional.of(email))
        );

    }

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) throws UserAlreadyActiveException {
        Optional<UserApp> existingUserOpt = userRepository.findByEmail(userDTO.getEmail());

        if (existingUserOpt.isPresent()) {
            UserApp existing = existingUserOpt.get();

            if (existing.isActive()) {
                throw new UserAlreadyActiveException("El usuario con ese email ya se encuentra activo");
            }

            existing.setActive(true);

            userRepository.save(existing);

            return userMapper.toDTO(existing);
        }
        UserApp newUser = userMapper.toEntity(userDTO);
        newUser.setActive(true);
        userRepository.save(newUser);

        return userMapper.toDTO(newUser);
    }

    @Override
    public UserDTO authenticateUser(LoginRequest request) throws UserNotFoundException, UserAlreadyInactiveException, InvalidPasswordException {
        log.info("Intentando autenticar usuario con email: {}", request.getEmail());
        UserApp user = findActiveUserOrThrow(Optional.empty(), Optional.of(request.getEmail()));

        if(!user.getPassword().equals(request.getPassword())){
            log.warn("Contraseña incorrecta para el usuario {}", request.getEmail());
            throw new InvalidPasswordException("La contraseña ingresada es invalida");
        }
        log.info("Usuario autenticado correctamente: {}", request.getEmail());
        return userMapper.toDTO(user);
    }

    @Override
    @Transactional
    public void softDeleteUser(Long id) throws UserNotFoundException, UserAlreadyInactiveException {
        UserApp user = findActiveUserOrThrow(Optional.of(id), Optional.empty());
        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDTO reactivateUser(Long id) throws UserNotFoundException, UserAlreadyActiveException{
        UserApp user = findInactiveUserOrThrow(id);
        user.setActive(true);
        userRepository.save(user);

        return userMapper.toDTO(user);
    }

    private UserApp findActiveUserOrThrow(Optional<Long> id, Optional<String> email) throws UserNotFoundException, UserAlreadyInactiveException {
        if (id.isEmpty() && email.isEmpty()) {
            throw new IllegalArgumentException("Debe especificarse id o email");
        }

        UserApp user = id.map(userRepository::findById)
                .orElseGet(() -> email.flatMap(userRepository::findByEmail))
                .orElseThrow(() -> new UserNotFoundException("No existe el usuario que está buscando"));

        if (!user.isActive()) {
            throw new UserAlreadyInactiveException("El usuario existe pero está inactivo");
        }

        return user;
    }



    private UserApp findInactiveUserOrThrow(Long id) throws UserNotFoundException,UserAlreadyActiveException {
        if (id == null) {
            throw new IllegalArgumentException("Debe especificarse id o email");
        }

        UserApp user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("No existe usuario con id " + id)
        );
        if (user.isActive()) {
            throw new UserAlreadyActiveException("El usuario ya está activo");
        }
        return user;
    }

}
