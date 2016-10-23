/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;


import com.datastax.driver.core.Cluster;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;

/**
 *
 * @author craigwatt
 */
@WebServlet(name = "delete", urlPatterns = {"/Delete/*","/Delete"})
public class delete extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Cluster c=null;
    private HashMap CommandsMap = new HashMap();
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        c = CassandraHosts.getCluster();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        
        HttpSession hsession=request.getSession();
        String args[] = Convertors.SplitRequestPath(request);
        LoggedIn lg = (LoggedIn) hsession.getAttribute("LoggedIn");
        String pp = args[2];
        //String pp = (String) request.getAttribute("Pic");
        System.out.println("string : "+pp);
        String uname = lg.getUsername();
        try {
            //String createkeyspace = "create keyspace if not exists instagrim  WITH replication = {'class':'SimpleStrategy', 'replication_factor':1}";
            String Deleteanimage = "DELETE FROM instagrim.userpiclist WHERE picid = "+pp+" AND user = '"+uname+"';";
            //String Deleteanimage = "DELETE FROM instagrim.userpiclist WHERE picid = 552057e0-9589-11e6-a03a-3a0875798e52 AND user = '"+uname+"';";
            Session session = c.connect();
            try {
                SimpleStatement cqlQuery = new SimpleStatement(Deleteanimage);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("error deleting within userpiclist " + et);
            }
            String Deleteanimage2 = "DELETE FROM instagrim.Pics WHERE picid = "+pp+";";
            //String Deleteanimage2 = "DELETE FROM instagrim.Pics WHERE picid = 552057e0-9589-11e6-a03a-3a0875798e52;";
            try {
                SimpleStatement cqlQuery = new SimpleStatement(Deleteanimage2);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("error deleting within Pics " + et);
            }
            session.close();
        } catch (Exception et) {
            System.out.println("Other keyspace or coulm definition error" + et);
        }
        response.sendRedirect("/Instagrim");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException { 
        RequestDispatcher
        rd = request.getRequestDispatcher("/delete.jsp");
        rd.forward(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
