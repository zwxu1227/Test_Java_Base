package com.pak;

import jdk.nashorn.internal.ir.RuntimeNode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Hashtable;
import  java.util.Map;

@WebServlet(
        name="storeServlet",
        urlPatterns = "/shop"
)
public class StoreServlet extends HttpServlet {
    private  final Map<Integer,String> products=new Hashtable<>();
    public StoreServlet(){
        this.products.put(1, "Sandpaper");
        this.products.put(2, "Nails");
        this.products.put(3, "Glue");
        this.products.put(4, "Paint");
        this.products.put(5, "Tape");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action=req.getParameter("action");
        if(action==null)
            action="browse";
        switch (action)
        {
            case "addToCart":
             this.addToCart(req,resp);
             break;
            case "emptyCart":
                this.emptyCart(req,resp);
                break;
            case "viewCart":
                this.viewCart(req, resp);
                break;

            case "browse":
            default:
                this.browse(req, resp);
                break;
        }
        // super.doGet(req, resp);
    }

    private  void addToCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        int productId;
       try {
           productId=Integer.parseInt(req.getParameter("productId"));
       }catch (Exception ex)
       {
           resp.sendRedirect("shop");
           return;
       }

        HttpSession session=req.getSession();
       if(session.getAttribute("cart")==null)
       {
           session.setAttribute("cart",new Hashtable<Integer,Integer>());
       }
       @SuppressWarnings("unchecked")
       Map<Integer,Integer> cart=(Map<Integer, Integer>) session.getAttribute("cart");
       if(!cart.containsKey(productId))
           cart.put(productId,0);
       cart.put(productId,cart.get(productId)+1);
       resp.sendRedirect("shop?action=viewCart");
    }

    private  void emptyCart (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.getSession().removeAttribute("cart");
        resp.sendRedirect("shop?action=viewCart");
    }

    private  void viewCart(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        req.setAttribute("products",this.products);
        req.getRequestDispatcher("/WEB-INF/Hello_Shop/viewCart.jsp").forward(req,resp);
    }

    private  void browse(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        req.setAttribute("products",this.products);
        req.getRequestDispatcher("/WEB-INF/Hello_Shop/browse.jsp").forward(req,resp);
    }
}
