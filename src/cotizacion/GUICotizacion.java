package cotizacion;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class GUICotizacion extends JFrame {
	private JTextArea TAObservaciones;
	private JLabel lblNotas;
	private JSpinner m_numberSpinner;
	private Float totalPax;
	private PanelPestaña2 panelPestañas;
	private BDM bdm;
	private Cotizacion cot;
	private InsumoCotizacion iCot;
	
		
	// Constructor
	public GUICotizacion(String titulo) {
		super (titulo);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(1200, 500);
		this.setResizable(true);
		this.addComponents();
		this.setVisible(true);
		bdm= new BDM();
	};	

	// Se crean y agregan los componentes al Frame Principal
	private void addComponents() {
		
		Component panelSuperior = creaPanelSuperior();		
		this.add(panelSuperior, BorderLayout.PAGE_START);
		
	//	Component panelDerecho = creaPanelDerecho();
	//	this.add(panelDerecho, BorderLayout.EAST);			
		
		Component panelCentral = creaPanelCentral();
		this.add(panelCentral, BorderLayout.CENTER);
		
		Component panelInferior = creaPanelInferior();
		this.add(panelInferior, BorderLayout.PAGE_END);	
	}
	
	// Panel superior: muestra sólo el título de la ventana
	private Component creaPanelSuperior() {
		
		JPanel infoCotizacion = new JPanel(new BorderLayout());		
		
		JPanel numPersonas = new JPanel(new FlowLayout(FlowLayout.LEADING));
		JLabel nPersonas = new JLabel("Número de personas: ");
		//JTextField cantPersonas = new JTextField();
		//cantPersonas.setPreferredSize(new Dimension(80,25));		
		numPersonas.add(nPersonas);
		//numPersonas.add(cantPersonas);		
		SpinnerNumberModel m_numberSpinnerModel;
	    Integer current = new Integer(10);
	    Integer min = new Integer(0);
	    Integer max = new Integer(10000);
	    Integer step = new Integer(10);
	    m_numberSpinnerModel = new SpinnerNumberModel(current, min, max, step);
	    m_numberSpinner = new JSpinner(m_numberSpinnerModel);
	    ((DefaultEditor) m_numberSpinner.getEditor()).getTextField().setEditable(false);
	    numPersonas.add(m_numberSpinner);
		infoCotizacion.add(numPersonas,BorderLayout.PAGE_START);
		
		JPanel panelObservaciones = new JPanel(new BorderLayout());		
		lblNotas = new JLabel("Notas: ");
		panelObservaciones.add(lblNotas, BorderLayout.PAGE_START);		
		
		TAObservaciones = new JTextArea(3,30);
		TAObservaciones.setEditable(true);
		TAObservaciones.setLineWrap(true);
		TAObservaciones.setWrapStyleWord(true);  // wrap line at word boundary	    
		TAObservaciones.setPreferredSize(new Dimension(200,80));
		TAObservaciones.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				if(TAObservaciones.getText().length()==400)
					e.consume();				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		JScrollPane scrollObservaciones = new JScrollPane(TAObservaciones);
		scrollObservaciones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		scrollObservaciones.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);		
		panelObservaciones.add(scrollObservaciones, BorderLayout.CENTER);
		
		infoCotizacion.add(panelObservaciones, BorderLayout.CENTER);
		
		return infoCotizacion;		
	}
	
	// Panel que contiene la tabla(s) con los insumos a seleccionar (Cristian)
	private Component creaPanelCentral() {
		
		JPanel centro = new JPanel(new BorderLayout());
	
		panelPestañas = new PanelPestaña2();
		centro.add(panelPestañas);
		
		return centro;
		
	}
		
	// Panel inferior: contiene únicamente el botón de registrar que crea la cotización
	private Component creaPanelInferior() {		
		JPanel pie = new JPanel(new FlowLayout(FlowLayout.CENTER));		
		
		
		JButton registrarCotizacion = new JButton("Registrar");
		registrarCotizacion.addActionListener(new ActionListener() {
			int idCotizacion=0;
			@Override
			public void actionPerformed(ActionEvent e) {
				totalPax = (float) (panelPestañas.total/Integer.parseInt(m_numberSpinner.getValue().toString()));								
				Calendar currentDate = Calendar.getInstance(); //Get the current date
				SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
				String dateNow = formatter.format(currentDate.getTime());				
				cot = null;
				cot = new Cotizacion(Integer.parseInt(m_numberSpinner.getValue().toString()), (Float)totalPax, Float.parseFloat(String.valueOf(panelPestañas.total)), TAObservaciones.getText(), dateNow);
								
				try { // Registra la cotización
					cot.registrarCotizacion(cot.getNumPax(), cot.getTotalPax(), cot.getCostoTotal(), cot.getObservaciones(), cot.getFechaCotizacion(), bdm);
					
					
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try{ // Obtiene el ID
					idCotizacion = cot.getIdCotizacion(bdm);
				} catch (Exception e2){
					e2.printStackTrace();
				}
				try{ // Guarda cada cotInsumo en su tabla
					// AQu+i va más código
					for (int i=0; i<panelPestañas.listaInsumos.size();i++){
						InsumoCotizacion insumoCot = new InsumoCotizacion(idCotizacion,panelPestañas.listaInsumos.get(i).getIdInsumo(),panelPestañas.listaInsumos.get(i).getCant());
						insumoCot.registrarInsumoCotizacion(insumoCot.getIdCotizacion(), insumoCot.getIdInsumo(), insumoCot.getCant(), bdm);
					}
					
					
				} catch (Exception e3){
					e3.printStackTrace();
				}
				
				
			}
		});
		pie.add(registrarCotizacion);
		//this.add(pie, BorderLayout.PAGE_END);
		return pie;
	}
	
	

	


}
