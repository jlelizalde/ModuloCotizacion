package cotizacion;

import java.sql.SQLException;

public class InsumoCotizacion{
	private int idCotizacion;
	private int idInsumo;
	private int cant;
	
	public InsumoCotizacion(int idC,int idI,int c){
		this.setIdCotizacion(idC);
		this.setIdInsumo(idI);
		this.setCant(c);
	}
	public InsumoCotizacion(int idI,int c){
		this.setIdInsumo(idI);
		this.setCant(c);
	}

	public int getIdInsumo() {
		return idInsumo;
	}

	public void setIdInsumo(int idInsumo) {
		this.idInsumo = idInsumo;
	}

	public int getCant() {
		return cant;
	}

	public void setCant(int cant) {
		this.cant = cant;
	}
	
	public int getIdCotizacion() {
		return idCotizacion;
	}
	
	public void setIdCotizacion(int idC){
		this.idCotizacion = idC;
	}
	
	// MÉTODO PARA GUARDAR EN LA BD
	public void registrarInsumoCotizacion(int idCotizacion, int idInsumo, int numInsumos, BDM bdm ) throws SQLException{
		// Validaciones de los componentes
		String query = "INSERT INTO taller2int.cotinsumo (idCotizacion, idInsumo, numeroinsumos) ";
		       query += "VALUES ("+idCotizacion+","+idInsumo+","+numInsumos+")";
		bdm.getSt().executeUpdate(query);		
	}

}
