package sound;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SoundTest extends JFrame {
	int volume;
	float volFloat;
	JLabel volText;
	
	String[] sounds;

	public SoundTest() {

		volText = new JLabel("Gain: " + Integer.toString(volume));

		JPanel sliderPanel = new JPanel();
		Container cp = this.getContentPane();

		cp.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

		int volMin = -6;
		int volMax = 6;

		JSlider volSlider = new JSlider(volMin, volMax);
		volSlider.setPaintTicks(true);
		volSlider.setMajorTickSpacing(1);
		volSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				volume = ((JSlider) e.getSource()).getValue();
				// volText = new JLabel(Integer.toString(volume));
				volText.setText("Gain: " + Integer.toString(volume));
				volFloat = (float) volume;
			}
		});
		sliderPanel.add(volText);
		sliderPanel.add(volSlider);
		cp.add(sliderPanel);

		sounds = new String[] { "DECAP_ONE", "EX1", "EX2", "EX3", "EXECUTION", "GRUNT_ONE",
				"GRUNT_TWO", "KO", "ROUND1", "ROUND2", "STOMP", "SWING1", "MENUSHOUT" };
		
		JButton[] buttons = new JButton[sounds.length];
		
		for (int i = 0; i < sounds.length; i++) {
			buttons[i] = new JButton(sounds[i]);
			
			addListener(buttons[i],sounds[i]);
			cp.add(buttons[i]);
		}
		

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Test SoundEffect");
		this.setSize(500, 500);
		this.setVisible(true);
	}
	private void addListener(JButton b, final String s){
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ref = "SoundSource/"+s+".wav";
				SoundHandler.play(ref, volFloat);
			}
		});
	}

	public static void main(String[] args) {
		new SoundTest();

	}
}
