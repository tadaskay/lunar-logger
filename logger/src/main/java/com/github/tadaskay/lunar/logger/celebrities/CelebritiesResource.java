package com.github.tadaskay.lunar.logger.celebrities;

import com.github.tadaskay.lunar.logger.api.CelebrityRepresentation;
import com.github.tadaskay.lunar.logger.api.RegisterCelebritiesRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class CelebritiesResource {

    private final CelebritiesRegistrar registrar;

    CelebritiesResource(CelebritiesRegistrar registrar) {
        this.registrar = registrar;
    }

    @GetMapping("/urls/{id}/celebrities")
    public List<CelebrityRepresentation> list(@PathVariable("id") String urlId) {
        return registrar.list(urlId)
            .stream()
            .map(CelebritiesResource::represent)
            .collect(toList());
    }

    @PutMapping("/urls/{id}/celebrities")
    public ResponseEntity<Void> put(@PathVariable("id") String urlId,
                                    @Valid @RequestBody RegisterCelebritiesRequest request) {
        List<Celebrity> celebrities = Celebrity.createMany(request.getEntries());
        registrar.register(urlId, celebrities);
        return ResponseEntity.accepted().build();
    }

    public static CelebrityRepresentation represent(Celebrity entity) {
        CelebrityRepresentation rep = new CelebrityRepresentation();
        rep.setSceneName(entity.getSceneName());
        rep.setFirstName(entity.getFirstName());
        rep.setLastName(entity.getLastName());
        return rep;
    }
}
