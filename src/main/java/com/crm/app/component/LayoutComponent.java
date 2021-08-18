package com.crm.app.component;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayoutVariant;

public class LayoutComponent {

    private SplitLayout mainLayout;
    private SplitLayout secondLayout;
    private VerticalLayout formLayout;
    private VerticalLayout gridLayout;
    private VerticalLayout listBoxLayout;

    public LayoutComponent() {

        secondLayout = new SplitLayout();
        secondLayout.addThemeVariants(SplitLayoutVariant.LUMO_SMALL);
        secondLayout.setSplitterPosition(50);
        secondLayout.setMaxHeight("450px");

        mainLayout = new SplitLayout();
        mainLayout.addThemeVariants(SplitLayoutVariant.LUMO_SMALL);
        mainLayout.setSplitterPosition(15);
        mainLayout.addToSecondary(secondLayout);

        listBoxLayout = new VerticalLayout(new Label("Tables"));

        gridLayout = new VerticalLayout(new Label("Table Data"));

        formLayout = new VerticalLayout(new Label("Create/Update table"));
        formLayout.setMaxHeight("450px");
    }
    public SplitLayout getMainLayout() {
        return mainLayout;
    }

    public SplitLayout getSecondLayout() {
        return secondLayout;
    }

    public VerticalLayout getFormLayout() {
        return formLayout;
    }

    public VerticalLayout getGridLayout() {
        return gridLayout;
    }

    public VerticalLayout getListBoxLayout() {
        return listBoxLayout;
    }
}
