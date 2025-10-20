package com.desarrollox.learncompany.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
import com.desarrollox.learncompany.domain.accessDb.IRepositoryBadge;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.exception.BadgeAlreadyRegisteredException;
import com.desarrollox.learncompany.domain.exception.BadgeNotFoundException;
import com.desarrollox.learncompany.domain.exception.UserNotFoundException;
import com.desarrollox.learncompany.domain.model.Badge;
import com.desarrollox.learncompany.domain.service.impl.BadgeService;

@ExtendWith(MockitoExtension.class)
public class BadgeServiceTest {
    
    @Mock
    private IRepositoryBadge repositoryBadge;

    @Mock
    private IRepositoryUser repositoryUser;

    @Mock
    private LoggingService loggingService;

    @InjectMocks
    private BadgeService badgeService;

    private Badge badge;
    private List<Badge> listaBadges;

    @BeforeEach
    void setUp(){
        badge = new Badge();
        badge.setId(1L);
        badge.setName("Maestro");

        listaBadges = new ArrayList<>();
        listaBadges.add(badge);
    }

    @Test //201
    void createBadge_success(){
        when(repositoryBadge.findBadgeByName(anyString())).thenReturn(Optional.empty());
        when(repositoryBadge.save(any(Badge.class))).thenReturn(badge);

        Badge result = badgeService.createBadge(badge);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(repositoryBadge).findBadgeByName(anyString());
        verify(repositoryBadge).save(any(Badge.class));
    }

    @Test //409
    void createBadge_BadgeAlreadyRegistered(){
        when(repositoryBadge.findBadgeByName(anyString())).thenReturn(Optional.of(badge));

        BadgeAlreadyRegisteredException thrown = assertThrows(
            BadgeAlreadyRegisteredException.class,
            () -> badgeService.createBadge(badge)
        );

        assertNotNull(thrown);
        verify(repositoryBadge).findBadgeByName(anyString());
        verify(repositoryBadge, never()).save(any(Badge.class));
    }

    @Test //200
    void getBadgeById_success(){
        when(repositoryBadge.existsById(anyLong())).thenReturn(true);
        when(repositoryBadge.findById(anyLong())).thenReturn(Optional.of(badge));

        Optional<Badge> result = badgeService.getBadgeById(1L);

        assertTrue(result.isPresent());
        assertEquals(badge, result.get());
        assertEquals(badge.getId(), result.get().getId());

        verify(repositoryBadge).existsById(anyLong());
        verify(repositoryBadge).findById(anyLong());
    }

    @Test //404
    void getBadgeById_BadgeNotFound(){
        when(repositoryBadge.existsById(anyLong())).thenReturn(false);

        BadgeNotFoundException thrown = assertThrows(
            BadgeNotFoundException.class,
            () -> badgeService.getBadgeById(1L)
        );

        assertNotNull(thrown);
        verify(repositoryBadge).existsById(anyLong());
        verify(repositoryBadge, never()).findById(anyLong());
    }

    @Test //200
    void getAllBadges_success(){
        when(repositoryBadge.findAll()).thenReturn(listaBadges);

        List<Badge> result = badgeService.getAllBadges();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(listaBadges, result);

        verify(repositoryBadge).findAll();
    }

    @Test //204
    void getAllBadges_isEmpty(){
        when(repositoryBadge.findAll()).thenReturn(new ArrayList<>());

        List<Badge> result = badgeService.getAllBadges();

        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        assertNotEquals(listaBadges, result);

        verify(repositoryBadge).findAll();        
    }

    @Test //200
    void getBadgesByEmployeeId_success(){
        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryBadge.findBadgesByEmployeeId(anyLong())).thenReturn(listaBadges);

        List<Badge> result = badgeService.getBadgesByEmployeeId(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(listaBadges, result);

        verify(repositoryUser).existsById(anyLong());
        verify(repositoryBadge).findBadgesByEmployeeId(anyLong());
    }

    @Test //404
    void getBadgesByEmployeeId_UserNotFound(){
        when(repositoryUser.existsById(anyLong())).thenReturn(false);
        
        UserNotFoundException thrown = assertThrows(
            UserNotFoundException.class,
            () -> badgeService.getBadgesByEmployeeId(1L)
        );

        assertNotNull(thrown);
        
        verify(repositoryUser).existsById(anyLong());
        verify(repositoryBadge, never()).findBadgesByEmployeeId(anyLong());
    }

    @Test //200
    void findByName_success(){
        when(repositoryBadge.findBadgeByName(anyString())).thenReturn(Optional.of(badge));

        Optional<Badge> result = badgeService.findByName(badge.getName());

        assertTrue(result.isPresent());
        assertEquals(badge.getName(), result.get().getName());

        verify(repositoryBadge).findBadgeByName(anyString());
    }

    @Test //404
    void findByName_BadgeNotFound(){
        when(repositoryBadge.findBadgeByName(anyString())).thenReturn(Optional.empty());

        BadgeNotFoundException thrown = assertThrows(
            BadgeNotFoundException.class,
            () -> badgeService.findByName(badge.getName())
        );

        assertNotNull(thrown);

        verify(repositoryBadge).findBadgeByName(anyString());
    }

}