package cotizacion;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Cotizacion {
	private int idCotizacion;
	private int numPax;
	private Float totalPax;
	private Float costoTotal;
	private String observaciones;
	private String fechaCotizacion;
	
	public Cotizacion(int np, Float tp, Float cT, String ob, String fecha){
		this.numPax = np;
		this.totalPax = tp;
		this.costoTotal = cT;
		this.observaciones = ob;
		this.fechaCotizacion = fecha;		
	}

	public int getNumPax() {
		return numPax;
	}

	public void setNumPax(int numPax) {
		this.numPax = numPax;
	}

	public Float getTotalPax() {
		return totalPax;
	}

	public void setTotalPax(Float totalPax) {
		this.totalPax = totalPax;
	}

	public Float getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(Float costoTotal) {
		this.costoTotal = costoTotal;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getFechaCotizacion() {
		return fechaCotizacion;
	}

	public void setFechaCotizacion(String fechaCotizacion) {
		this.fechaCotizacion = fechaCotizacion;
	}

	public int getIdCotizacion(BDM bdm) throws SQLException {			
		ResultSet aux;		
		String query = "Select MAX(idcotizacion) from taller2int.cotizar";		
		aux = bdm.getSt().executeQuery(query);
		 while(aux.next()){
				idCotizacion = ((Integer)aux.getObject(1));
		 }
		return idCotizacion;
	}
	
	// Registra la cotización en la BD
	public void registrarCotizacion(int numPax, Float totalPax, Float costo, String observaciones, String fecha, BDM bdm) throws SQLException{
		// Validaciones de los componentes
		String query = "INSERT INTO taller2int.cotizar (numpax, totalpax, costototal, estado, observaciones, fechacotizar) ";
			   query += "VALUES ("+numPax+","+totalPax+","+costo+",0,'"+observaciones+"','"+fecha+"')";
			   //System.out.println(query);
		bdm.getSt().executeUpdate(query);		
	}
	

}
