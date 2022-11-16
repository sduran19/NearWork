package co.com.poli.authservice.controller;


import co.com.poli.authservice.dto.*;
import co.com.poli.authservice.entity.AuthUser;
import co.com.poli.authservice.entity.Image;
import co.com.poli.authservice.helpers.ErrorMessage;
import co.com.poli.authservice.helpers.Response;
import co.com.poli.authservice.helpers.ResponseBuild;
import co.com.poli.authservice.repository.ImageRepository;
import co.com.poli.authservice.service.AuthUserService;
import co.com.poli.authservice.utils.FileDownloadUtil;
import co.com.poli.authservice.utils.FileUploadUtil;
import co.com.poli.authservice.utils.ImageUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthUserController {

    private static final String EXTERNAL_FILE_PATH = "user-photos/1/";
    @Autowired
    AuthUserService authUserService;

    @Autowired
    ResponseBuild responseBuild;

    @Autowired
    ImageRepository imageRepository;

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

    @GetMapping("/view/profile/{email}")
    public Response viewProfileUser(@PathVariable("email") String email){
        UserInfoDto authUser = authUserService.getInfoUser(email);
        if(authUser == null)
            return responseBuild.failed("NO EXISTE");
        return responseBuild.success(authUser);
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

    @PostMapping("/upload/image/{email}")
    public Response uplaodImage(@PathVariable("email") String email,@RequestParam("image") MultipartFile file) throws IOException {
        AuthUser authUser = authUserService.getAuthUser(email);
        if (imageRepository.findByIdUser(authUser.getId()).isPresent()){
            Image image = imageRepository.findByIdUser(authUser.getId()).get();
            image.setType(file.getContentType());
            image.setName(file.getOriginalFilename());
            image.setImage(ImageUtility.compressImage(file.getBytes()));
            imageRepository.save(image);
        }else {
            imageRepository.save(Image.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .idUser(authUser.getId())
                    .image(ImageUtility.compressImage(file.getBytes())).build());
        }

        return responseBuild.success("Image uploaded successfully: " + file.getOriginalFilename());
    }

    @GetMapping("/profile/get/image/{email}")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable("email") String email) {
        AuthUser authUser = authUserService.getAuthUser(email);
        final Optional<Image> dbImage = imageRepository.findByIdUser(authUser.getId());
        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(dbImage.get().getType()))
                .body(ImageUtility.decompressImage(dbImage.get().getImage()));
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
