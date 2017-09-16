package vt.uiparts;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;

import java.io.File;
import java.util.Date;

/**
 * Created by Андрей on 26.04.2015.
 */
public class BottomPanel {

    /* -- Bottom Panel -- */
    private Label[] bottomLabels;

    public void initBottomPanel(Layout parentLayout) {
        final String[] captions = new String[]{
                "File Size (Bytes)", "File Date", "Usable Space (Bytes)", "Total Space (Bytes)", "Free Space (Bytes)"
        };

        HorizontalLayout bottomPanelLayout = new HorizontalLayout();
        // Растягиваем на 100% доступной ширины
        bottomPanelLayout.setWidth("100%");
        parentLayout.addComponent(bottomPanelLayout);

        // Создаем объекты Label для отображения информации о файле
        bottomLabels = new Label[captions.length];
        for (int i = 0; i < captions.length; i++) {
            bottomLabels[i] = new Label();
            bottomLabels[i].setCaption(captions[i]);
            bottomLabels[i].setValue("NA");
            bottomPanelLayout.addComponent(bottomLabels[i]);
        }
    }

    // Обновление нижней панели
    public void updateBottomPanel(String pathname) {
        try {
            File file = new File(pathname);
            // Присваиваем значения объектам Label — информация о файле
            bottomLabels[0].setValue(Long.toString(file.length()));
            bottomLabels[1].setValue((new Date(file.lastModified())).toString());
            // Информация о диске
            bottomLabels[2].setValue(Long.toString(file.getUsableSpace()));
            bottomLabels[3].setValue(Long.toString(file.getTotalSpace()));
            bottomLabels[4].setValue(Long.toString(file.getFreeSpace()));
        } catch (Exception e) {
            // Скроем исключительную ситуацию
            for (Label bottomLabel : bottomLabels) {
                bottomLabel.setValue("NA");
            }
        }
    }
}
