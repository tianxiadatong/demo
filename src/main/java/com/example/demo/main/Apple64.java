package com.example.demo.main;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class Apple64 {
	static JMenu file2;

	static JPanel pane;
	static int[] arr;
	static JButton[] but;

	public static int[] shuzhu(int[] arr) {
		Random ran = new Random();
		arr = new int[64];

		for (int i = 1; i < 65; i++) {
			arr[i - 1] = i;
		}
		for (int i = 0; i < 64; i++) {
			int t = ran.nextInt(64);
			int tmp = arr[i];
			arr[i] = arr[t];
			arr[t] = tmp;
		}
		return arr;

	}

	public static void main(String[] args) throws Exception, Exception, Exception, Exception {

		JFrame frame = new JFrame();

		pane = new JPanel();

		pane.setLayout(new GridLayout(8,8));
		but = new JButton[64];
		for (int i : Apple64.shuzhu(arr)) {

			String str = String.valueOf(i);
			but[i - 1] = new JButton(str);
			pane.add(but[i - 1]);

		}
		frame.add(pane);
		JMenuBar bar = new JMenuBar();
		JMenu file = new JMenu("刷新");
		file2 = new JMenu("成绩");

		JMenuItem ji = new JMenuItem("刷新");
		file.add(ji);
		bar.add(file);
		bar.add(file2);
		frame.setJMenuBar(bar);
		ActionListener al = new ActionListener() {
			int 开始 = 0;
			int 结束 = 0;
			String timess = "";
			String timemm = "";

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String str = arg0.getActionCommand();

				Date date = new Date();

				if ("刷新".equals(str)) {

					开始 = 0;
					结束 = 0;
					Random ran = new Random();
					arr = new int[64];

					for (int i = 1; i < 65; i++) {
						arr[i - 1] = i;
					}
					for (int i = 0; i < 64; i++) {
						int t = ran.nextInt(64);
						int tmp = arr[i];
						arr[i] = arr[t];
						arr[t] = tmp;
					}
					for (int i = 0; i < 64; i++) {
						String str1 = String.valueOf(arr[i]);
						but[i].setText(str1);
					}

				}
				if ("1".equals(str)) {

					DateFormat formatstart = new SimpleDateFormat("ss");
					DateFormat formatstart1 = new SimpleDateFormat("mm");
					DateFormat formatstart2 = new SimpleDateFormat("mm:ss");
					timess = formatstart.format(date);
					timemm = formatstart1.format(date);
					String time = formatstart2.format(date);
					System.out.println(time);

				} else if ("64".equals(str)) {

					DateFormat formatstart2 = new SimpleDateFormat("mm:ss");
					String time = formatstart2.format(date);
					System.out.println(time);
					DateFormat format = new SimpleDateFormat("ss");
					DateFormat format1 = new SimpleDateFormat("mm");
					String timess1 = format.format(date);
					String timemm1 = format1.format(date);

					// stu.frame.setVisible(true);
					try {
						file2.setText(String.valueOf(Integer.parseInt(timemm1)*60+Integer.parseInt(timess1) - Integer.parseInt(timemm)*60-Integer.parseInt(timess)) + "秒");

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

		};
		for (int i = 0; i < 64; i++) {
			but[i].addActionListener(al);
		}
		ji.addActionListener(al);
		file.addActionListener(al);
		frame.setSize(480, 480);
		frame.setLocation(400, 20);
		// frame.set
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}
