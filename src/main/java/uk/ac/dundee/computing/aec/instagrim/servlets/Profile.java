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
import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author craigwatt
 */
@WebServlet(name = "Profile", urlPatterns = {"/Profile","/Profile/*"})

@MultipartConfig
public class Profile extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Cluster cluster;
    private HashMap CommandsMap = new HashMap();
        /**
     * @see HttpServlet#HttpServlet()
     */
    public Profile() {
        super();
        // TODO Auto-generated constructor stub
        CommandsMap.put("Profile", 1);
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException { 
        
        String args[] = Convertors.SplitRequestPath(request);
        int command;
        try {
            command = (Integer) CommandsMap.get(args[1]);
        } catch (Exception et) {
            //error("Bad Operator", response);
            return;
        }
        switch (command) {
            case 1:
                gatherProfile(Convertors.DISPLAY_THUMB, args[2], request, response);
                break;
            default:
                //error("Bad Operator", response);
        }
        //RequestDispatcher
        //rd = request.getRequestDispatcher("/profile.jsp");
        //rd.forward(request, response);
    }
    
    
    private void gatherProfile(int type, String Name, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PicModel tm = new PicModel();
        tm.setCluster(cluster);
        Set<String> tmt = new HashSet<String>();
        Pic proPic = tm.getProPic(type, Name);
        tmt = tm.getProfile(Name);
        
      //  OutputStream out = response.getOutputStream();

       // response.setContentType(proPic.getType());
       // response.setContentLength(proPic.getLength());
        //out.write(Image);
      //  InputStream is = new ByteArrayInputStream(proPic.getBytes());
       // BufferedInputStream input = new BufferedInputStream(is);
       // byte[] buffer = new byte[8192];
       // for (int length = 0; (length = input.read(buffer)) > 0;) {
       //     out.write(buffer, 0, length);
       // }
       // out.close();
        
        RequestDispatcher rd = request.getRequestDispatcher("/profile.jsp");
        request.setAttribute("Pic", proPic);
        request.setAttribute("setdata",tmt);
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
        if(request.getParameter("picture")!=null){
            //HttpSession session=request.getSession();
            response.sendRedirect("/Instagrim/UploadP");
        }
        if(request.getParameter("name")!=null){
            response.sendRedirect("/Instagrim/ChangeProfileN");
        }
        if(request.getParameter("email")!=null){
            response.sendRedirect("/Instagrim/ChangeProfileE");
        }
        if(request.getParameter("addemail")!=null){
            response.sendRedirect("/Instagrim/ChangeProfileEA");
        }
    }
    
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
