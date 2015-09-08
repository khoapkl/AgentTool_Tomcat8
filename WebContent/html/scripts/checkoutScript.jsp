
<script language="JavaScript" type="text/javascript">

	var refno = /^[0-9]{1,}[.]{1}[0-9]{2}$/;

	function formatCurrency(num) {
		num = num.toString().replace(/\$|\,/g, '');
		if (isNaN(num))
			num = "0";
		isNeg = false;
		if (num < 0) {
			num = num * -1;
			isNeg = true;
		}
		cents = Math.floor((num * 100 + 0.5) % 100);
		num = Math.floor((num * 100 + 0.5) / 100).toString();
		if (cents == 0)
			cents = "0";
		if (cents < 10)
			cents = "0" + cents;
		for ( var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
			num = num.substring(0, num.length - (4 * i + 3)) + ','
					+ num.substring(num.length - (4 * i + 3));
		num = "$" + num + "." + cents;
		if (isNeg) {
			num = '-' + num;
		}
		return (num);
	}

	function price_check(adjPrice, fieldName, input_object) {
		var bSigned = true;
		var sSigned = "(\\$?-?|-?\\$?)";
		var re = new RegExp(
				"^"
						+ ((bSigned) ? sSigned : "\\$?")
						+ "((\\d{1,3})*(,?\\d{3})*\\.?\\d{1,100}|\\d{1,3}(,?\\d{3})*\\.?(\\d{1,100})?)$");
	
		if (re.test(adjPrice)) {
			input_object.value = formatCurrency(adjPrice);
			return true;
		} else {
			alert('Invalid ' + fieldName + ' Entry.');
			input_object.focus();
			return false;
		}

	}

	function calc_discount(form_object, input_object, index, adjPrice,origPrice, discount_object, mhz_object, speed_object) 
	{
		//alert(adjPrice);
		var validatePrice = parseFloat(adjPrice.replace(/\$|\,/g, ''));
		//if(isNaN(validatePrice) || (validatePrice <= 0) || (validatePrice > origPrice))
		//Allows discount negative; Do add this line 30/May
		if(isNaN(validatePrice) || (validatePrice <= 0))
		{
			alert("Value input is invalid.");
			var discountedOld = origPrice - parseFloat((discount_object.value).replace(/\$|\,/g, ''));
			input_object.value =  formatCurrency(discountedOld) ;
			return false ; 
		}
		/*else if(validatePrice > origPrice)
		{
			alert("Value input is less than or equal to unit price.");
			var discountedOld = origPrice - parseFloat((discount_object.value).replace(/\$|\,/g, ''));
			input_object.value =  formatCurrency(discountedOld) ;
			return false ;
		}
		*/
		
		var adjSum = 0;
		var origSum = 0;
		var priceList;
		var arrOPrices, arrMPrices;
		var i, startIndex, endIndex, startIndex_Mhz, endIndex_Mhz;
		var cnt, price_len, letter;
		var LIDISCOUNT;
		var ttldiscp;
		var ttldisc;
		var LIDISCOUNT2;
		var discount;

		discount = origPrice - parseFloat((adjPrice).replace(/\$|\,/g, ''));

		prices_original = new String(form_object.price_list.value);
		sku_list = new String(form_object.sku_list.value);
		sku_mhz_list = new String(form_object.sku_mhz_list.value);

		cnt = parseInt(eval(form_object.row.value));
		arrOPrices = new Array(cnt);
		arrMPrices = new Array(cnt, 2);

		// original prices 
		startIndex = 0;
		for (i = 0; i < cnt; i++) 
		{
			endIndex = prices_original.indexOf(",", startIndex);
			arrOPrices[i] = parseFloat(eval(prices_original.substring(startIndex, endIndex)));
			startIndex = endIndex + 1;
		}

		// modified prices 
		startIndex = 0;
		startIndex_Mhz = 0;
		total_mhz = 0;
		total_mhz_price = 0;

		for (i = 0; i < cnt; i++) 
		{
			endIndex = sku_list.indexOf(",", startIndex);
			endIndex_Mhz = sku_mhz_list.indexOf(",", startIndex_Mhz);
			thisVal = eval("form_object." + sku_list.substring(startIndex, endIndex));
			thisMhz = parseFloat(eval("form_object." + sku_mhz_list.substring(startIndex_Mhz, endIndex_Mhz) + "hid.value"));
			
			if (thisMhz > 0) 
			{
				total_mhz = total_mhz + thisMhz;
				total_mhz_price = total_mhz_price + parseFloat(thisVal.value.replace(/\$|\,/g, ''));
			}
			arrMPrices[i] = parseFloat(thisVal.value.replace(/\$|\,/g, ''));
			
			if (!price_check(thisVal.value, "Line Item", thisVal)) 
			{
				return false;
			}
			startIndex = endIndex + 1;
			startIndex_Mhz = endIndex_Mhz + 1;
		}

		// sum up new total
		for (i = 0; i < cnt; i++) 
		{
			adjSum = adjSum + arrMPrices[i];
			origSum = origSum + arrOPrices[i];
		}

		if (adjSum != origSum) 
		{
			form_object.VOLUME.value = formatCurrency(0);
			disc_li = (origSum - adjSum);
			disc_total = (origSum - adjSum);
			disc_perc = ((origSum - adjSum) / origSum) * 100;
		} else {
			form_object.VOLUME.value = formatCurrency(origSum * form_object.Volume_Discount.value);
			disc_li = 0; 
			disc_total = origSum * form_object.Volume_Discount.value;
			disc_perc = form_object.Volume_Discount.value * 100;
		}

		disc_perc = Math.round(disc_perc * 100) / 100 + "%";
		if (speed_object > 0) 
		{
			mhz_object.value = Math.round(parseFloat((adjPrice).replace(/\$|\,/g, '')) / speed_object * 1000) / 1000;
		}
		if (total_mhz > 0) {
			form_object.AvgMhz.value = Math.round(total_mhz_price / total_mhz * 1000) / 1000;
		} else 
		{
			form_object.AvgMhz.value = 0;
		}
		//Allows discount negative; Do add this line 30/May
		form_object.avg_price_mhz.value =form_object.AvgMhz.value;
		
		if (form_object.VOLUME.value > formatCurrency(0)) 
		{
		
			discount_object.value = formatCurrency(discount);
			form_object.ttldisc.value = formatCurrency(0);
			form_object.adj_discount.value = "";
			form_object.ttldiscp.value = "0%";
			form_object.ETOTAL.value = formatCurrency(origSum - disc_total);
			form_object.Disc_TotCol.value = formatCurrency(0);
			form_object.Ext_TotCol.value = formatCurrency(adjSum);
			form_object.Est_SubTot.value = formatCurrency(adjSum);
		} else 
		{
			discount_object.value = formatCurrency(discount);
			form_object.ttldisc.value = formatCurrency(disc_total);
			form_object.adj_discount.value = disc_perc.replace(/\%|\,/g, '');
			form_object.ttldiscp.value = disc_perc;
			form_object.ETOTAL.value = formatCurrency(origSum - disc_total);
			form_object.Disc_TotCol.value = formatCurrency(disc_total);
			form_object.Ext_TotCol.value = formatCurrency(adjSum);
			form_object.Est_SubTot.value = formatCurrency(adjSum);
		}
	}
	
	function mass_cal_discount_option(form_object, cal_value) 
	{
		var i, startIndex_OP, startIndex_DP, startIndex_MP, startIndex_Mhz ;
		var endIndex_OP, endIndex_DP, endIndex_MP, endIndex_Mhz;
		var cnt, price_len, letter;
		var arrOPrices, arrMPrices;
		var modified_price, discount_price;
		var pass;

		prices_original = new String(form_object.price_list.value);
		sku_list = new String(form_object.sku_list.value);
		sku_disc_list = new String(form_object.sku_disc_list.value);
		sku_mhz_list = new String(form_object.sku_mhz_list.value);

		cnt = parseInt(eval(form_object.row.value));
		arrOPrices = new Array(cnt);
		arrMPrices = new Array(cnt, 2);

		startIndex_OP = 0;
		startIndex_MP = 0;
		startIndex_DP = 0;
		startIndex_Mhz = 0;
		modified_price = 0;
		discount_price = 0;
		total_discount = 0;
		total_ext_price = 0;
		total_total = 0;
		total_mhz = 0;
		total_mhz_price = 0;
		
		if (form_object.massdiscount.value == 1)
		{
			if (form_object.adj_discount.value == 0)
			{	
				mass_cal_discount_clear(form_object, cal_value);
			}
			else
				alert("Please input the value of 0 (zero) to clear the discount.");
		}
		else if (form_object.massdiscount.value == 2)
			if (cal_value > 80)
				alert("The discount percentage can't be more than 80%");
			else
				mass_cal_discount(form_object, cal_value);
		else if (form_object.massdiscount.value == 3)
		{
			/**
			pass = 1;
			for (i = 0; i < cnt; i++) 
			{
				endIndex_OP = prices_original.indexOf(",", startIndex_OP);
				endIndex_MP = sku_list.indexOf(",", startIndex_MP);
				endIndex_DP = sku_disc_list.indexOf(",", startIndex_DP);
				endIndex_Mhz = sku_mhz_list.indexOf(",", startIndex_Mhz);
				
				thisVal_MP = eval("form_object." + sku_list.substring(startIndex_MP, endIndex_MP));
				thisVal_OP = parseFloat(eval(prices_original.substring(startIndex_OP, endIndex_OP)));
				thisVal_DP = eval("form_object." + sku_disc_list.substring(startIndex_DP, endIndex_DP));
				thisMhz = parseFloat(eval("form_object." + sku_mhz_list.substring(startIndex_Mhz, endIndex_Mhz) + "hid.value"));
				
				if (sku_list.substring(startIndex_MP, endIndex_MP).substring(10, 18) != "WARRANTY")
				{
					if ((cal_value > thisVal_OP) || (cal_value < 1))
					{
						alert("One or more item(s) have a discount of more than the orignal price OR the unit price is $0 (zero). see Tag #" + sku_list.substring(startIndex_MP, endIndex_MP).replace("LIDiscount","") + ". Please review the discount value. DISCOUNT IS ABORTED!");
						i = cnt;
						pass = 0;
					}
				}
				
				startIndex_OP = endIndex_OP + 1;
				startIndex_MP = endIndex_MP + 1;
				startIndex_DP = endIndex_DP + 1;
				startIndex_Mhz = endIndex_Mhz + 1;
			}
			if (pass == 1) mass_cal_discount_amt(form_object, cal_value); **/
			mass_cal_discount_amt(form_object, cal_value);
		}
		else if (form_object.massdiscount.value == 4)
		{
			/**
			pass = 1;
			for (i = 0; i < cnt; i++) 
			{
				endIndex_OP = prices_original.indexOf(",", startIndex_OP);
				endIndex_MP = sku_list.indexOf(",", startIndex_MP);
				endIndex_DP = sku_disc_list.indexOf(",", startIndex_DP);
				endIndex_Mhz = sku_mhz_list.indexOf(",", startIndex_Mhz);
				
				thisVal_MP = eval("form_object." + sku_list.substring(startIndex_MP, endIndex_MP));
				thisVal_OP = parseFloat(eval(prices_original.substring(startIndex_OP, endIndex_OP)));
				thisVal_DP = eval("form_object." + sku_disc_list.substring(startIndex_DP, endIndex_DP));
				thisMhz = parseFloat(eval("form_object." + sku_mhz_list.substring(startIndex_Mhz, endIndex_Mhz) + "hid.value"));
				
				if (sku_list.substring(startIndex_MP, endIndex_MP).substring(10, 18) != "WARRANTY") 
				{
					if (thisMhz > 0)
					{
						if (thisVal_OP < (cal_value / 100 * thisMhz))
						{
							alert("One ore more item(s) have discount more than the orignal price. see Tag #" + sku_list.substring(startIndex_MP, endIndex_MP).substring(10, 18) + ". Please review the discount value. DISCOUNT IS ABORTED!");
							i = cnt;
							pass = 0;
						}
					}
				}
				
				startIndex_OP = endIndex_OP + 1;
				startIndex_MP = endIndex_MP + 1;
				startIndex_DP = endIndex_DP + 1;
				startIndex_Mhz = endIndex_Mhz + 1;
			}
			if (pass == 1) mass_cal_discount_mhz(form_object, cal_value); **/
			mass_cal_discount_mhz(form_object, cal_value);
		}
		else
			alert("Please select a discount type.");
	}

	function mass_cal_discount(form_object, cal_value) 
	{
		//if(isNaN(cal_value) || (cal_value < 0) || (cal_value > 100))
		//Allows discount negative; Do add this line 30/May
		if(isNaN(cal_value))
		{
			alert("Discount Percent is invalid.");
			form_object.adj_discount.value = form_object.ttldiscp.value.replace(/\%|\,/g, '') ;
			form_object.adj_discount.focus();
			return false ;
		}
		
		var i, startIndex_OP, startIndex_DP, startIndex_MP, startIndex_Mhz ;
		var endIndex_OP, endIndex_DP, endIndex_MP, endIndex_Mhz;
		var cnt, price_len, letter;
		var arrOPrices, arrMPrices;
		var modified_price, discount_price;
		var warranty_price;

		prices_original = new String(form_object.price_list.value);
		sku_list = new String(form_object.sku_list.value);
		sku_disc_list = new String(form_object.sku_disc_list.value);
		sku_mhz_list = new String(form_object.sku_mhz_list.value);

		cnt = parseInt(eval(form_object.row.value));
		arrOPrices = new Array(cnt);
		arrMPrices = new Array(cnt, 2);

		startIndex_OP = 0;
		startIndex_MP = 0;
		startIndex_DP = 0;
		startIndex_Mhz = 0;
		modified_price = 0;
		discount_price = 0;
		total_discount = 0;
		total_ext_price = 0;
		total_total = 0;
		total_mhz = 0;
		total_mhz_price = 0;
		warranty_price = 0;
		for (i = 0; i < cnt; i++) 
		{
			endIndex_OP = prices_original.indexOf(",", startIndex_OP);
			endIndex_MP = sku_list.indexOf(",", startIndex_MP);
			endIndex_DP = sku_disc_list.indexOf(",", startIndex_DP);
			endIndex_Mhz = sku_mhz_list.indexOf(",", startIndex_Mhz);
			
			thisVal_MP = eval("form_object." + sku_list.substring(startIndex_MP, endIndex_MP));
			thisVal_OP = parseFloat(eval(prices_original.substring(startIndex_OP, endIndex_OP)));
			thisVal_DP = eval("form_object." + sku_disc_list.substring(startIndex_DP, endIndex_DP));
			thisMhz = parseFloat(eval("form_object." + sku_mhz_list.substring(startIndex_Mhz, endIndex_Mhz) + "hid.value"));

			if (sku_list.substring(startIndex_MP, endIndex_MP).substring(10, 18) == "WARRANTY") {
				//alert("form_object." + "qty" + sku_list.substring(startIndex_MP, endIndex_MP).replace("LIDiscount",""));
				//alert(eval("form_object." + "qty" + sku_list.substring(startIndex_MP, endIndex_MP).replace("LIDiscount","") + ".value"));
				modified_price = thisVal_OP * eval("form_object." + "qty" + sku_list.substring(startIndex_MP, endIndex_MP).replace("LIDiscount","") + ".value");
				warranty_price = warranty_price + modified_price;
				discount_price = 0;
			}
			else 
			{
				modified_price = thisVal_OP - (thisVal_OP * cal_value / 100);
				discount_price = thisVal_OP * cal_value / 100;
			}
			total_discount = total_discount + discount_price;
			total_ext_price = total_ext_price + modified_price;
			total_total = total_total + (thisVal_OP * eval("form_object." + "qty" + sku_list.substring(startIndex_MP, endIndex_MP).replace("LIDiscount","") + ".value"));
			eval("form_object." + sku_list.substring(startIndex_MP, endIndex_MP) + ".value =" + modified_price);
			eval("form_object." + sku_disc_list.substring(startIndex_DP, endIndex_DP) + ".value =" + discount_price);
			if (thisMhz > 0) {
				total_mhz = total_mhz + thisMhz;
				total_mhz_price = total_mhz_price + modified_price;
				eval("form_object." + sku_mhz_list.substring(startIndex_Mhz, endIndex_Mhz) + ".value ="
							+ Math.round(modified_price / thisMhz * 1000) / 1000);
			}
			
			if (!price_check(thisVal_MP.value, "Line Item", thisVal_MP)) {
				return false;
			}

			if (!price_check(thisVal_DP.value, "Line Item", thisVal_DP)) {
				return false;
			}

			startIndex_OP = endIndex_OP + 1;
			startIndex_MP = endIndex_MP + 1;
			startIndex_DP = endIndex_DP + 1;
			startIndex_Mhz = endIndex_Mhz + 1;
		}
		form_object.Disc_TotCol.value = formatCurrency(total_discount);
		form_object.Ext_TotCol.value = formatCurrency(total_ext_price);
		form_object.ttldisc.value = formatCurrency(total_discount);
		form_object.ttldiscp.value = Math.round(cal_value * 100) / 100 + "%";
		form_object.ETOTAL.value = formatCurrency(total_total - total_discount);
		form_object.Est_SubTot.value = formatCurrency(total_ext_price);
		if (total_mhz > 0) {
			form_object.AvgMhz.value = Math.round(total_mhz_price / total_mhz
					* 1000) / 1000;
			form_object.avg_price_mhz.value = Math.round(total_mhz_price
					/ total_mhz * 1000) / 1000;
		} else {
			form_object.AvgMhz.value = 0;
			form_object.avg_price_mhz.value = 0;
		}
		return true;
	}
	
	function mass_cal_discount_amt(form_object, cal_value) 
	{
		//if(isNaN(cal_value) || (cal_value < 0) || (cal_value > 100))
		//Allows discount negative; Do add this line 30/May
		if(isNaN(cal_value))
		{
			alert("Discount Percent is invalid.");
			form_object.adj_discount.value = form_object.ttldiscp.value.replace(/\%|\,/g, '') ;
			form_object.adj_discount.focus();
			return false ;
		}
		
		var i, startIndex_OP, startIndex_DP, startIndex_MP, startIndex_Mhz ;
		var endIndex_OP, endIndex_DP, endIndex_MP, endIndex_Mhz;
		var cnt, price_len, letter;
		var arrOPrices, arrMPrices;
		var modified_price, discount_price;
		var warranty_price;

		prices_original = new String(form_object.price_list.value);
		sku_list = new String(form_object.sku_list.value);
		sku_disc_list = new String(form_object.sku_disc_list.value);
		sku_mhz_list = new String(form_object.sku_mhz_list.value);

		cnt = parseInt(eval(form_object.row.value));
		arrOPrices = new Array(cnt);
		arrMPrices = new Array(cnt, 2);

		startIndex_OP = 0;
		startIndex_MP = 0;
		startIndex_DP = 0;
		startIndex_Mhz = 0;
		modified_price = 0;
		discount_price = 0;
		total_discount = 0;
		total_ext_price = 0;
		total_total = 0;
		total_mhz = 0;
		total_mhz_price = 0;
		warranty_price = 0;
		for (i = 0; i < cnt; i++) 
		{
			endIndex_OP = prices_original.indexOf(",", startIndex_OP);
			endIndex_MP = sku_list.indexOf(",", startIndex_MP);
			endIndex_DP = sku_disc_list.indexOf(",", startIndex_DP);
			endIndex_Mhz = sku_mhz_list.indexOf(",", startIndex_Mhz);
			
			thisVal_MP = eval("form_object." + sku_list.substring(startIndex_MP, endIndex_MP));
			thisVal_OP = parseFloat(eval(prices_original.substring(startIndex_OP, endIndex_OP)));
			thisVal_DP = eval("form_object." + sku_disc_list.substring(startIndex_DP, endIndex_DP));
			thisMhz = parseFloat(eval("form_object." + sku_mhz_list.substring(startIndex_Mhz, endIndex_Mhz) + "hid.value"));

			if (sku_list.substring(startIndex_MP, endIndex_MP).substring(10, 18) == "WARRANTY") {
				//alert("form_object." + "qty" + sku_list.substring(startIndex_MP, endIndex_MP).replace("LIDiscount",""));
				//alert(eval("form_object." + "qty" + sku_list.substring(startIndex_MP, endIndex_MP).replace("LIDiscount","") + ".value"));
				modified_price = thisVal_OP * eval("form_object." + "qty" + sku_list.substring(startIndex_MP, endIndex_MP).replace("LIDiscount","") + ".value");
				discount_price = 0;
				warranty_price = (warranty_price + modified_price) * 1;
			}
			else 
			{
				modified_price = cal_value * 1; //thisVal_OP - (thisVal_OP * cal_value / 100);
				discount_price = thisVal_OP - cal_value; //thisVal_OP * cal_value / 100;
			}
			total_discount = total_discount + discount_price;
			total_ext_price = total_ext_price + modified_price;
			total_total = total_total + (thisVal_OP * eval("form_object." + "qty" + sku_list.substring(startIndex_MP, endIndex_MP).replace("LIDiscount","") + ".value"));
			eval("form_object." + sku_list.substring(startIndex_MP, endIndex_MP) + ".value =" + modified_price);
			eval("form_object." + sku_disc_list.substring(startIndex_DP, endIndex_DP) + ".value =" + discount_price);
			if (thisMhz > 0) {
				total_mhz = total_mhz + thisMhz;
				total_mhz_price = total_mhz_price + modified_price;
				eval("form_object." + sku_mhz_list.substring(startIndex_Mhz, endIndex_Mhz) + ".value ="
							+ Math.round(modified_price / thisMhz * 1000) / 1000);
			}
			
			if (!price_check(thisVal_MP.value, "Line Item", thisVal_MP)) {
				return false;
			}

			if (!price_check(thisVal_DP.value, "Line Item", thisVal_DP)) {
				return false;
			}

			startIndex_OP = endIndex_OP + 1;
			startIndex_MP = endIndex_MP + 1;
			startIndex_DP = endIndex_DP + 1;
			startIndex_Mhz = endIndex_Mhz + 1;
		}
		form_object.Disc_TotCol.value = formatCurrency(total_discount);
		form_object.Ext_TotCol.value = formatCurrency(total_ext_price);
		form_object.ttldisc.value = formatCurrency(total_discount);
		form_object.ttldiscp.value = Math.round((total_discount / (total_total - warranty_price) * 100) * 100)/100 + "%";
		form_object.ETOTAL.value = formatCurrency(total_total - total_discount);
		form_object.Est_SubTot.value = formatCurrency(total_ext_price);
		if (total_mhz > 0) {
			form_object.AvgMhz.value = Math.round(total_mhz_price / total_mhz
					* 1000) / 1000;
			form_object.avg_price_mhz.value = Math.round(total_mhz_price
					/ total_mhz * 1000) / 1000;
		} else {
			form_object.AvgMhz.value = 0;
			form_object.avg_price_mhz.value = 0;
		}
		return true;
	}
	
	function mass_cal_discount_mhz(form_object, cal_value) 
	{
		//if(isNaN(cal_value) || (cal_value < 0) || (cal_value > 100))
		//Allows discount negative; Do add this line 30/May
		if(isNaN(cal_value))
		{
			alert("Discount is invalid.");
			form_object.adj_discount.value = form_object.ttldiscp.value.replace(/\%|\,/g, '') ;
			form_object.adj_discount.focus();
			return false ;
		}
		
		var i, startIndex_OP, startIndex_DP, startIndex_MP, startIndex_Mhz ;
		var endIndex_OP, endIndex_DP, endIndex_MP, endIndex_Mhz;
		var cnt, price_len, letter;
		var arrOPrices, arrMPrices;
		var modified_price, discount_price;
		var warranty_price;

		prices_original = new String(form_object.price_list.value);
		sku_list = new String(form_object.sku_list.value);
		sku_disc_list = new String(form_object.sku_disc_list.value);
		sku_mhz_list = new String(form_object.sku_mhz_list.value);

		cnt = parseInt(eval(form_object.row.value));
		arrOPrices = new Array(cnt);
		arrMPrices = new Array(cnt, 2);

		startIndex_OP = 0;
		startIndex_MP = 0;
		startIndex_DP = 0;
		startIndex_Mhz = 0;
		modified_price = 0;
		discount_price = 0;
		total_discount = 0;
		total_ext_price = 0;
		total_total = 0;
		total_mhz = 0;
		total_mhz_price = 0;
		warranty_price = 0;
		for (i = 0; i < cnt; i++) 
		{
			endIndex_OP = prices_original.indexOf(",", startIndex_OP);
			endIndex_MP = sku_list.indexOf(",", startIndex_MP);
			endIndex_DP = sku_disc_list.indexOf(",", startIndex_DP);
			endIndex_Mhz = sku_mhz_list.indexOf(",", startIndex_Mhz);
			
			thisVal_MP = eval("form_object." + sku_list.substring(startIndex_MP, endIndex_MP));
			thisVal_OP = parseFloat(eval(prices_original.substring(startIndex_OP, endIndex_OP)));
			thisVal_DP = eval("form_object." + sku_disc_list.substring(startIndex_DP, endIndex_DP));
			thisMhz = parseFloat(eval("form_object." + sku_mhz_list.substring(startIndex_Mhz, endIndex_Mhz) + "hid.value"));

			if (thisMhz > 0) {
				total_mhz = total_mhz + thisMhz;
				total_mhz_price = (cal_value * thisMhz) + total_mhz_price; //total_mhz_price + modified_price;
				//eval("form_object." + sku_mhz_list.substring(startIndex_Mhz, endIndex_Mhz) + ".value ="
				//			+ Math.round(modified_price / thisMhz * 1000) / 1000);
				eval("form_object." + sku_mhz_list.substring(startIndex_Mhz, endIndex_Mhz) + ".value ="
						+ Math.round(cal_value * 1000) / 1000);
			}
			
			if (sku_list.substring(startIndex_MP, endIndex_MP).substring(10, 18) == "WARRANTY") {
				//alert("form_object." + "qty" + sku_list.substring(startIndex_MP, endIndex_MP).replace("LIDiscount",""));
				//alert(eval("form_object." + "qty" + sku_list.substring(startIndex_MP, endIndex_MP).replace("LIDiscount","") + ".value"));
				modified_price = thisVal_OP * eval("form_object." + "qty" + sku_list.substring(startIndex_MP, endIndex_MP).replace("LIDiscount","") + ".value");
				warranty_price = warranty_price + modified_price;
				discount_price = 0;
			}
			else if (thisMhz > 0)
			{
				modified_price = cal_value * thisMhz; //thisVal_OP - (thisVal_OP * cal_value / 100);
				discount_price = thisVal_OP - (cal_value * thisMhz); //thisVal_OP * cal_value / 100;
			}
			else
			{
				modified_price = thisVal_OP;
				discount_price = 0;
			}
			total_discount = total_discount + discount_price;
			total_ext_price = total_ext_price + modified_price;
			total_total = total_total + (thisVal_OP * eval("form_object." + "qty" + sku_list.substring(startIndex_MP, endIndex_MP).replace("LIDiscount","") + ".value"));
			eval("form_object." + sku_list.substring(startIndex_MP, endIndex_MP) + ".value =" + modified_price);
			eval("form_object." + sku_disc_list.substring(startIndex_DP, endIndex_DP) + ".value =" + discount_price);
			
			
			if (!price_check(thisVal_MP.value, "Line Item", thisVal_MP)) {
				return false;
			}

			if (!price_check(thisVal_DP.value, "Line Item", thisVal_DP)) {
				return false;
			}

			startIndex_OP = endIndex_OP + 1;
			startIndex_MP = endIndex_MP + 1;
			startIndex_DP = endIndex_DP + 1;
			startIndex_Mhz = endIndex_Mhz + 1;
		}
		form_object.Disc_TotCol.value = formatCurrency(total_discount);
		form_object.Ext_TotCol.value = formatCurrency(total_ext_price);
		form_object.ttldisc.value = formatCurrency(total_discount);
		form_object.ttldiscp.value = Math.round((total_discount / (total_total - warranty_price) * 100) * 100)/100 + "%";
		form_object.ETOTAL.value = formatCurrency(total_total - total_discount);
		form_object.Est_SubTot.value = formatCurrency(total_ext_price);
		if (total_mhz > 0) {
			form_object.AvgMhz.value = Math.round(total_mhz_price / total_mhz
					* 1000) / 1000;
			form_object.avg_price_mhz.value = Math.round(total_mhz_price
					/ total_mhz * 1000) / 1000;
		} else {
			form_object.AvgMhz.value = 0;
			form_object.avg_price_mhz.value = 0;
		}
		return true;
	}
	
	function mass_cal_discount_clear(form_object, cal_value) 
	{
		//if(isNaN(cal_value) || (cal_value < 0) || (cal_value > 100))
		//Allows discount negative; Do add this line 30/May
		if(isNaN(cal_value))
		{
			alert("Discount Percent is invalid.");
			form_object.adj_discount.value = form_object.ttldiscp.value.replace(/\%|\,/g, '') ;
			form_object.adj_discount.focus();
			return false ;
		}
		
		var i, startIndex_OP, startIndex_DP, startIndex_MP, startIndex_Mhz ;
		var endIndex_OP, endIndex_DP, endIndex_MP, endIndex_Mhz;
		var cnt, price_len, letter;
		var arrOPrices, arrMPrices;
		var modified_price, discount_price;

		prices_original = new String(form_object.price_list.value);
		sku_list = new String(form_object.sku_list.value);
		sku_disc_list = new String(form_object.sku_disc_list.value);
		sku_mhz_list = new String(form_object.sku_mhz_list.value);

		cnt = parseInt(eval(form_object.row.value));
		arrOPrices = new Array(cnt);
		arrMPrices = new Array(cnt, 2);

		startIndex_OP = 0;
		startIndex_MP = 0;
		startIndex_DP = 0;
		startIndex_Mhz = 0;
		modified_price = 0;
		discount_price = 0;
		total_discount = 0;
		total_ext_price = 0;
		total_total = 0;
		total_mhz = 0;
		total_mhz_price = 0;
		for (i = 0; i < cnt; i++) 
		{
			endIndex_OP = prices_original.indexOf(",", startIndex_OP);
			endIndex_MP = sku_list.indexOf(",", startIndex_MP);
			endIndex_DP = sku_disc_list.indexOf(",", startIndex_DP);
			endIndex_Mhz = sku_mhz_list.indexOf(",", startIndex_Mhz);
			
			thisVal_MP = eval("form_object." + sku_list.substring(startIndex_MP, endIndex_MP));
			thisVal_OP = parseFloat(eval(prices_original.substring(startIndex_OP, endIndex_OP)));
			thisVal_DP = eval("form_object." + sku_disc_list.substring(startIndex_DP, endIndex_DP));
			thisMhz = parseFloat(eval("form_object." + sku_mhz_list.substring(startIndex_Mhz, endIndex_Mhz) + "hid.value"));

			if (sku_list.substring(startIndex_MP, endIndex_MP).substring(10, 18) == "WARRANTY") {
				//alert("form_object." + "qty" + sku_list.substring(startIndex_MP, endIndex_MP).replace("LIDiscount",""));
				//alert(eval("form_object." + "qty" + sku_list.substring(startIndex_MP, endIndex_MP).replace("LIDiscount","") + ".value"));
				modified_price = thisVal_OP * eval("form_object." + "qty" + sku_list.substring(startIndex_MP, endIndex_MP).replace("LIDiscount","") + ".value");
				
				discount_price = 0;
			}
			else 
			{
				modified_price = thisVal_OP;
				discount_price = 0;
			}
			total_discount = total_discount + discount_price;
			total_ext_price = total_ext_price + modified_price;
			total_total = total_total + (thisVal_OP * eval("form_object." + "qty" + sku_list.substring(startIndex_MP, endIndex_MP).replace("LIDiscount","") + ".value"));
			eval("form_object." + sku_list.substring(startIndex_MP, endIndex_MP) + ".value =" + modified_price);
			eval("form_object." + sku_disc_list.substring(startIndex_DP, endIndex_DP) + ".value =" + discount_price);
			if (thisMhz > 0) {
				total_mhz = total_mhz + thisMhz;
				total_mhz_price = total_mhz_price + modified_price;
				eval("form_object." + sku_mhz_list.substring(startIndex_Mhz, endIndex_Mhz) + ".value ="
							+ Math.round(modified_price / thisMhz * 1000) / 1000);
			}
			
			if (!price_check(thisVal_MP.value, "Line Item", thisVal_MP)) {
				return false;
			}

			if (!price_check(thisVal_DP.value, "Line Item", thisVal_DP)) {
				return false;
			}

			startIndex_OP = endIndex_OP + 1;
			startIndex_MP = endIndex_MP + 1;
			startIndex_DP = endIndex_DP + 1;
			startIndex_Mhz = endIndex_Mhz + 1;
		}
		form_object.Disc_TotCol.value = formatCurrency(total_discount);
		form_object.Ext_TotCol.value = formatCurrency(total_ext_price);
		form_object.ttldisc.value = formatCurrency(total_discount);
		form_object.ttldiscp.value = Math.round(cal_value * 100) / 100 + "%";
		form_object.ETOTAL.value = formatCurrency(total_total - total_discount);
		form_object.Est_SubTot.value = formatCurrency(total_ext_price);
		if (total_mhz > 0) {
			form_object.AvgMhz.value = Math.round(total_mhz_price / total_mhz
					* 1000) / 1000;
			form_object.avg_price_mhz.value = Math.round(total_mhz_price
					/ total_mhz * 1000) / 1000;
		} else {
			form_object.AvgMhz.value = 0;
			form_object.avg_price_mhz.value = 0;
		}
		return true;
	}

	function check_shipterms(checkForm) {

		if (price_check(checkForm.ship_cost.value, "Shipping Cost", checkForm.ship_cost)) 
		{
			if ((checkForm.ship_terms.value == "Description of Other Shipping") || (checkForm.ship_terms.value == "")) 
			{
				alert("Please Describe the Shipping Terms");
				return false;
			}else
			{
				return true;
			}
		}
		return false;
	}

	function CheckMaxDiscount(checkForm) 
	{
		var val2 = parseFloat(checkForm.MaxDiscount.value);
		var val1 = parseFloat(checkForm.ttldiscp.value);
		if (val1 > val2) 
		{
			alert('Total Discount exceeds Max Discount of ' + val2 + '%.');
			return false;
		}else 
		{
			return true;
		}

	}

	function submitForm() 
	{
		var flag = true; 
		<%
		if(isCustomer == null)
		{
		%>
		if (!CheckMaxDiscount(document.payinfo)) 
		{
			flag = false;
		}
		<%
		}
		%>
		
		if(flag == true)
		{
			if ((document.payinfo.dtmultip.value > 0) && (document.payinfo.nbmultip.value > 0)) 
			{
				dialogOpen();
				document.payinfo.submit();
			}else 
			{
				alert("Please select a multiPack Qty.");
			}
		}
	}

</script>

