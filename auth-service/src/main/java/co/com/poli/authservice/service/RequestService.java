package co.com.poli.authservice.service;

import co.com.poli.authservice.dto.*;
import co.com.poli.authservice.entity.AuthUser;
import co.com.poli.authservice.entity.Profile;
import co.com.poli.authservice.entity.Request;
import co.com.poli.authservice.repository.AuthUserRepository;
import co.com.poli.authservice.repository.ProfileRepository;
import co.com.poli.authservice.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    RequestRepository requestRepository;

    public Request save(RequestServiceDto dto) {
        Optional<AuthUser> user = authUserRepository.findByEmail(dto.getEmailClient());
        Optional<Profile> profile = profileRepository.findById(dto.getIdProfile());
        if(user.isEmpty() || profile.isEmpty())
            return null;
        Request request = Request.builder()
                .idClient(user.get().getId())
                .idWorker(profile.get().getIdUser())
                .idProfile(profile.get().getId())
                .comment(dto.getComment())
                .state(1)
                .build();
        return requestRepository.save(request);
    }

    public Request updateState(UpdateStateRequest dto) {
        Request request = requestRepository.findById(dto.getIdRequest()).get();
        if(request.equals(null))
            return null;
        request.setState(dto.getState());
        return requestRepository.save(request);
    }

    public List<ServiceWorker> getListServiceWorker(String email) {
        List<ServiceWorker> serviceWorkerList = new ArrayList<>();
        Optional<AuthUser> user = authUserRepository.findByEmail(email);
        if(user.isEmpty())
            return null;
        List<Request> requests = requestRepository.findAllByIdWorker(user.get().getId());
        for (Request request: requests) {
            AuthUser userClient = authUserRepository.findById(request.getIdClient()).get();
            ServiceWorker serviceWorker = ServiceWorker.builder()
                    .idRequest(request.getId())
                    .fullName(userClient.getName() + " " + userClient.getLastName())
                    .cellPhone(userClient.getCellPhone())
                    .emailClient(userClient.getEmail())
                    .comment(request.getComment())
                    .state(request.getState())
                    .build();
            serviceWorkerList.add(serviceWorker);
        }
        return serviceWorkerList;
    }

    public List<MyServices> getListServiceClient(String email) {
        List<MyServices> myServicesList = new ArrayList<>();
        Optional<AuthUser> user = authUserRepository.findByEmail(email);
        if(user.isEmpty())
            return null;
        List<Request> requests = requestRepository.findAllByIdClient(user.get().getId());
        for (Request request: requests) {
            AuthUser userClient = authUserRepository.findById(request.getIdWorker()).get();
            Profile profile = profileRepository.findById(request.getIdProfile()).get();
            MyServices myServices = MyServices.builder()
                    .idRequest(request.getId())
                    .fullNameWorker(userClient.getName() + " " + userClient.getLastName())
                    .typeService(profile.getTypeService())
                    .comment(request.getComment())
                    .state(request.getState())
                    .build();
            myServicesList.add(myServices);
        }
        return myServicesList;
    }

}
