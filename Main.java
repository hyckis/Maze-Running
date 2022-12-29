// 105403506���3A��y��

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main {

	public static void main(String[] args) {

		ReadMap readMap = new ReadMap();
		JFrame frame = new JFrame();
		JButton run = new JButton("run");
		BorderLayout layout = new BorderLayout();

		// run���s�ƥ�
		run.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				readMap.run();	// �NReadMap����drawing�]��true�H�e�X���|
			}
		});

		frame.setLayout(layout);
		frame.add(readMap, BorderLayout.CENTER);
		frame.add(run, BorderLayout.SOUTH);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 650);
		frame.setResizable(false);	// �T�w�����j�p
		frame.setVisible(true);

	}

}
