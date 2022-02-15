/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cursoegg.ejemplopersonas.repositories;

import com.cursoegg.ejemplopersonas.entidades.Ciudad;
import com.cursoegg.ejemplopersonas.entidades.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author cuent
 */
@Repository
public interface FotoRepository extends JpaRepository<Foto, String> {
    
}
