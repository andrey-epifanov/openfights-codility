package vt.uiparts;

import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.*;

import java.io.File;
import java.util.Arrays;

/**
 * Created by Андрей on 26.04.2015.
 */
public class ViewPanel {

    /* -- Graphical Panel - View Panel ------ */
    private HorizontalLayout previewLayout;
    private Embedded previewEmbedded;

//    // Инициализация основной панели, содержащей просмотр файловой структуры и предварительный просмотр файла
//    public void initMainPanels(VerticalLayout parentLayout, UImy uimy, TreeTableMy treeTableMy) {
//        HorizontalSplitPanel mainPanels = new HorizontalSplitPanel();
//        mainPanels.setSizeFull();
//        parentLayout.addComponent(mainPanels);
//        parentLayout.setExpandRatio(mainPanels, 1);
//
//        treeTableMy.initFileTree(mainPanels, uimy); // TODO : why is it in here ? - disable it
//        initPreview(mainPanels);
//    }

    // Инициализация панели предварительного просмотра файла
    public void initPreview(ComponentContainer parentLayout) {
        previewLayout = new HorizontalLayout();
        previewLayout.setSizeFull();
        parentLayout.addComponent(previewLayout);

        // Создаем элемент для предпросмотра изображений
        // Создаем объект Embedded
        previewEmbedded = new Embedded("Preview area", null);
        // Задаем видимость
        previewEmbedded.setVisible(true);
        // Добавляем в компоновку
        previewLayout.addComponent(previewEmbedded);
        // Располагаем по центру
        previewLayout.setComponentAlignment(previewEmbedded, Alignment.MIDDLE_CENTER);
    }

    // Скрыть предварительный просмотр файла
    private void clearPreview() {
        previewEmbedded.setSource(null);
        previewEmbedded.setVisible(true);
    }

    // Обновить предварительный просмотр файла
    public void updatePreview(String pathname) {
        if (pathname == null || pathname.length() == 0) {
            clearPreview();
            return;
        }
        // Выделим расширение файла
        File file = new File(pathname);
        int lastIndexOf = pathname.lastIndexOf(".");
        String extension = (lastIndexOf == -1) ? "" : pathname.substring(lastIndexOf);
        // Ограничение на размер файла для предпросмотра — до 128 Кб
        final int PREVIEW_FILE_LIMIT = 10 * 128 * 1024;
        // Расширения файлов для предпросмотра с помощью объекта Embedded (изображения, Flash и так далее)
        final String[] imageExtensions = new String[]{
                ".gif", ".jpeg", ".jpg", ".png", ".bmp", ".ico", ".cur", "swf", "svg"
        };
        // Скроем объект, используемый для предпросмотра
        previewEmbedded.setVisible(false);
        // Проверим, не превышает ли размер файла пороговый
        if (file.length() > PREVIEW_FILE_LIMIT) {
            clearPreview();
            return;
        }
        // Если расширение файла — в списке изображений
        if (Arrays.asList(imageExtensions).contains(extension)) {
            Resource resource = new FileResource(file); // Создаем файловый ресурс
            previewEmbedded.setSource(resource);        // Задаем источник для объекта Embedded
            previewEmbedded.setVisible(true);           // Показываем объект
            previewLayout.setExpandRatio(previewEmbedded, 1.0f); // Будет занимать все доступное место
        }
    }

}
