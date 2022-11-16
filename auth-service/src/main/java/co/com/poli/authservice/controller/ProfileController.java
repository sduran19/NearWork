package co.com.poli.authservice.controller;

import co.com.poli.authservice.dto.NewUserDto;
import co.com.poli.authservice.dto.ProfileDto;
import co.com.poli.authservice.entity.AuthUser;
import co.com.poli.authservice.entity.Profile;
import co.com.poli.authservice.helpers.ErrorMessage;
import co.com.poli.authservice.helpers.Response;
import co.com.poli.authservice.helpers.ResponseBuild;
import co.com.poli.authservice.service.AuthUserService;
import co.com.poli.authservice.service.ProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/perfil")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @Autowired
    ResponseBuild responseBuild;

    @PostMapping("/create")
    public Response create(@RequestBody ProfileDto dto, BindingResult result){
        Profile profile = profileService.save(dto);
        if(profile == null)
            return responseBuild.failed(formatMessage(result));
        return responseBuild.created(profile);
    }

    @GetMapping("/profile/list/{email}")
    public Response findAllListProfile(@PathVariable("email") String email, BindingResult result){
        List<Profile> listProfile = profileService.getListProfile(email);
        if(listProfile == null)
            return responseBuild.failed(formatMessage(result));
        return responseBuild.success(listProfile);
    }

    //comentario

    @GetMapping("/profile/{id}")
    public Response findAllList(@PathVariable("id") String email, BindingResult result){
        List<Profile> listProfile = profileService.getListProfile(email);
        if(listProfile == null)
            return responseBuild.failed(formatMessage(result));
        return responseBuild.success(listProfile);
    }

    //terminar las demas capas el perfil debe guardar el id de la persona por medio del correo
    //Pasar el guardado del documento a esta entidad y relacionarlo al campo

    //Crear otra entidad de la solicitud del usuario
    //A esa entidad relacionarle el perfil

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
