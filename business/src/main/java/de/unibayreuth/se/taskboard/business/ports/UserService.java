package de.unibayreuth.se.taskboard.business.ports;

import de.unibayreuth.se.taskboard.business.domain.User;
import de.unibayreuth.se.taskboard.business.exceptions.DuplicateNameException;
import de.unibayreuth.se.taskboard.business.exceptions.MalformedRequestException;
import de.unibayreuth.se.taskboard.business.exceptions.TaskNotFoundException;
import de.unibayreuth.se.taskboard.business.exceptions.UserNotFoundException;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    //TODO: Add user service interface that the controller uses to interact with the business layer.
    //TODO: Implement the user service interface in the business layer, using the existing user persistence service.
    void clear();
    @NonNull
    User create(@NonNull User user) throws MalformedRequestException;
    @NonNull
    List<User> getAll();
    @NonNull
    Optional<User> getById(@NonNull UUID id) throws TaskNotFoundException;
    @NonNull
    User upsert(@NonNull User user) throws UserNotFoundException, DuplicateNameException;
}
