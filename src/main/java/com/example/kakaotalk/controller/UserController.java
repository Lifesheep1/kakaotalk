package com.example.kakaotalk.controller;

import com.example.kakaotalk.dto.UserRegisterRequest;
import com.example.kakaotalk.dto.UserResponse;
import com.example.kakaotalk.entity.User;
import com.example.kakaotalk.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 신규 사용자 등록
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<UserResponse> register(@RequestBody UserRegisterRequest request){
        User created = userService.register(
                request.username(),
                request.email(),
                request.password()
        );
        UserResponse response = UserResponse.fromEntity(created);
        return ResponseEntity.created(URI.create("/api/users/" + created.getUserId())).body(response);
    }

    /**
     * 모든 사용자 목록 조회
     * @return
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll(){
        List<UserResponse> list = userService.findAll().stream()
                .map(UserResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    /**
     * 단일 사용자 조회
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id){
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(UserResponse.fromEntity(user)))
                .orElse(ResponseEntity.notFound().build());
    }

}
