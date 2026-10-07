package com.chibachimi.springdmtools.experimental;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("/editor_test")
public class TableEditorView extends VerticalLayout {

    public TableEditorView() {
        Table testTable = Table.newDefaultTable();

        TableEditor editor = new TableEditor();
        editor.editTable(testTable);

        add(editor);
    }
}
