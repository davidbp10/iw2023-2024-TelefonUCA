package es.uca.iw.telefonuca.ticket.domain;

public enum TicketStatus {
    PENDING_ANSWER_BY_STAFF, PENDING_ANSWER_BY_CUSTOMER, ANSWERED, CLOSED;

    @Override
    public String toString() {
        return name().toUpperCase().replace('_', ' ');
    }
}
