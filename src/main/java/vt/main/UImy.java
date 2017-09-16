package vt.main;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.*;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import vt.audiorecord.AudioRecorder;
import vt.uiparts.*;

import java.io.File;

/**
 *
 */
@Theme("mytheme")
@Widgetset("vt.MyAppWidgetset")
public class UImy extends UI {

    private File currentPath;

    private AudioRecorder recorder = new AudioRecorder();;

    private MenuBarMy menuBarMy = new MenuBarMy();
    private TopPanel topPanel = new TopPanel();
    private TreeTableMy treeTableMy = new TreeTableMy();
    private ViewPanel viewPanel = new ViewPanel();
    private BottomPanel bottomPanel = new BottomPanel();
    private WebCamMy webCamMy = new WebCamMy();
    private MediaPlayerMy mediaPlayerMy = new MediaPlayerMy();

    public UImy() {}

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);

        Button btnOneClick = new Button("Click Me");
        btnOneClick.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                layout.addComponent(new Label("Thank you for clicking"));
                getDefaultDirectory();
                treeTableMy.updateFileTree(currentPath);
            }
        });

        Button btnAudioRecordClick = new Button("Audio Rec. Click Me!");
        btnAudioRecordClick.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                layout.addComponent(new Label("Thank you for clicking"));
                recorder.execute();
            }
        });

        menuBarMy.initMenuBar(layout, this);
        topPanel.initTopPanel(layout, this);
        layout.addComponent(btnOneClick);
        layout.addComponent(btnAudioRecordClick);
        treeTableMy.initFileTree(layout, this);
        viewPanel.initPreview(layout);
//        webCamMy.initWebCam(layout, this); // not work
        mediaPlayerMy.initMediaPlayer(layout);

        bottomPanel.initBottomPanel(layout);

        getDefaultDirectory();
        treeTableMy.updateFileTree(currentPath);
    }

    private String selectedFilename;

    // Пользовательское действие — обновление каталога
    public void doRefresh() {
        updateAll();
    }

    // Пользовательское действие — переход в другой каталог
    public void doChangeDir(String path) {
        currentPath = new File(path);
        if (currentPath.isDirectory()) {
            selectedFilename = null;
            updateAll();
        }
    }

    // Пользовательское действие — переход в каталог на уровень выше
    public void doUpLevel() {
        currentPath = currentPath.getParentFile();
        selectedFilename = null;
        updateAll();
    }

    // Пользовательское действие — выбор файла
    public void doSelectFile(String filename) {
        selectedFilename = filename;
        updateInfo();
    }

// Вспомогательная функция для получения каталога приложения по умолчанию
// ~/NetBeansProjects/fileman/target/fileman-1.0-SNAPSHOT/
    private void getDefaultDirectory() {
        //UI ui = MyVaadinUI.getCurrent();
        UI ui = this;
        VaadinSession session = ui.getSession();
        VaadinService service = session.getService();
        currentPath = service.getBaseDirectory();
    }

    // Обновление всех элементов
    private void updateAll() {
        treeTableMy.updateFileTree(currentPath);
        updateInfo();
    }

    // Обновление информации о файле/каталоге (при изменении файла/каталога)
    private void updateInfo() {
        menuBarMy.updateMenuBar();
        topPanel.updateTopPanel(currentPath, selectedFilename);
        bottomPanel.updateBottomPanel(selectedFilename);
        viewPanel.updatePreview(selectedFilename);
        mediaPlayerMy.update(selectedFilename);
    }


    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = UImy.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
