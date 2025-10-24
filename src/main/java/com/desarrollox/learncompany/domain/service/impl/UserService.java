package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.core.security.PasswordEncoderConfig;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryCourse;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryDepartment;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.exception.CourseNotFoundException;
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
public class UserService implements IUserService {

    private final IRepositoryUser repositoryUser;
    private final IRepositoryDepartment repositoryDepartment;
    private final IRepositoryCourse repositoryCourse;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final LoggingService loggingService; // Inyectar LoggingService

    @Transactional(readOnly = false)
    @Override
    public Employee createEmployee(Employee employee) {
        loggingService.logInfo("Iniciando creación de Employee con email: {}", employee.getEmail());
        try {
            Optional<User> existingUser = repositoryUser.findByEmailIncludingInactive(employee.getEmail());
            if (existingUser.isPresent()) {
                User existing = existingUser.get();
                if (existing.isActive()) {
                    loggingService.logError("Usuario con email {} ya está registrado y activo", employee.getEmail());
                    throw new UserAlreadyRegisteredException(employee.getEmail());
                } else {
                    loggingService.logInfo("Reactivando usuario inactivo con email: {}", employee.getEmail());
                    existing.setStatus(true);
                    existing.setName(employee.getName());
                    existing.setLastname(employee.getLastname());
                    existing.setPassword(passwordEncoderConfig.passwordEncoder().encode(employee.getPassword()));
                    existing.setRole(employee.getRole());
                    existing.setUrlPhoto(employee.getUrlPhoto());

                    loggingService.logDebug("Validando departamento con ID: {}", employee.getDepartment().getId());
                    if (!repositoryDepartment.existsById(employee.getDepartment().getId())) {
                        loggingService.logError("Departamento con ID {} no encontrado", employee.getDepartment().getId());
                        throw new DepartmentNotFoundException(employee.getDepartment().getId());
                    }
                    existing.setDepartment(repositoryDepartment.findById(employee.getDepartment().getId()).get());

                    Employee savedEmployee = (Employee) repositoryUser.save(existing);
                    loggingService.logInfo("Employee reactivado exitosamente con ID: {} y email: {}", 
                            savedEmployee.getId(), savedEmployee.getEmail());
                    return savedEmployee;
                }
            }

            loggingService.logDebug("Validando departamento con ID: {}", employee.getDepartment().getId());
            if (!repositoryDepartment.existsById(employee.getDepartment().getId())) {
                loggingService.logError("Departamento con ID {} no encontrado", employee.getDepartment().getId());
                throw new DepartmentNotFoundException(employee.getDepartment().getId());
            }

            employee.setDepartment(repositoryDepartment.findById(employee.getDepartment().getId()).get());
            employee.setPassword(passwordEncoderConfig.passwordEncoder().encode(employee.getPassword()));

            Employee savedEmployee = (Employee) repositoryUser.save(employee);
            loggingService.logInfo("Employee creado exitosamente con ID: {} y email: {}", 
                    savedEmployee.getId(), savedEmployee.getEmail());
            return savedEmployee;
        } catch (Exception e) {
            loggingService.logError("Error al crear Employee con email {}: {}", employee.getEmail(), e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = false)
    @Override
    public Instructor createInstructor(Instructor instructor) {
        loggingService.logInfo("Iniciando creación de Instructor con email: {}", instructor.getEmail());
        try {
            Optional<User> existingUser = repositoryUser.findByEmailIncludingInactive(instructor.getEmail());
            if (existingUser.isPresent()) {
                User existing = existingUser.get();
                if (existing.isActive()) {
                    loggingService.logError("Usuario con email {} ya está registrado y activo", instructor.getEmail());
                    throw new UserAlreadyRegisteredException(instructor.getEmail());
                } else {
                    loggingService.logInfo("Reactivando usuario inactivo con email: {}", instructor.getEmail());
                    existing.setStatus(true);
                    existing.setName(instructor.getName());
                    existing.setLastname(instructor.getLastname());
                    existing.setPassword(passwordEncoderConfig.passwordEncoder().encode(instructor.getPassword()));
                    existing.setRole(instructor.getRole());
                    existing.setUrlPhoto(instructor.getUrlPhoto());

                    loggingService.logDebug("Validando departamento con ID: {}", instructor.getDepartment().getId());
                    if (!repositoryDepartment.existsById(instructor.getDepartment().getId())) {
                        loggingService.logError("Departamento con ID {} no encontrado", instructor.getDepartment().getId());
                        throw new DepartmentNotFoundException(instructor.getDepartment().getId());
                    }
                    existing.setDepartment(repositoryDepartment.findById(instructor.getDepartment().getId()).get());

                    Instructor savedInstructor = (Instructor) repositoryUser.save(existing);
                    loggingService.logInfo("Instructor reactivado exitosamente con ID: {} y email: {}", 
                            savedInstructor.getId(), savedInstructor.getEmail());
                    return savedInstructor;
                }
            }

            loggingService.logDebug("Validando departamento con ID: {}", instructor.getDepartment().getId());
            if (!repositoryDepartment.existsById(instructor.getDepartment().getId())) {
                loggingService.logError("Departamento con ID {} no encontrado", instructor.getDepartment().getId());
                throw new DepartmentNotFoundException(instructor.getDepartment().getId());
            }

            instructor.setPassword(passwordEncoderConfig.passwordEncoder().encode(instructor.getPassword()));
            instructor.setDepartment(repositoryDepartment.findById(instructor.getDepartment().getId()).get());

            Instructor savedInstructor = (Instructor) repositoryUser.save(instructor);
            loggingService.logInfo("Instructor creado exitosamente con ID: {} y email: {}", 
                    savedInstructor.getId(), savedInstructor.getEmail());
            return savedInstructor;
        } catch (Exception e) {
            loggingService.logError("Error al crear Instructor con email {}: {}", instructor.getEmail(), e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(Long id) {
        loggingService.logInfo("Obteniendo User con ID: {}", id);
        try {
            if (!repositoryUser.existsById(id)) {
                loggingService.logError("Usuario con ID {} no encontrado", id);
                throw new UserNotFoundException(id);
            }
            Optional<User> user = repositoryUser.findById(id);
            loggingService.logInfo("User ID {} obtenido exitosamente", id);
            return user;
        } catch (Exception e) {
            loggingService.logError("Error al obtener User ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByEmail(String email) {
        loggingService.logInfo("Obteniendo User con email: {}", email);
        try {
            if (!repositoryUser.existsByEmail(email)) {
                loggingService.logError("Usuario con email {} no encontrado", email);
                throw new UserNotFoundException(email);
            }
            Optional<User> user = repositoryUser.findByEmail(email);
            loggingService.logInfo("User con email {} obtenido exitosamente", email);
            return user;
        } catch (Exception e) {
            loggingService.logError("Error al obtener User con email {}: {}", email, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        loggingService.logInfo("Obteniendo todos los Users");
        try {
            List<User> users = repositoryUser.findAll();
            if (users.isEmpty()) {
                loggingService.logWarning("No se encontraron Users");
            } else {
                loggingService.logInfo("Se encontraron {} Users", users.size());
            }
            return users;
        } catch (Exception e) {
            loggingService.logError("Error al obtener todos los Users: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = false)
    @Override
    public Optional<User> updateUser(Long id, User user) {
        loggingService.logInfo("Iniciando actualización de User con ID: {}", id);
        try {
            if (!repositoryUser.existsById(id)) {
                loggingService.logError("Usuario con ID {} no encontrado", id);
                throw new UserNotFoundException(id);
            }

            loggingService.logDebug("Validando departamento con ID: {}", user.getDepartment().getId());
            if (!repositoryDepartment.existsById(user.getDepartment().getId())) {
                loggingService.logError("Departamento con ID {} no encontrado", user.getDepartment().getId());
                throw new DepartmentNotFoundException(user.getDepartment().getId());
            }

            if (!user.getEmail().equals(repositoryUser.findById(id).get().getEmail())) {
                if (repositoryUser.existsByEmail(user.getEmail())) {
                    loggingService.logError("Ya existe un usuario con el email {}", user.getEmail());
                    throw new UserAlreadyRegisteredException("Ya existe un usuario con el email ingresado");
                }
            }

            loggingService.logDebug("Obteniendo User existente con ID: {}", id);
            User existingUser = repositoryUser.findById(id).get();

            existingUser.setName(user.getName());
            existingUser.setLastname(user.getLastname());
            existingUser.setEmail(user.getEmail());
            existingUser.setStatus(user.isStatus());
            existingUser.setUrlPhoto(user.getUrlPhoto());
            existingUser.setPassword(passwordEncoderConfig.passwordEncoder().encode(user.getPassword()));
            existingUser.setDepartment(repositoryDepartment.findById(user.getDepartment().getId()).get());

            if (existingUser instanceof Instructor && user instanceof Instructor) {
                loggingService.logDebug("Actualizando campos específicos de Instructor");
                ((Instructor) existingUser).setSpecialty(((Instructor) user).getSpecialty());
                ((Instructor) existingUser).setBiography(((Instructor) user).getBiography());
            } else if (existingUser instanceof Employee && user instanceof Employee) {
                loggingService.logDebug("Actualizando campos específicos de Employee");
                ((Employee) existingUser).setPuntos(((Employee) user).getPuntos());
            }

            Optional<User> updatedUser = repositoryUser.update(id, existingUser);
            loggingService.logInfo("User ID {} actualizado exitosamente", id);
            return updatedUser;
        } catch (Exception e) {
            loggingService.logError("Error al actualizar User ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = false)
    @Override
    public Optional<User> delete(Long id) {
        loggingService.logInfo("Iniciando eliminación de User con ID: {}", id);
        try {
            if (!repositoryUser.existsById(id)) {
                loggingService.logError("Usuario con ID {} no encontrado", id);
                throw new UserNotFoundException(id);
            }
            Optional<User> deletedUser = repositoryUser.delete(id);
            loggingService.logInfo("User ID {} eliminado exitosamente", id);
            return deletedUser;
        } catch (Exception e) {
            loggingService.logError("Error al eliminar User ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Employee> getRankingByDepartment(Long departmentId) {
        loggingService.logInfo("Obteniendo ranking de Employees para departmentId: {}", departmentId);
        try {
            if (!repositoryDepartment.existsById(departmentId)) {
                loggingService.logError("Departamento con ID {} no encontrado", departmentId);
                throw new DepartmentNotFoundException(departmentId);
            }
            List<Employee> ranking = repositoryUser.getRankingByDepartment(departmentId);
            if (ranking.isEmpty()) {
                loggingService.logWarning("No se encontraron Employees para el ranking en departmentId: {}", departmentId);
            } else {
                loggingService.logInfo("Se encontraron {} Employees para el ranking en departmentId: {}", 
                        ranking.size(), departmentId);
            }
            return ranking;
        } catch (Exception e) {
            loggingService.logError("Error al obtener ranking de Employees para departmentId {}: {}", 
                    departmentId, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findUsersByFilters(Long departmentId, Role role, boolean status) {
        loggingService.logInfo("Obteniendo Users con filtros: departmentId={}, role={}, status={}", 
                departmentId, role, status);
        try {
            List<User> users = repositoryUser.findUsersByFilters(departmentId, role, status);
            if (users.isEmpty()) {
                loggingService.logWarning("No se encontraron Users con filtros: departmentId={}, role={}, status={}", 
                        departmentId, role, status);
            } else {
                loggingService.logInfo("Se encontraron {} Users con filtros: departmentId={}, role={}, status={}", 
                        users.size(), departmentId, role, status);
            }
            return users;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Users con filtros: departmentId={}, role={}, status={}: {}", 
                    departmentId, role, status, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Employee> findEmployeesFinished() {
        loggingService.logInfo("Obteniendo Employees que han finalizado");
        try {
            List<Employee> employees = repositoryUser.findEmployeesFinished();
            if (employees.isEmpty()) {
                loggingService.logWarning("No se encontraron Employees que han finalizado");
            } else {
                loggingService.logInfo("Se encontraron {} Employees que han finalizado", employees.size());
            }
            return employees;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Employees que han finalizado: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Employee> findEmployeesFinishedByCourseId(Long id) {
        loggingService.logInfo("Obteniendo Employees que han finalizado el curso con ID: {}", id);
        try {
            if (!repositoryCourse.existsById(id)) {
                loggingService.logError("Curso con ID {} no encontrado", id);
                throw new CourseNotFoundException(id);
            }
            List<Employee> employees = repositoryUser.findEmployeesFinishedByCourseId(id);
            if (employees.isEmpty()) {
                loggingService.logWarning("No se encontraron Employees que han finalizado el curso con ID: {}", id);
            } else {
                loggingService.logInfo("Se encontraron {} Employees que han finalizado el curso con ID: {}", 
                        employees.size(), id);
            }
            return employees;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Employees que han finalizado el curso con ID {}: {}", 
                    id, e.getMessage(), e);
            throw e;
        }
    }
}