package cotizacion;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

// ESTE PANEL DEBERÍA LLEVAR TODA LA LÓGICA DE CRISTIAN
@SuppressWarnings("serial")
public class TablaInsumos extends JPanel {
	private JLabel lblNumPax, lblTotalPax, lblCostoTotal, lblObservaciones, lblFechaCotizacion;
	private JTextField tfNumPax, tfTotalPax, tfCostoTotal, tfObservaciones, tfFechaCotizacion;
	private Cotizacion cot;
	
	// Constructor
	public TablaInsumos(){
		this.setLayout(new GridLayout(5,2));
		this.addComponents();
	}

	// Inicializa los componentes dentro del panel principal
	private void addComponents() {
		lblNumPax = new JLabel("Numero de personas: ");
		lblTotalPax = new JLabel("Total de personas: ");
		lblCostoTotal = new JLabel("Costo: ");
		lblObservaciones = new JLabel("Observaciones: ");
		lblFechaCotizacion = new JLabel("Fecha de cotización: ");
		
		tfNumPax = new JTextField();
		tfTotalPax = new JTextField();
		tfCostoTotal = new JTextField();
		tfObservaciones = new JTextField();
		tfFechaCotizacion = new JTextField();
		
		this.add(lblNumPax);
		this.add(tfNumPax);
		this.add(lblTotalPax);
		this.add(tfTotalPax);
		this.add(lblCostoTotal);
		this.add(tfCostoTotal);
		this.add(lblObservaciones);
		this.add(tfObservaciones);
		this.add(lblFechaCotizacion);
		this.add(tfFechaCotizacion);		
	}
	
	
	
	
	 

	

}
