package com.trees.treeSave.services;

import com.trees.treeSave.Entity.Producto;
import com.trees.treeSave.Entity.ProductoLista;
import com.trees.treeSave.repositories.PLRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PLService {
    
    @Autowired
    private PLRepository plr;
    @Autowired
    private ProductoServicio ps;
    
    
    @Transactional
    public ProductoLista save(ProductoLista pl){
        return plr.save(pl);
    }
    
    
    public List<ProductoLista> listAll(String documento) {
        return plr.listaAllPL("%" + documento + "%");
    }
    
    @Transactional
    public void agregar(String documento, String sku){
        Producto p = ps.searchCod(sku);
        ProductoLista pl = new ProductoLista();
        pl.setDocumento(documento);
        pl.setCategoria(p.getCategoria());
        pl.setCodigoBarra(p.getCodigoBarra() + documento);
        pl.setNombre(p.getNombre());
        pl.setOrigen(p.getOrigen());
        pl.setPrecio(p.getPrecio());
        
        pl.setTipo(p.getTipo());
        pl.setPuntos(p.getPuntos());
        
        save(pl);
    }
    
    @Transactional
    public void delete(String documento, String sku){
        plr.delete(plr.forDelete("%"+documento+"%", "%"+sku+"%"));
    }   
    
    
    
    
}