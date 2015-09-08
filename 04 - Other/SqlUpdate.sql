--## 20110318
IF OBJECT_ID ( 'ShopperListPaging', 'P' ) IS NOT NULL 
    DROP PROCEDURE ShopperListPaging;
GO
CREATE PROCEDURE ShopperListPaging
	-- Add the parameters for the stored procedure here
	@SelectedPage int,
	@PageSize int,
	@WhereClause varchar(500),
	@TotalRecords int OUTPUT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DECLARE @SQLQuery nvarchar(4000), @StartRecord int, @EndRecord int, @TableOrViewName varchar(500), @i int, @Columns varchar(3000)

	SET @Columns = 'shopper_id, shopper_number, linkNumber, BillingGrpID, ContactType, Program, Contact_Status, CreditRating, TermCode, specialty_discount, latperdiscount, latamtdiscount, latexpdate, insperdiscount, insamtdiscount, insexpdate, optperdiscount, optamtdiscount, optexpdate, dimperdiscount, dimamtdiscount, dimexpdate, monperdiscount, monamtdiscount, monexpdate, serperdiscount, seramtdiscount, serexpdate, worperdiscount, woramtdiscount, worexpdate, tax_exempt, tax_exempt_number, tax_exempt_expire, promo_email, heardAboutSiteFrom, primUse, agent_id, agent_id_exp, ship_to_fname, ship_to_mname, ship_to_lname, ship_to_fullName, ship_to_salutation, ship_to_title, ship_to_company, ship_to_address1, ship_to_address2, ship_to_address3, ship_to_city, ship_to_state, ship_to_postal, ship_to_county, ship_to_country, ship_to_fax, ship_to_phone, ship_to_phoneext, ship_to_email, ship_method, bill_to_fname, bill_to_mname, bill_to_lname, bill_to_salutation, bill_to_title, bill_to_company, bill_to_address1, bill_to_address2, bill_to_address3, bill_to_city, bill_to_state, bill_to_postal, bill_to_county, bill_to_country, bill_to_fax, bill_to_phone, bill_to_phoneext, bill_to_email, loginID, password, hint, answer, cc_name, cc_number, cc_type, cc_expmonth, cc_expyear, createdDate, updatedDate, calledDate, recallDate, fmID, account_type, loa, creditline, creditexp, creditavail, creditcomment'
	SET @TableOrViewName = 'contactInfo'

	-- Create temporary table
	CREATE TABLE #ShopperListTempTable (
	RowNumber int IDENTITY (1, 1),
	ShopperNumber int )

	-- Find first record on selected page
	SET @StartRecord = (@SelectedPage - 1) * @PageSize + 1
	 
	-- Find last record on selected page
	SET @EndRecord = @SelectedPage * @PageSize
	 
	-- Check if there is WHERE clause
	IF @WhereClause <>  ''
		BEGIN
			SET @WhereClause = ' WHERE ' + @WhereClause
		END

	-- Build caculate total record
	SET @SQLQuery = 'SELECT @i = (SELECT COUNT(shopper_number) FROM ' + @TableOrViewName + @WhereClause +  ')'

	EXEC sp_executesql @SQLQuery, @params = N'@i INT OUTPUT', @i = @i OUTPUT 
	SET @TotalRecords = @i

	-- Build INSERT statement used to populate temporary table
	SET @SQLQuery = 'INSERT #ShopperListTempTable (ShopperNumber) ' +
		' SELECT TOP ' + CAST(@EndRecord AS varchar(20)) + ' shopper_number FROM ' + @TableOrViewName + 
		@WhereClause + ' ORDER BY ship_to_fname'
	-- Execute statement and populate temp table
	EXEC (@SQLQuery)

	-- Build SQL query to return only selected page
	SET @SQLQuery = N'SELECT RowNumber,' + @Columns + 
		' FROM ' + @TableOrViewName + 
		' JOIN #ShopperListTempTable tmp ON tmp.ShopperNumber = ' + @TableOrViewName + '.shopper_number' +  
		' WHERE RowNumber >= ' + CAST(@StartRecord AS varchar(20)) +
		' AND RowNumber <= ' + CAST(@EndRecord AS varchar(20)) +
		' ORDER BY RowNumber'

	-- Return selected page
	EXEC (@SQLQuery)
	-- Delete temporary table
	DROP TABLE #ShopperListTempTable
	 
	SET NOCOUNT OFF
END

GO
--## 20110317
--ThuyNguyen

IF OBJECT_ID ( 'uspListAllOrderPending', 'P' ) IS NOT NULL 
    DROP PROCEDURE uspListAllOrderPending;
GO
CREATE PROCEDURE uspListAllOrderPending  
  @SelectedPage int,
  @PageSize int,
  @TotalRecord int OUTPUT
  AS
 
SET NOCOUNT ON

DECLARE @SQLQuery nvarchar(4000), @StartRecord int, @EndRecord int, @TableOrViewName varchar(500), @i int,@Columns varchar(3000)
DECLARE @GroupBy varchar(1000), @SortColumn varchar(255), @WhereClause varchar(255)
SET @Columns = 'h.ordernumber, h.ship_to_name, h.total_total, u.username, h.cc_type '
SET @TableOrViewName = 'orderheader h, adminusers u'
SET @WhereClause = 'h.agentidenter = u.agent_id  and h.orderstatus = ''PENDING'' '
SET @GroupBy = ''
SET @SortColumn = 'h.ordernumber ASC'

-- Create temporary table
CREATE TABLE #TempTable(
RowNumber int IDENTITY (1, 1),
ordernumber varchar(100) )
 
-- Find first record on selected page
SET @StartRecord = (@SelectedPage - 1) * @PageSize + 1
 
-- Find last record on selected page
SET @EndRecord = @SelectedPage * @PageSize
 
-- Check if there is WHERE clause
IF @WhereClause <>  ''
  BEGIN
    SET @WhereClause = ' WHERE ' + @WhereClause
  END
  
-- Build caculate total record
SET @SQLQuery = 'SELECT @i = (SELECT COUNT(ordernumber) FROM (SELECT h.ordernumber ' + ' FROM ' + @TableOrViewName + @WhereClause  + @GroupBy +  ') tb1)'

EXEC sp_executesql @SQLQuery,  @params = N'@i INT OUTPUT', @i = @i OUTPUT 
SET @TotalRecord = @i
PRINT @SQLQuery

-- Build INSERT statement used to populate temporary table
SET @SQLQuery = 'INSERT  #TempTable (ordernumber) ' +
' SELECT TOP ' + CAST(@EndRecord AS varchar(20)) + ' ' + 'h.ordernumber' + ' FROM ' + @TableOrViewName + 
@WhereClause +  @GroupBy + ' ORDER BY ' +  @SortColumn 
-- Execute statement and populate temp table
EXEC (@SQLQuery)
PRINT @SQLQuery

-- Build SQL query to return only selected page
SET @SQLQuery = N'SELECT RowNumber,' + @Columns + 
' FROM '  + @TableOrViewName + 
' ,#TempTable tmp' +  
' WHERE RowNumber >= ' + CAST(@StartRecord AS varchar(20)) +
' AND RowNumber <= ' + CAST(@EndRecord AS varchar(20))   +
' AND tmp.ordernumber = h.ordernumber AND h.agentidenter = u.agent_id  AND h.orderstatus = ''PENDING''' + 
' ORDER BY RowNumber ' 
PRINT @SQLQuery
-- Return selected page
EXEC (@SQLQuery)
-- Delete temporary table
DROP TABLE #TempTable 
SET NOCOUNT OFF
GO
--## 20110317
--ThuyNguyen

IF OBJECT_ID ( 'uspGetShopperMoveNote', 'P' ) IS NOT NULL 
    DROP PROCEDURE uspGetShopperMoveNote;
GO
CREATE PROCEDURE uspGetShopperMoveNote
  @ShopperID varchar(255),
  @SelectedPage int,
  @PageSize int,
  @TotalRecord int OUTPUT
  AS
 
SET NOCOUNT ON

DECLARE @SQLQuery nvarchar(4000), @StartRecord int, @EndRecord int, @TableOrViewName varchar(500), @i int,@Columns varchar(3000)
DECLARE @GroupBy varchar(1000), @SortColumn varchar(255), @WhereClause varchar(255)
SET @Columns = 't1.*, t2.username as agent_name, t3.subject as subject, t3.noteType as noteType'
SET @TableOrViewName = 'contactLog t1, adminusers t2, notesubjects t3'
SET @WhereClause = 't1.agent_id = t2.agent_id and t1.subjectid = t3.indexkey and shopper_id = '''+@ShopperID+''' '
SET @GroupBy = ''
SET @SortColumn = 'timeoff DESC'

-- Create temporary table
CREATE TABLE #TempTable(
RowNumber int IDENTITY (1, 1),
contact_id varchar(100) )
 
-- Find first record on selected page
SET @StartRecord = (@SelectedPage - 1) * @PageSize + 1
 
-- Find last record on selected page
SET @EndRecord = @SelectedPage * @PageSize
 
-- Check if there is WHERE clause
IF @WhereClause <>  ''
  BEGIN
    SET @WhereClause = ' WHERE ' + @WhereClause
  END
  
-- Build caculate total record
SET @SQLQuery = 'SELECT @i = (SELECT COUNT(contact_id) FROM (SELECT t1.contact_id ' + ' FROM ' + @TableOrViewName + @WhereClause  + @GroupBy +  ') tb1)'

EXEC sp_executesql @SQLQuery,  @params = N'@i INT OUTPUT', @i = @i OUTPUT 
SET @TotalRecord = @i
--PRINT @SQLQuery

-- Build INSERT statement used to populate temporary table
SET @SQLQuery = 'INSERT  #TempTable (contact_id) ' +
' SELECT TOP ' + CAST(@EndRecord AS varchar(20)) + ' ' + 't1.contact_id' + ' FROM ' + @TableOrViewName + 
@WhereClause +  @GroupBy + '  ORDER BY ' +  @SortColumn 
-- Execute statement and populate temp table
EXEC (@SQLQuery)
--PRINT @SQLQuery

-- Build SQL query to return only selected page
SET @SQLQuery = N'SELECT RowNumber,' + @Columns + 
' FROM '  + @TableOrViewName + 
' ,#TempTable tmp' +  
' WHERE RowNumber >= ' + CAST(@StartRecord AS varchar(20)) +
' AND RowNumber <= ' + CAST(@EndRecord AS varchar(20))   +
' AND tmp.contact_id = t1.contact_id AND t1.agent_id = t2.agent_id AND t1.subjectid = t3.indexkey' + 
' ORDER BY RowNumber ' 
--PRINT @SQLQuery
-- Return selected page
EXEC (@SQLQuery)
-- Delete temporary table
DROP TABLE #TempTable 
SET NOCOUNT OFF
GO

--## 20110315
--Linhdo

IF OBJECT_ID ( 'uspShopperOrders', 'P' ) IS NOT NULL 
    DROP PROCEDURE uspShopperOrders;
GO
CREATE PROCEDURE uspShopperOrders
  @SelectedPage int,
  @PageSize int,
  @ShopperID varchar(50),
  @TotalRecord int OUTPUT
  AS
 
SET NOCOUNT ON

DECLARE @SQLQuery nvarchar(4000), @StartRecord int, @EndRecord int, @TableOrViewName varchar(500), @i int,@Columns varchar(3000)
DECLARE @GroupBy varchar(255), @Top varchar(255), @WhereClause varchar(255) , @SortColumn varchar(255)

SET @Columns = ' t.OrderNumber,createdDate, total_total '
SET @TableOrViewName = ' orderHeader t '
SET @GroupBy= ' GROUP BY t.OrderNumber,createdDate, total_total, RowNumber'
SET @SortColumn = 'createdDate'

-- Create temporary table
CREATE TABLE #TempTableOrder (
RowNumber int IDENTITY (1, 1),
OrderNumber varchar(50) )
 
-- Find first record on selected page
SET @StartRecord = (@SelectedPage - 1) * @PageSize + 1
 
-- Find last record on selected page
SET @EndRecord = @SelectedPage * @PageSize
 
-- Check if there is WHERE clause
IF @ShopperID <>  ''
  BEGIN
    SET @WhereClause = ' WHERE shopper_id ='''+ @ShopperID+''' ' 
  END
  
-- Build caculate total record
SET @SQLQuery = 'SELECT @i = (SELECT COUNT(OrderNumber) FROM (SELECT OrderNumber ' + ' FROM ' + @TableOrViewName + @WhereClause +') tb1)'
EXEC sp_executesql @SQLQuery,  @params = N'@i INT OUTPUT', @i = @i OUTPUT 
SET @TotalRecord = @i
PRINT @SQLQuery
IF (@SelectedPage > 0)
BEGIN
	SET @Top = CAST(@EndRecord AS varchar(20));
END 
ELSE
BEGIN
	SET @Top = CAST(@TotalRecord AS varchar(20));
END

 
-- Build INSERT statement used to populate temporary table
SET @SQLQuery = 'INSERT  #TempTableOrder (OrderNumber) ' +
' SELECT TOP ' + @Top + ' ' + 'OrderNumber' + ' FROM ' + @TableOrViewName + 
@WhereClause + '  ORDER BY ' +  @SortColumn 
-- Execute statement and populate temp table
EXEC (@SQLQuery)
PRINT @SQLQuery

IF (@SelectedPage > 0)
BEGIN 
	-- Build SQL query to return only selected page
	SET @SQLQuery = N'SELECT RowNumber,' + @Columns + 
	' FROM '  + @TableOrViewName + 
	' JOIN ' + '#TempTableOrder tmp ON tmp.OrderNumber = t.OrderNumber ' +  
	' WHERE RowNumber >= ' + CAST(@StartRecord AS varchar(20)) +
	' AND RowNumber <= ' + CAST(@EndRecord AS varchar(20))  +  @GroupBy +
	' ORDER BY RowNumber ' 
END
ELSE
BEGIN
	-- Build SQL query to return only selected page
	SET @SQLQuery = N'SELECT RowNumber,' + @Columns + 
	' FROM '  + @TableOrViewName + 
	' JOIN ' + '#TempTableOrder tmp ON tmp.OrderNumber = t.OrderNumber ' +   @GroupBy +
	' ORDER BY RowNumber ' 
END
-- Return selected page
EXEC (@SQLQuery)
PRINT @SQLQuery
-- Delete temporary table
DROP TABLE #TempTableOrder
 
SET NOCOUNT OFF
GO

--Author Vinhhq
IF OBJECT_ID ( 'uspGetOrderDateDay', 'P' ) IS NOT NULL 
    DROP PROCEDURE uspGetOrderDateDay;
GO
CREATE PROCEDURE uspGetOrderDateDay
  @Year int,
  @Month int,
  @Day int,
  @SelectedPage int,
  @PageSize int,
  @TotalRecord int OUTPUT
  AS
 
SET NOCOUNT ON

DECLARE @SQLQuery nvarchar(4000), @StartRecord int, @EndRecord int, @TableOrViewName varchar(500), @i int,@Columns varchar(3000)
DECLARE @GroupBy varchar(1000), @SortColumn varchar(255), @WhereClause varchar(255)

SET @Columns = 'T1.OrderNumber, T1.AgentIDEnter, T1.ship_to_name, T1.shopper_id, T1.createdDate, T1.total_total, case when T1.total_total = 0 then 0 else T1.discount_total / T1.total_total * 100 end AS discount_perc, COUNT(T2.OrderNumber) AS items_sold,T1.byAgent'
SET @TableOrViewName = 'orderHeader T1 INNER JOIN orderLine T2 ON T1.OrderNumber = T2.OrderNumber'
SET @WhereClause = '{ fn YEAR(createdDate) } = '+ CAST(@Year AS varchar(20)) +' and { fn MONTH(createdDate) } = '+ CAST(@Month AS varchar(20)) +' and { fn DAYOFMONTH(createdDate) } = '+ CAST(@Day AS varchar(20)) +' AND T2.item_sku <> ''FREE SWKIT v3.0'''
SET @GroupBy = 'GROUP BY T1.OrderNumber, T1.AgentIDEnter, T1.ship_to_name, T1.shopper_id, T1.createdDate, T1.total_total, T1.discount_total, T2.OrderNumber,T1.byAgent'
SET @SortColumn = 'createdDate DESC'

-- Create temporary table
CREATE TABLE #TempTable(
RowNumber int IDENTITY (1, 1),
OrderNumber varchar(100) )
 
-- Find first record on selected page
SET @StartRecord = (@SelectedPage - 1) * @PageSize + 1
 
-- Find last record on selected page
SET @EndRecord = @SelectedPage * @PageSize
 
-- Check if there is WHERE clause
IF @WhereClause <>  ''
  BEGIN
    SET @WhereClause = ' WHERE ' + @WhereClause
  END
  
-- Build caculate total record
SET @SQLQuery = 'SELECT @i = (SELECT COUNT(OrderNumber) FROM (SELECT T1.OrderNumber ' + ' FROM ' + @TableOrViewName + @WhereClause  + @GroupBy +  ') tb1)'

EXEC sp_executesql @SQLQuery,  @params = N'@i INT OUTPUT', @i = @i OUTPUT 
SET @TotalRecord = @i
--PRINT @SQLQuery

-- Build INSERT statement used to populate temporary table
SET @SQLQuery = 'INSERT  #TempTable (OrderNumber) ' +
' SELECT TOP ' + CAST(@EndRecord AS varchar(20)) + ' ' + 'T1.OrderNumber' + ' FROM ' + @TableOrViewName + 
@WhereClause +  @GroupBy + '  ORDER BY ' +  @SortColumn 
-- Execute statement and populate temp table
EXEC (@SQLQuery)
--PRINT @SQLQuery

-- Build SQL query to return only selected page
SET @SQLQuery = N'SELECT RowNumber,' + @Columns + 
' FROM '  + @TableOrViewName + 
' JOIN ' + '#TempTable tmp ON tmp.OrderNumber = T1.OrderNumber ' +  
' WHERE RowNumber >= ' + CAST(@StartRecord AS varchar(20)) +
' AND RowNumber <= ' + CAST(@EndRecord AS varchar(20))  + @GroupBy + ',RowNumber' +
' ORDER BY RowNumber ' 
--PRINT @SQLQuery
-- Return selected page
EXEC (@SQLQuery)
-- Delete temporary table
DROP TABLE #TempTable
 
SET NOCOUNT OFF
GO

--## 20110314
--Author ThuyNguyen

IF OBJECT_ID ( 'uspGetOrderCustomerHeld', 'P' ) IS NOT NULL 
    DROP PROCEDURE uspGetOrderCustomerHeld;
GO
CREATE PROCEDURE uspGetOrderCustomerHeld
  @ShopperID varchar(255),
  @SelectedPage int,
  @PageSize int,
  @TotalRecord int OUTPUT
  AS
 
SET NOCOUNT ON

DECLARE @SQLQuery nvarchar(4000), @StartRecord int, @EndRecord int, @TableOrViewName varchar(500), @i int,@Columns varchar(3000)
DECLARE @GroupBy varchar(1000), @SortColumn varchar(255), @WhereClause varchar(255)

SET @Columns = 't1.shopper_id, t1.held_order, t2.order_id, t2.modified, t3.ship_to_fname + '' '' + t3.ship_to_lname AS ship_to_name, t3.ship_to_company, t3.bill_to_lname + '' '' + t3.bill_to_fname  as userHold '
SET @TableOrViewName = 'ordersHeld t1 INNER JOIN estore_basket t2 ON t1.held_order = t2.shopper_id  INNER JOIN contactInfo t3 ON t1.shopper_id = t3.shopper_id'
SET @WhereClause = 't3.shopper_id = '''+@ShopperID+'''  AND (t2.userHold IS NULL OR t2.userHold = '''') '
SET @GroupBy = 'GROUP BY t1.shopper_id, t1.held_order, t2.order_id, t2.modified, t3.ship_to_fname, t3.ship_to_lname, t3.ship_to_company, t3.bill_to_lname, t3.bill_to_fname,t2.userHold'
SET @SortColumn = 'modified DESC, t2.userHold'

-- Create temporary table
CREATE TABLE #TempTable(
RowNumber int IDENTITY (1, 1),
order_id varchar(100) )
 
-- Find first record on selected page
SET @StartRecord = (@SelectedPage - 1) * @PageSize + 1
 
-- Find last record on selected page
SET @EndRecord = @SelectedPage * @PageSize
 
-- Check if there is WHERE clause
IF @WhereClause <>  ''
  BEGIN
    SET @WhereClause = ' WHERE ' + @WhereClause
  END
  
-- Build caculate total record
SET @SQLQuery = 'SELECT @i = (SELECT COUNT(order_id) FROM (SELECT t2.order_id ' + ' FROM ' + @TableOrViewName + @WhereClause  + @GroupBy +  ') tb1)'

EXEC sp_executesql @SQLQuery,  @params = N'@i INT OUTPUT', @i = @i OUTPUT 
SET @TotalRecord = @i
--PRINT @SQLQuery

-- Build INSERT statement used to populate temporary table
SET @SQLQuery = 'INSERT  #TempTable (order_id) ' +
' SELECT TOP ' + CAST(@EndRecord AS varchar(20)) + ' ' + 't2.order_id' + ' FROM ' + @TableOrViewName + 
@WhereClause +  @GroupBy + '  ORDER BY ' +  @SortColumn 
-- Execute statement and populate temp table
EXEC (@SQLQuery)
--PRINT @SQLQuery

-- Build SQL query to return only selected page
SET @SQLQuery = N'SELECT RowNumber,' + @Columns + 
' FROM '  + @TableOrViewName + 
' JOIN ' + '#TempTable tmp ON tmp.order_id = t2.order_id ' +  
' WHERE RowNumber >= ' + CAST(@StartRecord AS varchar(20)) +
' AND RowNumber <= ' + CAST(@EndRecord AS varchar(20))  + @GroupBy + ',RowNumber' +
' ORDER BY RowNumber ' 
--PRINT @SQLQuery
-- Return selected page
EXEC (@SQLQuery)
-- Delete temporary table
DROP TABLE #TempTable
 
SET NOCOUNT OFF
GO


--Author Vinhhq
IF OBJECT_ID ( 'uspGetOrderByAgent', 'P' ) IS NOT NULL 
    DROP PROCEDURE uspGetOrderByAgent;
GO
CREATE PROCEDURE uspGetOrderByAgent
  @StartDate varchar(255),
  @EndDate varchar(255),
  @AgentID varchar(255),
  @SelectedPage int,
  @PageSize int,
  @TotalRecord int OUTPUT
  AS
 
SET NOCOUNT ON

DECLARE @SQLQuery nvarchar(4000), @StartRecord int, @EndRecord int, @TableOrViewName varchar(500), @i int,@Columns varchar(3000)
DECLARE @GroupBy varchar(1000), @SortColumn varchar(255), @WhereClause varchar(255)

SET @Columns = 'adminUsers.UserName AS Agent, T1.OrderNumber, T1.AgentIDEnter, T1.ship_to_name, T1.ship_to_company, T1.shopper_id, T1.createdDate, T1.total_total, T1.total_disc / T1.total_list * 100 AS discount_perc, T1.total_disc, COUNT(T2.OrderNumber) AS items_sold,T1.byAgent'
SET @TableOrViewName = 'adminUsers INNER JOIN orderHeader T1 INNER JOIN orderLine T2 ON T1.OrderNumber = T2.OrderNumber ON T1.AgentIDEnter = convert(varchar,adminUsers.agent_id)'
SET @WhereClause = 'T1.createddate BETWEEN '''+ @StartDate +''' AND '''+ @EndDate +''' AND T1.orderstatus <> ''CANCELED'' AND (adminUsers.agent_id = '+ @AgentID+') AND total_total > 0 AND T2.item_sku NOT LIKE ''FREE%'''
SET @GroupBy = 'GROUP BY adminUsers.UserName, T1.OrderNumber, T1.AgentIDEnter, T1.ship_to_name, T1.ship_to_company, T1.shopper_id, T1.createdDate, T1.total_total, T1.total_disc, T1.total_list, T2.OrderNumber,T1.byAgent'
SET @SortColumn = 'createdDate DESC'

-- Create temporary table
CREATE TABLE #TempTable(
RowNumber int IDENTITY (1, 1),
OrderNumber varchar(100) )
 
-- Find first record on selected page
SET @StartRecord = (@SelectedPage - 1) * @PageSize + 1
 
-- Find last record on selected page
SET @EndRecord = @SelectedPage * @PageSize
 
-- Check if there is WHERE clause
IF @WhereClause <>  ''
  BEGIN
    SET @WhereClause = ' WHERE ' + @WhereClause
  END
  
-- Build caculate total record
SET @SQLQuery = 'SELECT @i = (SELECT COUNT(OrderNumber) FROM (SELECT T1.OrderNumber ' + ' FROM ' + @TableOrViewName + @WhereClause  + @GroupBy +  ') tb1)'

EXEC sp_executesql @SQLQuery,  @params = N'@i INT OUTPUT', @i = @i OUTPUT 
SET @TotalRecord = @i
PRINT @SQLQuery

-- Build INSERT statement used to populate temporary table
SET @SQLQuery = 'INSERT  #TempTable (OrderNumber) ' +
' SELECT TOP ' + CAST(@EndRecord AS varchar(20)) + ' ' + 'T1.OrderNumber' + ' FROM ' + @TableOrViewName + 
@WhereClause +  @GroupBy + '  ORDER BY ' +  @SortColumn 
-- Execute statement and populate temp table
EXEC (@SQLQuery)
PRINT @SQLQuery

-- Build SQL query to return only selected page
SET @SQLQuery = N'SELECT RowNumber,' + @Columns + 
' FROM '  + @TableOrViewName + 
' JOIN ' + '#TempTable tmp ON tmp.OrderNumber = T1.OrderNumber ' +  
' WHERE RowNumber >= ' + CAST(@StartRecord AS varchar(20)) +
' AND RowNumber <= ' + CAST(@EndRecord AS varchar(20))  + @GroupBy + ',RowNumber' +
' ORDER BY RowNumber ' 
PRINT @SQLQuery
-- Return selected page
EXEC (@SQLQuery)
-- Delete temporary table
DROP TABLE #TempTable
 
SET NOCOUNT OFF
GO

--Author Vinhhq
IF OBJECT_ID ( 'uspGetOrderHeld', 'P' ) IS NOT NULL 
    DROP PROCEDURE uspGetOrderHeld;
GO
CREATE PROCEDURE uspGetOrderHeld
  @SelectedPage int,
  @PageSize int,
  @Type varchar(255),
  @AgentID varchar(255),
  @TotalRecord int OUTPUT
  AS
 
SET NOCOUNT ON

DECLARE @SQLQuery nvarchar(4000), @StartRecord int, @EndRecord int, @TableOrViewName varchar(500), @i int,@Columns varchar(3000)
DECLARE @GroupBy varchar(1000), @SortColumn varchar(255), @WhereClause varchar(255)

SET @TableOrViewName = 'ordersHeld t1 INNER JOIN estore_basket t2 ON t1.held_order = t2.shopper_id INNER JOIN contactInfo t3 ON t1.shopper_id = t3.shopper_id'

IF (@TYPE = 'AGENT')
BEGIN 
	SET @Columns = 't1.shopper_id, t1.held_order, t2.order_id, t2.modified, (t3.ship_to_fname + '' '' + t3.ship_to_lname) AS ship_to_name, t3.ship_to_company, (SELECT username FROM adminusers ad INNER JOIN contactinfo ct ON ad.agent_id=ct.agent_id where ct.shopper_id = t1.shopper_id) as userHeld'
	SET @WhereClause = 't2.userHold IS NOT NULL AND t2.userHold != '''' '
END
ELSE IF (@TYPE = 'CUSTOMER')
BEGIN
	SET @Columns = 't1.shopper_id, t1.held_order, t2.order_id, t2.modified, (t3.ship_to_fname + '' '' + t3.ship_to_lname) AS ship_to_name, t3.ship_to_company, (SELECT username FROM adminusers ad INNER JOIN contactinfo ct ON ad.agent_id=ct.agent_id where ct.shopper_id = t1.shopper_id) as userHeld' 
	SET @WhereClause = 't2.userHold IS NULL OR t2.userHold = ''''  '
END
ELSE IF (@TYPE = 'BYAGENT')
BEGIN 
	SET @Columns = 't1.shopper_id, t1.held_order, t2.order_id, t2.modified, (t3.ship_to_fname + '' '' + t3.ship_to_lname) AS ship_to_name, t3.ship_to_company, (SELECT username FROM adminusers ad INNER JOIN contactinfo ct ON ad.agent_id=ct.agent_id where ct.shopper_id = t1.shopper_id) as userHeld'
	SET @WhereClause = 't2.userHold =  '+ @AgentID +' '
END
ELSE IF (@TYPE = 'BYCUSTOMER')
BEGIN
	SET @Columns = 't1.shopper_id, t1.held_order, t2.order_id, t2.modified, (t3.ship_to_fname + '' '' + t3.ship_to_lname) AS ship_to_name, t3.ship_to_company, (SELECT username FROM adminusers ad INNER JOIN contactinfo ct ON ad.agent_id=ct.agent_id where ct.shopper_id = t1.shopper_id) as userHeld' 
	SET @WhereClause = '(t1.shopper_id IN (SELECT shopper_id FROM contactInfo WHERE agent_id = '+ @AgentID +' ) AND (t2.userHold IS NULL OR t2.userHold= ''''))'
END

SET @GroupBy = 'GROUP BY t1.shopper_id, t1.held_order, t2.order_id, t2.modified, t3.ship_to_fname, t3.ship_to_lname, t3.ship_to_company, t3.ship_to_fullName, t2.userHold '
SET @SortColumn = 'modified desc , (t3.ship_to_fname + '' '' + t3.ship_to_lname)'

-- Create temporary table
CREATE TABLE #TempTable(
RowNumber int IDENTITY (1, 1),
order_id varchar(100) )
 
-- Find first record on selected page
SET @StartRecord = (@SelectedPage - 1) * @PageSize + 1
 
-- Find last record on selected page
SET @EndRecord = @SelectedPage * @PageSize
 
-- Check if there is WHERE clause
IF @WhereClause <>  ''
  BEGIN
    SET @WhereClause = ' WHERE ' + @WhereClause
  END
  
-- Build caculate total record
SET @SQLQuery = 'SELECT @i = (SELECT COUNT(order_id) FROM (SELECT t2.order_id ' + ' FROM ' + @TableOrViewName + @WhereClause  + @GroupBy +  ') tb1)'

EXEC sp_executesql @SQLQuery,  @params = N'@i INT OUTPUT', @i = @i OUTPUT 
SET @TotalRecord = @i

-- Build INSERT statement used to populate temporary table
SET @SQLQuery = 'INSERT  #TempTable (order_id) ' +
' SELECT TOP ' + CAST(@EndRecord AS varchar(20)) + ' ' + 't2.order_id' + ' FROM ' + @TableOrViewName + 
@WhereClause +  @GroupBy + '  ORDER BY ' +  @SortColumn 
-- Execute statement and populate temp table
EXEC (@SQLQuery)

-- Build SQL query to return only selected page
SET @SQLQuery = N'SELECT RowNumber,' + @Columns + 
' FROM '  + @TableOrViewName + 
' JOIN ' + '#TempTable tmp ON tmp.order_id = t2.order_id ' +  
' WHERE RowNumber >= ' + CAST(@StartRecord AS varchar(20)) +
' AND RowNumber <= ' + CAST(@EndRecord AS varchar(20))  + @GroupBy + ',RowNumber' +
' ORDER BY RowNumber ' 

-- Return selected page
EXEC (@SQLQuery)
-- Delete temporary table
DROP TABLE #TempTable
 
SET NOCOUNT OFF
GO

--Author Linhdo
IF OBJECT_ID ( 'uspOrdersByShopper', 'P' ) IS NOT NULL 
    DROP PROCEDURE uspOrdersByShopper;
GO
CREATE PROCEDURE [uspOrdersByShopper]
  @SortColumn varchar(500),
  @SelectedPage int,
  @PageSize int,
  @WhereClause varchar(500),
  @TotalRecord int OUTPUT
  AS
 
SET NOCOUNT ON

DECLARE @SQLQuery nvarchar(4000), @StartRecord int, @EndRecord int, @TableOrViewName varchar(500), @i int,@Columns varchar(3000)
DECLARE @GroupBy varchar(255)

SET @Columns = ' T1.shopper_id, (T2.ship_to_fname+'' ''+T2.ship_to_lname+'' ''+T2.ship_to_company) as ship_to_name, count(OrderNumber) num_orders, sum(total_total) total_sum, avg(total_total) total_avg, max(total_total) total_max, min(total_total) total_min'
SET @TableOrViewName = ' orderHeader T1 INNER JOIN contactInfo T2 ON T1.shopper_id = T2.shopper_id '
SET @GroupBy = ' GROUP BY T1.shopper_id, T2.ship_to_fname ,T2.ship_to_lname,T2.ship_to_company '

-- Create temporary table
CREATE TABLE #TempTableOrdersByShopper (
RowNumber int IDENTITY (1, 1),
ShopperID varchar(1000) )
 
-- Find first record on selected page
SET @StartRecord = (@SelectedPage - 1) * @PageSize + 1
 
-- Find last record on selected page
SET @EndRecord = @SelectedPage * @PageSize
 
-- Check if there is WHERE clause
IF @WhereClause <>  ''
  BEGIN
    SET @WhereClause = ' WHERE ' + @WhereClause
  END
  
-- Build caculate total record
SET @SQLQuery = 'SELECT @i = (SELECT COUNT(shopper_id) FROM (SELECT T1.shopper_id ' + ' FROM ' + @TableOrViewName + @WhereClause  + @GroupBy +  ') tb1)'
PRINT @SQLQuery
EXEC sp_executesql @SQLQuery,  @params = N'@i INT OUTPUT', @i = @i OUTPUT 
SET @TotalRecord = @i

-- Build INSERT statement used to populate temporary table
SET @SQLQuery = 'INSERT  #TempTableOrdersByShopper (ShopperID) ' +
' SELECT TOP ' + CAST(@EndRecord AS varchar(20)) + ' T1.shopper_id ' + ' FROM ' + @TableOrViewName + 
@WhereClause +  @GroupBy + '  ORDER BY ' +  @SortColumn 
-- Execute statement and populate temp table
EXEC (@SQLQuery)
PRINT @SQLQuery
-- Build SQL query to return only selected page
SET @SQLQuery = N'SELECT RowNumber,tb1.* FROM (SELECT ' + @Columns + 
' FROM '  + @TableOrViewName +  @GroupBy + ') tb1 ' + 
' JOIN  #TempTableOrdersByShopper tmp ON tmp.ShopperID = tb1.shopper_id ' +  
' WHERE RowNumber >= ' + CAST(@StartRecord AS varchar(20)) +
' AND RowNumber <= ' + CAST(@EndRecord AS varchar(20)) +
' ORDER BY RowNumber ' 

-- Return selected page
EXEC (@SQLQuery)
PRINT @SQLQuery
-- Delete temporary table
DROP TABLE #TempTableOrdersByShopper
 
SET NOCOUNT OFF
GO

--Vinhhq
IF OBJECT_ID ( 'uspOrderHeldAgentAdmin', 'P' ) IS NOT NULL 
    DROP PROCEDURE uspOrderHeldAgentAdmin;
GO

--## 20110312
--Author Vinhhq

IF OBJECT_ID ( 'uspOrderLookup', 'P' ) IS NOT NULL 
    DROP PROCEDURE uspOrderLookup;
GO
CREATE PROCEDURE uspOrderLookup
  @SortColumn varchar(500),
  @SelectedPage int,
  @PageSize int,
  @WhereClause varchar(500),
  @TotalRecord int OUTPUT
  AS
 
SET NOCOUNT ON

DECLARE @SQLQuery nvarchar(4000), @StartRecord int, @EndRecord int, @TableOrViewName varchar(500), @i int,@Columns varchar(3000)
DECLARE @GroupBy varchar(255), @Top varchar(255)

SET @Columns = 'T1.OrderNumber, T1.AgentIDEnter, T3.shopper_number, T1.ship_to_name, T1.ship_to_company, T1.orderStatus, T1.shopper_id, T1.createdDate, T1.total_total, T1.oadjust_subtotal, T1.shipping_total, (T1.total_disc / T1.total_list * 100) AS discount_perc, COUNT(T2.OrderNumber) AS items_sold,T1.byAgent'
SET @TableOrViewName = 'orderHeader T1 INNER JOIN orderLine T2 ON T1.OrderNumber = T2.OrderNumber INNER JOIN contactInfo T3 ON T1.shopper_id = T3.shopper_id'
SET @GroupBy = ' GROUP BY T3.shopper_number, T1.OrderNumber, T1.AgentIDEnter, T1.ship_to_name, T1.shopper_id, T1.orderStatus, T1.createdDate, T1.total_total, T1.oadjust_subtotal, T1.shipping_total, T1.total_disc, total_list, T2.OrderNumber, T1.ship_to_company,T1.byAgent'

-- Create temporary table
CREATE TABLE #TempTableOrderLookup (
RowNumber int IDENTITY (1, 1),
OrderNumber varchar(100) )
 
-- Find first record on selected page
SET @StartRecord = (@SelectedPage - 1) * @PageSize + 1
 
-- Find last record on selected page
SET @EndRecord = @SelectedPage * @PageSize
 
-- Check if there is WHERE clause
IF @WhereClause <>  ''
  BEGIN
    SET @WhereClause = ' WHERE ' + @WhereClause
  END
  
-- Build caculate total record
SET @SQLQuery = 'SELECT @i = (SELECT COUNT(OrderNumber) FROM (SELECT T1.OrderNumber ' + ' FROM ' + @TableOrViewName + @WhereClause  + @GroupBy +  ') tb1)'

EXEC sp_executesql @SQLQuery,  @params = N'@i INT OUTPUT', @i = @i OUTPUT 
SET @TotalRecord = @i

IF (@SelectedPage > 0)
BEGIN
	SET @Top = CAST(@EndRecord AS varchar(20));
END 
ELSE
BEGIN
	SET @Top = CAST(@TotalRecord AS varchar(20));
END

 
-- Build INSERT statement used to populate temporary table
SET @SQLQuery = 'INSERT  #TempTableOrderLookup (OrderNumber) ' +
' SELECT TOP ' + @Top + ' ' + 'T1.OrderNumber' + ' FROM ' + @TableOrViewName + 
@WhereClause +  @GroupBy + '  ORDER BY ' +  @SortColumn 
-- Execute statement and populate temp table
EXEC (@SQLQuery)


IF (@SelectedPage > 0)
BEGIN 
	-- Build SQL query to return only selected page
	SET @SQLQuery = N'SELECT RowNumber,' + @Columns + 
	' FROM '  + @TableOrViewName + 
	' JOIN ' + '#TempTableOrderLookup tmp ON tmp.OrderNumber = T1.OrderNumber ' +  
	' WHERE RowNumber >= ' + CAST(@StartRecord AS varchar(20)) +
	' AND RowNumber <= ' + CAST(@EndRecord AS varchar(20))  + @GroupBy + ',RowNumber' +
	' ORDER BY RowNumber ' 
END
ELSE
BEGIN
	-- Build SQL query to return only selected page
	SET @SQLQuery = N'SELECT RowNumber,' + @Columns + 
	' FROM '  + @TableOrViewName + 
	' JOIN ' + '#TempTableOrderLookup tmp ON tmp.OrderNumber = T1.OrderNumber ' +  
	+ @GroupBy + ',RowNumber' +
	' ORDER BY RowNumber ' 
END

PRINT @SQLQuery
-- Return selected page
EXEC (@SQLQuery)
-- Delete temporary table
DROP TABLE #TempTableOrderLookup
 
SET NOCOUNT OFF
GO

--## 20110310
--Author Vinhhq
IF OBJECT_ID ( 'uspPagingSearchProduct', 'P' ) IS NOT NULL 
    DROP PROCEDURE uspPagingSearchProduct;
GO
CREATE PROCEDURE uspPagingSearchProduct
  @Columns varchar(3000),
  @SortColumn varchar(500),
  @SelectedPage int,
  @PageSize int,
  @WhereClause varchar(500),
  @TotalRecord int OUTPUT
  AS
 
SET NOCOUNT ON

DECLARE @SQLQuery nvarchar(4000), @StartRecord int, @EndRecord int, @TableOrViewName varchar(500), @i int

SET @TableOrViewName = 'estore_basket_item t3 RIGHT OUTER JOIN estore_inventory as t1  ON t3.item_sku = t1.item_sku'
 
-- Create temporary table
CREATE TABLE #TempTable (
RowNumber int IDENTITY (1, 1),
item_sku varchar(255) )
 
-- Find first record on selected page
SET @StartRecord = (@SelectedPage - 1) * @PageSize + 1
 
-- Find last record on selected page
SET @EndRecord = @SelectedPage * @PageSize
 
-- Check if there is WHERE clause
IF @WhereClause <>  ''
  BEGIN
    SET @WhereClause = ' WHERE ' + @WhereClause
  END
  
-- Build caculate total record
SET @SQLQuery = 'SELECT @i = (SELECT COUNT(t1.item_sku) AS COUNT ' + ' FROM ' + @TableOrViewName + @WhereClause  + ')'
EXEC sp_executesql @SQLQuery,  @params = N'@i INT OUTPUT', @i = @i OUTPUT 
SET @TotalRecord = @i

-- Build INSERT statement used to populate temporary table
SET @SQLQuery = 'INSERT  #TempTable (item_sku) ' +
' SELECT TOP ' + CAST(@EndRecord AS varchar(20)) + ' ' + 't1.item_sku' + ' FROM ' + @TableOrViewName + 
@WhereClause + '  ORDER BY ' + @SortColumn 
 
-- Execute statement and populate temp table
EXEC (@SQLQuery) 

-- Build SQL query to return only selected page
SET @SQLQuery = N'SELECT ' + @Columns +  ',RowNumber ' +
' FROM '  + @TableOrViewName + 
' JOIN ' + '#TempTable tmp ON tmp.item_sku = t1.item_sku ' +  
' WHERE RowNumber >= ' + CAST(@StartRecord AS varchar(20)) +
' AND RowNumber <= ' + CAST(@EndRecord AS varchar(20)) +
' ORDER BY RowNumber '

-- Return selected page
EXEC (@SQLQuery)
-- Delete temporary table
DROP TABLE #TempTable
 
SET NOCOUNT OFF
GO 

--## 20110316
--HuyNVT

IF OBJECT_ID ( 'uspPagingCustomerLookup', 'P' ) IS NOT NULL 
    DROP PROCEDURE uspPagingCustomerLookup;
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[uspPagingCustomerLookup]  
  @StartRecord int,
  @EndRecord int,
  @SortColumn varchar(500),
  @WhereClause varchar(500)  
  AS
 
SET NOCOUNT ON

-- Create temporary table
CREATE TABLE #TempTableCustomerLookup (
RowNumber int IDENTITY (1, 1),
shopper_id char(32))
 

DECLARE @SQLQuery nvarchar(4000)

-- Build INSERT statement used to populate temporary table
SET @SQLQuery = 'INSERT #TempTableCustomerLookup (shopper_id) SELECT shopper_id FROM contactInfo ' + @WhereClause + ' ORDER BY ' + @SortColumn 
-- Execute statement and populate temp table
EXEC (@SQLQuery)

-- Build SQL query to return only selected page
SET @SQLQuery = N'SELECT RowNumber, contactInfo.*,(SELECT COUNT(*) FROM #TempTableCustomerLookup) AS TotalRow  FROM contactInfo INNER JOIN #TempTableCustomerLookup tmp ON tmp.shopper_id = contactInfo.shopper_id ' +  
' WHERE RowNumber >= ' + CAST(@StartRecord AS varchar(10)) +
' AND RowNumber <= ' +  CAST(@EndRecord AS varchar(10))

-- Return selected page
EXEC (@SQLQuery)
-- Delete temporary table
DROP TABLE #TempTableCustomerLookup
 
SET NOCOUNT OFF
GO

--## 20110316
--HuyNVT

IF OBJECT_ID ( 'uspPagingAgentLookup', 'P' ) IS NOT NULL 
    DROP PROCEDURE uspPagingAgentLookup;
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[uspPagingAgentLookup]  
  @StartRecord int,
  @EndRecord int,
  @SortColumn varchar(500),
  @WhereClause varchar(500)  
  AS
 
SET NOCOUNT ON

-- Create temporary table
CREATE TABLE #TempTableCustomerLookup (
RowNumber int IDENTITY (1, 1),
agent_id char(32))
 

DECLARE @SQLQuery nvarchar(4000)

-- Build INSERT statement used to populate temporary table
SET @SQLQuery = 'INSERT #TempTableCustomerLookup (agent_id) SELECT agent_id FROM adminUsers ' + @WhereClause + ' ORDER BY ' + @SortColumn 
-- Execute statement and populate temp table
EXEC (@SQLQuery)

-- Build SQL query to return only selected page
SET @SQLQuery = N'SELECT RowNumber, adminUsers.*,(SELECT COUNT(*) FROM #TempTableCustomerLookup) AS TotalRow  FROM adminUsers INNER JOIN #TempTableCustomerLookup tmp ON tmp.agent_id = adminUsers.agent_id ' +  
' WHERE RowNumber >= ' + CAST(@StartRecord AS varchar(10)) +
' AND RowNumber <= ' +  CAST(@EndRecord AS varchar(10))

-- Return selected page
EXEC (@SQLQuery)
-- Delete temporary table
DROP TABLE #TempTableCustomerLookup
 
SET NOCOUNT OFF
GO

--## 20110316
--HuyNVT

IF OBJECT_ID ( 'uspCheckForAgentInUse', 'P' ) IS NOT NULL 
    DROP PROCEDURE uspCheckForAgentInUse;
GO
/****** Object:  StoredProcedure [dbo].[uspCheckForAgentInUse]    Script Date: 03/18/2011 19:11:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[uspCheckForAgentInUse]  
  @AgentID int 
  AS 
SET NOCOUNT ON

DECLARE @count int
SET @count = (select DISTINCT COUNT(agent_id)  from contactInfo where agent_id = @AgentID)
IF (@count > 0)
BEGIN
SELECT @count AS result
RETURN
END 

SET @count = (select DISTINCT COUNT(userHold)  from estore_basket where userHold = @AgentID)
IF (@count > 0)
BEGIN
SELECT @count AS result
RETURN
END

SET @count = @count +(select DISTINCT COUNT(AgentIDEnter)+ COUNT(EditAgent)  from orderHeader where AgentIDEnter = @AgentID OR EditAgent = CAST(@AgentID AS nvarchar(50)))
IF (@count > 0)
BEGIN
SELECT @count AS result
RETURN
END

SET @count = @count +(select DISTINCT COUNT(agentid) from agent_report where agentid = @AgentID)
IF (@count > 0)
BEGIN
SELECT @count AS result
RETURN
END

SET @count = @count +(select DISTINCT COUNT(agent_id) from contactLog where agent_id = @AgentID)
IF (@count > 0)
BEGIN
SELECT @count AS result
RETURN
END


SELECT 0 AS result

SET NOCOUNT OFF
GO