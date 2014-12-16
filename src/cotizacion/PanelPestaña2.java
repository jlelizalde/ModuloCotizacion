/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cotizacion;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JSpinner;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Shaiduck
 */
@SuppressWarnings("serial")
public class PanelPestaña2 extends JPanel{
    
    public int tamaño;
    public List<CotInsumo> listaCategorias = new ArrayList<CotInsumo>();
    public List<InsumoCotizacion> listaInsumos = new ArrayList<InsumoCotizacion>();
    public JTabbedPane panelPestaña = new JTabbedPane();   
    private final String[] titulos = {"Concepto","Cantidad","Precio Unitario","Total"};
	private DefaultTableModel dtm = new DefaultTableModel();
	private JTable tablaResumen = new JTable(dtm){		
		public boolean isCellEditable(int Row, int vColIndex){
			return false;
		}
	};
	JScrollPane scroll;
	public double total;
    
    public PanelPestaña2()
    {
    	this.setLayout(new BorderLayout());
        this.creaPestañas(this.buscaCategorias());
        this.add(panelPestaña, BorderLayout.CENTER);
        JButton agregar = this.agregaInsumos();
        this.add(agregar, BorderLayout.PAGE_END);
        this.setSize(400,tamaño*50);
        scroll = new JScrollPane(tablaResumen);
        this.add(scroll,BorderLayout.EAST);
    }
    
    public JPanel creaContenido(ResultSet llenado, String tipoCatalogo)
    {
    CotInsumo contenedor = new CotInsumo(tipoCatalogo);
	String[] columnas = {"Cantidad", "Nombre", "Descripción", "Forma", "Tamaño", "Color", "Cintilla", "Precio"};
	int size = 0;
	JPanel contenido = new JPanel();
	contenido.setLayout(new GridLayout(0, 8));
	for(int i=0; i<columnas.length; i++)
	{
            contenido.add(new JLabel(columnas[i]));
	}
        try
        {
            while (llenado.next())
            {
		//La selección debe ser:
		//Select id, nombre, descripcion, tipoCatalogo, Forma, Tamaño, Color, Cintilla y Precio
		//ESTE CODIGO DEPENDE DE ELLO
		//contenido.add(new JTextField("0"));
        JSpinner cantInsumos;
        SpinnerNumberModel m_numberSpinnerModel;
	    Integer current = new Integer(0);
	    Integer min = new Integer(0);
	    Integer max = new Integer(1000);
	    Integer step = new Integer(1);
	    m_numberSpinnerModel = new SpinnerNumberModel(current, min, max, step);
	    cantInsumos = new JSpinner(m_numberSpinnerModel);
	    ((DefaultEditor) cantInsumos.getEditor()).getTextField().setEditable(false);
	    contenido.add(cantInsumos);
        
		contenedor.identificadores.add(Integer.parseInt(llenado.getObject(1).toString()));
		contenido.add(new JLabel(llenado.getObject(2).toString()));
		contenedor.nombreInsumo.add(llenado.getObject(2).toString());
		contenido.add(new JLabel(llenado.getObject(3).toString()));
		//contenido.add(Integer.parseInt(llenado.getObject(4).toString()));
		contenido.add(new JLabel(llenado.getObject(5).toString()));
		contenido.add(new JLabel(llenado.getObject(6).toString()));
		contenido.add(new JLabel(llenado.getObject(7).toString()));
		contenido.add(new JLabel(llenado.getObject(8).toString()));
		contenido.add(new JLabel(llenado.getObject(9).toString()));
		contenedor.cantidad.add(0);
		contenedor.precios.add(Double.parseDouble(llenado.getObject(9).toString()));
                size++;
            }
            if (size>tamaño)
            {
                this.tamaño = size;
            }
            listaCategorias.add(contenedor);
            }
        catch(Throwable ignore)
        {
        }
	return contenido;
    }
    
    public String[] buscaCategorias()
    {
        List<String> listaCategorias = new ArrayList<String>();
	int indice = 0;
	BDM prueba = new BDM();
	//Connection conn = prueba.connection();
	try
	{
            String query = "SELECT DISTINCT tipoCatalogo from taller2int.insumos";
            //Statement state = conn.createStatement();
            ResultSet resultado = prueba.getSt().executeQuery(query);
            while (resultado.next())
            {
                System.out.println(resultado.getObject(1).toString());
                    //categorias[indice] = resultado.getObject(1).toString();
                listaCategorias.add(resultado.getObject(1).toString());
            }
	}
	catch(Throwable ignore)
	{

	}	
	
    String[] arregloCategorias = new String[listaCategorias.size()];
    listaCategorias.toArray(arregloCategorias);
	return arregloCategorias;
    }
    
    //public JTabbedPane creaPestañas(String[] resultado)
    public void creaPestañas(String[] resultado)
    {
	for(int i= 0; i<resultado.length; i++)
	{
            BDM prueba = new BDM();
            //Connection conn = prueba.connection();
            try
            {
                    //La selección debe ser:
            //Select id, nombre, descripcion, tipoCatalogo, 
            //Forma, Tamaño, Color, Cintilla y Precio
            //ESTE CODIGO DEPENDE DE ELLO
                String query = "SELECT idinsumo, nombreinsumo, descripcion, tipoCatalogo, forma, tamaño, color, cintilla, precio FROM taller2int.insumos WHERE tipoCatalogo ='"+resultado[i]+"';";
                //Statement state = conn.createStatement();                
                ResultSet resultadoquery = prueba.getSt().executeQuery(query);
                JScrollPane scrolling = new JScrollPane(this.creaContenido(resultadoquery, resultado[i]));
                //scrolling.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                panelPestaña.addTab(resultado[i],scrolling);
                //panelPestaña.addTab(resultado[i],this.creaContenido(resultadoquery, resultado[i]));
                panelPestaña.getComponent(i).setName(resultado[i]);
            }
            catch(Throwable ignore)
            {
            }           
	}
        //return panelPestaña;
}

    public JButton agregaInsumos()
    {
        JButton agregar = new JButton("agregar");
        agregar.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Component pestaña = panelPestaña.getSelectedComponent();
                String tipoCatalogoActual = panelPestaña.getSelectedComponent().getName();
                int busqueda = 0;
                boolean encontrado= false;
                while (encontrado!=true)
                {
                        if (listaCategorias.get(busqueda).tipoCatalogo == tipoCatalogoActual)
                        {
                                encontrado = true;
                        }
                        else
                        {
                                busqueda++;
                        }
                }
                listaCategorias.get(busqueda).cantidad.clear();
                JPanel contenido = null;
                try
                {
                    JScrollPane muestra = new JScrollPane();
                    muestra = (JScrollPane) pestaña;
                    JViewport viewport = muestra.getViewport();
                    contenido = (JPanel) viewport.getView();
                }
                catch(Exception otherwise)
                {
                    contenido = (JPanel) pestaña;
                }
                for (int i=0; i<contenido.getComponentCount(); i++)
                {
                    Component componenteInterno = null;
                    componenteInterno = contenido.getComponent(i);
                    //if(componenteInterno instanceof JTextField)
                    if(componenteInterno instanceof JSpinner)
                    {
                        JSpinner contenidoTexto = (JSpinner) componenteInterno;
                        int entero = 0;
                        entero = (int)(contenidoTexto.getValue());
                        //if (entero > 0)
                        //{
                        int indicadorIdentificador = i/8;
                        //listaCategorias.get(busqueda).cantidad(indicadorIdentificador) = entero;
                        listaCategorias.get(busqueda).cantidad.add(entero);
                        //}
                    }
                }
                llenarTabla();
               }
            
        });
	return agregar;
    }
    
    public void llenarTabla(){
    	listaInsumos.clear();
    	tablaResumen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	dtm.setRowCount(0);
		dtm.setColumnCount(0);
		dtm.setColumnIdentifiers(titulos);
		total = 0;
		
		for (int i=0; i<listaCategorias.size(); i++){
			for (int j=0; j<listaCategorias.get(i).identificadores.size(); j++){
				if (listaCategorias.get(i).cantidad.get(j)>0){
					InsumoCotizacion insumocot = new InsumoCotizacion(listaCategorias.get(i).identificadores.get(j),listaCategorias.get(i).cantidad.get(j));
					listaInsumos.add(insumocot);
					Object[] fila ={listaCategorias.get(i).nombreInsumo.get(j),listaCategorias.get(i).cantidad.get(j),
						listaCategorias.get(i).precios.get(j),(listaCategorias.get(i).cantidad.get(j)*listaCategorias.get(i).precios.get(j))};
					total += (listaCategorias.get(i).cantidad.get(j))*(listaCategorias.get(i).precios.get(j));
					dtm.addRow(fila);
				}
			}
		}		
		Object[] filavacia = {"","","",""};
		dtm.addRow(filavacia);
		Object[] filaTotal = {"", "", "Costo Total", total};
		dtm.addRow(filaTotal);
		
		
    }
//	public static void main(String[] args)
//	{
//            PanelPestaña2 prueba1 = new PanelPestaña2();
//            prueba1.setVisible(true);
//	}
}
