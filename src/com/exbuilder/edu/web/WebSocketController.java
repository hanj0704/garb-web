package com.exbuilder.edu.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.websocket.server.ServerEndpoint;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;



@Controller()
@ServerEndpoint(value="/echo")
public class WebSocketController {

	protected static final String JSON = null;
	//private static final java.util.Set<Session> sessions = java.util.Collections.synchronizedSet(new java.util.HashSet<Session>());
	static List<Session> sessions = Collections.synchronizedList(new ArrayList<Session>());
	static Boolean runCheck = false;
	Timer timer1 = new Timer(true);
	int max = 100;
	int now = 0;
	 public WebSocketController() {
	        // TODO Auto-generated constructor stub
	        System.out.println("웹소켓(서버) 객체생성");
	        
	    }

    /**
     * @OnOpen allows us to intercept the creation of a new session.
     * The session class allows us to send data to the user.
     * In the method onOpen, we'll let the user know that the handshake was 
     * successful.
     */
    @OnOpen
    public void onOpen(final Session session){
        System.out.println("Open session id : " + session.getId());
      //  try {
            final Basic basic = session.getBasicRemote();
            //basic.sendText("Connection Established");
            sessions.add(session);
           
       // }
     
    }
    
    
    /**
     * 모든 사용자에게 메시지를 전달한다.
     * @param self
     * @param message
     */

    private void sendAllSessionToMessage(Session self, String message){
        try {

            for( Session session : WebSocketController.sessions ){

                if( ! self.getId().equals(session.getId()) )
                    session.getBasicRemote().sendText("All : " + message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * When a user sends a message to the server, this method will intercept the message
     * and allow us to react to it. For now the message is read as a String.
     */

    @OnMessage
    public void onMessage(String message, Session session) {
        
//        try {
//            final Basic basic = session.getBasicRemote();
//            basic.sendText("to : " + message);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
        //sendAllSessionToMessage( session, message );
    	System.out.println(message);
    	System.out.println(session);
    	
//    	TimerTask ts = new TimerTask() {
			
//			@Override
//			public void run() {
				// TODO Auto-generated method stub
		JSONParser parser = new JSONParser();
		// = parser.parse(message);
		JSONObject json = new JSONObject();
		json.put("max", max);
		try {
			JSONObject mesJson = (JSONObject) parser.parse(message);
			System.out.println(mesJson.keySet());
			json.put("now", mesJson.get("now"));
			List<Map<String, Object>> lists = new ArrayList<>();

			Map<String, Object> map1 = new HashMap<>();
			map1.put("column1", mesJson.get("column1"));
			map1.put("column2", mesJson.get("column2"));
			map1.put("column3", mesJson.get("column3"));
			// Map<String,Object> map2 = new HashMap<>();
			// map2.put("column1", "2");
			// map2.put("column2", "3");
			// map2.put("column3", "col44");
			// Map<String,Object> map3 = new HashMap<>();
			// map3.put("column1", "3");
			// map3.put("column2", "4");
			// map3.put("column3", "col555");
			lists.add(map1);
			// lists.add(map2);
			// lists.add(map3);
			json.put("ds1", lists);

		} catch (Exception e) {
			e.printStackTrace();
		}
		    	
		    	
		    	String res = json.toJSONString();
//		    	try {
//		    		json = (JSONObject) parser.parse(message);
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		    	int targetId = Integer.parseInt(json.get("target").toString());
		    	
//		    	Session session2 = sessions.get(targetId);
		    	try {
		    		sessions.get(0).getBasicRemote().sendText(res);
//					session.getBasicRemote().sendText(res);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
		    	
//			}
//		};
    	
		
//		timer1.scheduleAtFixedRate(ts, 0, 1*200);
    	
    }

   

    @OnError

    public void onError( Throwable e, Session session){
    }

    /**

     * The user closes the connection.
     * 
     * Note: you can't send messages to the client from this method
     */

    @OnClose

    public void onClose(Session session){
        System.out.println("Session " +session.getId()+" has ended");
        sessions.remove(session);
        timer1.cancel();
    }

	
}
