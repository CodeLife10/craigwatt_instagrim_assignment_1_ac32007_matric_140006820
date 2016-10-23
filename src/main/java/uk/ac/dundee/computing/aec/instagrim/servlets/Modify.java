/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aec.instagrim.models.PicModel;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;

/**
 *
 * @author craigwatt
 */
@WebServlet(name = "Modify", urlPatterns = {"/ChangeProfileN", "/ChangeProfileE", "/ChangeProfileEA"})

@MultipartConfig
public class Modify extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Cluster cluster;
    private HashMap CommandsMap = new HashMap();

        @Override
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String args[] = Convertors.SplitRequestPath(request);
        String namechange = "namechange";
        String editemail = "editemail";
        String addemail = "addemail";
        String ur = args[1];
        if(ur.equals("ChangeProfileN")){
            request.setAttribute("editp", namechange);
        }
        if(ur.equals("ChangeProfileE")){
            request.setAttribute("editp", editemail);
        }
        if(ur.equals("ChangeProfileEA")){
            request.setAttribute("editp", addemail);
        }
        RequestDispatcher rd = request.getRequestDispatcher("/edit.jsp");
        rd.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String fname = "";
            String sname = "";

            fname = request.getParameter("First Name");
            sname = request.getParameter("Last Name");
            if(fname.equals(null) || sname.equals(null)){
                response.sendRedirect("/Instagrim");
            }

            if(!fname.equals("") && !sname.equals("")){
                HttpSession session=request.getSession();
                LoggedIn lg= (LoggedIn)session.getAttribute("LoggedIn");
                String username="majed";
                if (lg.getlogedin()){
                    username=lg.getUsername();
                }
                else{response.sendRedirect("/Instagrim");}
                PicModel tm = new PicModel();
                tm.setCluster(cluster);
                tm.setflname(fname, sname, username);
                response.sendRedirect("/Instagrim/Profile/"+username);
                }
            else{response.sendRedirect("/Instagrim");} 
        }   
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
