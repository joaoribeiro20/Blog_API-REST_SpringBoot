package com.api.blog.portfolio.blogApi.controllers.publication;

import com.api.blog.portfolio.blogApi.controllers.publication.dtos.*;
import com.api.blog.portfolio.blogApi.controllers.user.dtos.ResponseUpdateUserDto;
import com.api.blog.portfolio.blogApi.entities.Publication;
import com.api.blog.portfolio.blogApi.services.publication.PublicationService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publication")
public class PublicationController {

    @Autowired
    PublicationService publicationService;

    @PostMapping()
    @Transactional()
    public ResponseEntity createNewPublication(@RequestBody @Valid RequestPublicationDto data, @RequestHeader("Authorization") String authToken){
        var createPublication = publicationService.createNewPublication(data, authToken);
        return ResponseEntity.ok().body(new ResponsePublicationDto(createPublication));
    }
    @PutMapping
    public ResponseEntity updatePublication(@RequestBody @Valid RequestUpdatePublicationDto data, @RequestHeader("Authorization") String authToken) throws Exception {
        Publication rest = publicationService.Update(data, authToken);
        return ResponseEntity.ok().body(new ResponseUpdatePublicationDto(rest));
    }
    @GetMapping("/feed")
    public ResponseEntity<List<ResponsePublicationFeedDto>> feedPublication() {
        List<ResponsePublicationFeedDto> responseDtos = publicationService.feedPublication().stream()
                .map(ResponsePublicationFeedDto::new)
                .toList();
        return ResponseEntity.ok().body(responseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity viewPublication(@PathVariable String id) throws Exception {
        Publication res = publicationService.viewPublication(id);
        return ResponseEntity.ok().body(new ResponsePublicationViewDto(res));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id, @RequestHeader("Authorization") String authToken) throws Exception {
        publicationService.delete(id,authToken);
        return ResponseEntity.ok().body("Publicação deletada com suceso");
    }
}
