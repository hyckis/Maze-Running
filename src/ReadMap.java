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

	ArrayList<Point> points = new ArrayList<Point>();	// 存牆壁的座標點
	int column = 1;	// 取得牆壁y座標
	int direction = 0;	// 測試方向的次數
	boolean drawing = false;	// 判斷是否要畫路徑

	// 畫迷宮
	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		readMap().stream()
				 .forEach(p -> g.fillRect((p.x-1) * 60, (p.y-1) * 60, 60, 60));	// 畫牆壁
		
		if(drawing)	// 如果按下run按鈕再畫路徑
			drawStep(g);
		
		// 起點
		g.setColor(Color.blue);
		g.fillRect(0, 0, 60, 60);

	}

	// 畫路徑
	public void drawStep(Graphics g) {
		backtracking().stream().forEach(p -> {
			  if (p.x == 1 && p.y == 7)
				  g.setColor(Color.blue);
			  else
				  g.setColor(Color.red);
			  g.fillRect((p.x-1) * 60, (p.y-1) * 60, 60, 60);		  
		  });
	}
	
	// 按下run按鈕
	public void run() {
		drawing=true;
		repaint();
	}

	// 讀檔案
	public ArrayList<Point> readMap() {
		
		try {
			Pattern pattern = Pattern.compile("\n");
			Stream<String> map =
				Files.lines(Paths.get("map.txt"))	// 得到map.txt的路徑
					 .flatMap(line -> pattern.splitAsStream(line));	// 用enter分開
			
			// 加到list裡
			map.forEach(s1 -> {
				if (column < 11) {
					List<String> mapRow = Arrays.asList(s1.split(" "));
					mapRow.forEach(s2 -> {
						Point p = new Point(Integer.parseInt(s2), column);	// 存牆壁座標
						points.add(p);	// 存入arraylist
					});
					column++;	// y座標
				}
			});
			return points;
			
		} catch (IOException e) {
			return null;
		}

	}

	// 走迷宮
	public Stack<Point> backtracking() {
		
		// 四周方向
		Point[] move = {
				new Point(0, 1),
				new Point(1, 0),
				new Point(0, -1),
				new Point(-1, 0)
		};
		
		// 加邊界
		ArrayList<Point> allMaze = new ArrayList<>();		// 加了邊界的迷宮
		allMaze.addAll(readMap());	// 原本的迷宮
		for (int i=0; i<12; i++) {	// 加邊界
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
		Point temp = new Point(1, 1);	// 目前走到的點
		Point endPoint = new Point(1, 7);	// 終點
		
		resolution.push(temp);	// push起點
		boolean isEnd = false;	// 是否走到終點
		
		while(!isEnd && !resolution.isEmpty()) {	// 沒到終點就一直跑
			
			while (direction < 4 && !isEnd) {	// 還沒試完4個方向
				
				// 目前座標值
				int x = temp.x;
				int y = temp.y;
				
				Point tryPath = new Point(x + move[direction].x, y + move[direction].y);	// 測試4個方向，如果是道路再用temp走
				
				if (!allMaze.contains(tryPath) && !stepped.contains(tryPath)) {	// road point that has not been stepped
					temp = tryPath;	// 往前走
					resolution.push(temp);	// 將該點push到stack
					stepped.add(temp);	// 記錄該點
					direction = 0;	// 重新測試4個方向
					if (temp.x == endPoint.x && temp.y == endPoint.y)	// 走到終點
						isEnd = true;	// 離開迴圈
						
				} else if (allMaze.contains(tryPath) || stepped.contains(tryPath)) 	// wall point || stepped road point
					direction++;	// 繼續試下一個方向				
			} 
			if (direction == 4) {	// 測完4個方向並且4個方向都不能走
				resolution.pop();	// 丟掉目前的點
				temp = resolution.peek();	// 走回前一個點
				direction = 0;	// 重新測試4個方向
			}
			
		}		
		return resolution;
		
	}
	
}
