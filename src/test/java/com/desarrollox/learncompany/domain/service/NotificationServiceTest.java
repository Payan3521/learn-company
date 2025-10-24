package com.desarrollox.learncompany.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryNotification;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.exception.NotificationNotFoundException;
import com.desarrollox.learncompany.domain.exception.UserNotFoundException;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.domain.model.Notification;
import com.desarrollox.learncompany.domain.model.NotificationContent;
import com.desarrollox.learncompany.domain.model.User;
import com.desarrollox.learncompany.domain.model.User.Role;
import com.desarrollox.learncompany.domain.service.impl.NotificationService;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {
    
    @Mock
    private IRepositoryNotification repositoryNotification;

    @Mock
    private IRepositoryUser repositoryUser;

    @Mock
    private LoggingService loggingService;

    @InjectMocks
    private NotificationService notificationService;

    private Notification notification;
    private User user;
    private NotificationContent notificationContent;
    private List<Notification> notificationList;

    @BeforeEach
    void setUp(){

        user = new Employee();
        user.setId(1L);
        user.setRole(Role.EMPLOYEE);
        
        notificationContent = new NotificationContent();
        notificationContent.setId(1L);
        
        notification = new Notification();
        notification.setId(1L);
        notification.setUser(user);
        notification.setContent(notificationContent);
        notification.setReadStatus(false);

        notificationList = new ArrayList<>();
        notificationList.add(notification);
    }

    @Test //201
    void createNotification_success(){
        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.findById(anyLong())).thenReturn(Optional.of(user));
        when(repositoryNotification.save(any(Notification.class))).thenReturn(notification);

        Notification result = notificationService.createNotification(notification);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        
        verify(repositoryUser).existsById(anyLong());
        verify(repositoryUser).findById(anyLong());
        verify(repositoryNotification).save(any(Notification.class));
    }

    @Test //404
    void createNotification_UserNotFound(){
        when(repositoryUser.existsById(anyLong())).thenReturn(false);

        UserNotFoundException thrown = assertThrows(
            UserNotFoundException.class, 
            () -> notificationService.createNotification(notification)
        );

        assertNotNull(thrown);

        verify(repositoryUser).existsById(anyLong());
        verify(repositoryUser, never()).findById(anyLong());
        verify(repositoryNotification, never()).save(any(Notification.class));
    }

    @Test //200
    void getNotificationById_success(){
        when(repositoryNotification.existsById(anyLong())).thenReturn(true);
        when(repositoryNotification.findById(anyLong())).thenReturn(Optional.of(notification));

        Optional<Notification> result = notificationService.getNotificationById(1L);

        assertTrue(result.isPresent());
        assertEquals(notification, result.get());
        assertEquals(1L, result.get().getId());

        verify(repositoryNotification).existsById(anyLong());
        verify(repositoryNotification).findById(anyLong());
    }

    @Test //404
    void getNotificationById_NotificationNotFound(){
        when(repositoryNotification.existsById(anyLong())).thenReturn(false);

        NotificationNotFoundException thrown = assertThrows(
            NotificationNotFoundException.class, 
            () -> notificationService.getNotificationById(1L)
        );

        assertNotNull(thrown);

        verify(repositoryNotification).existsById(anyLong());
        verify(repositoryNotification, never()).findById(anyLong());
    }

    @Test //200
    void getAllNotifications_success(){
        when(repositoryNotification.findAll()).thenReturn(notificationList);

        List<Notification> result = notificationService.getAllNotifications();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(notificationList, result);

        verify(repositoryNotification).findAll();
    }

    @Test //204
    void getAllNotifications_isEmpty(){
        when(repositoryNotification.findAll()).thenReturn(new ArrayList<>());

        List<Notification> result = notificationService.getAllNotifications();

        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        assertNotEquals(notificationList, result);

        verify(repositoryNotification).findAll();
    }

    @Test //200
    void getNotificationsByUserId_success(){
        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryNotification.findNotificationsByUserId(anyLong())).thenReturn(notificationList);

        List<Notification> result = notificationService.getNotificationsByUserId(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(notificationList, result);

        verify(repositoryUser).existsById(anyLong());
        verify(repositoryNotification).findNotificationsByUserId(anyLong());
    }

    @Test //404
    void getNotificationsByUserId_UserNotFound(){
        when(repositoryUser.existsById(anyLong())).thenReturn(false);

        UserNotFoundException thrown = assertThrows(
            UserNotFoundException.class, 
            () -> notificationService.getNotificationsByUserId(1L)
        );

        assertNotNull(thrown);

        verify(repositoryUser).existsById(anyLong());
        verify(repositoryNotification, never()).findNotificationsByUserId(anyLong());
    }

    @Test //204
    void getNotificationsByUserId_isEmpty(){
        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryNotification.findNotificationsByUserId(anyLong())).thenReturn(new ArrayList<>());

        List<Notification> result = notificationService.getNotificationsByUserId(1L);

        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        assertNotEquals(notificationList, result);

        verify(repositoryUser).existsById(anyLong());
        verify(repositoryNotification).findNotificationsByUserId(anyLong());
    }

    @Test //200
    void markAsRead_success(){

        Notification notificacionLocal = new Notification();
        notificacionLocal.setId(1L);
        notificacionLocal.setUser(user);
        notificacionLocal.setContent(notificationContent);
        notificacionLocal.setReadStatus(true);

        when(repositoryNotification.existsById(anyLong())).thenReturn(true);
        when(repositoryNotification.markAsRead(anyLong())).thenReturn(notificacionLocal);

        Notification result = notificationService.markAsRead(1L);

        assertNotNull(result);
        assertEquals(notification.getId(), result.getId());
        assertNotEquals(notification.isReadStatus(), result.isReadStatus());
        assertTrue(result.isReadStatus());
        assertFalse(notification.isReadStatus());

        verify(repositoryNotification).existsById(anyLong());
        verify(repositoryNotification).markAsRead(anyLong());
    }

    @Test //404
    void markAsRead_NotificationNotFound(){
        when(repositoryNotification.existsById(anyLong())).thenReturn(false);

        NotificationNotFoundException thrown = assertThrows(
            NotificationNotFoundException.class, 
            () -> notificationService.markAsRead(1L)
        );

        assertNotNull(thrown);

        verify(repositoryNotification).existsById(anyLong());
        verify(repositoryNotification, never()).markAsRead(anyLong());
    }

}