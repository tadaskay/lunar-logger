package com.github.tadaskay.lunar.logger.remotekey;

import com.github.tadaskay.lunar.logger.api.dto.RegisterRemoteKeyRequest;
import com.github.tadaskay.lunar.logger.api.dto.RemoteKeyRepresentation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RemoteKeyResource {

    private final RemoteKeyRegistrar registrar;

    RemoteKeyResource(RemoteKeyRegistrar registrar) {
        this.registrar = registrar;
    }

    @GetMapping("/api/urls/{id}/remote-key")
    public RemoteKey get(@PathVariable("id") String urlId) {
        return registrar.get(urlId);
    }

    @ApiResponses({
        @ApiResponse(code = 202, message = "Accepted"),
        @ApiResponse(code = 404, message = "URL not found"),
        @ApiResponse(code = 422, message = "Already crawled")
    })
    @PutMapping("/api/urls/{id}/remote-key")
    public ResponseEntity<Void> put(@PathVariable("id") String urlId,
                                    @Valid @RequestBody RegisterRemoteKeyRequest req) {
        registrar.register(urlId, new RemoteKey(req.getRemoteKey()));
        return ResponseEntity.accepted().build();
    }

    public static RemoteKeyRepresentation represent(RemoteKey entity) {
        return new RemoteKeyRepresentation(entity.getValue());
    }
}
