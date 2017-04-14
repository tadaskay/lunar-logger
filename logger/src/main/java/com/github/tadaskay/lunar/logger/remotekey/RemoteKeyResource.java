package com.github.tadaskay.lunar.logger.remotekey;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
class RemoteKeyResource {

    private final RemoteKeyRegistrar registrar;

    RemoteKeyResource(RemoteKeyRegistrar registrar) {
        this.registrar = registrar;
    }

    @GetMapping("/urls/{urlId}/remote-key")
    public RemoteKey get(@PathVariable("urlId") String urlId) {
        return registrar.get(urlId);
    }

    @PutMapping("/urls/{urlId}/remote-key")
    public ResponseEntity<Void> put(@PathVariable("urlId") String urlId,
                                    @Valid @RequestBody RegisterRemoteKeyRequest req) {
        registrar.register(urlId, new RemoteKey(req.getRemoteKey()));
        return ResponseEntity.accepted().build();
    }

}
