package vt.uiparts;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import vt.main.UImy;

/**
 * Created by Андрей on 26.04.2015.
 */
public class MenuBarMy {

    /*----Menu bar------*/
    public void initMenuBar(Layout parentLayout, final UImy uimy) {
        // Описание объекта MenuBar
        // https://vaadin.com/book/-/page/components.menubar.html

        // Создаем главное меню
        MenuBar menuBar = new MenuBar();    // Создаем объект
        //menuBar.setWidth("100%");           // Растягиваем на 100% доступной ширины
        menuBar.setSizeFull(); // ?

        parentLayout.addComponent(menuBar); // Добавляем в layout

        // Добавляем в главное меню подменю File
        final MenuBar.MenuItem fileMenuItem = menuBar.addItem("File", null, null);

        // Добавляем в меню File элемент Refresh и обработчик при его выборе
        fileMenuItem.addItem("Refresh", FontAwesome.REFRESH, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                uimy.doRefresh();
            }
        });

        // Добавляем в меню File элемент Up Level и обработчик при его выборе
        fileMenuItem.addItem("Up Level", FontAwesome.ARROW_UP, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                uimy.doUpLevel();
            }
        });
    }


    public void updateMenuBar() {
        // Пока ничего не делать
    }

}
