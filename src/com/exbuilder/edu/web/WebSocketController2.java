package com.exbuilder.edu.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.server.ServerEndpoint;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;

@Controller()
@ServerEndpoint("/echo2")
public class WebSocketController2 {

	protected static final String JSON = null;
	//private static final java.util.Set<Session> sessions = java.util.Collections.synchronizedSet(new java.util.HashSet<Session>());
	static List<Session> sessions = Collections.synchronizedList(new ArrayList<Session>());
	static Boolean runCheck = false;
	Timer timer1 = new Timer(true);
	int max = 100;
	int now = 0;
	 public WebSocketController2() {
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
        
    	System.out.println(message);
    	System.out.println(session);
    	
		JSONParser parser = new JSONParser();
		// = parser.parse(message);
		JSONObject json = new JSONObject();
		    	String res = message;
		    	
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
