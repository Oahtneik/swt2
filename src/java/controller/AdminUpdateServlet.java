/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Category;
import model.Product;

/**
 *
 * @author hii
 */
@WebServlet(name="AdminUpdateServlet", urlPatterns={"/update"})
public class AdminUpdateServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AdminUpdateServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminUpdateServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
    String id_raw = request.getParameter("id");
    DAO cdb = new DAO();

    try {
        Product c = cdb.getProductById(id_raw);
        request.setAttribute("product", c);
        request.getRequestDispatcher("updateProduct.jsp").forward(request, response);
    } catch (NumberFormatException e) {
        e.printStackTrace(); // Print stack trace to help debug
    }
}

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
    DAO d = new DAO();
    request.setCharacterEncoding("UTF-8");
    
    // Get data from form
    String id = request.getParameter("id");
    String name = request.getParameter("name");
    String describe = request.getParameter("describe");
    String image = request.getParameter("image");
    
    // Add try-catch for potential number format exceptions
    double price = 0;
    int quantity = 0;
    int categoryID = 0;

    try {
        price = Double.parseDouble(request.getParameter("price"));
        quantity = Integer.parseInt(request.getParameter("quantity"));
        categoryID = Integer.parseInt(request.getParameter("productCategoryID"));
    } catch (NumberFormatException e) {
        e.printStackTrace(); // Print stack trace to help debug
        // You can add some user-friendly error handling here if needed
    }

    // Get category by ID
    Category c = d.getCategoryByID(categoryID);
    
    try {
        // Create updated product
        Product pNew = new Product(id, name, describe, image, price, quantity, c);
        d.update(pNew);
        response.sendRedirect("admin");
    } catch (Exception e) {
        e.printStackTrace(); // Print stack trace to help debug
        // Optionally, you can add some error message handling to show user-friendly messages
    }
}


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
