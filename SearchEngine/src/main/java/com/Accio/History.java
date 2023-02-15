package com.Accio;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/History")
public class History extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        try{
            //got connection to mysql
            Connection connection = DatabaseConnection.getConnection();
            //get results from history table
            ResultSet resultSet = connection.createStatement().executeQuery("Select * from history");
            ArrayList<HistoryResult> results = new ArrayList<HistoryResult>();
            //iterate through resultset to store entries inside results arraylist
            while(resultSet.next()) {
            String keyword = resultSet.getString("keyword");
                String link = resultSet.getString("link");
                HistoryResult historyResult = new HistoryResult(keyword,link);
                results.add(historyResult);
            }
//            while(resultSet.next()){
//                HistoryResult historyResult = new HistoryResult();
//                historyResult.setKeyword(resultSet.getString("keyword"));
//                historyResult.setLink(resultSet.getString("searchLink"));
//                results.add(historyResult);
//            }
            //set attribute of request with results arraylist
            request.setAttribute("results", results);
            //forward the req to History.jsp
            request.getRequestDispatcher("/History.jsp").forward(request, response);
            response.setContentType("/html");
            //Writer created for displaying in history.jsp
            PrintWriter out= response.getWriter();
        }catch (SQLException | ServletException | IOException sqlException){
            sqlException.printStackTrace();
        }

    }
}