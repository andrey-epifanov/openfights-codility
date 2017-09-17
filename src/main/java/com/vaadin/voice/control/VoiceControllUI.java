package com.vaadin.voice.control;

import javax.servlet.annotation.WebServlet;

import com.google.speech.VoiceManager;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.voice.control.backend.Contact;
import com.vaadin.voice.control.backend.ContactService;
import com.vaadin.voice.control.tab.RatesInfoTab;
import com.vaadin.voice.control.tab.VoiceControlTab;
import com.vaadin.ui.*;
import com.vaadin.v7.data.util.BeanItemContainer;
import com.vaadin.v7.ui.Grid;
import com.vaadin.v7.ui.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vt.audiorecord.AudioRecorder;

/* User Interface written in Java.
 *
 * Define the user interface shown on the Vaadin generated web page by extending the UI class.
 * By default, a new UI instance is automatically created when the page is loaded. To reuse
 * the same instance, add @PreserveOnRefresh.
 */
@Title("Voice-Control")
@Theme("valo")
@Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
public class VoiceControllUI extends UI {
    private static final Logger logger = LoggerFactory.getLogger(VoiceControllUI.class);

    private TabSheet tabsheet = new TabSheet();

    private AudioRecorder recorder = new AudioRecorder();;
    private VoiceManager voiceManager = new VoiceManager();

    TextField filterContacts = new TextField();

    private VoiceControlTab voiceControlTab;

    /*
     * Hundreds of widgets. Vaadin's user interface components are just Java
     * objects that encapsulate and handle cross-browser support and
     * client-server communication. The default Vaadin components are in the
     * com.vaadin.ui package and there are over 500 more in
     * vaadin.com/directory.
     */
    Grid contactList = new Grid();

    // ContactForm is an example of a custom component class
    ContactForm contactForm = new ContactForm();

    // ContactService is a in-memory mock DAO that mimics
    // a real-world datasource. Typically implemented for
    // example as EJB or Spring Data based service.
    ContactService service = ContactService.createDemoService();

    /*
     * The "Main method".
     *
     * This is the entry point method executed to initialize and configure the
     * visible user interface. Executed on every browser reload because a new
     * instance is created for each web page loaded.
     */
    @Override
    protected void init(VaadinRequest request) {
        configureComponents();
        buildLayout();
    }

    private void configureComponents() {
        /*
         * Synchronous event handling.
         *
         * Receive user interaction events on the server-side. This allows you
         * to synchronously handle those events. Vaadin automatically sends only
         * the needed changes to the web page without loading a new page.
         */

        contactList
                .setContainerDataSource(new BeanItemContainer<>(Contact.class));
        contactList.setColumnOrder("firstName", "lastName", "email");
        contactList.removeColumn("id");
        contactList.removeColumn("birthDate");
        contactList.removeColumn("phone");
        contactList.setSelectionMode(Grid.SelectionMode.SINGLE);
        contactList.addSelectionListener(
                e -> contactForm.edit((Contact) contactList.getSelectedRow()));
        refreshContacts();
    }

    /*
     * Robust layouts.
     *
     * Layouts are components that contain other components. HorizontalLayout
     * contains TextField and Button. It is wrapped with a Grid into
     * VerticalLayout for the left side of the screen. Allow user to resize the
     * components with a SplitPanel.
     *
     * In addition to programmatically building layout in Java, you may also
     * choose to setup layout declaratively with Vaadin Designer, CSS and HTML.
     */
    private void buildLayout() {
        HorizontalLayout mainLayout = new HorizontalLayout(tabsheet, contactForm);
        mainLayout.setSizeFull();
        mainLayout.setExpandRatio(tabsheet, 1);

//        tabsheet.addTab(generateCreditCardsInfo(),
//                "Кредитные Карты",
//                new ThemeResource("./src/main/resources/img/ratesInfo.bmp"));

//        tabsheet.addTab(generateDepositInfo(),
//                "Депозиты",
//                new ThemeResource("./src/main/resources/img/ratesInfo.bmp"));

        RatesInfoTab ratesInfoTab = new RatesInfoTab(recorder, voiceManager);
        tabsheet.addTab(ratesInfoTab.generateRatesInfo(),
                "Курсы Валют",
                new ThemeResource("./src/main/resources/img/ratesInfo.bmp"));

        tabsheet.addTab(generateTabContacts(),
                "Контакты",
                new ThemeResource("img/planets/Mercury_symbol.png"));

        voiceControlTab = new VoiceControlTab(recorder, voiceManager);
        tabsheet.addTab(voiceControlTab.generateTabVoiceControl(),
                "VoiceControl",
                new ThemeResource("img/planets/Mercury_symbol.png"));

        // Split and allow resizing
        setContent(mainLayout);
    }

    /** Контакты
     *
     * @return
     */
    private VerticalLayout generateTabContacts() {
        filterContacts.setInputPrompt("Filter contacts...");
        filterContacts.addTextChangeListener(e -> refreshContacts(e.getText()));

        Button btnNewContact = new Button("Новый Контакт");
        btnNewContact.addClickListener(e -> contactForm.edit(new Contact()));

        HorizontalLayout actions = new HorizontalLayout(
                filterContacts,
                btnNewContact
        );

        actions.setWidth("100%");
        filterContacts.setWidth("100%");
        actions.setExpandRatio(filterContacts, 1);

        VerticalLayout left = new VerticalLayout(actions, contactList);
        left.setSizeFull();
        contactList.setSizeFull();
        left.setExpandRatio(contactList, 1);

        VerticalLayout tab = new VerticalLayout();
        tab.addComponent(new Image(null,
                new ThemeResource("img/planets/Mercury.jpg")));

        tab.addComponent(left);

        return tab;
    }

    /*
     * Choose the design patterns you like.
     *
     * It is good practice to have separate data access methods that handle the
     * back-end access and/or the user interface updates. You can further split
     * your code into classes to easier maintenance. With Vaadin you can follow
     * MVC, MVP or any other design pattern you choose.
     */
    void refreshContacts() {
        refreshContacts(filterContacts.getValue());
    }

    private void refreshContacts(String stringFilter) {
        contactList.setContainerDataSource(new BeanItemContainer<>(
                Contact.class, service.findAll(stringFilter)));
        contactForm.setVisible(false);
    }

    /*
     * Deployed as a Servlet or Portlet.
     *
     * You can specify additional servlet parameters like the URI and UI class
     * name and turn on production mode when you have finished developing the
     * application.
     */
    @WebServlet(urlPatterns = "/*")
    @VaadinServletConfiguration(ui = VoiceControllUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

}
