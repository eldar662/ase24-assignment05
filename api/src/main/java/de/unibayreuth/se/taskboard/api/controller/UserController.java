package de.unibayreuth.se.taskboard.api.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import de.unibayreuth.se.taskboard.api.dtos.UserDto;
import de.unibayreuth.se.taskboard.api.mapper.UserDtoMapper;
import de.unibayreuth.se.taskboard.business.exceptions.UserNotFoundException;
import de.unibayreuth.se.taskboard.business.ports.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@OpenAPIDefinition(
        info = @Info(
                title = "TaskBoard",
                version = "0.0.1"
        )
)
@Tag(name = "Users")
@Controller
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    // TODO: Add GET /api/users endpoint to retrieve all users.
    // TODO: Add GET /api/users/{id} endpoint to retrieve a user by ID.
    // TODO: Add POST /api/users endpoint to create a new user based on a provided user DTO.
        private final UserService userService;
        private final UserDtoMapper userDtoMapper;

        @Operation(
                summary = "Get all users.",
                responses = {
                        @ApiResponse(
                                responseCode = "200",
                                content = @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(type = "array", implementation = UserDto.class)
                                ),
                                description = "All users as a JSON array."
                        )
                }
        )
        @GetMapping
        public ResponseEntity<List<UserDto>> getAll() {
                return ResponseEntity.ok(
                        userService.getAll().stream()
                                .map(userDtoMapper::fromBusiness)
                                .toList()
                );
        }

        @Operation(
                summary = "Get user by ID.",
                responses = {
                        @ApiResponse(
                                responseCode = "200",
                                content = @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = UserDto.class)
                                ),
                                description = "The user with the provided ID as a JSON object."
                        ),
                        @ApiResponse(
                                responseCode = "404",
                                description = "No user with the provided ID could be found."
                        )
                }
        )
        @GetMapping("/{id}")
        public ResponseEntity<UserDto> getById(@PathVariable UUID id) {
                try {
                        return ResponseEntity.ok(
                                userDtoMapper.optionalToDto(userService.getById(id))
                        );
                } catch (UserNotFoundException e) {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
                }
        }

        @Operation(
                summary = "Create a new user.",
                responses = {
                        @ApiResponse(
                                responseCode = "201",
                                content = @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = UserDto.class)
                                ),
                                description = "The newly created user as a JSON object."
                        ),
                        @ApiResponse(
                                responseCode = "400",
                                description = "Invalid user data provided."
                        )
                }
        )
        @PostMapping
        public ResponseEntity<UserDto> create(@RequestBody @Valid UserDto userDto) {
                try {
                        return ResponseEntity.status(HttpStatus.CREATED).body(
                                userDtoMapper.fromBusiness(
                                        userService.upsert(userDtoMapper.toBusiness(userDto))
                                )
                        );
                } catch (Exception e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
                }
        }
}
