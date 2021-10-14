package com.trees.treeSave.services;

import com.trees.treeSave.Entity.Categoria;
import com.trees.treeSave.excepciones.WebException;
import com.trees.treeSave.repositories.CategoriaRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Fede
 */
@Service
public class CategoriaService {

    
    @Autowired(required = true)
    private CategoriaRepository categoriaRepository;
    
    public Categoria save(Categoria categoria) throws WebException {
        validar(categoria);
        
       return categoriaRepository.save(categoria);
    }
    
    private void validar(Categoria categoria) throws WebException {
        if(categoria.getNombreCat() == null || categoria.getNombreCat().isEmpty()) {
            throw new WebException("La categoría debe llevar nombre");
        }
    }
    
    public List<Categoria> listAll() {
        return categoriaRepository.findAll();
    }

    public List<Categoria> listAllByQ(String q) {
        return categoriaRepository.findAllByQ("%" + q + "%");
    }

    @Transactional
    public void modificarCategoria(String id, String nombreCat) {
        Optional<Categoria> respuesta = categoriaRepository.findById(id);
        if (respuesta.isPresent()) {
            Categoria categoria = respuesta.get();
            categoria.setNombreCat(nombreCat);
            categoriaRepository.save(categoria);
        }
    }

    public Optional<Categoria> findById(String id) {
        return categoriaRepository.findById(id);
    }

    public Categoria findById(Categoria categoria) {
        Optional<Categoria> optional = categoriaRepository.findById(categoria.getId());
        if (optional.isPresent()) {
            categoria = optional.get();
        }
        return categoria;
    }

    @Transactional
    public void delete(Categoria categoria) {
        categoriaRepository.delete(categoria);
    }

    @Transactional
    public void deleteById(String id) {
        Optional<Categoria> optional = categoriaRepository.findById(id);
        if (optional.isPresent()) {
            Categoria categoria = optional.get();
            categoriaRepository.delete(categoria);
        }
    }

}