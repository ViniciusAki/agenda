package view;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DAO;

public class TelaContato extends JFrame {

	private JPanel contentPane;
	private JLabel lblStatus;
	private JTextField textId;
	private JTextField textNome;
	private JTextField textFone;
	private JTextField textEmail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaContato frame = new TelaContato();
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
	public TelaContato() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				// aivação do formulário (formulário carregado)
				// status da conexão
				status();
			}
		});
		setTitle("Agenda de Contatos");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaContato.class.getResource("/icones/favicon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(TelaContato.class.getResource("/icones/dberror.png")));
		lblStatus.setBounds(392, 218, 32, 32);
		contentPane.add(lblStatus);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(39, 21, 46, 14);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setBounds(39, 49, 46, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Fone");
		lblNewLabel_2.setBounds(39, 87, 46, 14);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("E-mail");
		lblNewLabel_3.setBounds(39, 126, 46, 14);
		contentPane.add(lblNewLabel_3);

		textId = new JTextField();
		textId.setEditable(false);
		textId.setBounds(87, 18, 114, 20);
		contentPane.add(textId);
		textId.setColumns(10);

		textNome = new JTextField();
		textNome.setBounds(85, 46, 223, 20);
		contentPane.add(textNome);
		textNome.setColumns(10);

		textFone = new JTextField();
		textFone.setBounds(85, 84, 143, 20);
		contentPane.add(textFone);
		textFone.setColumns(10);

		textEmail = new JTextField();
		textEmail.setBounds(87, 123, 221, 20);
		contentPane.add(textEmail);
		textEmail.setColumns(10);

		btnCreate = new JButton("");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inserirContato();
			}

		});
		btnCreate.setEnabled(false);
		btnCreate.setBackground(SystemColor.control);
		btnCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCreate.setBorder(null);
		btnCreate.setToolTipText("Adicionar Contato");
		btnCreate.setIcon(new ImageIcon(TelaContato.class.getResource("/icones/create.png")));
		btnCreate.setBounds(76, 186, 64, 64);
		contentPane.add(btnCreate);

		btnUpdate = new JButton("");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterarContato();
			}
		});
		btnUpdate.setEnabled(false);
		btnUpdate.setToolTipText("Editar Contato");
		btnUpdate.setIcon(new ImageIcon(TelaContato.class.getResource("/icones/update.png")));
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.setBorder(null);
		btnUpdate.setBackground(SystemColor.control);
		btnUpdate.setBounds(150, 186, 64, 64);
		contentPane.add(btnUpdate);

		btnDelete = new JButton("");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletarConato();
			}
		});
		btnDelete.setEnabled(false);
		btnDelete.setBorder(null);
		btnDelete.setToolTipText("Excluir contato");
		btnDelete.setIcon(new ImageIcon(TelaContato.class.getResource("/icones/delete.png")));
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDelete.setBackground(SystemColor.control);
		btnDelete.setBounds(224, 186, 64, 64);
		contentPane.add(btnDelete);

		btnRead = new JButton("");
		btnRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecionarContato();
			}
		});
		btnRead.setEnabled(false);
		btnRead.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRead.setIcon(new ImageIcon(TelaContato.class.getResource("/icones/read.png")));
		btnRead.setToolTipText("Pesquisar Contato");
		btnRead.setBorder(null);
		btnRead.setBackground(SystemColor.control);
		btnRead.setBounds(335, 34, 48, 48);
		contentPane.add(btnRead);
	} // fim do construtor
		// Criação de um objeto para acessar o método da classe DAO

	DAO dao = new DAO();
	private JButton btnRead;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnCreate;

	/**
	 * status da conexão
	 * 
	 */
	private void status() {
		try {
			// estabelecer uma conexão

			Connection con = dao.conectar();
			// status
			//System.out.println(con);
			// trocando o ícone do banco (status da conexãó)
			if (con != null) {
				lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/dbok.png")));
				btnRead.setEnabled(true);

			} else {
				lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/dberror.png")));

			}

			// encerrar a conexão
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 
	 * Selecionar contato
	 * 
	 */
	private void selecionarContato() {
//Introsução sql para pesquisar um contat pelo nome 
		String read = "select * from contatos where nome = ?";
		try {
			// estabelecer uma conexão
			Connection con = dao.conectar();
// preparar a instrução sql
			PreparedStatement pst = con.prepareCall(read);
//substuir 
			pst.setString(1, textNome.getText());

			ResultSet rs = pst.executeQuery();
			// se existir um contato correspodente
			if (rs.next()) {
				textId.setText(rs.getString(1));
				textFone.setText(rs.getString(3));
				textEmail.setText(rs.getString(4));
				btnUpdate.setEnabled(true);
				btnDelete.setEnabled(true);
				btnRead.setEnabled(false);
			} else {
				// criar contato inexistente
				// JOptionPane.showMessageDialog(null, "Contato inexistente");
				btnCreate.setEnabled(true);
				btnRead.setEnabled(false);
			}

			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/**
	 * Inserir um novo contato CRUD Create
	 */

	private void inserirContato() {
		// validação dos campos
		if (textNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do contato");
		} else if (textFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o fone do contato");
		} else if (textNome.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo nome não pode ter mais que 50 carcteres");
		} else if (textFone.getText().length() > 15) {
			JOptionPane.showMessageDialog(null, "O campo nome não pode ter mais que 15 carcteres");
		} else if (textEmail.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo nome não pode ter mais que 50 carcteres");
		} else {

			String create = "insert into contatos (nome,fone,email) values (?,?,?)";

			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareCall(create);
				// substuir os parâmetros (?.?.?) pelo conteúdo das caixas de texto
				pst.setString(1, textNome.getText());
				pst.setString(2, textFone.getText());
				pst.setString(3, textEmail.getText());
				// execuatr a query (insert no banco de dados)
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Contato adicionado");
				con.close();
				limpar();

			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}
	/*
	 * 
	 * 
	 * Editar contato CRUD Update
	 */

	private void alterarContato() {

		// validação dos campos
		if (textNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do contato");
		} else if (textFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o fone do contato");
		} else if (textNome.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo nome não pode ter mais que 50 carcteres");
		} else if (textFone.getText().length() > 15) {
			JOptionPane.showMessageDialog(null, "O campo nome não pode ter mais que 15 carcteres");
		} else if (textEmail.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "O campo nome não pode ter mais que 50 carcteres");
		} else {

			String update = "update contatos set nome=?,fone=?,email=? where idcon=?";

			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareCall(update);
				// substuir os parâmetros (?.?.?) pelo conteúdo das caixas de texto
				pst.setString(1, textNome.getText());
				pst.setString(2, textFone.getText());
				pst.setString(3, textEmail.getText());
				pst.setString(4, textId.getText());
				// execuatr a query (insert no banco de dados)
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Contato editado com sucesso");
				con.close();
				limpar();

			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}

	/**
	 * Excluir Contato
	 * 
	 */
	private void deletarConato() {
		String delete = "delete from contatos where idcon=?";
		// Confimação de exclusão do contato
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste contato?", "Anteção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_NO_OPTION) {
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, textId.getText());
				pst.executeUpdate();
				limpar();
				con.close();

			} catch (Exception e) {

				System.out.println(e);

			}

		} else {
			limpar();
		}

	}

	/**
	 * Limpar campos e configurar botôes
	 * 
	 */
	private void limpar() {
		textId.setText(null);
		textNome.setText(null);
		textFone.setText(null);
		textEmail.setText(null);
		btnCreate.setEnabled(false);
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
		btnRead.setEnabled(true);

	}

}
