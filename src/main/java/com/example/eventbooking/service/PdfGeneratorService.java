package com.example.eventbooking.service;

import com.example.eventbooking.model.Booking;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfGeneratorService {

    public byte[] generateBookingTicket(Booking booking) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document() {
                @Override
                public int getLength() {
                    return 0;
                }

                @Override
                public void addDocumentListener(DocumentListener listener) {

                }

                @Override
                public void removeDocumentListener(DocumentListener listener) {

                }

                @Override
                public void addUndoableEditListener(UndoableEditListener listener) {

                }

                @Override
                public void removeUndoableEditListener(UndoableEditListener listener) {

                }

                @Override
                public Object getProperty(Object key) {
                    return null;
                }

                @Override
                public void putProperty(Object key, Object value) {

                }

                @Override
                public void remove(int offs, int len) throws BadLocationException {

                }

                @Override
                public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {

                }

                @Override
                public String getText(int offset, int length) throws BadLocationException {
                    return null;
                }

                @Override
                public void getText(int offset, int length, Segment txt) throws BadLocationException {

                }

                @Override
                public Position getStartPosition() {
                    return null;
                }

                @Override
                public Position getEndPosition() {
                    return null;
                }

                @Override
                public Position createPosition(int offs) throws BadLocationException {
                    return null;
                }

                @Override
                public Element[] getRootElements() {
                    return new Element[0];
                }

                @Override
                public Element getDefaultRootElement() {
                    return null;
                }

                @Override
                public void render(Runnable r) {

                }
            };

            // Title
            document.add(new Paragraph("Event Ticket").setFontSize(20).setBold());

            // Event Details
            document.add(new Paragraph("Event: " + booking.getEvent().getName()));
            document.add(new Paragraph("Date: " + booking.getEvent().getEventDate()));
            document.add(new Paragraph("Location: " + booking.getEvent().getLocation()));

            // User Details
            document.add(new Paragraph("Attendee: " + booking.getAttendee().getName()));
            document.add(new Paragraph("Email: " + booking.getAttendee().getEmail()));

            document.close();
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }
}

