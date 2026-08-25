package org.iplacex.proyectos.discografia.discos;

import java.util.List;
import java.util.Optional;

import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepo;

    @Autowired
    private IArtistaRepository artistaRepo;


    // Crear disco
    @PostMapping(
        value = "/disco",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandlePostDiscoRequest(
        @RequestBody Disco disco
    ) {
        try {

            // Verificar si el artista existe
            if (!artistaRepo.existsById(disco.idArtista)) {
                return new ResponseEntity<>(
                    "Artista no encontrado",
                    HttpStatus.NOT_FOUND
                );
            }

            Disco discoGuardado = discoRepo.save(disco);

            return new ResponseEntity<>(
                discoGuardado,
                HttpStatus.CREATED
            );

        } catch (Exception e) {

            return new ResponseEntity<>(
                "Error al crear el disco",
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


    // Actualizar disco
    @PutMapping(
        value = "/disco/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandlePutDiscoRequest(
        @PathVariable String id,
        @RequestBody Disco disco
    ) {
        try {

            Optional<Disco> discoExistente = discoRepo.findById(id);

            if (!discoExistente.isPresent()) {
                return new ResponseEntity<>(
                    "Disco no encontrado",
                    HttpStatus.NOT_FOUND
                );
            }

            // Verificar si el artista existe
            if (!artistaRepo.existsById(disco.idArtista)) {
                return new ResponseEntity<>(
                    "Artista no encontrado",
                    HttpStatus.NOT_FOUND
                );
            }

            // Aseguramos que se actualice el disco correcto
            disco._id = id;

            Disco discoActualizado = discoRepo.save(disco);

            return new ResponseEntity<>(
                discoActualizado,
                HttpStatus.OK
            );

        } catch (Exception e) {

            return new ResponseEntity<>(
                "Error al actualizar el disco",
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


    // Obtener todos los discos
    @GetMapping(
        value = "/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {

        try {

            List<Disco> discos = discoRepo.findAll();

            return new ResponseEntity<>(
                discos,
                HttpStatus.OK
            );

        } catch (Exception e) {

            return new ResponseEntity<>(
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


    // Obtener disco por ID
    @GetMapping(
        value = "/disco/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleGetDiscoRequest(
        @PathVariable String id
    ) {

        try {

            Optional<Disco> disco = discoRepo.findById(id);

            if (disco.isPresent()) {

                return new ResponseEntity<>(
                    disco.get(),
                    HttpStatus.OK
                );
            }

            return new ResponseEntity<>(
                "Disco no encontrado",
                HttpStatus.NOT_FOUND
            );

        } catch (Exception e) {

            return new ResponseEntity<>(
                "Error al encontrar el disco",
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }


    // Obtener discos por artista
    @GetMapping(
        value = "/artista/{id}/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(
        @PathVariable String id
    ) {

        try {

            List<Disco> discos = discoRepo.findDiscosByIdArtista(id);

            return new ResponseEntity<>(
                discos,
                HttpStatus.OK
            );

        } catch (Exception e) {

            return new ResponseEntity<>(
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

}