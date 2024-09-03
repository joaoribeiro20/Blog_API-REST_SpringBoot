package com.api.blog.portfolio.blogApi.services.publication;


import com.api.blog.portfolio.blogApi.controllers.publication.dtos.RequestPublicationDto;
import com.api.blog.portfolio.blogApi.controllers.publication.dtos.RequestUpdatePublicationDto;
import com.api.blog.portfolio.blogApi.controllers.publication.dtos.ResponsePublicationFeedDto;
import com.api.blog.portfolio.blogApi.controllers.publication.dtos.ResponseUpdatePublicationDto;
import com.api.blog.portfolio.blogApi.entities.Publication;
import com.api.blog.portfolio.blogApi.entities.user.User;
import com.api.blog.portfolio.blogApi.infra.exception.genericExceptions.ResourceNotFoundException;
import com.api.blog.portfolio.blogApi.infra.exception.genericExceptions.UnauthorizedAccessException;
import com.api.blog.portfolio.blogApi.infra.security.SecurityFilter;
import com.api.blog.portfolio.blogApi.infra.security.TokenService;
import com.api.blog.portfolio.blogApi.repositories.publication.PublicationRepositorie;
import com.api.blog.portfolio.blogApi.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublicationService {

    @Autowired
    PublicationRepositorie publicationRepositorie;
    @Autowired
    SecurityFilter securityFilter;



    //CRUD
    public Publication createNewPublication(RequestPublicationDto data, String token) {
        User user = securityFilter.authToken(token);
        Publication publication = new Publication(data, user);
        publicationRepositorie.save(publication);

        return publication;
    }
    public Publication viewPublication(String id)  {
        Optional<Publication> rest = publicationRepositorie.findById(id);
        if (rest.isPresent()) {
            return rest.get();
        } else {
            throw new ResourceNotFoundException();
        }
    }
    public Publication Update(RequestUpdatePublicationDto dataUpdate, String token) {
        User user = securityFilter.authToken(token);

        // Verificar se a publicação pertence ao usuário autenticado
        Optional<Publication> rest = user.getPublications().stream()
                .filter(publication -> publication.getId().equals(dataUpdate.id()))
                .findFirst();

        if (!rest.isPresent()) {
            throw new UnauthorizedAccessException();
        }
        Publication publication = rest.get();
        if (dataUpdate.title() != null && !dataUpdate.title().trim().isEmpty()) {
            publication.setTitle(dataUpdate.title());
        }
        if (dataUpdate.content() != null && !dataUpdate.content().trim().isEmpty()) {
            publication.setContent(dataUpdate.content());
        }
        if (dataUpdate.description() != null && !dataUpdate.description().trim().isEmpty()) {
            publication.setDescription(dataUpdate.description());
        }
        if (dataUpdate.subjects() != null && !dataUpdate.subjects().isEmpty()) {
            publication.setSubjects(dataUpdate.subjects());
        }
        publicationRepositorie.save(publication);
        return publication;
    }
    public void delete(String publicationID ,String token) throws Exception {
        User user = securityFilter.authToken(token);

        Optional<Publication> rest = user.getPublications().stream()
                .filter(publication -> publication.getId().equals(publicationID))
                .findFirst();

        if (!rest.isPresent()) {
            throw new UnauthorizedAccessException();
        }
         publicationRepositorie.delete(rest.get());
    }

    //EXTRAS
    public List<Publication> feedPublication() {
        return publicationRepositorie.findAll();
    }
}
