package es.uca.iw.telefonuca.ticket.views;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import es.uca.iw.telefonuca.MainLayout;
import es.uca.iw.telefonuca.ticket.domain.Ticket;
import es.uca.iw.telefonuca.ticket.domain.TicketMessage;
import es.uca.iw.telefonuca.ticket.services.TicketManagementService;
import es.uca.iw.telefonuca.user.security.AuthenticatedUser;
import jakarta.annotation.security.RolesAllowed;

import java.time.LocalDateTime;
import java.util.List;

@RolesAllowed({"CUSTOMER_SERVICE", "ADMIN"})
@PageTitle("Responder ticket")
@Route(value = "/tickets/reply", layout = MainLayout.class)
public class NewTicketMessageView extends Composite<VerticalLayout> {

    private final TicketManagementService ticketManagementService;

    ComboBox<Ticket> ticketComboBox = new ComboBox<>("Ticket");
    TextArea content = new TextArea("Contenido del mensaje");
    private Grid<TicketMessage> ticketMessageGrid;
    Button saveButton = new Button("Guardar", event -> saveTicket());
    Button resetButton = new Button("Limpiar", event -> clearFields());

    public NewTicketMessageView(TicketManagementService ticketManagementService, AuthenticatedUser authenticatedUser) {
        this.ticketManagementService = ticketManagementService;

        List<Ticket> tickets = ticketManagementService.loadAll();
        ticketComboBox.setItems(tickets);
        ticketComboBox.setItemLabelGenerator(Ticket::getSubject);
        ticketMessageGrid = new Grid<>(TicketMessage.class);
        ticketMessageGrid.setColumns("id", "content", "date");

        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        FormLayout formLayout2Col = new FormLayout();
        HorizontalLayout layoutRow = new HorizontalLayout();

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        h3.setText("Nuevo mensaje de ticket");
        h3.setWidth("100%");
        formLayout2Col.setWidth("100%");
        ticketComboBox.setLabel("Ticket");
        content.setLabel("Contenido del mensaje");
        saveButton.setWidth("min-content");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        resetButton.setWidth("min-content");
        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(ticketComboBox);
        formLayout2Col.add(content);
        formLayout2Col.add(ticketMessageGrid);
        layoutColumn2.add(layoutRow);
        layoutRow.add(saveButton);
        layoutRow.add(resetButton);

        ticketComboBox.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                List<TicketMessage> ticketMessages = ticketManagementService
                        .loadTicketMessageByTicketId(event.getValue().getId());
                ticketMessageGrid.setItems(ticketMessages);
            } else {
                List<TicketMessage> ticketMessages = ticketManagementService.loadAllTicketMessages();
                ticketMessageGrid.setItems(ticketMessages);
            }
        });
    }

    private void saveTicket() {
        Ticket ticket = ticketComboBox.getValue();

        TicketMessage ticketMessage = new TicketMessage();
        ticketMessage.setTicketId(ticket.getId());
        ticketMessage.setContent(content.getValue());
        ticketMessage.setDate(LocalDateTime.now());

        try {
            ticketManagementService.saveTicketMessage(ticketMessage);
            Notification.show("Ticket message saved successfully!");
        } catch (Exception e) {
            Notification.show("Failed to save ticket message: " + e.getMessage(), 5000, Notification.Position.MIDDLE);
        }
    }

    private void clearFields() {
        ticketComboBox.clear();
        content.clear();
    }

}
