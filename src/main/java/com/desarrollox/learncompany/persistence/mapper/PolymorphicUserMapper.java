package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import com.desarrollox.learncompany.domain.model.Administrator;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.domain.model.Instructor;
import com.desarrollox.learncompany.domain.model.User;
import com.desarrollox.learncompany.persistence.entity.AdministratorEntity;
import com.desarrollox.learncompany.persistence.entity.EmployeeEntity;
import com.desarrollox.learncompany.persistence.entity.InstructorEntity;
import com.desarrollox.learncompany.persistence.entity.UserEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class PolymorphicUserMapper {

    @Autowired
    protected EmployeeMapper employeeMapper;
    
    @Autowired
    protected InstructorMapper instructorMapper;
    
    @Autowired
    protected AdministratorMapper administratorMapper;

    public User toDomain(UserEntity entity) {
        if (entity instanceof EmployeeEntity) {
            return employeeMapper.toDomain((EmployeeEntity) entity);
        } else if (entity instanceof InstructorEntity) {
            return instructorMapper.toDomain((InstructorEntity) entity);
        } else if (entity instanceof AdministratorEntity) {
            return administratorMapper.toDomain((AdministratorEntity) entity);
        }
        throw new IllegalArgumentException("Tipo de entidad no soportado: " + entity.getClass().getSimpleName());
    }

    public UserEntity toEntity(User domain) {
        if (domain instanceof Employee) {
            return employeeMapper.toEntity((Employee) domain);
        } else if (domain instanceof Instructor) {
            return instructorMapper.toEntity((Instructor) domain);
        } else if (domain instanceof Administrator) {
            return administratorMapper.toEntity((Administrator) domain);
        }
        throw new IllegalArgumentException("Tipo de dominio no soportado: " + domain.getClass().getSimpleName());
    }
}
