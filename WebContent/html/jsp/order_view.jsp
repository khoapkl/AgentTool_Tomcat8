
<%@page import="java.math.BigDecimal"%><link rel="stylesheet" href="/styles/global.css" type="text/css" />
<%@page import="java.util.List"%>
<%@page import="com.dell.enterprise.agenttool.model.OrderViewPending"%>
<%@page import="com.dell.enterprise.agenttool.model.Customer"%>
<%@page import="com.dell.enterprise.agenttool.model.Agent"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="java.util.ArrayList"%>
<%@include file="/html/scripts/order_view_pendingScript.jsp"%>
<%
Agent agentClear =(Agent)session.getAttribute(Constants.AGENT_INFO);
Boolean isAdmin =(Boolean)session.getAttribute(Constants.IS_ADMIN);
String userLevel =(String)session.getAttribute(Constants.USER_LEVEL);
    OrderViewPending orderViewPending = null;
	OrderViewPending orderViewPendingIdEnter=null;	
	String title=request.getAttribute(Constants.TITLE_ORDER_NUMBER) == null ? "" : (String) request.getAttribute(Constants.TITLE_ORDER_NUMBER);
	String order_id_pending=request.getAttribute(Constants.TITLE_ORDER_NUMBER) == null ? "" : (String) request.getAttribute(Constants.TITLE_ORDER_NUMBER);
	String order_id = "";
	if(request.getAttribute(Constants.ORDER_ID_PENDING) != null)
	{
	    order_id = request.getAttribute(Constants.ORDER_ID_PENDING).toString().trim();   
	}
	 	
	String check_track_number= request.getAttribute(Constants.CHECK_TRACK_NUMBER) == null ? "" : request.getAttribute(Constants.CHECK_TRACK_NUMBER).toString();
	float orginalPrice = new Float(0);
	String shopperId="";
	if(request.getAttribute(Constants.SHOPPER_ID)!=null)
	{
	    shopperId=(String) request.getAttribute(Constants.SHOPPER_ID);
	}
	
%>	

	<%
		if(!title.equals("")||(!title.isEmpty())){	    
		%>  
		  <script>
		 $('#pageTitle').html("Order Number: <%=order_id%><font color=black>---------------------------------------</font>Customer Number:<%=title%>");
		 $('#topTitle').html("Order Number: <%=order_id%><br>Customer Number:<%=title%>");
		 </script>
		    
	<%	}
		else{
		%>    
		 <script>
		 $('#pageTitle').html("Order Number: <%=order_id%><font color=black>---------------------------------------</font>Customer Number:Not Available");
		 $('#topTitle').html("Order Number: <%=order_id%><br>Customer Number:Not Available");
		 </script> 
		<%}
	%>
	
	 
 	<%  if (request.getAttribute(Constants.ATTR_ORDER_VIEW_PENDING) != null)
    {
        orderViewPending=(OrderViewPending)request.getAttribute(Constants.ATTR_ORDER_VIEW_PENDING);
        orderViewPendingIdEnter=(OrderViewPending)request.getAttribute(Constants.ATTR_ORDER_VIEW_PENDING_AGENT_IDENTER);       
       	String showAccountType=(String)request.getAttribute(Constants.SHOW_ACCOUNT_TYPE);      
       	List<OrderViewPending>  listFollowNumber=(List<OrderViewPending> )request.getAttribute(Constants.LIST_FOLLOW_ORDER_NUMBER);
        int i =  0 ;
        if(Constants.isIntNumber(orderViewPending.getAgentIDEnter()))
        {
            i = Integer.parseInt(orderViewPending.getAgentIDEnter()); 
        }
               
        String ShipMethod=(String)request.getAttribute(Constants.SHORT_SHIPPING);
        String FraudDetail=(String) request.getAttribute(Constants.FRAUD_DETAIL); 
  
	
    %>    
<%
if(userLevel!=null && !userLevel.equals("C")){
%>
<input type="button" id="orderBackButton" value="<<Back" />
<br/>
<br/>
<A HREF="shopper_manager.do?method=getShopperDetails&shopperId=<%=orderViewPending.getShopper_id()%>"><font color="red"><u>View Shopper</u></font> </A>
<%} %>
<br>
<br>
<TABLE> 
    <TR>      
	        <TD VALIGN=TOP ALIGN=RIGHT>
	            <B>Order Id:</B>
	        </TD>
	        <TD VALIGN=TOP>
	            <%= orderViewPending.getOrder_id()%>
	        </TD>
	        <TD VALIGN=TOP>
	        </TD>

	<% 
	if ((i >=0)&(i!=-1)){
	
	%>	   	
	        <TD VALIGN=TOP ALIGN=RIGHT>
	            <B>
	            Agent/Sale Type:
	            </B>
	        </TD>
	        <TD VALIGN=TOP>
		<% 
		     if(request.getAttribute(Constants.ATTR_AGENT_OF_CUSTOMER) != null)
		     {
		         Agent agent = (Agent)request.getAttribute(Constants.ATTR_AGENT_OF_CUSTOMER);
		         out.print(agent.getUserName());    
		         //System.out.println("Name Agent Clear :"+agentClear.getUserName());
			 }
			 else
			 {
				 if (orderViewPending.getByAgent()==1) //In case AgentIDEnter is null
			     {					 
					out.print("Unknown Sale Type");
			     }
				 else  //In case there is no agent with this ID from Customer's profile
				 {										 
					 Customer customer = (Customer)request.getAttribute(Constants.ATTR_CUSTOMER);					 
					 out.print("UNKNOWN ID - "+customer.getAgent_id());
				 }
			 }
		
	     %>   
	      
	        </TD>
	<%} %>
     </TR>
    
    <TR>
    
         
        <TD VALIGN=TOP ALIGN=RIGHT>
            <B>Order Status:</B>
        </TD>        
        <TD VALIGN=TOP><%=orderViewPending.getOrderStatus()%></TD>
        <TD VALIGN=TOP>
        </TD> 
        <TD VALIGN=TOP ALIGN=RIGHT>
            <B>Ship Via:</B>
        </TD>
        <TD VALIGN=TOP>
        	<div id="ship"></div>
            <%=ShipMethod %>
        </TD>
    </TR>
  <TR>  
        <TD VALIGN=TOP ALIGN=RIGHT>
            <B>Date:</B>
        </TD>
        <TD VALIGN=TOP>
        
        <%       
        if(orderViewPending.getCreatedDate()==""){ 		
			out.print ("NULL");
			}else{
			    String createdate =orderViewPending.getCreatedDate().substring(0,10);
				createdate=createdate.replaceAll("-","/");
				String year=createdate.substring(0,4);
				String month=createdate.substring(5,7);
				String day=createdate.substring(8,10);
				out.print(month+"/"+day+"/"+year);
			%>		
		<% }%>		
        </TD>
         <TD VALIGN=TOP>
        </TD>       
        <TD VALIGN=TOP ALIGN=RIGHT>
            <B>Tracking:</B>
        </TD>
        <TD VALIGN=TOP>
        
        
        
        <%
     	if(orderViewPending.getTracking_number()!= null && !orderViewPending.getTracking_number().isEmpty())
     	{ 
     	   String tracking_number = orderViewPending.getTracking_number().trim();
     	   String trackURL = "";
// 			if(tracking_number.length() > 49 && tracking_number.substring(0, 49).trim() != "")
// 			{
// 				if(tracking_number.contains("1Z"))
// 				{
// 					trackURL = "http://wwwapps.ups.com/etracking/tracking.cgi?tracknums_displayed=5&TypeOfInquiryNumber=T&HTMLVersion=4.0&InquiryNumber1="+tracking_number+"&InquiryNumber2=&InquiryNumber3=&InquiryNumber4=&InquiryNumber5=&track.x=40&track.y=13";				
// 					out.println("<a href="+trackURL+">"+tracking_number+"</a>");
// 				}
// 			}

// NEW CODE
//if(tracking_number.length() > 16 && tracking_number.substring(0, 16).trim() != "")
			//{
				if(tracking_number.contains("1Z"))
				{
					trackURL = "http://wwwapps.ups.com/etracking/tracking.cgi?tracknums_displayed=5&TypeOfInquiryNumber=T&HTMLVersion=4.0&InquiryNumber1="+tracking_number+"&InquiryNumber2=&InquiryNumber3=&InquiryNumber4=&InquiryNumber5=&track.x=40&track.y=13";				
					out.println("<a href="+trackURL+">"+tracking_number+"</a>");
				}
			//}
// END NEW CODE
			else 
			{
				if(tracking_number.trim().length() > 11)
				{
					trackURL = "http://fedex.com/Tracking?action=track&tracknumber_list="+tracking_number+"&cntry_code=us";	
					out.println("<a href="+trackURL+">"+tracking_number+"</a>");
				}
				else if(tracking_number.trim().length() <= 11)
				{
					trackURL = "http://track.airborne.com/atrknav.asp?ShipmentNumber="+tracking_number;
					out.println("<a href="+trackURL+">"+tracking_number+"</a>");
				}
			}			
		}
		else
		{
			out.println("N/A");	
	    }
        %>
        </TD>
    </TR>
    
  
   <TR>       
        <TD VALIGN=TOP ALIGN=RIGHT>
            <B>Bill To:</B>
        </TD>
        <TD VALIGN=TOP>
        		<b>
        		<%=Constants.collapseRowBR(orderViewPending.getBill_to_name(),"") %>        		
				</b>
				<%=Constants.collapseRowBR(orderViewPending.getBill_to_company(),"") %>   
				<%=Constants.collapseRowBR(orderViewPending.getBill_to_address1(),"") %>  
				<%=Constants.collapseRowBR(orderViewPending.getBill_to_address2(),"") %>   
				<%=Constants.collapseRowNotBR(orderViewPending.getBill_to_city(),"") %>
				<%=Constants.collapseRowNotBR(orderViewPending.getBill_to_state(),"") %>
				<%=Constants.collapseRowNotBRNotPrefix(orderViewPending.getBill_to_postal(),"") %>				
				<%=Constants.collapseRowBR(orderViewPending.getBill_to_country(),"") %>
				<%=Constants.collapseRowBR(orderViewPending.getBill_to_phone(),"TEL #:") %>
				<%=Constants.collapseRowBR(orderViewPending.getBill_to_phoneext(),"ext:") %>
				<%=Constants.collapseRowBR(orderViewPending.getBill_to_fax(),"FAX #:") %>         
                        
          </TD>  
          <TD WIDTH=16></TD>
        <TD VALIGN=TOP ALIGN=RIGHT>
            <B>Ship To:</B>
        </TD>
        <TD VALIGN=TOP>  
        		
        		<b>
        			<%=Constants.collapseRowBR(orderViewPending.getShip_to_name(),"") %>         		
				</b>
				<%=Constants.collapseRowBR(orderViewPending.getShip_to_title(),"") %>   
				  <%=Constants.collapseRowBR(orderViewPending.getShip_to_company(),"") %>  
				  <%=Constants.collapseRowBR(orderViewPending.getShip_to_address1(),"") %>   
				  <%=Constants.collapseRowBR(orderViewPending.getShip_to_address2(),"") %>  
				  <%=Constants.collapseRowNotBR(orderViewPending.getShip_to_city(),"") %>   
				  <%=Constants.collapseRowNotBR(orderViewPending.getShip_to_state(),"") %>  
				  <%=Constants.collapseRowNotBRNotPrefix(orderViewPending.getShip_to_postal(),"") %>   
				  <%=Constants.collapseRowBR(orderViewPending.getShip_to_country(),"") %>  
				  <%=Constants.collapseRowBR(orderViewPending.getShip_to_phone(),"TEL #:") %>   
				  <%=Constants.collapseRowBR(orderViewPending.getShip_to_phoneext(),"ext:") %>    
			 </TD>
   	 </TR>           
    <TR>
                
         <TD VALIGN=TOP ALIGN=RIGHT NOWRAP>
            <b>Payment:</b> 
           </Td>          
        
         <TD colspan="2" VALIGN=TOP>
       	  <%= Constants.collapseRowBR(orderViewPending.getCc_type(),"") %>    
          <%= Constants.collapseRowBR(orderViewPending.getCc_name(),"") %>    
 
 		<% 		
                    
           if((!orderViewPending.getCc_number().trim().equals(""))||(!orderViewPending.getCc_number().trim().isEmpty()))
           {
               	String textInput1 = "Expires: ";
               	
               	if(orderViewPending.getCc_expmonth() == null && orderViewPending.getCc_expyear() == null)
               	{
               		textInput1 += "N/A"; 	
               	}else
               	{
               	 	textInput1 += orderViewPending.getCc_expmonth()+"/"+orderViewPending.getCc_expyear();   
               	}
               	
               	
            	String textInput2="Approval: "+orderViewPending.getApprovalNumber();            	
               %>
               <%= Constants.collapseRowBR(textInput1,"") %>    
	               
   		
   			<%	
   		 	if(orderViewPending.getApprovalNumber().equals(""))
   			{
   		    %>
   		    	<%= Constants.collapseRowBR("Approval: MISSING","") %>  
   		    <%   			   
   			}
   			else
   			{
			%>
				<%= Constants.collapseRowBR(textInput2,"") %>  		    			    
          	<% 
          	}
   			}
      
       
       if((!orderViewPending.getCc_number().trim().equals(""))||(!orderViewPending.getCc_number().trim().isEmpty()))
       {
           
			int k=orderViewPending.getCc_number().length();
    	    if(k>12)
    	    {
    	        String cc_numer=orderViewPending.getCc_number().substring(0,12).concat(" - ").concat(orderViewPending.getCc_number().substring(12));
    	        out.print(cc_numer);            
    	    }     	       
    	    else
    	    {
    	    %>
    	    	<%=Constants.collapseRowBR(orderViewPending.getCc_number(),"")%>         	                  	       
    	    <%  
    	    }
        }   
   	    if(!FraudDetail.isEmpty())
   	    {
	        String showFraudDetail="";
	        int lenghFraudDetail=FraudDetail.length();
	        int orderId=Integer.parseInt(order_id);
	        if((lenghFraudDetail>20)&&(orderId>242961))
	        {
	             String[] myString=FraudDetail.split("&");
	             String[] avsad =myString[4].split("=") ;
	             String[] avszip =myString[5].split("=") ;
	             String[] csc =myString[6].split("=") ;				                  
	             showFraudDetail = "<BR/><b><u>AVS Address</u>: "+avsad[1]+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<u>AVS ZIP</u>: " +avszip[1]+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<u>CSC</u>: "+csc[1]+"</b>";
          	%>
          		<%= Constants.collapseRowBR(showFraudDetail,"")%> 
		    <% 
		    }
         	else
         	{
		 	%>    	
		 		<%= Constants.collapseRowBR("","")%>  		
		    <%
		  	}
        }
   	    else
   	    {  
        %>
        	<%= Constants.collapseRowBR("","")%> 		
      	<%
      	}   
        %>      
    			 </TD>
    			 <td valign="top" align="right"><b>Account Type:</b></td>
				<td valign="top"><%=showAccountType%></td>    		
             </TR>
    <br>       
<tr>
<td colspan=5>
<TABLE CELLSPACING=4 CELLPADDING=0 BORDER=0 WIDTH=100%> 
   
    <TR>
   
    	<TD VALIGN=BOTTOM ALIGN=LEFT>
    	 <%
   		 if((Boolean)session.getAttribute(Constants.IS_ADMIN)){
  	 	 %> 
    	<b>Add<br> Note</b>
    	<%} %>
    	</TD>
    	
        <TD VALIGN=BOTTOM ALIGN=LEFT><b>Product<br>Number</b></TD>
        <%if((isAdmin) || (!isAdmin)) {%>
        	<TD VALIGN=BOTTOM ALIGN=LEFT><b>Cosmetic Grade</b></TD>	
        	
        <%}else{%>
        	<TD VALIGN=BOTTOM ALIGN=LEFT></TD>
        <%}%>
        <TD VALIGN=BOTTOM ALIGN=LEFT><b>Product Name</b></TD>
        <TD VALIGN=BOTTOM ALIGN=CENTER><b>Qty</b></TD>
        <TD VALIGN=BOTTOM ALIGN=RIGHT><b>Price</b></TD>
        <TD VALIGN=BOTTOM ALIGN=CENTER><b>Tracking #</b></TD>
        <TD VALIGN=BOTTOM ALIGN=CENTER><b>Listing # </b></TD>
       
    </TR>
    <tr><TD BGCOLOR=black colspan=9 HEIGHT="2"><IMG SRC="manager/MSCS_Images/extras/dot_clear.gif" WIDTH=1 HEIGHT=1></TD></tr>   
	           <%	
if(listFollowNumber != null && !listFollowNumber.isEmpty()){  
 for(OrderViewPending order : listFollowNumber) {
     %>
     
      <TR><td>
      		<%
      		
        	if(userLevel!=null && !userLevel.equals("C")&& (session.getAttribute(Constants.IS_CUSTOMER) == null)){
        	%>      			
      			<input type="image"  style="width: 16px; height:16px" onclick="openWarrenty('shopper_manager.do?method=addNote&mscssid=<%=orderViewPending.getShopper_id()%>&item_sku=<%=order.getItem_sku()%>&order_id=<%=order_id%>');" onmouseOver="window.status='Click for warranty information.'; return true" onmouseOut="window.status=''; return true" CLASS="nounderline" src="images/Edit_button.jpg"/>
      	<%		}
      		%>	
    	</td>
      		
            <%-- <TD VALIGN=TOP ALIGN=LEFT><%=order.getCosmetic_grade() %></td> --%>
            <TD VALIGN=TOP ALIGN=LEFT>
          													
             <a href="javascript:void(0)" onClick="openWarrenty('searchProduct.do?method=productDetail&item_sku=<%=order.getItem_sku()%>');" onmouseOver="window.status='Click for warranty information.'; return true" onmouseOut="window.status=''; return true" CLASS="nounderline"><font color="red"><u><%= order.getItem_sku()%></u></font></a>
                     
            </TD>
            <%if((isAdmin) || (!isAdmin)) {%>
        		<TD VALIGN=TOP ALIGN=CENTER><%=order.getCosmetic_grade() %></td>	
        		
        	<%}else{%>
        		<TD VALIGN=BOTTOM ALIGN=LEFT></TD>
        	<%}%>
            
             <TD VALIGN=TOP ALIGN=LEFT><%=order.getDescription() %></td>
             <TD VALIGN=TOP ALIGN=CENTER><%=order.getOrderedQty() %></td>       
           
    		 <TD VALIGN=TOP ALIGN=RIGHT>
    <%     
if (order.getDiscounted_price()==null){
	out.println("NULL");
}
else{       	
		float money=Float.parseFloat(order.getDiscounted_price()); 
		money=money/100;
		out.println(Constants.FormatCurrency(money));
		orginalPrice += money;
	//response.write (MSCSDataFunctions.Money(100 * rsReceiptItems("product_list_price").Value))
}%>
 <%  
     if(!order.getTracking_number().equals("")||!order.getTracking_number().isEmpty())
     {        	 
  	%>
  	  <TD VALIGN=TOP ALIGN=CENTER><%=order.getTracking_number().trim()  %></TD> 
  	
    <% }
     else{
         %>
         <TD VALIGN=TOP ALIGN=CENTER>N/A</TD> 
    <%
     }
     %>   
     
     <%  
     if(orderViewPending.getListing()!=null ){      		
           
                %>
                <TD VALIGN=TOP ALIGN=RIGHT>&nbsp;&nbsp;<%=orderViewPending.getListing() %></TD>
            <%     
     }
     else{
         %>
         <TD VALIGN=TOP ALIGN=CENTER>N/A</TD> 
    <%
     }
     %>   
     
					
        </TR>   
     <%     
 }}
%>
<tr><TD BGCOLOR=black colspan=9 HEIGHT="2"><IMG SRC="manager/MSCS_Images/extras/dot_clear.gif" WIDTH=1 HEIGHT=1></TD></tr>
<TR>
        <TD COLSPAN=4 VALIGN=TOP ALIGN=RIGHT>
            Subtotal:
        </TD>
        <TD ALIGN=RIGHT>    
       <%       
      // float epp_number=Float.parseFloat(orderViewPending.getEpp_id());    
       if(orderViewPending.getEpp_id()==null){                    
           if(orderViewPending.getOadjust_subtotal()==null){
               out.print("NULL");
           }
           else{
                        
                   float moneyTotal=Float.parseFloat(orderViewPending.getOadjust_subtotal());                           
                   out.print(Constants.FormatCurrency(moneyTotal));
                       
           }
       }
       else 
       {   
               if(orderViewPending.getEst_subtotal()==null)
               {
                   out.print("NULL");
               }
               else
               {
                   float Est_subtotal=Float.parseFloat(orderViewPending.getEst_subtotal());
                   Est_subtotal=Est_subtotal/100;
                   out.print(Constants.FormatCurrency(Est_subtotal));
                   
               }
       }
%>         </TD>
    </TR>
   
<TR>
        <TD COLSPAN=4 VALIGN=TOP ALIGN=RIGHT>
            Shipping & Handling:
        </TD>
        <TD ALIGN=RIGHT>
        <%
        if(orderViewPending.getEpp_id()==null){
            if(orderViewPending.getShipping_total()==null){
                out.print("NULL");
            }
            else{
               float shipping_total=Float.parseFloat(orderViewPending.getShipping_total());
              // shipping_total=shipping_total*100;
               out.print(Constants.FormatCurrency(shipping_total));
            }
        }  
        else{
            Float shipping_dis=Float.parseFloat(orderViewPending.getShipping_discount());           
	            if(shipping_dis>0){
	                out.print("FREE");
	            }
	            else{
	                out.print(Constants.FormatCurrency(shipping_dis));
	            }                
            }   

%>          </TD>
    </TR>    
    
    <TR>
        <TD COLSPAN=4 VALIGN=TOP ALIGN=RIGHT>
            Environmental Fee :
        </TD>
        <TD ALIGN=RIGHT>
        <%
        if(orderViewPending.getHandling_total() ==null){
            out.print("NULL");            
        }
        else{
            out.print(Constants.FormatCurrency(new Float (orderViewPending.getHandling_total())));
        }
        %>
   </TD>
    </TR>
    
    <TR>
        <TD COLSPAN=4 VALIGN=TOP ALIGN=RIGHT>
            Tax:
        </TD>
        <TD ALIGN=RIGHT>
        <%
        if(orderViewPending.getTax_total()==null){
            out.print("NULL");            
        }
        else{
            out.print(Constants.FormatCurrency(new Float (orderViewPending.getTax_total())));
        }
        %>
   </TD>
    </TR>

     <TR>
        <TD COLSPAN=4 HEIGHT=1></TD>
    </TR>
    <tr><TD colspan=4></TD>
    <TD BGCOLOR=black HEIGHT="2"><IMG SRC="manager/MSCS_Images/extras/dot_clear.gif" WIDTH=1 HEIGHT=1></TD></tr>
    <TR>
        <TD COLSPAN=4 VALIGN=TOP ALIGN=RIGHT>
            <B>Total:</B>
        </TD>
        <TD VALIGN=TOP ALIGN=RIGHT><B>
        <%
        if(orderViewPending.getTax_total()==null){
            out.print("NULL");
        }
        else{
            float total=Float.parseFloat(orderViewPending.getTotal_total());            
            out.print(Constants.FormatCurrency(total));
        }
    

%>
</B>        </TD>
    </TR>
     <%
   
    float Discount_total=Float.parseFloat(orderViewPending.getDiscount_total());	
    if(!orderViewPending.getDiscount_total().equals("")){       
        if(Discount_total!=0.0){            
            if(orderViewPending.getEpp_id()==null){
     %>         
		  <TR>
		        <TD COLSPAN=4  VALIGN=TOP ALIGN=RIGHT>
		            Discounts:
		        </TD>
		        <TD ALIGN=RIGHT>                
    	 <%  
    				out.print(Constants.FormatCurrency(new Float(Discount_total)));
    			}
         else 
         {            
          %>
		      <TR>
		        <TD COLSPAN=4  VALIGN=TOP ALIGN=RIGHT>
		            Discounts:
		        </TD>
		        <TD ALIGN=RIGHT>      
	         <%  
            	Float discounts = orginalPrice -  new Float(orderViewPending.getOadjust_subtotal());
            	out.print(discounts);
             }
            %>
             </TD>
                </TR>
                </TD>
                </TR>
         <%   
         }
    }
%>
	<tr>
		<td colspan="3">
			<%
			
				if((orderViewPending.getOrderStatus().toString().equals("PENDING"))&&(session.getAttribute(Constants.IS_ADMIN).toString().equals("true"))){				
				%>
			
				 <form method="post" action="order_db.do?method=orderViewPending&order_id=<%=orderViewPending.getOrder_id()%>&agentname=<%=agentClear.getUserName()%>">
					<input type="submit" value="Clear Order" id="clear"/>
				</form>  
				
			<%	}
			
			%>
			
		</td>
	</tr>
	
</TABLE>
	</td>
    </TR>
</TABLE>
<script LANGUAGE="JavaScript">
$(document).ready(function(){
	<%		
	if (request.getParameter("from") != null) {
		if (request.getParameter("from").toString().equals("order_result")) {
	%>
	$('#orderBackButton').click(function() {		
	    javascript:document.location.href='<%=request.getContextPath()%>/order_db.do?method=backOrderForwardPage';
	});	
	<%	
		}else if (request.getParameter("from").toString().equals("orderAgent")) {
	%>
	$('#orderBackButton').click(function() {		
	    javascript:document.location.href='<%=request.getContextPath()%>/order_db.do?method=detailAgent&back=true';
	});	
	<%	
		}
	} else {
	%>
	$('#orderBackButton').css('display', 'none');
	<%
	}
	%>
});
//
function openWarrenty(URL) 
{
    var newWIN;
    day = new Date();
    id = day.getTime();
    eval("page"+id+"=window.open(URL,'"+id+"','status=1,toolbar=0,scrollbars=1,location=0,statusbars=1,menubar=0,resizable=1,width=540,height=440');");
}
<%
	Object isCustomer = session.getAttribute(Constants.IS_CUSTOMER);
	if(isCustomer != null && (Boolean)isCustomer)
	{
	    %>
	    $("#menuShopper").hide();
	    <%
	}
%>
</script> 		       
<%@include file="/html/scripts/order_view_pendingClickScript.jsp"%>
<%      
    }
%>
