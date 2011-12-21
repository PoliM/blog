package ch.bbv.blog.client;

import java.util.Collection;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface BlogServiceAsync {

    void post(String username, String blogValue, AsyncCallback<String> callback);

    void getAllBlogsToReview(AsyncCallback<Collection<String>> asyncCallback);

    void getNextEntryForUser(String userName, AsyncCallback<String> asyncCallback);

    void accept(String value, String text, String text2, AsyncCallback<Boolean> asyncCallback);

    void deny(String value, String text, String text2, AsyncCallback<Boolean> asyncCallback);

    void getAllEntries(AsyncCallback<Collection<String>> asyncCallback);

}
