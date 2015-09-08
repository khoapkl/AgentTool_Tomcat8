
<%@page import="java.util.List"%>
<%@page import="com.dell.enterprise.agenttool.model.Note"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="com.dell.enterprise.agenttool.model.Shopper"%>
<%@page import="com.dell.enterprise.agenttool.model.ShopperViewReceipts"%>
<%
	String mscssid=(String)request.getAttribute(Constants.SHOPPER_ID);
	Shopper shopper = (Shopper) request.getAttribute(Constants.SHOPPER_INFO);
	String item_sku="";
	String order_id="";
	if(request.getAttribute(Constants.ITEM_SKU)!=null){
	    item_sku = (String) request.getAttribute(Constants.ITEM_SKU);
	};
	if(request.getAttribute(Constants.ORDER_ID_PENDING)!=null){
	    order_id = (String) request.getAttribute(Constants.ORDER_ID_PENDING);
	};
	
	
%>

<script>
		 $('#pageTitle').html("Shopper - <%=shopper.getShipToFName()%> <%=shopper.getShipToLName()%> (<%=shopper.getShopperNumber()%>)");
		 $('#topTitle').html("Shopper - <%=shopper.getShipToFName()%> <%=shopper.getShipToLName()%> (<%=shopper.getShopperNumber()%>)");
</script>
<form name="form_memo"
action="<%=request.getContextPath()%>/shopper_manager.do?method=actionAddNote&mscssid=<%=mscssid %>"
method="POST" 
 style="margin:0" onSubmit="return check_Form(document.form_memo)">

<%

List<Note> listSubjectType=(List<Note>)request.getAttribute(Constants.SHOPPER_ADD_NOTES_LIST_SUBJECT_TYPE);
List<Note> listGroupSubject=(List<Note>)request.getAttribute(Constants.SHOPPER_ADD_NOTES_LIST_GROUP_SUBJECT);
String mscsShopperID="";


%>
<table border=0>
<tr>
	<td width=125>Time Open: </td>
	<td width=275><input size=5 name=timespent><br></td>
</tr>
<tr>
	<td width="125"></td>
	<td align="left"><input type="checkbox" name="attempt">Declined Attempt</td>
</tr>

<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
<!-- //
function MakeArray( n ) {
    if( n <= 0 ) {
        this.length = 0;
        return this;
    }
    this.length = n;
    for( var i = 1; i <= n; i++ ) {
        this[ i ] = 0;
    }
    return this;
}

function storeNote(p_notetype, p_subject, p_id) {
    var i;
    if( index >= notetype.length ) {
        for( i = 1; i < notetype.length; i++ ) {
            notetype[i-1] = notetype[i];
            subject[i-1] = subject[i];
            ids[i-1] = ids[i];
        }
        index = notetype.length - 1;
    }

    notetype[ index ] = p_notetype;
    subject[ index ] = p_subject;
    ids[ index ] = p_id;

    ++cmmnd;
    ++index;
}

function populateTopicPre(inForm, selected) {
    document.form_memo.fldTopic.value='';

    var c=0
    while (c < inForm.topicPre.options.length) {
        inForm.topicPre.options[(inForm.topicPre.options.length - 1)] = null;
    }

    eval("inForm.topicPre.options[0]=" + "new Option" + "('Select...')");

    count = 1;
    for (var i=0; i < index; i++) {
        //alert(notetype[i]);
        if (notetype[i] == selected) {
            eval("inForm.topicPre.options[count]=" + "new Option" + "('" + subject[i] +  "', '" + subject[i] + "')");        
            count ++;
        }
    }
    
    if (selected = "Select ...") {
        inForm.topicPre.selectedIndex = 0;
    }
}

var notetype = new MakeArray(30);
var subject  = new MakeArray(30);
var ids      = new MakeArray(30);

var index = 0;
var cmmnd = 1;

function OthersOther() {
    if (document.form_memo.subjectType.options[document.form_memo.subjectType.selectedIndex].value == "Others") {
        if (document.form_memo.topicPre.options[document.form_memo.topicPre.selectedIndex].value == "Other") {
            return;
        }
    }
    document.form_memo.fldTopic.blur();
}

// -->
</SCRIPT>
<tr><td>
  Type:
</td><td>
  <select name="subjectType" style="width:100%" onChange="populateTopicPre(document.form_memo,document.form_memo.subjectType.options[document.form_memo.subjectType.selectedIndex].value);">
        <option>Select...</option>        
  <%
  if(listSubjectType.size()>0){
      for(Note note : listSubjectType) {	  	
   
        %>
        <option value="<%=note.getSubject()%>"><%=note.getSubject()%></option>        
        <%
        
  }}

	%>
	</select>
</td></tr>
<tr><td>
Subject:
</td><td>
<select name="topicPre" style="width:100%" onChange="document.form_memo.fldTopic.value='';">
    </select>
</td></tr>
     <script>
    <%
  if(listGroupSubject.size()>0){
      for(Note note : listGroupSubject) {	  	
   
        %>
       
         storeNote('<%=note.getSubject()%>', '<%=note.getNoteType()%>', '<%=note.getIndexKey()%>'); 
       
        <%
        
  }}

	%>
	  </script>
	
<tr><td>
    Custom Subject:
</td>
<td>
<input type="text" size=50 name="fldTopic" onFocus="OthersOther();"  value="" style="width:100%">
</td></tr>
<tr><td>
    Order Number:
</td>
<td>
<input type="text" size=50 name="fldOrder" value="<%=order_id %>" style="width:100%" maxlength="10" >
</td></tr>
	
<tr><td colspan=2>
<textarea name="fldNotes" rows="10" style="width:100%" >
<%=item_sku %>
</textarea>
</td></tr>
<tr><td colspan=2>
<input type="submit" name="Submit" value="Submit">
<INPUT TYPE="button" VALUE="Cancel" onClick="window.close()">
<input type="hidden" name="fldWhen" value="<%=Constants.currentFullDate() %>">
<input type="hidden" name="mscssid" value="<%= mscsShopperID %>">
</td></tr>
</table>


</form>
<script>
<!-- //

startday = new Date();
clockStart = startday.getTime();

function initStopwatch() { 
    var myTime = new Date(); 
    return((myTime.getTime() - clockStart)/1000); 
}
function getSecs() { 
    var tSecs = Math.round(initStopwatch()); 
    var iSecs = tSecs % 60;
    var iMins = Math.round((tSecs-30)/60);   
    var sSecs ="" + ((iSecs > 9) ? iSecs : "0" + iSecs);
    var sMins ="" + ((iMins > 9) ? iMins : "0" + iMins);
    document.form_memo.timespent.value = sMins+":"+sSecs;
    window.setTimeout('getSecs()',1000); 
}

function  check_Form(checkForm) {

  // check the selected index of the the two pull downs!
  if (checkForm.subjectType.selectedIndex < 1) {
    if (!check_onError(checkForm, checkForm.subjectType, '', "Please select a note type.", "Note")) {
      return false; 
    }
  }

  if (checkForm.topicPre.selectedIndex < 1) {
    if (!check_onError(checkForm, checkForm.topicPre, '', "Please select a note subject.", "Note")) {
      return false; 
    }
  }
  
  if (checkForm.fldOrder.value == '') {
  	if (!check_onError(checkForm, checkForm.fldOrder, '', "Please enter an order number.", "Note")) {
		return false;
	}
  }

  if(isNaN(checkForm.fldOrder.value))
  {
	if (!check_onError(checkForm, checkForm.fldOrder, '', "Please enter a number in Order Number field.", "Note")) {
		return false;
	}
  }
  
    if (document.form_memo.subjectType.options[document.form_memo.subjectType.selectedIndex].value == "Others") {
        if (document.form_memo.topicPre.options[document.form_memo.topicPre.selectedIndex].value == "Other") {
          if (!check_hasValue(checkForm.fldTopic, "TEXT" )) {
           if (!check_onError(checkForm, checkForm.fldTopic, checkForm.fldTopic.value, "Please enter a topic.", "Note")) {
              return false; 
            }
          }
       }
   }


  if (!check_hasValue(checkForm.fldNotes, "TEXT" )) {
   if (!check_onError(checkForm, checkForm.fldNotes, checkForm.fldNotes.value, "Please write a memo.", "Note")) {
      return false; 
    }
  }

  return true; 
}

function check_hasValue(obj, obj_type) {
  if (obj_type == "TEXT") {
    var strTemp;

    strTemp = trimString(obj.value);
    obj.value = strTemp;

    if (strTemp.length == 0) {
      return false;
    } else {
      return true;
    }
  }
}

function check_onError(form_object, input_object, object_value, error_message, obj_section) {
  window.alert('Incomplete '+obj_section+'\n--------------------------------------------\n'+error_message);
  input_object.focus();
  return false;	
}

function trimString(userInput){
  var iStart, iEnd;
  var sTrimmed;
  var cChar;

  iEnd = userInput.length - 1;
  iStart = 0;
  bLoop = true;

  cChar = userInput.charAt(iStart);
  while ((iStart < iEnd) && ((cChar == "\n") || (cChar == "\r") ||
                            (cChar == "\t") || (cChar == " "))){
     iStart ++;
     cChar = userInput.charAt(iStart);
  }

  cChar = userInput.charAt(iEnd);
  while ((iEnd >= 0) && ((cChar == "\n") || (cChar == "\r") ||
                    (cChar == "\t") || (cChar == " "))){
     iEnd --;
     cChar = userInput.charAt(iEnd);
  }

  if (iStart < iEnd){
     sTrimmed = userInput.substring(iStart, iEnd + 1);
  } else {
     sTrimmed = "";
  }
  return sTrimmed;
}

window.setTimeout('getSecs()',1);

// -->
</script>
