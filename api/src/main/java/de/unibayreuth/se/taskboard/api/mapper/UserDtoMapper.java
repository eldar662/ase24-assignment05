package de.unibayreuth.se.taskboard.api.mapper;

import de.unibayreuth.se.taskboard.api.dtos.UserDto;
import de.unibayreuth.se.taskboard.business.domain.User;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Mapper(componentModel = "spring")
@ConditionalOnMissingBean // prevent IntelliJ warning about duplicate beans
@NoArgsConstructor
public abstract class UserDtoMapper {
    public abstract UserDto fromBusiness(User source);

    //TODO: Fix this mapper after resolving the other TODOs.
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    //@Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdAt", expression = "java(mapTimestamp(source.getCreatedAt()))")
    public abstract User toBusiness(UserDto source);

    protected LocalDateTime mapTimestamp (LocalDateTime timestamp) {
        if (timestamp == null) {
            return LocalDateTime.now(ZoneId.of("UTC"));
        }
        return timestamp;
    }

    public UserDto optionalToDto(Optional<User> source) { return source.map(this::fromBusiness).orElse(null);}
}
