<%@page import="java.io.OutputStream"%><%@page import="org.apache.poi.hssf.usermodel.HSSFWorkbook"%><%@page import="org.apache.poi.ss.usermodel.Workbook"%><% Workbook workbook = null; if (request.getAttribute("ExcelContent") != null && request.getAttribute("ExcelName") != null ){ String ExcelName = request.getAttribute("ExcelName").toString(); response.setContentType("application/vnd.ms-excel");response.setHeader("Content-Transfer-Encoding", "7bit"); response.setHeader("Content-Disposition", "inline; filename ="+ExcelName+".xls");OutputStream os = response.getOutputStream(); workbook = (HSSFWorkbook) request.getAttribute("ExcelContent");workbook.write(os);os.flush();}%>