
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/chat")
public class Chat {
    private static Set<Session> userSessions = Collections.newSetFromMap(new ConcurrentHashMap<Session, Boolean>());
    
    @OnOpen
    public void onOpen(Session curSession)
    {
        userSessions.add(curSession);
        System.out.println("open");
    }
            
    @OnClose
    public void onClose(Session curSession)
    {
        userSessions.remove(curSession);
        System.out.println("close");
    }
    
    @OnMessage
    public void onMessage(String message, Session userSession)
    {
        for(Session ses : userSessions)
        {
            ses.getAsyncRemote().sendText("User"+userSession.getId()+":"+message);
        }
    }
}
