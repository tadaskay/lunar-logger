package com.github.tadaskay.lunar.logger.remotekey;

import com.github.tadaskay.lunar.logger.api.RegisterRemoteKeyRequest;
import com.github.tadaskay.lunar.logger.api.RemoteKeyRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RemoteKeyResource {

    private final RemoteKeyRegistrar registrar;

    RemoteKeyResource(RemoteKeyRegistrar registrar) {
        this.registrar = registrar;
    }

    @GetMapping("/urls/{id}/remote-key")
    public RemoteKey get(@PathVariable("id") String urlId) {
        return registrar.get(urlId);
    }

    @PutMapping("/urls/{id}/remote-key")
    public ResponseEntity<Void> put(@PathVariable("id") String urlId,
                                    @Valid @RequestBody RegisterRemoteKeyRequest req) {
        registrar.register(urlId, new RemoteKey(req.getRemoteKey()));
        return ResponseEntity.accepted().build();
    }

    public static RemoteKeyRepresentation represent(RemoteKey entity) {
        RemoteKeyRepresentation rep = new RemoteKeyRepresentation();
        rep.setValue(entity.getValue());
        return rep;
    }
}
