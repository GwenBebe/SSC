package SeniorSuperlatives;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
//The WindowBuilder extension for Eclipse was used to make the GUI.
public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 211221818043528925L;
	public static List<studentPanel> tallyPanels = new ArrayList<studentPanel>();
	private static int id = 0;
	private JTextField studentName;
	private String defaultPath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
	private String currentFile = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
		super ("Senior Superlative Counter");
		getContentPane().setBackground(UIManager.getColor("window"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/assets/icons/icon.png")));
		setBounds(100, 100, 600, 155);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		
		System.out.println(defaultPath);
		
		studentName = new JTextField();
		studentName.setBounds(83, 52, 309, 28);
		studentName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		studentName.setHorizontalAlignment(SwingConstants.LEFT);
		getContentPane().add(studentName);
		studentName.setColumns(10);
		
		JLabel nameWarning = new JLabel("Please Enter A Student Name");
		nameWarning.setBounds(83, 82, 163, 14);
		nameWarning.setVisible(false);
		nameWarning.setForeground(Color.RED);
		nameWarning.setFont(new Font("Tahoma", Font.PLAIN, 11));
		getContentPane().add(nameWarning);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(83, 39, 435, 2);
		getContentPane().add(separator);
		
		JLabel lblSeniorSuperlativeCounter = new JLabel("Senior Superlative Counter");
		lblSeniorSuperlativeCounter.setBounds(83, 0, 289, 41);
		lblSeniorSuperlativeCounter.setFont(new Font("Tahoma", Font.BOLD, 21));
		getContentPane().add(lblSeniorSuperlativeCounter);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(83, 107, 435, 1);
		getContentPane().add(separator_1);
		
		Panel studentList = new Panel();
		studentList.setBounds(83, 114, 435, 0);
		getContentPane().add(studentList);
		studentList.setLayout(new GridLayout(0, 1, 0, 0));
		
		/*
		 * If the studentName JTextField is empty, text is displayed telling the user to enter a name, otherwise,
		 * it runs the addStudent() method with the current ID, the studentList Panel, and the name entered in the studentName JTextField, 
		 * and studentName is cleared. The id variable is incremented to prepair for the next student entry.
		 */
		JButton addStudent = new JButton("Add Student");
		addStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = studentName.getText();
				if(name.isEmpty()) {
					nameWarning.setVisible(true);
				}else {
					SwingUtilities.invokeLater(new Runnable(){
						@Override
						public void run() {
							nameWarning.setVisible(false);
							addStudent(id, studentList, name);
							studentName.setText("");
							id++;
						}
						
					});
				}
			}
		});
		addStudent.setBounds(398, 52, 120, 28);
		addStudent.addMouseListener(new MouseAdapter() {
		});
		getContentPane().add(addStudent);
		

  		//If the ENTER button is clicked whilst the user is in the studentName JTextField, it simulates a button click on the addStudent button.
		studentName.addKeyListener(new KeyAdapter() {
	       public void keyPressed(KeyEvent e) {
	          if (e.getKeyCode()==KeyEvent.VK_ENTER) {
	           addStudent.doClick();
	          }
	       }
	    });
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		//Runs save() when the ActionListener detects an ActionEvent.
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					save();
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnFile.add(mntmSave);
		
		//Runs saveAs() when the ActionListener detects an ActionEvent.
		JMenuItem mntmSaveAs = new JMenuItem("Save As");
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					saveAs();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnFile.add(mntmSaveAs);
	}
	
	/*
	 * addStudent()
	 * It takes in an int id, Panel where the student entry will be added, and the student's name. The ID number is defaulted to 0.
	 * It calculates the position of the entry based on the id. The first entry is 0px from the top, the next entry is 40px from the top, etc.
	 * A new JPanel is created, and then added to the tallyPanels List. The entry is added to the panel that contains the students, which
	 * is then resized, along with the application window, to fit the student entry.
	 */
	private void addStudent(int id, Panel panel, String name) {
		int h = id * 40;
		JPanel newPanel = new studentPanel(id, name, h);
		tallyPanels.add((studentPanel) newPanel);
		
		panel.add(newPanel);
		panel.setBounds(83, 114, 435, h + 40);
		int x = getX();
		int y = getY();
		setBounds(x, y, 600, 213 + h + 40);
		
		System.out.println(tallyPanels);
		System.out.println(tallyPanels.size());
	}
	
	/*
	 * If there is no current file, the method creates a new JFileChooser with the default system path (typically the Documents folder). 
	 * A filter for text files is placed on the File Chooser. If the File Chooser responds with a valid location for the save, the method runs a 
	 * check for a '.txt' file extension on the chosen file name, and adds one if there is none. The file is created, and is sent to the saveFile() 
	 * method to have the student data written into it.
	 * 
	 * If there is already a file open, it overwrites the data in that file.
	 */
	private void save() throws IOException {
		if(currentFile == null) {
			JFileChooser save = new JFileChooser(defaultPath);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt", "text");
			save.setFileFilter(filter);
			
			int returned = save.showSaveDialog(this);
			
			if(returned == JFileChooser.APPROVE_OPTION) {
				File file1 = save.getSelectedFile();
				String filename = save.getSelectedFile().toString();
				if(!filename.endsWith(".txt"))
					file1 = new File(file1.toString() + ".txt");
				currentFile = file1.getAbsolutePath();
				saveFile(file1);
			}
		}else {
			File file = new File(currentFile);
			saveFile(file);
		}
	}
	
	/*
	 * Basically the save thing as save(), except there is no check for a currentFile, and instead creates a new file and sets that as the currentFile.
	 */
	private void saveAs() throws IOException {
			JFileChooser save = new JFileChooser(defaultPath);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt", "text");
			save.setFileFilter(filter);
			
			int returned = save.showSaveDialog(this);
			
			if(returned == JFileChooser.APPROVE_OPTION) {
				File file1 = save.getSelectedFile();
				String filename = save.getSelectedFile().toString();
				if(!filename.endsWith(".txt"))
					file1 = new File(file1.toString() + ".txt");
				currentFile = file1.getAbsolutePath();
				saveFile(file1);
		}
	}
	
	/*
	 * A new PrintWriter is created for the set file. The method iterates through the List of studentPanels and saves the information (ID, name, and voteCount) 
	 * on a separate line each. The ID is included incase I decide to add an open() method.
	 */
	private void saveFile(File file) throws FileNotFoundException {
		PrintWriter pr = new PrintWriter(file);
		System.out.println("Saving to "  + file.getAbsolutePath() + "");
		for(int i = 0; i < tallyPanels.size(); i++) {
			studentPanel[] tempArray = tallyPanels.toArray(new studentPanel[tallyPanels.size()]);
			int currentid = tempArray[i].getId();
			String currentname = tempArray[i].getName();
			int currentvote = tempArray[i].getVotes();
			pr.println(currentid + ": " + currentname + " - " + currentvote);
			System.out.println("Student " + i + " saved..");
		}
		pr.close();
	}
}
