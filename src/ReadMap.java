// Author: YICHIN HO

import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.regex.*;
import java.util.stream.*;
import java.util.List;
import javax.swing.*;
import java.util.*;

public class ReadMap extends JPanel {

	ArrayList<Point> points = new ArrayList<Point>();	// �s������y���I
	int column = 1;	// ���o���y�y��
	int direction = 0;	// ���դ�V������
	boolean drawing = false;	// �P�_�O�_�n�e���|

	// �e�g�c
	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		readMap().stream()
				 .forEach(p -> g.fillRect((p.x-1) * 60, (p.y-1) * 60, 60, 60));	// �e���
		
		if(drawing)	// �p�G���Urun���s�A�e���|
			drawStep(g);
		
		// �_�I
		g.setColor(Color.blue);
		g.fillRect(0, 0, 60, 60);

	}

	// �e���|
	public void drawStep(Graphics g) {
		backtracking().stream().forEach(p -> {
			  if (p.x == 1 && p.y == 7)
				  g.setColor(Color.blue);
			  else
				  g.setColor(Color.red);
			  g.fillRect((p.x-1) * 60, (p.y-1) * 60, 60, 60);		  
		  });
	}
	
	// ���Urun���s
	public void run() {
		drawing=true;
		repaint();
	}

	// Ū�ɮ�
	public ArrayList<Point> readMap() {
		
		try {
			Pattern pattern = Pattern.compile("\n");
			Stream<String> map =
				Files.lines(Paths.get("map.txt"))	// �o��map.txt�����|
					 .flatMap(line -> pattern.splitAsStream(line));	// ��enter���}
			
			// �[��list��
			map.forEach(s1 -> {
				if (column < 11) {
					List<String> mapRow = Arrays.asList(s1.split(" "));
					mapRow.forEach(s2 -> {
						Point p = new Point(Integer.parseInt(s2), column);	// �s����y��
						points.add(p);	// �s�Jarraylist
					});
					column++;	// y�y��
				}
			});
			return points;
			
		} catch (IOException e) {
			return null;
		}

	}

	// ���g�c
	public Stack<Point> backtracking() {
		
		// �|�P��V
		Point[] move = {
				new Point(0, 1),
				new Point(1, 0),
				new Point(0, -1),
				new Point(-1, 0)
		};
		
		// �[���
		ArrayList<Point> allMaze = new ArrayList<>();		// �[�F��ɪ��g�c
		allMaze.addAll(readMap());	// �쥻���g�c
		for (int i=0; i<12; i++) {	// �[���
			Point[] bounds = {	
					new Point(0, i), 
					new Point(11, i),
					new Point(i, 0),
					new Point(i, 11)
			};
			for (Point p: bounds)
				allMaze.add(p);			
		}
			
		Stack<Point> resolution = new Stack<>();	// stack that stores points
		ArrayList<Point> stepped = new ArrayList<Point>();	// store points that had been stepped
		Point temp = new Point(1, 1);	// �ثe���쪺�I
		Point endPoint = new Point(1, 7);	// ���I
		
		resolution.push(temp);	// push�_�I
		boolean isEnd = false;	// �O�_������I
		
		while(!isEnd && !resolution.isEmpty()) {	// �S����I�N�@���]
			
			while (direction < 4 && !isEnd) {	// �٨S�է�4�Ӥ�V
				
				// �ثe�y�Э�
				int x = temp.x;
				int y = temp.y;
				
				Point tryPath = new Point(x + move[direction].x, y + move[direction].y);	// ����4�Ӥ�V�A�p�G�O�D���A��temp��
				
				if (!allMaze.contains(tryPath) && !stepped.contains(tryPath)) {	// road point that has not been stepped
					temp = tryPath;	// ���e��
					resolution.push(temp);	// �N���Ipush��stack
					stepped.add(temp);	// �O�����I
					direction = 0;	// ���s����4�Ӥ�V
					if (temp.x == endPoint.x && temp.y == endPoint.y)	// ������I
						isEnd = true;	// ���}�j��
						
				} else if (allMaze.contains(tryPath) || stepped.contains(tryPath)) 	// wall point || stepped road point
					direction++;	// �~��դU�@�Ӥ�V				
			} 
			if (direction == 4) {	// ����4�Ӥ�V�åB4�Ӥ�V�����ਫ
				resolution.pop();	// �ᱼ�ثe���I
				temp = resolution.peek();	// ���^�e�@���I
				direction = 0;	// ���s����4�Ӥ�V
			}
			
		}		
		return resolution;
		
	}
	
}
