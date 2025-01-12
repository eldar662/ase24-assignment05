package de.unibayreuth.se.taskboard.business.impl;

import de.unibayreuth.se.taskboard.business.domain.User;
import de.unibayreuth.se.taskboard.business.exceptions.DuplicateNameException;
import de.unibayreuth.se.taskboard.business.exceptions.MalformedRequestException;
import de.unibayreuth.se.taskboard.business.exceptions.UserNotFoundException;
import de.unibayreuth.se.taskboard.business.ports.UserPersistenceService;
import de.unibayreuth.se.taskboard.business.ports.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import de.unibayreuth.se.taskboard.business.exceptions.TaskNotFoundException;

import java.util.List;
import java.util.UUID;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserPersistenceService userPersistenceService;

    public User create(@NonNull User user) throws MalformedRequestException {
        if (user.getId() != null) {
            throw new MalformedRequestException("Task ID must not be set.");
        }
        return upsert(user);
    }

    @Override
    public void clear() {
        userPersistenceService.clear();
    }

    @Override
    @NonNull
    public List<User> getAll() {
        return userPersistenceService.getAll();
    }

    @Override
    public Optional<User> getById(UUID id) throws TaskNotFoundException {
        return userPersistenceService.getById(id);
    }

    @Override
    @NonNull
    public User upsert(@NonNull User user) throws DuplicateNameException, UserNotFoundException {
        return userPersistenceService.upsert(user);
    }


}
