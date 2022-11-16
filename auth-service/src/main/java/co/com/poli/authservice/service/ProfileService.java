package co.com.poli.authservice.service;

import co.com.poli.authservice.dto.ProfileDto;
import co.com.poli.authservice.entity.AuthUser;
import co.com.poli.authservice.entity.Profile;
import co.com.poli.authservice.repository.AuthUserRepository;
import co.com.poli.authservice.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    AuthUserRepository authUserRepository;

    public Profile save(ProfileDto dto) {
        Optional<AuthUser> user = authUserRepository.findByEmail(dto.getEmailUser());
        if(user.isEmpty())
            return null;
        Profile profile = Profile.builder()
                .idUser(user.get().getId())
                .typeService(dto.getTypeService())
                .commune(dto.getCommune())
                .profession(dto.getProfession())
                .yearsExperience(dto.getYearsExperience())
                .linkFacebook(dto.getLinkFacebook())
                .linkInstragram(dto.getLinkInstragram())
                .build();
        return profileRepository.save(profile);
    }

    public List<Profile> getListProfile(String email) {
        Optional<AuthUser> user = authUserRepository.findByEmail(email);
        if(user.isEmpty())
            return null;
        return profileRepository.findAllByIdUser(user.get().getId());
    }

    public Profile getProfile(int idProfile) {
        Optional<Profile> profile = profileRepository.findById(idProfile);
        if(profile.isEmpty())
            return null;
        return profile.get();
    }

    public Profile setCodeBriefcase(int idProfile,String codeBriefcase) {
        Profile profile = profileRepository.findById(idProfile).get();
        profile.setCodeBriefcase(codeBriefcase);
        return profileRepository.save(profile);
    }



}
