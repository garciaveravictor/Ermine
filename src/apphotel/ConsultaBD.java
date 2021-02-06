/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apphotel;

import entidades.Cliente;
import entidades.Provincia;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author UsuarioDAM
 */
public class ConsultaBD {
    
    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AppHotelPU");
        EntityManager em = emf.createEntityManager();
        
        
        Query queryProvincias = em.createNamedQuery("Provincia.findAll");
        List<Provincia> listProvincias = queryProvincias.getResultList();

        // Muestra el nombre de todas las tablas provincias
        for (Provincia provincia : listProvincias) {
            System.out.println(provincia.getId()+provincia.getNombre()+provincia.getLocalidad());
            System.out.println();
           
        }
        
        Query q = em.createNamedQuery("Cliente.findAll");
        List<Cliente> list = q.getResultList();


        // Muestra el nombre de todas las tablas provincias
        for (Cliente provincia : list) {
            if(provincia.getDni() == null)
            {
                System.out.println("Error");
            }
            System.out.println(provincia.getNombre());
            System.out.println(provincia.getDni());
        }
        
    }
    
}