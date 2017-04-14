package com.github.tadaskay.lunar.logger.celebrities;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
class CelebritiesResource {

    private final CelebritiesRegistrar registrar;

    CelebritiesResource(CelebritiesRegistrar registrar) {
        this.registrar = registrar;
    }

    @GetMapping("/urls/{urlId}/celebrities")
    public List<Celebrity> list(@PathVariable("urlId") String urlId) {
        return registrar.list(urlId);
    }

    @PutMapping("/urls/{urlId}/celebrities")
    public ResponseEntity<Void> put(@PathVariable("urlId") String urlId,
                                    @Valid @RequestBody RegisterCelebritiesRequest request) {
        List<Celebrity> celebrities = Celebrity.createMany(request.getEntries());
        registrar.register(urlId, celebrities);
        return ResponseEntity.accepted().build();
    }
}
