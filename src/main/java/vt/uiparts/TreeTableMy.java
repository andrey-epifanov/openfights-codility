package vt.uiparts;

import com.vaadin.data.util.FilesystemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.TreeTable;
import vt.main.UImy;

import java.io.File;

/**
 * Created by Андрей on 26.04.2015.
 */
public class TreeTableMy {

    private TreeTable treetable;

    public void initFileTree(ComponentContainer parentLayout, final UImy uimy) {
        // Создадим объект TreeTable для отображения иерархических данных в табличном виде
        treetable = new TreeTable("File System");
        treetable.setSelectable(true);
        treetable.setColumnCollapsingAllowed(true);
        treetable.setColumnReorderingAllowed(true);
        treetable.setSizeFull();
//        treetable.setHeight("70%");
//        treetable.setHeight("50%");
//        treetable.setHeight(50.0F, Unit.PERCENTAGE);
        treetable.setHeight(100, Sizeable.Unit.MM); // should be calc height of current browser window => to this

        // Добавляем обработчик нажатия
        treetable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                String clickedFilename = itemClickEvent.getItemId().toString(); // Элемент, на котором была нажата кнопка мыши
                System.out.println("ItemClick: pathname:" + clickedFilename);

                // Если двойной клик
                if (itemClickEvent.isDoubleClick()) {
                    uimy.doChangeDir(clickedFilename);
                } else {
                    uimy.doSelectFile(clickedFilename);
                }
            }
        });

        parentLayout.addComponent(treetable);
    }

    public void updateFileTree(File sourcePath) {
        // Создаем контейнер файловой системы
        FilesystemContainer currentFileSystem = new FilesystemContainer(sourcePath);
        currentFileSystem.setRecursive(false); // Отключаем рекурсивное считывание подкаталогов

        // Связываем его с объектом TreeTable, отображающим файловую систему
        treetable.setContainerDataSource(currentFileSystem);
        treetable.setItemIconPropertyId("Icon");
        treetable.setVisibleColumns(new Object[]{"Name", "Size", "Last Modified"}); // Для того чтобы скрыть колонку с идентификатором иконки, укажем нужные колонки
    }

}
