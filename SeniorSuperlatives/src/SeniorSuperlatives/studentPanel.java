package SeniorSuperlatives;

import java.awt.SystemColor;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/*
 * studentPanel is a class for a studentEntry. A studentPanel component is created everytime a new entry is created, and the data for that entry is saved.
 */
public class studentPanel extends JPanel {
	private int id;
	private String name;
	private int votes;
	private static final long serialVersionUID = 7943290161865669155L;
	
	/**
	 * CCreate the frame.
	 */
	public studentPanel(int id, String name, int y) {
		setBackground(SystemColor.window);
		setLayout(null);
		this.id = id;
		this.name = name;
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(SystemColor.window);
		panel.setBounds(0, 0, 435, 35);
		add(panel);
		panel.setLayout(null);
		
		//When the JSpinner value is changed in anyway, it runs storeVotes().
		JSpinner spinner = new JSpinner();
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				storeVotes(spinner);
				System.out.println(name + ":" + votes);
			}
		});
		spinner.setBounds(365, 8, 60, 20);
		panel.add(spinner);
		spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		
		JLabel lblNewLabel = new JLabel(name);
		lblNewLabel.setBounds(10, 11, 374, 14);
		panel.add(lblNewLabel);

	}
	
	/*
	 * Converts the current value of the spinner into a number, and sets the vote count to that number.
	 */
	public void storeVotes(JSpinner spinner) {
		Object o = spinner.getValue();
		Number n = (Number) o;
		int i = n.intValue();
		this.votes = i;
	}
	
	//Returns id
	public int getId() {
		return id;
	}
	
	//Returns votes
	public int getVotes() {
		return votes;
	}
	
	//Returns name
	public String getName() {
		return name;
	}
}
