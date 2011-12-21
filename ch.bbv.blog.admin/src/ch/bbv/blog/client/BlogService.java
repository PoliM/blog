package ch.bbv.blog.client;

import java.util.Collection;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("blog")
public interface BlogService extends RemoteService {

    String post(String username, String blogValue);

    Collection<String> getAllBlogsToReview();

    String getNextEntryForUser(String userName);

    boolean accept(String value, String text, String text2);

    boolean deny(String value, String text, String text2);

}
