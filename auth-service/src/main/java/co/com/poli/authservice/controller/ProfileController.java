package co.com.poli.authservice.controller;

import co.com.poli.authservice.dto.ListSearchDto;
import co.com.poli.authservice.dto.NewUserDto;
import co.com.poli.authservice.dto.ProfileDto;
import co.com.poli.authservice.dto.SearchServiceDto;
import co.com.poli.authservice.entity.AuthUser;
import co.com.poli.authservice.entity.Profile;
import co.com.poli.authservice.helpers.ErrorMessage;
import co.com.poli.authservice.helpers.Response;
import co.com.poli.authservice.helpers.ResponseBuild;
import co.com.poli.authservice.service.AuthUserService;
import co.com.poli.authservice.service.ProfileService;
import co.com.poli.authservice.utils.FileDownloadUtil;
import co.com.poli.authservice.utils.FileUploadUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @Autowired
    ResponseBuild responseBuild;

    @PostMapping("/create")
    public Response create(@Valid @RequestBody ProfileDto dto, BindingResult result){
        Profile profile = profileService.save(dto);
        if(profile == null)
            return responseBuild.failed(formatMessage(result));
        return responseBuild.created(profile);
    }

    @GetMapping("/list/{email}")
    public Response findAllListProfile(@PathVariable("email") String email){
        System.out.println("EMAIL: " + email);
        List<Profile> listProfile = profileService.getListProfile(email);
        if(listProfile == null)
            return responseBuild.failed("ERROR CONSULTA");
        return responseBuild.success(listProfile);
    }

    @GetMapping("/{id}")
    public Response findProfile(@PathVariable("id") int id){
        Profile profile = profileService.getProfile(id);
        if(profile == null)
            return responseBuild.failed("ERROR CONSULTA");
        return responseBuild.success(profile);
    }

    @PostMapping("/cv/upload/{idProfile}")
    public Response uploadProfileImage(@PathVariable("idProfile") int id,@RequestParam("file") MultipartFile multipartFile) throws IOException {
        Profile profile = profileService.getProfile(id);
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        //String uploadDir = "user-files/" + profile.getId();
        String uploadDir = "user-files";
        String nameFile = FileUploadUtil.saveFile(uploadDir, fileName,String.valueOf(profile.getId()), multipartFile);
        profile.setCodeBriefcase(nameFile);
        return responseBuild.success(profileService.update(profile));
    }

    @GetMapping("/cv/downloadFile/{idProfile}")
    public ResponseEntity<?> downloadFile(@PathVariable("idProfile") String fileCode) {
        FileDownloadUtil downloadUtil = new FileDownloadUtil();

        Resource resource = null;
        try {
            resource = downloadUtil.getFileAsResource(fileCode);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

    @PostMapping("/search")
    public Response search(@RequestBody SearchServiceDto dto, BindingResult result){
        List<ListSearchDto> searchDtos = profileService.listSearchServices(dto);
        if(searchDtos == null)
            return responseBuild.failed(formatMessage(result));
        return responseBuild.success(searchDtos);
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
