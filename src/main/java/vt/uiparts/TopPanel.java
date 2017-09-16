package vt.uiparts;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import vt.main.UImy;

import java.io.File;

/**
 * Created by Андрей on 26.04.2015.
 */
public class TopPanel {
    /* ---- Top Panel  -----------------*/
    private Label labelFileName;

    // Инициализация верхней панели, содержащей кнопки и текущий путь / выбранный файл
    public void initTopPanel(Layout parentLayout, final UImy uimy) {
        // Создаем новую горизонтальную компоновку, которая будет служить панелью инструментов
        HorizontalLayout topPanelLayout = new HorizontalLayout();
        // Растягиваем на 100% доступной ширины
        topPanelLayout.setWidth("100%");
        // Между элементами будет пустое пространство
        topPanelLayout.setSpacing(true);
        // Добавляем к основной компоновке
        parentLayout.addComponent(topPanelLayout);

        // Создаем кнопку Refresh
        // Создаем сам объект
        Button refreshButton = new Button("Refresh");
        // Задаем иконку из FontAwesome
        refreshButton.setIcon(FontAwesome.REFRESH);
        // Есть стили разных размеров
        refreshButton.addStyleName(ValoTheme.BUTTON_SMALL);
        // Добавляем в компоновку
        topPanelLayout.addComponent(refreshButton);
        // Добавляем обработчик нажатия
        refreshButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                uimy.doRefresh();
            }
        });

        // Создаем кнопку Up Level
        // Создаем сам объект
        refreshButton = new Button("Up Level");
        // Задаем иконку из FontAwesome
        refreshButton.setIcon(FontAwesome.ARROW_UP);
        // Есть стили разных размеров
        // button.addStyleName(ValoTheme.BUTTON_SMALL);
        // Добавляем в компоновку
        topPanelLayout.addComponent(refreshButton);
        // Добавляем обработчик нажатия
        refreshButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                uimy.doUpLevel();
            }
        });

        // Добавляем текст с именем выбранного файла
        // Создаем сам объект
        labelFileName = new Label();
        // Добавляем в компоновку
        topPanelLayout.addComponent(labelFileName);
        topPanelLayout.setComponentAlignment(labelFileName, Alignment.MIDDLE_CENTER);
        // Данный компонент будет занимать все доступное место
        topPanelLayout.setExpandRatio(labelFileName, 1);
    }

    // Обновление верхней панели
    public void updateTopPanel(File currentPath, String selectedFilename) {
        if (selectedFilename != null) {
            labelFileName.setValue(selectedFilename);
        } else {
            labelFileName.setValue(currentPath.toString());
        }
    }

}
