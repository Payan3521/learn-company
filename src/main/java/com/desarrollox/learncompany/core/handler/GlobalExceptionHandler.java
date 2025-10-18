package com.desarrollox.learncompany.core.handler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.desarrollox.learncompany.domain.exception.AssessmentInstanceNotFoundException;
import com.desarrollox.learncompany.domain.exception.AssessmentTemplateNotFoundException;
import com.desarrollox.learncompany.domain.exception.BadgeAlreadyRegisteredException;
import com.desarrollox.learncompany.domain.exception.BadgeNotFoundException;
import com.desarrollox.learncompany.domain.exception.CertificateNotFoundException;
import com.desarrollox.learncompany.domain.exception.CourseNotFoundException;
import com.desarrollox.learncompany.domain.exception.DepartmentAlreadyRegisteredException;
import com.desarrollox.learncompany.domain.exception.DepartmentIncorrectException;
import com.desarrollox.learncompany.domain.exception.DepartmentNotFoundException;
import com.desarrollox.learncompany.domain.exception.DurationCourseInvalidException;
import com.desarrollox.learncompany.domain.exception.EmployeeBadgeAlreadyRegisteredException;
import com.desarrollox.learncompany.domain.exception.EmployeeBadgeNotFoundException;
import com.desarrollox.learncompany.domain.exception.InscriptionAlreadyRegisteredException;
import com.desarrollox.learncompany.domain.exception.InscriptionNotFoundException;
import com.desarrollox.learncompany.domain.exception.InvalidCredentialsException;
import com.desarrollox.learncompany.domain.exception.InvalidRoleException;
import com.desarrollox.learncompany.domain.exception.InvalidTokenException;
import com.desarrollox.learncompany.domain.exception.ModuleNotFoundException;
import com.desarrollox.learncompany.domain.exception.NotificationNotFoundException;
import com.desarrollox.learncompany.domain.exception.SeasonAlreadyCreatedException;
import com.desarrollox.learncompany.domain.exception.SeasonNotFoundException;
import com.desarrollox.learncompany.domain.exception.UserAlreadyRegisteredException;
import com.desarrollox.learncompany.domain.exception.UserNotActiveException;
import com.desarrollox.learncompany.domain.exception.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String error, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }

    // === Excepciones personalizadas ===

    @ExceptionHandler({
        AssessmentInstanceNotFoundException.class,
        AssessmentTemplateNotFoundException.class,
        BadgeNotFoundException.class,
        CertificateNotFoundException.class,
        CourseNotFoundException.class,
        DepartmentNotFoundException.class,
        InscriptionNotFoundException.class,
        ModuleNotFoundException.class,
        NotificationNotFoundException.class,
        UserNotFoundException.class,
        SeasonNotFoundException.class,
        EmployeeBadgeNotFoundException.class
    })
    public ResponseEntity<Map<String, Object>> handleNotFound(RuntimeException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Recurso no encontrado", ex.getMessage());
    }

    @ExceptionHandler({
        UserAlreadyRegisteredException.class,
        SeasonAlreadyCreatedException.class,
        DepartmentAlreadyRegisteredException.class,
        BadgeAlreadyRegisteredException.class,
        InscriptionAlreadyRegisteredException.class,
        EmployeeBadgeAlreadyRegisteredException.class
    })
    public ResponseEntity<Map<String, Object>> handleConflict(RuntimeException ex) {
        return buildResponse(HttpStatus.CONFLICT, "Conflicto en la solicitud", ex.getMessage());
    }

    @ExceptionHandler({InvalidCredentialsException.class, InvalidTokenException.class})
    public ResponseEntity<Map<String, Object>> handleInvalidCredentials(InvalidCredentialsException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Credenciales inválidas", ex.getMessage());
    }

    @ExceptionHandler({DepartmentIncorrectException.class, InvalidRoleException.class, UserNotActiveException.class})
    public ResponseEntity<Map<String, Object>> handleForbidden(DepartmentIncorrectException ex) {
        return buildResponse(HttpStatus.FORBIDDEN, "Acceso denegado", ex.getMessage());
    }

    @ExceptionHandler(DurationCourseInvalidException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessValidation(DurationCourseInvalidException ex){
        return buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Validacion de negocio fallida", ex.getMessage());
    }

    // === Errores comunes del cliente (HTTP 400) ===

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
            errors.put(err.getField(), err.getDefaultMessage())
        );
        return buildResponse(HttpStatus.BAD_REQUEST, "Error de validación", errors.toString());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleMalformedJson(HttpMessageNotReadableException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "JSON mal formado", "Revisa la estructura del cuerpo de la solicitud");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrity(DataIntegrityViolationException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Violación de integridad", "Datos duplicados o relaciones inválidas");
    }

    // === Otros errores del cliente ===

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        return buildResponse(HttpStatus.METHOD_NOT_ALLOWED, "Método no permitido", ex.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handleUnsupportedMedia(HttpMediaTypeNotSupportedException ex) {
        return buildResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Tipo de contenido no soportado", ex.getMessage());
    }

    // === Errores de base de datos o entidad inexistente ===

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFound(EntityNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Entidad no encontrada", ex.getMessage());
    }

    // === Fallback genérico ===

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        ex.printStackTrace(); // Para ver el error real en consola
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", ex.getMessage());
    }
}