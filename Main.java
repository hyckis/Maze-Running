// 105403506資管3A何宜親

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main {

	public static void main(String[] args) {

		ReadMap readMap = new ReadMap();
		JFrame frame = new JFrame();
		JButton run = new JButton("run");
		BorderLayout layout = new BorderLayout();

		// run按鈕事件
		run.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				readMap.run();	// 將ReadMap中的drawing設為true以畫出路徑
			}
		});

		frame.setLayout(layout);
		frame.add(readMap, BorderLayout.CENTER);
		frame.add(run, BorderLayout.SOUTH);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 650);
		frame.setResizable(false);	// 固定視窗大小
		frame.setVisible(true);

	}

}
