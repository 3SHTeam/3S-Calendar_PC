package GroupCalendar;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Calendar.Data.GroupData;
import Calendar.UI.FMypage;
import Calendar.UI.Images;
import MonthCalendar.CalendarMain;

public class GroupMain extends JFrame implements ActionListener {
	private JPanel contentPane;
	private JSplitPane GroupPanel; // 그룹 정보(왼쪽 그룹이름, 오른쪽 해당 그룹의 스캐줄)
	private JPanel RequestPanel;

	private GroupPane groupPane = new GroupPane();
	private RequestPane eRequest;

	private static CalendarMain calendar; // 스캐줄 정보를 가져오기 위해

	private JButton mypageBtn;
	private JButton calendarBtn;
	private JButton AddGroupBtn;
	private JButton logoutBtn;
	private Add_Group MakeGroup;
	private Vector<GroupData> allGroup_Vec = new Vector<GroupData>();

	//public static final GroupMain instance = new GroupMain(calendar);
	
	public GroupMain() {
		this.calendar = CalendarMain.getInstanace();
		setTitle("Group");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1230, 850);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		Image img = Images.mainIcon.getImage();
		contentPane = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(img, 0, 0, 1230, 850, null);
			}
		};
		calendarBtn = new JButton(Images.CalendarIcon);
		calendarBtn.setBounds(321, 730, 110, 50);
		calendarBtn.addActionListener(this);

		AddGroupBtn = new JButton(Images.AddGroupIcon);
		AddGroupBtn.setBounds(479, 730, 110, 50);
		AddGroupBtn.addActionListener(this);

		mypageBtn = new JButton(Images.ProFileIcon);
		mypageBtn.setBounds(625, 730, 110, 50);
		mypageBtn.addActionListener(this);

		logoutBtn = new JButton(Images.LogoutIcon);
		logoutBtn.setBounds(780, 730, 110, 50);
		logoutBtn.addActionListener(this);

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException ex) {
				}

				groupPane = new GroupPane();
				GroupPanel = groupPane.getGroupSpiltPanel();
				allGroup_Vec = groupPane.getAllGroupVec();

				contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				setContentPane(contentPane);
				contentPane.setLayout(null);
				setLocationRelativeTo(null);

				contentPane.add(GroupPanel);
				contentPane.add(calendarBtn);
				contentPane.add(AddGroupBtn);
				contentPane.add(mypageBtn);
				contentPane.add(logoutBtn);
			}
		});
	}

	public void freshpanel() {
		
		// tab의 group부분
		groupPane.freshpanel();
		GroupPanel = groupPane.getGroupSpiltPanel();
		allGroup_Vec = groupPane.getAllGroupVec();

//		 tab의 request부분
//		eRequest.freshPanel();
//		RequestPanel = eRequest.getRequestPanel();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mypageBtn) {
			FMypage mypage = new FMypage(CalendarMain.getInstanace().getUser());
			mypage.setVisible(true);
			dispose();
		}

		if (e.getSource() == calendarBtn) {
			calendar.refreshSchedule();
			calendar.freshTag();
			calendar.refreshTag();
			calendar.setVisible(true);
			dispose();
		}

		if (e.getSource() == AddGroupBtn) {
			MakeGroup = new Add_Group(groupPane);
			MakeGroup.setVisible(true);
		}
		if (e.getSource() == logoutBtn) {

		}
	}
	
//	public static GroupMain getInstanace(){
//		return instance;
//	}

}
