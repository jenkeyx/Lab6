import App.Character;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Response implements Serializable {
    private String message;
    private List<Character> collection;
    Response(String message, List<Character> collection){
        this.message = message;
        if (this.collection != null){
            this.collection = Collections.synchronizedList(collection);
        }
    }



    static Response createResponse(String message){
        return new Response(message,null);
    }
    Response createFullResponse(String message, List<Character> collection){
        return new Response(message, collection);
    }

    public String getMessage() {
        return message;
    }

    public List<Character> getCollection() {
        return collection;
    }
}
