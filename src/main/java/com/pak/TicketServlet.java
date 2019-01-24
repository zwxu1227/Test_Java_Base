package com.pak;

import jdk.nashorn.internal.ir.RuntimeNode;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet(
        name = "ticketServlet",
        urlPatterns = {"/tickets"},
        loadOnStartup = 1
)
@MultipartConfig(
        fileSizeThreshold = 5_242_880,//5MB
        maxFileSize = 20_971_520L,//20MB
        maxRequestSize = 41_943_040L //40MB
)
public class TicketServlet extends HttpServlet {
    private volatile int TICKET_ID_SEQUENCE = 1;
    private Map <Integer, Ticket> ticketDateBase = new LinkedHashMap <>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action==null)
            action="";
        switch (action)
        {
            case "create":
                this.showTicketForm(req, resp);
                break;
            case "view":
                this.viewTicket(req, resp);
                break;
            case "download":
                this.downloadAttachment(req, resp);
                break;
            case "list":
            default:
                this.listTickets(req, resp);
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        switch (action) {
            case "create":
                this.createTicket(req, resp);
                break;
            case "list":
            default:
                resp.sendRedirect("tickets");
                break;
        }
    }
   //下单
    private void showTicketForm(HttpServletRequest req,HttpServletResponse resp)throws ServletException, IOException{
        req.getRequestDispatcher("/WEB-INF/Customer_Ticket/ticketForm.jsp").forward(req,resp);
    }
   //票信息
    private  void viewTicket(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{

        String idString=req.getParameter("ticketId");
        Ticket ticket=this.getTicket(idString,resp);
        if(ticket==null)
            return;
        req.setAttribute("ticketId",idString);
        req.setAttribute("ticket",ticket);
        req.getRequestDispatcher("/WEB-INF/Hello_EL/viewTicket.jsp").forward(req,resp);

    }

    private  void listTickets(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        req.setAttribute("ticketDatabase",this.ticketDateBase);
        req.getRequestDispatcher("/WEB-INF/Hello_EL/listTicket.jsp").forward(req,resp);
    }
   //现在附件
    private void downloadAttachment(HttpServletRequest req,HttpServletResponse resp) throws  ServletException,IOException{
        String idString=req.getParameter("ticketId");
        Ticket ticket=this.getTicket(idString,resp);
        if(ticket==null)
            return;
        String name=req.getParameter("attachment");
        if(name==null) {
            resp.sendRedirect("tickets?action=view&ticketId=" + idString);
            return;
        }
        Attachment attachment=ticket.getAttachment(name);
        if(attachment==null)
        {
            resp.sendRedirect("tickets?action=view&ticketId=" + idString);
            return;
        }
        resp.setHeader("Content-Disposition","attachment; filename="+attachment.getName());
        resp.setContentType("application/octet-stream");
        ServletOutputStream stream=resp.getOutputStream();
        stream.write(attachment.getContents());
    }

    //创建票
    private void createTicket(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Ticket ticket = new Ticket();
        ticket.setSubject(req.getParameter("subject"));
        ticket.setBody(req.getParameter("body"));
        ticket.setCustomerName(req.getParameter("customerName"));
        Part filePart = req.getPart("file1");
        if (filePart != null && filePart.getSize() > 0) {
            Attachment attachment = this.processAttachment(filePart);
            if (attachment != null)
                ticket.addAttachment(attachment);
        }
        int id;
        //同步代码快
        synchronized (this) {
            id = this.TICKET_ID_SEQUENCE++;
            this.ticketDateBase.put(id,ticket);
        }

        resp.sendRedirect("tickets?action=view&ticketId="+ id+"");
    }

    private Attachment processAttachment(Part filePart) throws IOException {
        InputStream inputStream = filePart.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int read;
        final byte[] bytes = new byte[1024];
        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
        Attachment attachment = new Attachment();
        attachment.setContents(outputStream.toByteArray());
        attachment.setName(filePart.getSubmittedFileName());
        return attachment;
    }

    private Ticket getTicket(String idString, HttpServletResponse resp)throws ServletException,IOException
    {
        if(idString==null|| idString.length()==0)
        {
            resp.sendRedirect("tickets");
            return null;
        }
        try {
            Ticket ticket=this.ticketDateBase.get(Integer.parseInt(idString));
            if(ticket==null)
            {
                resp.sendRedirect("tickets");
                return  null;
            }
            return ticket;
        }catch (Exception ex)
        {
            resp.sendRedirect("tickets");
            return null;
        }
    }
}
