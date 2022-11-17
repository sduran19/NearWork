package co.com.poli.authservice.controller;

import co.com.poli.authservice.helpers.ResponseBuild;
import co.com.poli.authservice.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/request")
public class RequestController {

    @Autowired
    RequestService requestService;

    @Autowired
    ResponseBuild responseBuild;


    //Terminar de crear los tres endpoint guardar, actualizar estado, obtener lista mis servicios cliente y obtener lista servicios trabajador
    //Agregar path en el gateway

}
