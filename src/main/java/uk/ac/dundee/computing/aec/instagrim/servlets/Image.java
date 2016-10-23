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
 * Servlet implementation class Image
 */
@WebServlet(urlPatterns = {
    "/Image",
    "/Image/*",
    "/Thumb/*",
    "/ThumbP",
    "/ThumbP/*",
    "/Images",
    "/Images/*",
    "/Comments/*"
})

@MultipartConfig
public class Image extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Cluster cluster;
    private HashMap CommandsMap = new HashMap();
    
    

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Image() {
        super();
        // TODO Auto-generated constructor stub
        CommandsMap.put("Image", 1);
        CommandsMap.put("Images", 2);
        CommandsMap.put("Thumb", 3);
        CommandsMap.put("Comments",4);
        CommandsMap.put("ThumbP",5);

    }
    @Override
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        String args[] = Convertors.SplitRequestPath(request);
        int command;
        try {
            command = (Integer) CommandsMap.get(args[1]);
        } catch (Exception et) {
            error("Bad Operator", response);
            return;
        }
        switch (command) {
            case 1:
                DisplayImage(Convertors.DISPLAY_PROCESSED,args[2], request, response);
                break;
            case 2:
                DisplayImageList(args[2], request, response);
                break;
            case 3:
                DisplayImage(Convertors.DISPLAY_THUMB,args[2], request,  response);
                break;
            case 4:
                DisplayImageComments(args[2], request, response);
                break;
            case 5:
                DisplayProfileImage(Convertors.DISPLAY_THUMB,args[2], request,  response);
                break;
            default:
                error("Bad Operator", response);
        }
    }

    private void DisplayImageList(String User, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PicModel tm = new PicModel();
        tm.setCluster(cluster);
        java.util.LinkedList<Pic> lsPics = tm.getPicsForUser(User);

        RequestDispatcher rd = request.getRequestDispatcher("/images.jsp");
        request.setAttribute("Pics", lsPics);
        rd.forward(request, response);
    }
    
    private void DisplayImageComments(String Image, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PicModel tm = new PicModel();
        tm.setCluster(cluster);
        Set<String> tmt = new HashSet<String>();
        tmt = tm.getComments(java.util.UUID.fromString(Image));
        
        RequestDispatcher rd = request.getRequestDispatcher("/comments.jsp");
        request.setAttribute("Pic", Image);
        request.setAttribute("commentdata",tmt);
        rd.forward(request, response);
    }
    
    private void DisplayImage(int type,String Image, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PicModel tm = new PicModel();
        tm.setCluster(cluster);
  
        Pic p = tm.getPic(type,java.util.UUID.fromString(Image));
        
        OutputStream out = response.getOutputStream();

        response.setContentType(p.getType());
        response.setContentLength(p.getLength());
        //out.write(Image);
        InputStream is = new ByteArrayInputStream(p.getBytes());
        BufferedInputStream input = new BufferedInputStream(is);
        byte[] buffer = new byte[8192];
        for (int length = 0; (length = input.read(buffer)) > 0;) {
            out.write(buffer, 0, length);
        }
        out.close();
        RequestDispatcher rd = request.getRequestDispatcher("/singleimage.jsp");;
        rd.forward(request, response);
    }
    
        private void DisplayProfileImage(int type,String name, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PicModel tm = new PicModel();
        tm.setCluster(cluster);
  
        Pic p = tm.getProPic(type, name);
        
        OutputStream out = response.getOutputStream();

        response.setContentType(p.getType());
        response.setContentLength(p.getLength());
        //out.write(Image);
        InputStream is = new ByteArrayInputStream(p.getBytes());
        BufferedInputStream input = new BufferedInputStream(is);
        byte[] buffer = new byte[8192];
        for (int length = 0; (length = input.read(buffer)) > 0;) {
            out.write(buffer, 0, length);
        }
        out.close();
        RequestDispatcher rd = request.getRequestDispatcher("/profile.jsp");;
        rd.forward(request, response);
    }
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String cmt = request.getParameter("commentjo");
        HttpSession hsession=request.getSession();
        String args[] = Convertors.SplitRequestPath(request);
        LoggedIn lg = (LoggedIn) hsession.getAttribute("LoggedIn");
        String pp = args[2];
        //String pp = (String) request.getAttribute("Pic");
        //System.out.println("string : "+pp);
        String uname = lg.getUsername();
        try {
            //String createkeyspace = "create keyspace if not exists instagrim  WITH replication = {'class':'SimpleStrategy', 'replication_factor':1}";
            String addComment = "UPDATE instagrim.Pics SET comments = comments + {'"+cmt+"'} WHERE picid = "+pp+";";
            //String Deleteanimage = "DELETE FROM instagrim.userpiclist WHERE picid = 552057e0-9589-11e6-a03a-3a0875798e52 AND user = '"+uname+"';";
            Session session = cluster.connect();
            try {
                SimpleStatement cqlQuery = new SimpleStatement(addComment);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("error deleting within Pics " + et);
            }
          //  String Deleteanimage2 = "DELETE FROM instagrim.Pics WHERE picid = "+pp+";";
            //String Deleteanimage2 = "DELETE FROM instagrim.Pics WHERE picid = 552057e0-9589-11e6-a03a-3a0875798e52;";
          //  try {
           //     SimpleStatement cqlQuery = new SimpleStatement(Deleteanimage2);
           //     session.execute(cqlQuery);
          //  } catch (Exception et) {
          //      System.out.println("error deleting within Pics " + et);
           // }
            session.close();
        } catch (Exception et) {
            System.out.println("Other keyspace or coulm definition error" + et);
        }
        doGet(request,response);
        //response.sendRedirect("/Instagrim");

    }

    private void error(String mess, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = null;
        out = new PrintWriter(response.getOutputStream());
        out.println("<h1>You have a na error in your input</h1>");
        out.println("<h2>" + mess + "</h2>");
        out.close();
        return;
    }
}
