package com.trees.treeSave.services;

import com.trees.treeSave.Entity.Foto;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.repositories.FotoRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Fede
 */
@Service
public class FotoService {

    @Autowired
    private FotoRepository fotoRepository;

    @Transactional
    public Foto save(MultipartFile file) throws WebException {
        if (file != null) {
            try {
                Foto foto = new Foto();
                foto.setMime(file.getContentType());
                foto.setNombre(file.getName());
                foto.setContenido(file.getBytes());

                return fotoRepository.save(foto);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
    
    @Transactional
    public Foto actualizar(String idFoto, MultipartFile file) {
        if (file != null) {
            try {
                Foto foto = new Foto();
                if(idFoto != null) {
                    Optional<Foto> respuesta = fotoRepository.findById(idFoto);
                    if(respuesta.isPresent()) {
                        foto = respuesta.get();
                    }
                }
                foto.setMime(file.getContentType());
                foto.setNombre(file.getName());
                foto.setContenido(file.getBytes());

                return fotoRepository.save(foto);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
}
