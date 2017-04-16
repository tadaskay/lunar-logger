package com.github.tadaskay.lunar.logger.celebrities;

import com.github.tadaskay.lunar.logger.api.dto.CelebrityRepresentation;
import com.github.tadaskay.lunar.logger.api.dto.RegisterCelebritiesRequest;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.HttpURLConnection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class CelebritiesResource {

    private final CelebritiesRegistrar registrar;

    CelebritiesResource(CelebritiesRegistrar registrar) {
        this.registrar = registrar;
    }

    @GetMapping("/api/urls/{id}/celebrities")
    public List<CelebrityRepresentation> list(@PathVariable("id") String urlId) {
        return registrar.list(urlId)
            .stream()
            .map(CelebritiesResource::represent)
            .collect(toList());
    }

    @ApiResponses({
        @ApiResponse(code = HttpURLConnection.HTTP_ACCEPTED, message = "Accepted"),
        @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "URL not found"),
    })
    @PutMapping("/api/urls/{id}/celebrities")
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
