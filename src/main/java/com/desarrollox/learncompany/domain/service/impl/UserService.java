package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
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

    @Override
    public Employee createEmployee(Employee employee) {

        if(repositoryUser.existsByEmail(employee.getEmail())){
            throw new UserAlreadyRegisteredException(employee.getEmail());
        } 

        if(!repositoryDepartment.existsById(employee.getDepartment().getId())){
            throw new DepartmentNotFoundException(employee.getDepartment().getId());
        }

        employee.setDepartment(repositoryDepartment.findById(employee.getDepartment().getId()).get());
        employee.setPassword(passwordEncoderConfig.passwordEncoder().encode(employee.getPassword()));


        return (Employee) repositoryUser.save(employee);
    }

    @Override
    public Instructor createInstructor(Instructor instructor) {

        if(repositoryUser.existsByEmail(instructor.getEmail())){
            throw new UserAlreadyRegisteredException(instructor.getEmail());
        } 

        if(!repositoryDepartment.existsById(instructor.getDepartment().getId())){
            throw new DepartmentNotFoundException(instructor.getDepartment().getId());
        }

        instructor.setPassword(passwordEncoderConfig.passwordEncoder().encode(instructor.getPassword()));
        instructor.setDepartment(repositoryDepartment.findById(instructor.getDepartment().getId()).get());

        return (Instructor) repositoryUser.save(instructor);
    }

    @Override
    public Optional<User> findById(Long id) {

        if(!repositoryUser.existsById(id)){
            throw new UserNotFoundException(id);
        } 

        return repositoryUser.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if(!repositoryUser.existsByEmail(email)){
            throw new UserNotFoundException(email);
        } 

        return repositoryUser.findByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return repositoryUser.findAll();
    }

    @Override
    public Optional<User> updateUser(Long id, User user) {
        if(repositoryUser.existsByEmail(user.getEmail())){
            throw new UserNotFoundException(user.getEmail());
        }

        if(!repositoryUser.existsById(id)){
            throw new UserNotFoundException(id);
        }
        
        if(!repositoryDepartment.existsById(user.getDepartment().getId())){
            throw new DepartmentNotFoundException(user.getDepartment().getId());
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


    @Override
    public Optional<User> delete(Long id) {
        if (!repositoryUser.existsById(id)) {
            throw new UserNotFoundException(id);
        } 
        return repositoryUser.delete(id);
    }

    @Override
    public List<Employee> getRankingByDepartment(Long departmentId) {
        return repositoryUser.getRankingByDepartment(departmentId);
    }

    @Override
    public List<User> findUsersByFilters(Long departmentId, Role role, boolean status) {
        return repositoryUser.findUsersByFilters(departmentId, role, status);
    }

}