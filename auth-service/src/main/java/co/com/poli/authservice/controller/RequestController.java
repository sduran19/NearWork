package co.com.poli.authservice.controller;

import co.com.poli.authservice.constants.DocumentTypes;
import co.com.poli.authservice.dto.*;
import co.com.poli.authservice.entity.Request;
import co.com.poli.authservice.helpers.ErrorMessage;
import co.com.poli.authservice.helpers.Response;
import co.com.poli.authservice.helpers.ResponseBuild;
import co.com.poli.authservice.service.RequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/request")
public class RequestController {

    @Autowired
    RequestService requestService;

    @Autowired
    ResponseBuild responseBuild;

    @PostMapping("/create")
    public Response create(@Valid @RequestBody RequestServiceDto dto, BindingResult result){
        Request request = requestService.save(dto);
        if(request == null)
            return responseBuild.failed(formatMessage(result));
        return responseBuild.created(request);
    }

    @PostMapping("/updateState")
    public Response updateStateRequest(@Valid @RequestBody UpdateStateRequest dto, BindingResult result){
        Request request = requestService.updateState(dto);
        if(request == null)
            return responseBuild.failed(formatMessage(result));
        return responseBuild.created(request);
    }

    @GetMapping("/list/myservices/{email}")
    public Response findAllListClientService(@PathVariable("email") String email){
        List<MyServices> myServicesList = requestService.getListServiceClient(email);
        if(myServicesList == null)
            return responseBuild.failed("ERROR CONSULTA");
        return responseBuild.success(myServicesList);
    }

    @GetMapping("/list/serviceWorker/{email}")
    public Response findAllListServicesWorker(@PathVariable("email") String email){
        List<ServiceWorker> serviceWorkerList = requestService.getListServiceWorker(email);
        if(serviceWorkerList == null)
            return responseBuild.failed("ERROR CONSULTA");
        return responseBuild.success(serviceWorkerList);
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
