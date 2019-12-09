package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Token;

public class Ejecutable2 extends JFrame {

	private JPanel contentPane;
	private JTable tablaLineas;
	private JTable tablaSignos;
	private JTable tablaTokens;
	
	private ArrayList<Token> tokensPermitidos;
	private ArrayList<Token> listaDeTokens;
	private ArrayList<Token> signosToken;
	private String textoCompleto;
	private String cad;
	private String aux;
	
	private String[][] signosParaLista;
	private String[][] tokensParaLista;
	private String[] cabeseraTokens;
	
	private Token token;
	private JButton btnAnalizar;
	private JTextArea areaDeTexto;
	
	private int indiceLineas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ejecutable2 frame = new Ejecutable2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Ejecutable2() {
		
		valoresIniciales();
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dimencion = getToolkit().getScreenSize();
		setBounds(0, 0, dimencion.width, dimencion.height-30);
		contentPane = new JPanel();
		contentPane.setOpaque(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblAnalizadorLexico = new JLabel("Analizador Lexico");
		lblAnalizadorLexico.setHorizontalTextPosition(SwingConstants.CENTER);
		lblAnalizadorLexico.setIcon(new ImageIcon(Ejecutable2.class.getResource("/recursos/titulonaranjaTransp.png")));
		lblAnalizadorLexico.setHorizontalAlignment(SwingConstants.CENTER);
		lblAnalizadorLexico.setForeground(new Color(255, 255, 255));
		lblAnalizadorLexico.setFont(new Font("Tw Cen MT", Font.BOLD, 22));
		lblAnalizadorLexico.setBackground(new Color(102, 51, 51));
		GridBagConstraints gbc_lblAnalizadorLexico = new GridBagConstraints();
		gbc_lblAnalizadorLexico.insets = new Insets(0, 0, 5, 0);
		gbc_lblAnalizadorLexico.gridx = 0;
		gbc_lblAnalizadorLexico.gridy = 0;
		contentPane.add(lblAnalizadorLexico, gbc_lblAnalizadorLexico);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		contentPane.add(panel, gbc_panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		btnAnalizar = new JButton("Analizar");
		btnAnalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				analizar();
			}
		});
		panel.add(btnAnalizar, BorderLayout.SOUTH);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.7);
		panel.add(splitPane, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		splitPane.setLeftComponent(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setResizeWeight(0.1);
		panel_1.add(splitPane_1, BorderLayout.CENTER);
		
		areaDeTexto = new JTextArea();
		areaDeTexto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					textoCompleto = areaDeTexto.getText() + "\n";
					areaDeTexto.setText(textoCompleto);
				}
			}
		});
		areaDeTexto.setWrapStyleWord(true);
		areaDeTexto.setLineWrap(true);
		splitPane_1.setRightComponent(areaDeTexto);
		
		tablaLineas = new JTable();
		tablaLineas.setModel(new DefaultTableModel(
			new Object[][] {
				{"1"},
				{"2"},
				{"3"},
				{"4"},
				{"5"},
				{"6"},
				{"7"},
				{"8"},
				{"9"},
				{"10"},
				{null},
			},
			new String[] {
				"New column"
			}
		));
		splitPane_1.setLeftComponent(tablaLineas);
		
		JPanel panel_2 = new JPanel();
		splitPane.setRightComponent(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setResizeWeight(0.5);
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panel_2.add(splitPane_2, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane_2.setLeftComponent(scrollPane);
		
		tablaTokens = new JTable();
		tablaTokens.setModel(new DefaultTableModel(
			tokensParaLista,
			new String[] {
				"Token", "Lexema"
			}
		));
		scrollPane.setViewportView(tablaTokens);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane_2.setRightComponent(scrollPane_1);
		
		tablaSignos = new JTable();
		tablaSignos.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
			},
			new String[] {
				"Id"
			}
		));
		scrollPane_1.setViewportView(tablaSignos);
	}
	
	public void valoresIniciales() {
		
		tokensPermitidos = new ArrayList<Token>();
		listaDeTokens = new ArrayList<Token>();
		signosToken = new ArrayList<Token>();
		aux = "";
		cad = "";
		textoCompleto = "";
		indiceLineas = 0;
		signosParaLista = new String[][] {{"",""},{"",""}};
		tokensParaLista = new String[][] {{"",""},{"",""}};
		cabeseraTokens = new String[] {"Token", "Lexema"};
		
		
		token = new Token();
		token.setToken("Inicio");
		token.setLexema("Iniciar Programa");
		tokensPermitidos.add(token);
		
		token = new Token();
		token.setToken("Fin");
		token.setLexema("Finalizar Programa");
		tokensPermitidos.add(token);
		
		token = new Token();
		token.setToken("ENTERO");
		token.setLexema("Tipo Entero");
		tokensPermitidos.add(token);
		
		token = new Token();
		token.setToken("DECIMAL");
		token.setLexema("Tipo Decimal");
		tokensPermitidos.add(token);
		
		token = new Token();
		token.setToken("opSum");
		token.setLexema("Metodo Para Sumar");
		tokensPermitidos.add(token);
		
		token = new Token();
		token.setToken("opRes");
		token.setLexema("Metodo Para Restar");
		tokensPermitidos.add(token);
		
		token = new Token();
		token.setToken("opMul");
		token.setLexema("Metodo Para Multiplicar");
		tokensPermitidos.add(token);
		
		token = new Token();
		token.setToken("opDiv");
		token.setLexema("Metodo Para Dividir");
		tokensPermitidos.add(token);
		
		token = new Token();
		token.setToken("Print");
		token.setLexema("Metodo Para Imprimir");
		tokensPermitidos.add(token);
		
		token = new Token();
		token.setToken("(");
		token.setLexema("Abrir Paretesis");
		tokensPermitidos.add(token);
		
		token = new Token();
		token.setToken(")");
		token.setLexema("Cerrar Parentesis");
		tokensPermitidos.add(token);
		
		token = new Token();
		token.setToken("=");
		token.setLexema("Asignación");
		tokensPermitidos.add(token);
		
		token = new Token();
		token.setToken(",");
		token.setLexema("Coma");
		tokensPermitidos.add(token);
		
		token = new Token();
		token.setToken("{");
		token.setLexema("Abrir Llaves");
		tokensPermitidos.add(token);
		
		token = new Token();
		token.setToken("}");
		token.setLexema("Cerrar Llaves");
		tokensPermitidos.add(token);
		
		token = new Token();
		token.setToken(";");
		token.setLexema("Punto y Coma");
		tokensPermitidos.add(token);			
		
	}
	
	public void analizar() {
		textoCompleto = areaDeTexto.getText() + "&&";
		cad = textoCompleto;
		int i = 0;
		indiceLineas = 1;
		aux = "";
		listaDeTokens = new ArrayList<Token>();
		signosToken = new ArrayList<Token>();
		
		while (cad.charAt(i) != '&' && cad.charAt(i+1) != '&') {
			
			if (cad.charAt(i) == '\n') {
				aux = "";
				indiceLineas++;
				i++;
				continue;
			} else if (cad.charAt(i) == ' ' || cad.charAt(i) == '\t') {
				if (cad.charAt(i) == ' ') {
					if (isID(aux)) {
						colocarToken(new Token(aux, "ID"));
						colocarEnSignos(new Token(aux, "ID"));
						aux = "";
						i++;
						continue;
					} else {
						aux = "";
						i++;
						error();
						continue;
					}
				}
				aux = "";
				i++;
				continue;
			} else if (isCharValido(cad.charAt(i))) {
				
				if ((Character.isDigit(cad.charAt(i)) && !Character.isAlphabetic(cad.charAt(i)) ) || cad.charAt(i) == '.') {
					aux += cad.charAt(i);
					if (cad.charAt(i) == '.') {
						if (Character.isDigit(cad.charAt(i+1))) {
							aux += cad.charAt(i+1);
							if (Character.isDigit(cad.charAt(i+2))) {
								aux += cad.charAt(i+2);
								if (Character.isDigit(cad.charAt(i+3))) {
									aux = "";
									i = i+4;
									error();
									continue;
								} else {
									colocarToken(new Token(aux, "Número"));
									colocarEnSignos(new Token(aux, "Número"));
									aux = "";
									i = i+3;
									continue;
								}
							} else {
								colocarToken(new Token(aux, "Número"));
								colocarEnSignos(new Token(aux, "Número"));
								aux = "";
								i = i+2;
								continue;
							}
							
						} else {
							aux = "";
							i++;
							error();
							continue;
						}
					}
					
					if (!Character.isDigit(cad.charAt(i+1)) && (cad.charAt(i+1) != '.')) {
						colocarToken(new Token(aux, "Número"));
						colocarEnSignos(new Token(aux, "Número"));
						aux = "";
						i++;
						continue;
					}
					
					
				} else {
					aux += cad.charAt(i);
					if (isToken(aux)) {
						colocarToken(getToken(aux));
						aux = "";
						i++;
						continue;
					} else if (isToken(cad.charAt(i+1) + "")) {
						if (isID(aux)) {
							colocarToken(new Token(aux, "ID"));
							colocarEnSignos(new Token(aux, "ID"));
							aux = "";
							i++;
							continue;
						} else {
							aux = "";
							i++;
							error();
							continue;
						}
					}
					
					
				}
				
			}
			i++;
		}
	}
	
	public void error(){
		JOptionPane.showMessageDialog(null, "Error en la linea: " + indiceLineas);
	}
	
	public void colocarEnSignos(Token token) {
		signosToken.add(token);
		signosParaLista = new String[signosToken.size()][2];
		for (int i = 0; i < signosToken.size(); i++) {
			signosParaLista[i][0] = signosToken.get(i).getToken();
			signosParaLista[i][1] = signosToken.get(i).getLexema();
		}
		tablaSignos.setModel(new DefaultTableModel(signosParaLista,cabeseraTokens));
	}
	
	public void colocarToken(Token token) {
		listaDeTokens.add(token);
		tokensParaLista = new String[listaDeTokens.size()][2];
		for (int i = 0; i < listaDeTokens.size(); i++) {
			tokensParaLista[i][0] = listaDeTokens.get(i).getToken();
			tokensParaLista[i][1] = listaDeTokens.get(i).getLexema();
		}
		tablaTokens.setModel(new DefaultTableModel(tokensParaLista,cabeseraTokens));
	}
	
	public Token getToken(String s) {
		Token token = new Token();
		for (Token t : tokensPermitidos) {
			if (s.equals(t.getToken())) {
				token = t;
				return token;
			}
		}
		return null;
	}
	
	public boolean isToken(String s) {
		for (Token token : tokensPermitidos) {
			if (s.equals(token.getToken())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isCharValido(char c) {
		if (Character.isDigit(c)) {
			return true;
		} else if (Character.isAlphabetic(c)) {
			return true;
		} else if ((c == '(') || (c == ')') || (c == '{') || (c == '}')) {
			return true;
		} else if ((c == '=') || (c == ',') || (c == ';') || (c == '.')) {
			return true;
		}
		return false;
	}
	
	public boolean isID(String s) {
		if (!s.isEmpty()) {
			if (Character.isAlphabetic(s.charAt(0)) && Character.isLowerCase(s.charAt(0))) {
				try {
					if ((Character.isAlphabetic(s.charAt(1)) && Character.isLowerCase(s.charAt(1))) || Character.isDigit(s.charAt(1))) {
						if ((Character.isAlphabetic(s.charAt(2)) && Character.isLowerCase(s.charAt(2))) || Character.isDigit(s.charAt(2))) {
							if ((Character.isAlphabetic(s.charAt(3)) && Character.isLowerCase(s.charAt(3))) || Character.isDigit(s.charAt(3))) {
								return false;
							} else if (Character.isUpperCase(s.charAt(3))) {
								return false;
							} else {
								return true;
							}
						} else if (Character.isUpperCase(s.charAt(2))) {
							return false;
						} else {
							return true;
						}
					} else if (Character.isUpperCase(s.charAt(1))) {
						return false;
					} else {
						return true;
					}
				} catch (Exception e) {
					return true;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
