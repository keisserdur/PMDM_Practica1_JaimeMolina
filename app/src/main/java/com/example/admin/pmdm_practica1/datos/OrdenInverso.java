package com.example.admin.pmdm_practica1.datos;

import java.util.Comparator;

/**
 * Created by Admin on 18/10/2015.
 */
public class OrdenInverso implements Comparator<persona> {
    @Override
    public int compare(persona lhs, persona rhs) {
        //Ordena los valores que recibe de forma inversa a lo habitual
        int r =lhs.getNombre().compareToIgnoreCase(rhs.getNombre());
        if(r==0){
            r= (int) (lhs.getId() -lhs.getId());
        }else if(r<0){
            r=1;
        }else{
            r=-1;
        }

        return r;
    }
}
