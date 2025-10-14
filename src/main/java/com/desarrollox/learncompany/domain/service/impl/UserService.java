package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.desarrollox.learncompany.core.security.PasswordEncoderConfig;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryDepartment;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.exception.DepartmentNotFoundException;
import com.desarrollox.learncompany.domain.exception.UserAlreadyRegisteredException;
import com.desarrollox.learncompany.domain.exception.UserNotFoundException;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.domain.model.Instructor;
import com.desarrollox.learncompany.domain.model.User;
import com.desarrollox.learncompany.domain.model.User.Role;
import com.desarrollox.learncompany.domain.service.IUserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final IRepositoryUser repositoryUser;
    private final IRepositoryDepartment repositoryDepartment;
    private final PasswordEncoderConfig passwordEncoderConfig;

    @Transactional(readOnly = false)
    @Override
    public Employee createEmployee(Employee employee) {
    
        Optional<User> existingUser = repositoryUser.findByEmailIncludingInactive(employee.getEmail());
    
        if (existingUser.isPresent()) {
            User existing = existingUser.get();
    
            if (existing.isActive()) {
                // Ya hay un usuario activo con ese correo → error
                throw new UserAlreadyRegisteredException(employee.getEmail());
            } else {
                // Reactivar usuario inactivo
                existing.setStatus(true);
                existing.setName(employee.getName());
                existing.setLastname(employee.getLastname());
                existing.setPassword(passwordEncoderConfig.passwordEncoder().encode(employee.getPassword()));
                existing.setRole(employee.getRole());
                existing.setUrlPhoto(employee.getUrlPhoto());
                
                // Validar y asignar departamento
                if (!repositoryDepartment.existsById(employee.getDepartment().getId())) {
                    throw new DepartmentNotFoundException(employee.getDepartment().getId());
                }
                existing.setDepartment(repositoryDepartment.findById(employee.getDepartment().getId()).get());
    
                // Guardar cambios y devolver como Employee
                return (Employee) repositoryUser.save(existing);
            }
        }
    
        // Si no existía el usuario, crear normalmente
        if (!repositoryDepartment.existsById(employee.getDepartment().getId())) {
            throw new DepartmentNotFoundException(employee.getDepartment().getId());
        }
    
        employee.setDepartment(repositoryDepartment.findById(employee.getDepartment().getId()).get());
        employee.setPassword(passwordEncoderConfig.passwordEncoder().encode(employee.getPassword()));
    
        return (Employee) repositoryUser.save(employee);
    }

    @Transactional(readOnly = false)
    @Override
    public Instructor createInstructor(Instructor instructor) {
    
        Optional<User> existingUser = repositoryUser.findByEmailIncludingInactive(instructor.getEmail());
    
        if (existingUser.isPresent()) {
            User existing = existingUser.get();
    
            if (existing.isActive()) {
                // Si el usuario ya está activo, no se puede registrar nuevamente
                throw new UserAlreadyRegisteredException(instructor.getEmail());
            } else {
                // Reactivar usuario inactivo
                existing.setStatus(true);
                existing.setName(instructor.getName());
                existing.setLastname(instructor.getLastname());
                existing.setPassword(passwordEncoderConfig.passwordEncoder().encode(instructor.getPassword()));
                existing.setRole(instructor.getRole());
                existing.setUrlPhoto(instructor.getUrlPhoto());
    
                // Validar y asignar departamento
                if (!repositoryDepartment.existsById(instructor.getDepartment().getId())) {
                    throw new DepartmentNotFoundException(instructor.getDepartment().getId());
                }
                existing.setDepartment(repositoryDepartment.findById(instructor.getDepartment().getId()).get());
    
                // Guardar cambios y devolver como Instructor
                return (Instructor) repositoryUser.save(existing);
            }
        }
    
        // Si no existía el usuario, crear normalmente
        if (!repositoryDepartment.existsById(instructor.getDepartment().getId())) {
            throw new DepartmentNotFoundException(instructor.getDepartment().getId());
        }
    
        instructor.setPassword(passwordEncoderConfig.passwordEncoder().encode(instructor.getPassword()));
        instructor.setDepartment(repositoryDepartment.findById(instructor.getDepartment().getId()).get());
    
        return (Instructor) repositoryUser.save(instructor);
    }
    
    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(Long id) {

        if(!repositoryUser.existsById(id)){
            throw new UserNotFoundException(id);
        }

        return repositoryUser.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByEmail(String email) {
        if(!repositoryUser.existsByEmail(email)){
            throw new UserNotFoundException(email);
        } 

        return repositoryUser.findByEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return repositoryUser.findAll();
    }

    @Transactional(readOnly = false)
    @Override
    public Optional<User> updateUser(Long id, User user) {

        if(!repositoryUser.existsById(id)){
            throw new UserNotFoundException(id);
        }

        if(!repositoryDepartment.existsById(user.getDepartment().getId())){
            throw new DepartmentNotFoundException(user.getDepartment().getId());
        }

        if (!user.getEmail().equals(repositoryUser.findById(id).get().getEmail())) {
            if (repositoryUser.existsByEmail(user.getEmail())) {
                throw new UserAlreadyRegisteredException("Ya existe un usuario con el email ingresado");
            }
        }
        // Get the existing user to preserve the role and other fields
        User existingUser = repositoryUser.findById(id).get();
        
        // Update only the fields that should change
        existingUser.setName(user.getName());
        existingUser.setLastname(user.getLastname());
        existingUser.setEmail(user.getEmail());
        existingUser.setStatus(user.isStatus());
        existingUser.setUrlPhoto(user.getUrlPhoto());
        existingUser.setPassword(passwordEncoderConfig.passwordEncoder().encode(user.getPassword()));
        existingUser.setDepartment(repositoryDepartment.findById(user.getDepartment().getId()).get());
        
        // Update subclass-specific fields
        if (existingUser instanceof Instructor && user instanceof Instructor) {
            ((Instructor) existingUser).setSpecialty(((Instructor) user).getSpecialty());
            ((Instructor) existingUser).setBiography(((Instructor) user).getBiography());
        } else if (existingUser instanceof Employee && user instanceof Employee) {
            ((Employee) existingUser).setPuntos(((Employee) user).getPuntos());
        }
        
        return repositoryUser.update(id, existingUser);
    }

    @Transactional(readOnly = false)
    @Override
    public Optional<User> delete(Long id) {
        if (!repositoryUser.existsById(id)) {
            throw new UserNotFoundException(id);
        } 
        return repositoryUser.delete(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Employee> getRankingByDepartment(Long departmentId) {
        return repositoryUser.getRankingByDepartment(departmentId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findUsersByFilters(Long departmentId, Role role, boolean status) {
        return repositoryUser.findUsersByFilters(departmentId, role, status);
    }

}