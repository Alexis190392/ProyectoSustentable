package com.trees.treeSave.Entity;

import javax.persistence.Entity;

@Entity
public class ProductoLista extends Producto{
    
    private String documento;

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
    
    
}