package ch.bbv.blog.client;

import java.util.Collection;

import ch.bbv.blog.shared.FieldVerifier;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwtTests implements EntryPoint {
    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network " + "connection and try again.";

    /**
     * Create a remote service proxy to talk to the server-side Greeting
     * service.
     */
    private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
    private final BlogServiceAsync blogService = GWT.create(BlogService.class);

    /**
     * This is the entry point method.
     */
    @Override
    public void onModuleLoad() {
        // Create the popup dialog box
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText("Remote Procedure Call");
        dialogBox.setAnimationEnabled(true);
        final Button closeButton = new Button("Close");
        // We can set the id of a widget by accessing its Element
        closeButton.getElement().setId("closeButton");
        final Label textToServerLabel = new Label();
        final HTML serverResponseLabel = new HTML();
        VerticalPanel dialogVPanel = new VerticalPanel();
        dialogVPanel.addStyleName("dialogVPanel");
        dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
        dialogVPanel.add(textToServerLabel);
        dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
        dialogVPanel.add(serverResponseLabel);
        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        dialogVPanel.add(closeButton);
        dialogBox.setWidget(dialogVPanel);

        final ListBox listBox = new ListBox();
        final TextArea reviewTextFiled = new TextArea();
        final Button sendButton = new Button("Send");
        final TextBox nameField = new TextBox();
        nameField.setText("GWT User");
        final Label errorLabel = new Label();

        // We can add style names to widgets
        sendButton.addStyleName("sendButton");

        // Add the nameField and sendButton to the RootPanel
        // Use RootPanel.get() to get the entire body element
        RootPanel rootPanel = RootPanel.get("nameFieldContainer");

        TabPanel tabPanel = new TabPanel();
        rootPanel.add(tabPanel, 92, 219);
        tabPanel.setSize("573px", "231px");

        Grid grid = new Grid(3, 2);
        tabPanel.add(grid, "New Entry", false);
        grid.setSize("5cm", "3cm");

        Label lblUser = new Label("User");
        grid.setWidget(0, 0, lblUser);

        final TextBox usernameTextBox = new TextBox();
        usernameTextBox.setName("userName");
        grid.setWidget(0, 1, usernameTextBox);

        Label lblEntry = new Label("Entry");
        grid.setWidget(1, 0, lblEntry);

        final TextArea newEntryTextBox = new TextArea();
        newEntryTextBox.setVisibleLines(5);
        newEntryTextBox.setName("newEntry");
        grid.setWidget(1, 1, newEntryTextBox);
        newEntryTextBox.setSize("497px", "121px");

        Button btnCancel = new Button("Cancel");
        grid.setWidget(2, 0, btnCancel);

        Button btnSubmit = new Button("Submit");
        grid.setWidget(2, 1, btnSubmit);

        SplitLayoutPanel splitLayoutPanel = new SplitLayoutPanel();
        tabPanel.add(splitLayoutPanel, "Review", false);
        splitLayoutPanel.setSize("560px", "170px");

        splitLayoutPanel.addWest(listBox, 100.0);
        // TODO somewhere else...
        listBox.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                String userName = listBox.getValue(listBox.getSelectedIndex());
                blogService.getNextEntryForUser(userName, new AsyncCallback<String>() {

                    @Override
                    public void onFailure(Throwable caught) {
                        dialogBox.setText("Remote Procedure Call - Failure");
                        serverResponseLabel.addStyleName("serverResponseLabelError");
                        serverResponseLabel.setHTML(SERVER_ERROR);
                        dialogBox.center();
                        closeButton.setFocus(true);
                    }

                    @Override
                    public void onSuccess(String result) {
                        reviewTextFiled.setText(result);
                    }

                });
            }
        });

        listBox.addFocusHandler(new FocusHandler() {

            @Override
            public void onFocus(FocusEvent event) {
                listBox.clear();
                blogService.getAllBlogsToReview(new AsyncCallback<Collection<String>>() {

                    @Override
                    public void onFailure(Throwable caught) {
                        dialogBox.setText("Remote Procedure Call - Failure");
                        serverResponseLabel.addStyleName("serverResponseLabelError");
                        serverResponseLabel.setHTML(SERVER_ERROR);
                        dialogBox.center();
                        closeButton.setFocus(true);
                    }

                    @Override
                    public void onSuccess(Collection<String> result) {
                        for (String string : result) {
                            listBox.addItem(string);
                        }

                    }
                });
            }
        });

        listBox.setVisibleItemCount(5);

        Grid grid_1 = new Grid(3, 2);
        splitLayoutPanel.add(grid_1);

        Label lblReviewer = new Label("Reviewer");
        grid_1.setWidget(0, 0, lblReviewer);

        final TextBox reviewerNameTxt = new TextBox();
        grid_1.setWidget(0, 1, reviewerNameTxt);

        Label lblEntry_1 = new Label("Entry");
        grid_1.setWidget(1, 0, lblEntry_1);

        grid_1.setWidget(1, 1, reviewTextFiled);
        reviewTextFiled.setSize("390px", "84px");

        final Button btnDeny = new Button("Deny");
        btnDeny.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                blogService.deny(listBox.getValue(listBox.getSelectedIndex()), reviewTextFiled.getText(),
                        reviewerNameTxt.getText(), new AsyncCallback<Boolean>() {

                            @Override
                            public void onFailure(Throwable caught) {
                                // Show the RPC error message to the user
                                dialogBox.setText("Remote Procedure Call - Failure");
                                serverResponseLabel.addStyleName("serverResponseLabelError");
                                serverResponseLabel.setHTML(SERVER_ERROR);
                                dialogBox.center();
                                closeButton.setFocus(true);
                            }

                            @Override
                            public void onSuccess(Boolean result) {
                                reviewTextFiled.setText("");
                                reviewerNameTxt.setText("");
                            }
                        });
            }
        });
        grid_1.setWidget(2, 0, btnDeny);

        final Button btnAccept = new Button("Accept");
        grid_1.setWidget(2, 1, btnAccept);
        btnAccept.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                blogService.accept(listBox.getValue(listBox.getSelectedIndex()), reviewTextFiled.getText(),
                        reviewerNameTxt.getText(), new AsyncCallback<Boolean>() {

                            @Override
                            public void onFailure(Throwable caught) {
                                // Show the RPC error message to the user
                                dialogBox.setText("Remote Procedure Call - Failure");
                                serverResponseLabel.addStyleName("serverResponseLabelError");
                                serverResponseLabel.setHTML(SERVER_ERROR);
                                dialogBox.center();
                                closeButton.setFocus(true);
                            }

                            @Override
                            public void onSuccess(Boolean result) {
                                reviewTextFiled.setText("");
                                reviewerNameTxt.setText("");
                            }
                        });
            }
        });
        rootPanel.add(nameField);
        RootPanel.get("sendButtonContainer").add(sendButton);
        RootPanel.get("errorLabelContainer").add(errorLabel);

        // Focus the cursor on the name field when the app loads
        nameField.setFocus(true);
        nameField.selectAll();

        class SendPostHandler implements ClickHandler, KeyUpHandler {

            @Override
            public void onKeyUp(KeyUpEvent event) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onClick(ClickEvent event) {
                postBlogEntry();
                usernameTextBox.setText("");
                newEntryTextBox.setText("");

            }

            private void postBlogEntry() {
                String username = usernameTextBox.getText();
                String blogValue = newEntryTextBox.getText();
                blogService.post(username, blogValue,
                        defaultCallbackHandler(dialogBox, closeButton, serverResponseLabel));

            }

            private AsyncCallback<String> defaultCallbackHandler(final DialogBox dialogBox, final Button closeButton,
                    final HTML serverResponseLabel) {
                return new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        // Show the RPC error message to the user
                        dialogBox.setText("Remote Procedure Call - Failure");
                        serverResponseLabel.addStyleName("serverResponseLabelError");
                        serverResponseLabel.setHTML(SERVER_ERROR);
                        dialogBox.center();
                        closeButton.setFocus(true);
                    }

                    @Override
                    public void onSuccess(String result) {
                        dialogBox.setText("Remote Procedure Call");
                        serverResponseLabel.removeStyleName("serverResponseLabelError");
                        serverResponseLabel.setHTML(result);
                        dialogBox.center();
                        closeButton.setFocus(true);
                    }
                };
            }

        }

        // Create a handler for the sendButton and nameField
        class MyHandler implements ClickHandler, KeyUpHandler {
            /**
             * Fired when the user clicks on the sendButton.
             */
            @Override
            public void onClick(ClickEvent event) {
                sendNameToServer();
            }

            /**
             * Fired when the user types in the nameField.
             */
            @Override
            public void onKeyUp(KeyUpEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    sendNameToServer();
                }
            }

            /**
             * Send the name from the nameField to the server and wait for a
             * response.
             */
            private void sendNameToServer() {
                // First, we validate the input.
                errorLabel.setText("");
                String textToServer = nameField.getText();
                if (!FieldVerifier.isValidName(textToServer)) {
                    errorLabel.setText("Please enter at least four characters");
                    return;
                }

                // Then, we send the input to the server.
                sendButton.setEnabled(false);
                textToServerLabel.setText(textToServer);
                serverResponseLabel.setText("");
                greetingService.greetServer(textToServer, new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        // Show the RPC error message to the user
                        dialogBox.setText("Remote Procedure Call - Failure");
                        serverResponseLabel.addStyleName("serverResponseLabelError");
                        serverResponseLabel.setHTML(SERVER_ERROR);
                        dialogBox.center();
                        closeButton.setFocus(true);
                    }

                    @Override
                    public void onSuccess(String result) {
                        dialogBox.setText("Remote Procedure Call");
                        serverResponseLabel.removeStyleName("serverResponseLabelError");
                        serverResponseLabel.setHTML(result);
                        dialogBox.center();
                        closeButton.setFocus(true);
                    }
                });
            }
        }
        // Add a handler to close the DialogBox
        closeButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                dialogBox.hide();
                sendButton.setEnabled(true);
                sendButton.setFocus(true);
            }
        });

        // Add a handler to send the name to the server
        MyHandler handler = new MyHandler();
        sendButton.addClickHandler(handler);

        btnSubmit.addClickHandler(new SendPostHandler());

        nameField.addKeyUpHandler(handler);
    }
}
