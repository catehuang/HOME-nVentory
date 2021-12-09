package servlets;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import services.AccountService;
import services.InventoryService;

public class ReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        AccountService as = new AccountService();
        User user = as.get(email);
        request.setAttribute("user", user);

        // get all users in database
        List<User> all_users = as.getAll();
        request.setAttribute("all_users", all_users);

        // utilities
        InventoryService is = new InventoryService();
        request.setAttribute("is", is);

        if (request.getParameter("action") != null) {
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet spreadsheet = wb.createSheet("Inventory Summary");

            Map<String, Object[]> userData = new TreeMap<String, Object[]>();

            userData.put("1", new Object[]{"Email Address", "Number of items", "Total value of items"});
            int counter = 1;
            for (User u : all_users) {
                String rowNumber = String.valueOf(counter + 1);
                String u_email = u.getEmail();
                String total_number;
                try {
                    total_number = String.valueOf(is.getNumberOfItems(u_email));
                    String total_value = String.valueOf(is.getValueOfItems(u_email));
                    userData.put(rowNumber, new Object[]{u_email, total_number, total_value});
                } catch (Exception ex) {
                    Logger.getLogger(ReportServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            Set<String> keyid = userData.keySet();
            int rowid = 0;

            for (String key : keyid) {
                Row row = spreadsheet.createRow(rowid++);
                Object[] objectArr = userData.get(key);
                int cellid = 0;

                for (Object obj : objectArr) {
                    Cell cell = row.createCell(cellid++);
                    if (obj instanceof String) {
                        cell.setCellValue((String) obj);
                    }
                    if (obj instanceof Double) {
                        cell.setCellValue((Double) obj);
                    }
                    if (obj instanceof Integer) {
                        cell.setCellValue((Integer) obj);
                    }
                }
            }

            try {
                FileOutputStream out = new FileOutputStream(new File("report.xlsx"));
                wb.write(out);
                out.close();
                System.out.println("Excel file exported");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        getServletContext().getRequestDispatcher("/WEB-INF/report.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
