package com.Accio;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.Connection;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/Search")
public class Search extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        //get parameter called keyword from request
        String keyword = request.getParameter("keyword");
        System.out.println(keyword);
        try {
            //establish or get connection to the db
            Connection connection = DatabaseConnection.getConnection();
            //save keyword and link associated with history table
            PreparedStatement preparedStatement=connection.prepareStatement("Insert into history values(?,?)");
            //we get the keyword from index.jsp
            //stored at first parameter index
            preparedStatement.setString(1,keyword);
            preparedStatement.setString(2,"http://localhost:8080/SearchEngine/Search?keyword="+keyword);
            preparedStatement.executeUpdate();
            //executing a query related to keyword and get the results
            ResultSet resultSet = connection.createStatement().executeQuery("select pageTitle, pageLink, (length(lower(pageText)) - length(replace(lower(pageText),'" + keyword + "','')))/length('" + keyword + "') as countoccurence from pages order by countoccurence desc limit 30;");

            ArrayList<SearchResult> results = new ArrayList<>();
            //iterate through result set and save all the elements in results array
            while (resultSet.next()) {
                SearchResult searchResult = new SearchResult();
                //get pagetitle
                searchResult.setPageTitle(resultSet.getString("pageTitle"));
                //get pagelink
                searchResult.setPageLink(resultSet.getString("pageLink"));
                results.add(searchResult);
            }
            //display results array in console
            for(SearchResult searchResult:results)
            {
                System.out.println(searchResult.getPageTitle()+" "+searchResult.getPageLink()+"\n");
            }
            // set the attribute of request with results arraylist
            request.setAttribute("results",results);
            //forward request to frontend i.e. search.jsp
            request.getRequestDispatcher("/search.jsp").forward(request, response);

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
        }
        catch(SQLException | IOException | ServletException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}
