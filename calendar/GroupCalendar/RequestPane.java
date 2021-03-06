package GroupCalendar;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Calendar.DB.JsonMaster;
import Calendar.DB.SendToDB;
import Calendar.Data.GroupData;
import Calendar.Data.MessageData;
import MonthCalendar.CalendarMain;

public class RequestPane extends JFrame{
	
	private JPanel RequestPanel;
	
	private static Calendar.DB.SendToDB stDB;
	private static String url, Googleid, result;
	private JsonMaster jm;
	
	public ArrayList<MessageData> messages = new ArrayList<MessageData>();
	public ArrayList<MessageData> getMessages() {return messages;}
	public void setMessages(ArrayList<MessageData> messages) {this.messages = messages;}
	
	private ArrayList<MessageData>request_GroupList;
	public ArrayList<MessageData> getRequest_GroupList() {return request_GroupList;}
	private ArrayList<MessageData>request_ScheduleList;
	public ArrayList<MessageData> getRequest_ScheduleList() {return request_ScheduleList;}

	
	private Font f=new Font("���� ����",Font.BOLD,20);

	private GroupPane gmain;
	
	public RequestPane(GroupPane gmain) {
		this.gmain = gmain;
		RequestPanel = new JPanel();
		RequestPanel.setLayout(null);
		
		freshPanel();

	}
	
	

	public void freshPanel() {
		freshMessage();
		
		Request_GroupTable requestGTable=new Request_GroupTable(request_GroupList,gmain);
		RequestPanel.add(requestGTable.getRequestPane());
		
		Request_EventTable requestETable=new Request_EventTable(request_ScheduleList,gmain);
		RequestPanel.add(requestETable.getRequestPane());
		
	}
	private void freshMessage() {
		selectMyInviteToDB();
		
		request_GroupList=new ArrayList<MessageData>();
		request_ScheduleList=new ArrayList<MessageData>();
		
		//messageŸ�Ժ��� ���� model�� add
		if(messages!=null){
			for(int i=0;i<messages.size();i++){
				MessageData message=messages.get(i);
				if(message.getData(3).equals("groupInvite")){
					request_GroupList.add(message);
				}
				else if(message.getData(3).equals("groupSchedule")){
					request_ScheduleList.add(message);
				}
			}
		}
	}
	
	
	public JPanel getRequestPanel() {return RequestPanel;}
	
	
	private void deleteInviteToDB(String sql) {
	      String url = "http://113.198.84.67/Calendar3S/DeleteMessage.php";

	      SendToDB stDB = new SendToDB(url, sql);
	      stDB.start();
	      try {
	         stDB.join();
	      } catch (InterruptedException e) {
	         System.out.println(e.toString());
	      }
	}
	
	   private void selectMyInviteToDB() {
	      String url = "http://113.198.84.67/Calendar3S/SelectMyMessage.php";
	      String sql = "receiver='" + 
					CalendarMain.getInstanace().getUser().getData(0) + "'";
	      
	      SendToDB stDB = new SendToDB(url, sql);
	      stDB.start();
	      try {
	         stDB.join();
	      } catch (InterruptedException e) {
	         System.out.println(e.toString());
	      }
	      
	      String result = stDB.getResult();// Json������ String�� ������
	      System.out.println(result);

	      jm = new JsonMaster();
	      jm.onPostExecute("SelectMyMessage", result);
	      this.messages = jm.getMessages();
	   }
}
