package ch.bbv.blog.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.bbv.blog.client.BlogService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class BlogServiceImpl extends RemoteServiceServlet implements BlogService {

    private final Map<String, List<String>> userToEntries = new HashMap<String, List<String>>();
    private final Map<String, List<String>> acceptedEntries = new HashMap<String, List<String>>();

    @Override
    public String post(String username, String blogValue) {
        List<String> entriesOfUser = userToEntries.get(username);
        if (null == entriesOfUser) {
            entriesOfUser = new ArrayList<String>();
            userToEntries.put(username, entriesOfUser);
        }
        entriesOfUser.add(blogValue);
        return "Entry Added";
    }

    @Override
    public Collection<String> getAllBlogsToReview() {
        return new ArrayList<String>(userToEntries.keySet());
    }

    @Override
    public String getNextEntryForUser(String userName) {
        List<String> list = userToEntries.get(userName);
        if (!list.isEmpty()) {
            return list.iterator().next();
        } else {
            return "";
        }
    }

    @Override
    public boolean accept(String user, String text, String reviewerName) {
        System.out.println("Accepted" + text);
        boolean remove = userToEntries.get(user).remove(text);
        if (remove) {
            List<String> list = acceptedEntries.get(user);
            if (null == list) {
                list = new ArrayList<String>();
                acceptedEntries.put(user, list);
            }
            list.add(text);
        }
        if (userToEntries.get(user).isEmpty()) {
            userToEntries.remove(user);
        }
        return remove;
    }

    @Override
    public boolean deny(String user, String text, String reviewerName) {
        System.out.println("Denied" + text);
        boolean remove = userToEntries.get(user).remove(text);
        if (userToEntries.get(user).isEmpty()) {
            userToEntries.remove(user);
        }
        return remove;
    }

    @Override
    public Collection<String> getAllEntries() {
        List<String> entries = new ArrayList<String>();
        for (List<String> val : acceptedEntries.values()) {
            entries.addAll(val);
        }
        return entries;
    }
}
