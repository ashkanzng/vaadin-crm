package com.crm.app.component;

import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class HeaderComponent {

    private HorizontalLayout header;

    public HeaderComponent() {
        this.header = new HorizontalLayout();
        this.header.getStyle().set("border-bottom", "1px solid #EEEEEE").set("padding-bottom", "10px");
        header.add(new H4("SQLite schema tables"));
    }

    public HorizontalLayout getHeader() {
        return header;
    }
}
