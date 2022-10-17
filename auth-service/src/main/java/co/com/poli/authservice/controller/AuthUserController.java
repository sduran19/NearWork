package co.com.poli.authservice.controller;


import co.com.poli.authservice.dto.*;
import co.com.poli.authservice.entity.AuthUser;
import co.com.poli.authservice.helpers.ErrorMessage;
import co.com.poli.authservice.helpers.Response;
import co.com.poli.authservice.helpers.ResponseBuild;
import co.com.poli.authservice.service.AuthUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthUserController {

    @Autowired
    AuthUserService authUserService;

    @Autowired
    ResponseBuild responseBuild;

   /* @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody AuthUserDto dto){
        TokenDto tokenDto = authUserService.login(dto);
        if(tokenDto == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(tokenDto);
    }*/

    @PostMapping("/login")
    public Response login(@RequestBody AuthUserDto dto, BindingResult result){
        TokenDto tokenDto = authUserService.login(dto);
        if(result.hasErrors()){
            return responseBuild.failed(formatMessage(result));
        } else if (tokenDto == null) {
            return responseBuild.failed("Usuario o password no son correctos");
        }
        if(tokenDto.getLocked())
            return responseBuild.failed("Cambie password ingresada");
        return responseBuild.success(tokenDto);
    }

    /*@PostMapping("/validate")
    public ResponseEntity<TokenDto> validate(@RequestParam String token, @RequestBody RequestDto dto){
        TokenDto tokenDto = authUserService.validate(token, dto);
        if(tokenDto == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(tokenDto);
    }*/

    @PostMapping("/validate")
    public Response validate(@RequestParam String token, @RequestBody RequestDto dto, BindingResult result){
        TokenDto tokenDto = authUserService.validate(token, dto);
        if(result.hasErrors() || tokenDto == null)
            return responseBuild.failed(formatMessage(result));
        return responseBuild.success(tokenDto);
    }

    /*@PostMapping("/create")
    public ResponseEntity<AuthUser> create(@RequestBody NewUserDto dto){
        AuthUser authUser = authUserService.save(dto);
        if(authUser == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(authUser);
    }*/

    @PostMapping("/create")
    public Response create(@RequestBody NewUserDto dto, BindingResult result){
        AuthUser authUser = authUserService.save(dto);
        if(authUser == null)
            return responseBuild.failed(formatMessage(result));
        return responseBuild.created(authUser);
    }

    /*@PostMapping("/reset")
    public ResponseEntity<AuthUser> resetPassword(@RequestBody ResetPassWordUserDto dto){
        AuthUser authUser = authUserService.resetPassword(dto);
        if(authUser == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(authUser);
    }*/

    @PostMapping("/resetPassword")
    public Response resetPassword(@RequestBody ResetPassWordUserDto dto, BindingResult result){
        AuthUser authUser = authUserService.resetPassword(dto);
        if(authUser == null)
            return responseBuild.failed(formatMessage(result));
        return responseBuild.success(authUser);
    }

    @PostMapping("/changePassword")
    public Response changePassword(@RequestBody ChangePasswordUserDto dto){
        AuthUser authUser = authUserService.changePassword(dto);
        if(authUser == null)
            return responseBuild.failed("Error al validar los datos. Intente de nuevo");
        return responseBuild.success(authUser);
    }


    private String formatMessage(BindingResult result) {
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(error -> {
                    Map<String, String> err = new HashMap<>();
                    err.put(error.getField(), error.getDefaultMessage());
                    return err;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .error(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return json;
    }
}
