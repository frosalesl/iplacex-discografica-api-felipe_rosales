package org.iplacex.proyectos.discografia.artistas;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {

    @Autowired
    private IArtistaRepository artistaRepo;

    // Crear artista
    @PostMapping(
        value = "/artista",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleInsertArtistaRequest(
            @RequestBody Artista artista) {

        try {

            Artista artistaGuardado = artistaRepo.save(artista);

            return new ResponseEntity<>(
                    artistaGuardado,
                    HttpStatus.CREATED);

        } catch (Exception e) {

            return new ResponseEntity<>(
                    "Error al crear el artista",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener todos los artistas
    @GetMapping(
        value = "/artistas",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Artista>> HandleGetArtistasRequest() {

        try {

            List<Artista> artistas = artistaRepo.findAll();

            return new ResponseEntity<>(
                    artistas,
                    HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener artista por ID
    @GetMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleGetArtistaRequest(
            @PathVariable String id) {

        try {

            Optional<Artista> artista = artistaRepo.findById(id);

            if (artista.isPresent()) {

                return new ResponseEntity<>(
                        artista.get(),
                        HttpStatus.OK);
            }

            return new ResponseEntity<>(
                    "Artista no encontrado",
                    HttpStatus.NOT_FOUND);

        } catch (Exception e) {

            return new ResponseEntity<>(
                    "Error al obtener el artista",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar artista
    @PutMapping(
        value = "/artista/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleUpdateArtistaRequest(
            @PathVariable String id,
            @RequestBody Artista artista) {

        try {

            // Verificar si el artista existe
            if (!artistaRepo.existsById(id)) {

                return new ResponseEntity<>(
                        "Artista no encontrado",
                        HttpStatus.NOT_FOUND);
            }

            artista._id = id;

            Artista artistaActualizado = artistaRepo.save(artista);

            return new ResponseEntity<>(
                    artistaActualizado,
                    HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(
                    "Error al actualizar el artista",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar artista
    @DeleteMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleDeleteArtistaRequest(
            @PathVariable String id) {

        try {

            // Verificar si el artista existe
            if (!artistaRepo.existsById(id)) {

                return new ResponseEntity<>(
                        "Artista no encontrado",
                        HttpStatus.NOT_FOUND);
            }

            Optional<Artista> artista = artistaRepo.findById(id);

            artistaRepo.deleteById(id);

            return new ResponseEntity<>(
                    artista.get(),
                    HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(
                    "Error al eliminar el artista",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}