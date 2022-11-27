package Vista;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Dao.daoCaracteristicas;
import Modelo.Caracteristicas;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class vCaracteristicas extends JFrame {

	private JPanel contentPane;
	private JTextField txtMarca;
	private JTextField txtModelo;
	private JTextField txtPrecio;
	private JTextField txtImg;
	private JButton btnAgregar;
	private JButton btnEliminar;
	private JButton btnEditar;
	private JButton btnBorrar;
	private JButton btnSeleccionar;
	private JLabel lblId;
	private JTable tblCaracteristicas;
	private JScrollPane scrollPane;
	daoCaracteristicas dao = new daoCaracteristicas();
	DefaultTableModel modelo = new DefaultTableModel();
	ArrayList<Caracteristicas> lista;
	private JLabel lblImg;
	int fila = -1;
	Caracteristicas caracteristicas = new Caracteristicas();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					vCaracteristicas frame = new vCaracteristicas();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void actualizarTabla() {

		while (modelo.getRowCount() > 0) {
			modelo.removeRow(0);
		}
		lista = dao.consultaCaracteristicas();
		for (Caracteristicas ca : lista) {
			Object carac[] = new Object[4];
			carac[0] = ca.getId();
			carac[1] = ca.getMarca();
			carac[2] = ca.getModelo();
			carac[3] = ca.getPrecio();
			modelo.addRow(carac);

		}
		tblCaracteristicas.setModel(modelo);
	}

	public void limpiar() {
		lblId.setText("");
		txtMarca.setText("");
		txtModelo.setText("");
		txtPrecio.setText("");
		txtImg.setText("");

	}

	public vCaracteristicas() {
		setTitle("CARACTERISTICAS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 799, 505);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(10, 26, 46, 14);
		contentPane.add(lblNewLabel);

		lblId = new JLabel("0");
		lblId.setBounds(38, 26, 46, 14);
		contentPane.add(lblId);

		JLabel lblNewLabel_2 = new JLabel("MARCA");
		lblNewLabel_2.setBounds(10, 74, 46, 14);
		contentPane.add(lblNewLabel_2);

		txtMarca = new JTextField();
		txtMarca.setBounds(62, 71, 101, 20);
		contentPane.add(txtMarca);
		txtMarca.setColumns(10);

		txtModelo = new JTextField();
		txtModelo.setColumns(10);
		txtModelo.setBounds(62, 111, 101, 20);
		contentPane.add(txtModelo);

		JLabel lblNewLabel_2_1 = new JLabel("MODELO");
		lblNewLabel_2_1.setBounds(10, 114, 46, 14);
		contentPane.add(lblNewLabel_2_1);

		txtPrecio = new JTextField();
		txtPrecio.setColumns(10);
		txtPrecio.setBounds(62, 159, 101, 20);
		contentPane.add(txtPrecio);

		JLabel lblNewLabel_2_2 = new JLabel("PRECIO");
		lblNewLabel_2_2.setBounds(10, 162, 46, 14);
		contentPane.add(lblNewLabel_2_2);

		txtImg = new JTextField();
		txtImg.setColumns(10);
		txtImg.setBounds(62, 409, 101, 20);
		contentPane.add(txtImg);

		JLabel uihh = new JLabel("IMG");
		uihh.setBounds(10, 412, 46, 14);
		contentPane.add(uihh);

		btnAgregar = new JButton("AGREGAR");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (txtMarca.getText().equals("") || txtModelo.getText().equals("")
							|| txtPrecio.getText().equals("") || txtImg.getText().equals("")) {
						actualizarTabla();
						JOptionPane.showMessageDialog(null, "CAMPOS VACÍOS");
						return;
					}
					Caracteristicas caracteristicas = new Caracteristicas();
					caracteristicas.setMarca(txtMarca.getText());
					caracteristicas.setModelo(txtModelo.getText());
					caracteristicas.setPrecio(Double.parseDouble(txtPrecio.getText().toString()));
					caracteristicas.setImg(txtImg.getText());
					if (dao.insertarCaracteristica(caracteristicas)) {
						JOptionPane.showMessageDialog(null, "SE AGREGO CORRECTAMENTE");
					} else {
						JOptionPane.showMessageDialog(null, "ERROR");
					}

				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "ERROR");
				}
			}
		});
		btnAgregar.setBounds(10, 213, 89, 23);
		contentPane.add(btnAgregar);

		btnEliminar = new JButton("ELIMINAR");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int opcion=JOptionPane.showConfirmDialog(null,"ESTAS SEGURO DE ELIMINAR LA COLUMNA DE CARACTERISTICAS??","ELIMINAR CARACTERISTICAS",JOptionPane.YES_NO_OPTION);
				    if (opcion ==0) {
					if (dao.eliminarCaracteristica(caracteristicas.getId())) {
						actualizarTabla();
						limpiar();
						JOptionPane.showMessageDialog(null, "SE ELIMINÓ CORRECTAMENTE");

					} else {
						JOptionPane.showMessageDialog(null, "ERROR");

					}
				    }

				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "ERROR");
				}
				
			}
		});
		btnEliminar.setBounds(10, 261, 89, 23);
		contentPane.add(btnEliminar);

		btnEditar = new JButton("EDITAR");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (txtMarca.getText().equals("") || txtModelo.getText().equals("")
							|| txtPrecio.getText().equals("") || txtImg.getText().equals("")) {
						actualizarTabla();
						JOptionPane.showMessageDialog(null, "CAMPOS VACÍOS");
						return;
					}
					caracteristicas.setMarca(txtMarca.getText());
					caracteristicas.setModelo(txtModelo.getText());
					caracteristicas.setPrecio(Double.parseDouble(txtPrecio.getText().toString()));
					caracteristicas.setImg(txtImg.getText());
					if (dao.editarCaracteristica(caracteristicas)) {
						JOptionPane.showMessageDialog(null, "SE EDITÓ CORRECTAMENTE");
					} else {
						JOptionPane.showMessageDialog(null, "ERROR");
					}
					
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "ERROR");
					
				}
			}
		});
		btnEditar.setBounds(10, 308, 89, 23);
		contentPane.add(btnEditar);

		btnBorrar = new JButton("BORRAR");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiar();
			}
		});
		btnBorrar.setBounds(10, 353, 89, 23);
		contentPane.add(btnBorrar);

		btnSeleccionar = new JButton("SELECCIONAR");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileNameExtensionFilter filtro = new FileNameExtensionFilter("Formatos de Archivo JPGE(*.JPG;*.JPGE)",
						"jpg", "jpg");
				JFileChooser imagen = new JFileChooser();
				imagen.addChoosableFileFilter(filtro);
				imagen.setDialogTitle("Abrir Archivo");
				File ruta = new File("D:/motos");
				imagen.setCurrentDirectory(ruta);
				int window = imagen.showOpenDialog(null);
				if (window == JFileChooser.APPROVE_OPTION)
					;
				File file = imagen.getSelectedFile();
				txtImg.setText(String.valueOf(file));
				Image foto = getToolkit().getImage(txtImg.getText());
				;
				foto = foto.getScaledInstance(110, 110, Image.SCALE_DEFAULT);
				lblImg.setIcon(new ImageIcon(foto));

			}
		});

		btnSeleccionar.setBounds(166, 408, 139, 23);
		contentPane.add(btnSeleccionar);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(173, 11, 610, 365);
		contentPane.add(scrollPane);

		tblCaracteristicas = new JTable();
		tblCaracteristicas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				fila = tblCaracteristicas.getSelectedRow();
				caracteristicas = lista.get(fila);
				lblId.setText("" + caracteristicas.getId());
				txtMarca.setText(caracteristicas.getMarca());
				txtModelo.setText(caracteristicas.getModelo());
				txtPrecio.setText("" + caracteristicas.getPrecio());
				txtImg.setText(caracteristicas.getImg());

			}
		});
		tblCaracteristicas.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null, null, null }, { null, null, null, null, null },
						{ null, null, null, null, null }, { null, null, null, null, null },
						{ null, null, null, null, null }, { null, null, null, null, null }, },
				new String[] { "New column", "New column", "New column", "New column", "New column" }));
		scrollPane.setViewportView(tblCaracteristicas);
		actualizarTabla();

		lblImg = new JLabel("");
		lblImg.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblImg.setBounds(330, 382, 106, 84);
		contentPane.add(lblImg);
		modelo.addColumn("ID");
		modelo.addColumn("MARCA");
		modelo.addColumn("MODELO");
		modelo.addColumn("PRECIO");
		modelo.addColumn("IMG");
		actualizarTabla();
	
	
	}
	
}


