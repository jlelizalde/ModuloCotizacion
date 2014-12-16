/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cotizacion;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shaiduck
 */
public class CotInsumo {
    
    public String tipoCatalogo;
   // public int idCotizacion;
    public List<String> nombreInsumo = new ArrayList<String>();
    public List<Integer> identificadores = new ArrayList<Integer>();
    public List<Integer> cantidad = new ArrayList<Integer>();
    public List<Double> precios = new ArrayList<Double>();
    
    public CotInsumo(String tipo)
    {
        this.tipoCatalogo = tipo;
    }
    
}
