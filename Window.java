import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Window extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public Window (){
		super ("Vi löser det tillsammans");

		setSize (800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel p = new JPanel();
		JPanel p2 = new JPanel();
		//gives access to the gridbagsconstraints
		JPanel p3 = new JPanel(new GridBagLayout()); 

		JButton b = new JButton("Button");
		JButton c = new JButton("Button 1");

		p.add(b);
		p.add(c);

		JCheckBox cb = new JCheckBox("Fuck you");
		JCheckBox cb2 = new JCheckBox("Fuck you more");

		p2.add(cb);
		p2.add(cb2);

		JLabel label = new JLabel ("label");
		JTextArea tb = new JTextArea ("Text area");
		JTextField tf = new JTextField ("text field");

		//give structure
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(15,15,15,15);

		//coordinates, x:horisontellt, y:vertical000
		gbc.gridx = 0;
		gbc.gridy = 0;
		p3.add(label, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		p3.add(tb, gbc);
		gbc.gridx = 5;
		gbc.gridy = 0;
		p3.add(tf, gbc);

		add(p, BorderLayout.SOUTH);
		add(p2, BorderLayout.NORTH);
		add(p3,BorderLayout.CENTER);
	}
}
