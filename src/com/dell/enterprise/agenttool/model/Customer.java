package com.dell.enterprise.agenttool.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class Customer implements Serializable
{
    /**
     * <p>
     * TODO Describe what this data member models and how it's used.
     * </p>
     **/

    private static final long serialVersionUID = 1L;
    private int linkNumber;
    private int shopper_number;	
	private String shopper_id; 
	private String ship_to_fname;
	private String ship_to_lname;
	private String ship_to_company;
	private String ship_to_phone;
	private String ship_to_county;
	private String ship_to_country;
	private String ship_to_state;
	private String ship_to_fax;
	private String ship_to_email;
	private String bill_to_fname;
	private String bill_to_lname;
	private String bill_to_company;
	private String bill_to_phone;
	private String bill_to_title;
	private String bill_to_address1;	
	private String bill_to_address2;
	private String bill_to_city;
	private String bill_to_state;
	private String bill_to_country;
	private String bill_to_county;
	private String bill_to_postal;
	private String bill_to_fax;
	private String bill_to_phoneext;
	private String bill_to_email;
	private String ship_to_address1;
	private String ship_to_title;
	private String ship_to_phoneext;
	private String ship_to_address2;
	private String ship_to_city;
	private String ship_to_postal;
	private Date tax_exempt_expire;
	private String tax_exempt_number;
	
	private float specialty_discount;
	private	int tax_exempt;
	private String loginID;      
	private	String password;     
	private	String hint;         
	private	String answer;       
	private int promo_email;  
	private	String loa;
	private	int latperdiscount;  
	private	int latamtdiscount;  
	private	Date latexpdate;     
	private	int insperdiscount;
	private	int insamtdiscount; 
	private	Date insexpdate;    
	private	int optperdiscount;
	private	int optamtdiscount;
	private	Date optexpdate;
	private	int dimperdiscount;
	private	int dimamtdiscount;
	private	Date dimexpdate;
	private	int monperdiscount;
	private	int monamtdiscount;
	private	Date monexpdate; 
	private	int serperdiscount; 
	private	int seramtdiscount; 
	private	Date serexpdate;    
	private	int worperdiscount;
	private	int woramtdiscount;
	private	Date worexpdate;  
	private	String account_type;
	private	int heardAboutSiteFrom;
	private	String equip_use;
	private	int agent_id;
	private	Date agent_id_exp;
	private BigDecimal creditline;
	
	public int getAgent_id() {
		return agent_id;
	}

	public void setAgent_id(int agentId) {
		agent_id = agentId;
	}
	
	public int getShopper_number() {
		return shopper_number;
	}

	public void setShopper_number(int shopperNumber) {
		shopper_number = shopperNumber;
	}

	public Date getAgent_id_exp() {
		return agent_id_exp;
	}

	public void setAgent_id_exp(Date agentIdExp) {
		agent_id_exp = agentIdExp;
	}

	public String getEquip_use() {
		return equip_use;
	}

	public void setEquip_use(String equipUse) {
		equip_use = equipUse;
	}

	public int getHeardAboutSiteFrom() {
		return heardAboutSiteFrom;
	}

	public void setHeardAboutSiteFrom(int heardAboutSiteFrom) {
		this.heardAboutSiteFrom = heardAboutSiteFrom;
	}

	public String getAccount_type() {
		if (account_type == null)
			return "";
		else
			return account_type;
	}

	public void setAccount_type(String accountType) {
		if (accountType == null)
			account_type = "";
		else
			account_type = accountType;
	}

	public String getShip_to_fax() {
		return ship_to_fax;
	}

	public void setShip_to_fax(String shipToFax) {
		ship_to_fax = shipToFax;
	}
	
	public String getShip_to_email() {
		return (ship_to_email == null)?"":ship_to_email.trim();	
	}

	public void setShip_to_email(String shipToEmail) {
		ship_to_email = shipToEmail;
	}

	public String getShip_to_country() {
		return ship_to_country;
	}

	public void setShip_to_country(String shipToCountry) {
		ship_to_country = shipToCountry;
	}

	public String getShip_to_state() {
		return ship_to_state;
	}

	public void setShip_to_state(String shipToState) {
		ship_to_state = shipToState;
	}

	public String getShip_to_county() {
		return ship_to_county;
	}

	public void setShip_to_county(String shipToCounty) {
		ship_to_county = shipToCounty;
	}

	public String getBill_to_county() {
		return bill_to_county;
	}

	public void setBill_to_county(String billToCounty) {
		bill_to_county = billToCounty;
	}
	
	public float getSpecialty_discount() {
		return specialty_discount;
	}

	public void setSpecialty_discount(float specialtyDiscount) {
		specialty_discount = specialtyDiscount;
	}

	public int getTax_exempt() {
		return tax_exempt;
	}

	public void setTax_exempt(int taxExempt) {
		tax_exempt = taxExempt;
	}
	
	public String getLoginID() {
		if (loginID == null)
			return "";
		else			
			return loginID;
	}

	public void setLoginID(String loginID) {
		if (loginID == null)
			this.loginID = "";
		else
			this.loginID = loginID;
	}
		
	public String getPassword() {
		if (password == null)
			return "";
		else
			return password;
	}

	public void setPassword(String password) {
		if (password == null)
			this.password = "";
		else
			this.password = password;
	}

	public String getHint() {
		if (hint == null)
			return "";
		else
			return hint;
	}

	public void setHint(String hint) {
		if (hint == null)
			this.hint = "";
		else
			this.hint = hint;
	}

	public String getAnswer() {
		if (answer == null)
			return "";
		else
			return answer;
	}

	public void setAnswer(String answer) {
		if (answer == null)
			this.answer = "";
		else
			this.answer = answer;
	}

	public int getPromo_email() {
		return promo_email;
	}

	public void setPromo_email(int promoEmail) {
		promo_email = promoEmail;
	}

	public String getLoa() {
		return loa;
	}

	public void setLoa(String loa) {
		this.loa = loa;
	}

	public int getLatperdiscount() {
		return latperdiscount;
	}

	public void setLatperdiscount(int latperdiscount) {
		this.latperdiscount = latperdiscount;
	}

	public int getLatamtdiscount() {
		return latamtdiscount;
	}

	public void setLatamtdiscount(int latamtdiscount) {
		this.latamtdiscount = latamtdiscount;
	}

	public Date getLatexpdate() {
		return latexpdate;
	}

	public void setLatexpdate(Date latexpdate) {
		this.latexpdate = latexpdate;
	}

	public int getInsperdiscount() {
		return insperdiscount;
	}

	public void setInsperdiscount(int insperdiscount) {
		this.insperdiscount = insperdiscount;
	}

	public int getInsamtdiscount() {
		return insamtdiscount;
	}

	public void setInsamtdiscount(int insamtdiscount) {
		this.insamtdiscount = insamtdiscount;
	}

	public Date getInsexpdate() {
		return insexpdate;
	}

	public void setInsexpdate(Date insexpdate) {
		this.insexpdate = insexpdate;
	}

	public int getOptperdiscount() {
		return optperdiscount;
	}

	public void setOptperdiscount(int optperdiscount) {
		this.optperdiscount = optperdiscount;
	}

	public int getOptamtdiscount() {
		return optamtdiscount;
	}

	public void setOptamtdiscount(int optamtdiscount) {
		this.optamtdiscount = optamtdiscount;
	}

	public Date getOptexpdate() {
		return optexpdate;
	}

	public void setOptexpdate(Date optexpdate) {
		this.optexpdate = optexpdate;
	}

	public int getDimperdiscount() {
		return dimperdiscount;
	}

	public void setDimperdiscount(int dimperdiscount) {
		this.dimperdiscount = dimperdiscount;
	}

	public int getDimamtdiscount() {
		return dimamtdiscount;
	}

	public void setDimamtdiscount(int dimamtdiscount) {
		this.dimamtdiscount = dimamtdiscount;
	}

	public Date getDimexpdate() {
		return dimexpdate;
	}

	public void setDimexpdate(Date dimexpdate) {
		this.dimexpdate = dimexpdate;
	}

	public int getMonperdiscount() {
		return monperdiscount;
	}

	public void setMonperdiscount(int monperdiscount) {
		this.monperdiscount = monperdiscount;
	}

	public int getMonamtdiscount() {
		return monamtdiscount;
	}

	public void setMonamtdiscount(int monamtdiscount) {
		this.monamtdiscount = monamtdiscount;
	}

	public Date getMonexpdate() {
		return monexpdate;
	}

	public void setMonexpdate(Date monexpdate) {
		this.monexpdate = monexpdate;
	}

	public int getSerperdiscount() {
		return serperdiscount;
	}

	public void setSerperdiscount(int serperdiscount) {
		this.serperdiscount = serperdiscount;
	}

	public int getSeramtdiscount() {
		return seramtdiscount;
	}

	public void setSeramtdiscount(int seramtdiscount) {
		this.seramtdiscount = seramtdiscount;
	}

	public Date getSerexpdate() {
		return serexpdate;
	}

	public void setSerexpdate(Date serexpdate) {
		this.serexpdate = serexpdate;
	}

	public int getWorperdiscount() {
		return worperdiscount;
	}

	public void setWorperdiscount(int workperdiscount) {
		this.worperdiscount = workperdiscount;
	}

	public int getWoramtdiscount() {
		return woramtdiscount;
	}

	public void setWoramtdiscount(int workamtdiscount) {
		this.woramtdiscount = workamtdiscount;
	}

	public Date getWorexpdate() {
		return worexpdate;
	}

	public void setWorexpdate(Date workexpdate) {
		this.worexpdate = workexpdate;
	}
	
	public BigDecimal getCreditline() {
		return creditline;
	}

	public void setCreditline(BigDecimal creditline) {
		this.creditline = creditline;
	}

	public BigDecimal getCreditavail() {
		return creditavail;
	}

	public void setCreditavail(BigDecimal creditavail) {
		this.creditavail = creditavail;
	}

	public String getCreditexp() {
		if (creditexp == null)
			return "";
		else
			return creditexp;		
	}

	public void setCreditexp(String creditexp) {
		if (creditexp == null)
			this.creditexp = "";
		else
			this.creditexp = creditexp;
	}

	public String getCreditcomment() {
		if (creditcomment == null)
			return "";
		else
			return creditcomment;		
	}

	public void setCreditcomment(String creditcomment) {		
		if (creditcomment == null)
			this.creditcomment = "";
		else
			this.creditcomment = creditcomment;
	}
	private BigDecimal creditavail;
	private String creditexp; 
	private String creditcomment;

	 
	private int totalRow;
	
	public String getBill_to_title() {
		return (bill_to_title == null)?"":bill_to_title.trim();
	}

	public void setBill_to_title(String billToTitle) {
		bill_to_title = billToTitle;
	}

	public String getBill_to_address1() {		
		return (bill_to_address1 == null)?"":bill_to_address1.trim();
	}

	public void setBill_to_address1(String billToAddress1) {
		bill_to_address1 = billToAddress1;
	}

	public String getBill_to_address2() {
		return (bill_to_address2 == null)?"":bill_to_address2.trim();		
	}

	public void setBill_to_address2(String billToAddress2) {
		bill_to_address2 = billToAddress2;
	}

	public String getBill_to_city() {		
		return (bill_to_city == null)?"":bill_to_city.trim();
	}

	public void setBill_to_city(String billToCity) {
		bill_to_city = billToCity;
	}

	public String getBill_to_state() {		
		return (bill_to_state == null)?"":bill_to_state.trim();
	}

	public void setBill_to_state(String billToState) {
		bill_to_state = billToState;
	}

	public String getBill_to_country() {		
		return (bill_to_country == null)?"":bill_to_country.trim();
	}

	public void setBill_to_country(String billToCountry) {
		bill_to_country = billToCountry;
	}

	public String getBill_to_postal() {
		return (bill_to_postal == null)?"":bill_to_postal.trim();		
	}

	public void setBill_to_postal(String billToPostal) {
		bill_to_postal = billToPostal;
	}

	public String getBill_to_fax() {		
		return (bill_to_fax == null)?"":bill_to_fax.trim();
	}

	public void setBill_to_fax(String billToFax) {
		bill_to_fax = billToFax;
	}

	public String getBill_to_phoneext() {		
		return (bill_to_phoneext == null)?"":bill_to_phoneext.trim();
	}

	public void setBill_to_phoneext(String billToPhoneext) {
		bill_to_phoneext = billToPhoneext;
	}

	public String getBill_to_email() {
		return (bill_to_email == null)?"":bill_to_email.trim();		
	}

	public void setBill_to_email(String billToEmail) {
		bill_to_email = billToEmail;
	}

	public String getShip_to_address1() {		
		return (ship_to_address1 == null)?"":ship_to_address1.trim();
	}

	public void setShip_to_address1(String shipToAddress1) {		
		ship_to_address1 = shipToAddress1;
	}

	public String getShip_to_title() {
		return (ship_to_title == null)?"":ship_to_title.trim();		
	}

	public void setShip_to_title(String shipToTitle) {
		ship_to_title = shipToTitle;
	}

	public String getShip_to_phoneext() {
		return (ship_to_phoneext == null)?"":ship_to_phoneext.trim();		
	}

	public void setShip_to_phoneext(String shipToPhoneext) {
		ship_to_phoneext = shipToPhoneext;
	}

	public String getShip_to_address2() {		
		return (ship_to_address2 == null)?"":ship_to_address2.trim();
	}

	public void setShip_to_address2(String shipToAddress2) {
		ship_to_address2 = shipToAddress2;
	}

	public String getShip_to_city() {
		return (ship_to_city == null)?"":ship_to_city.trim();		
	}

	public void setShip_to_city(String shipToCity) {
		ship_to_city = shipToCity;
	}

	public String getShip_to_postal() {		
		return (ship_to_postal == null)?"":ship_to_postal.trim();
	}

	public void setShip_to_postal(String shipToPostal) {
		ship_to_postal = shipToPostal;
	}

	public Date getTax_exempt_expire() {		
		return tax_exempt_expire;
	}

	public void setTax_exempt_expire(Date taxExemptExpire) {
		this.tax_exempt_expire = taxExemptExpire;
	}

	public String getTax_exempt_number() {
		return (tax_exempt_number == null)?"":tax_exempt_number.trim();		
	}

	public void setTax_exempt_number(String taxExemptNumber) {
		tax_exempt_number = taxExemptNumber;
	}
	


    /**
     * @return the totalRow
     */
    public int getTotalRow()
    {
        return totalRow;
    }

    /**
     * @param totalRow the totalRow to set
     */
    public void setTotalRow(int totalRow)
    {
        this.totalRow = totalRow;
    }
	
	public String getShip_to_company() {		
		return (ship_to_company == null)?"":ship_to_company.trim();	
	}
	public void setShip_to_company(String shipToCompany) {
		ship_to_company = shipToCompany;
	}
	public String getShip_to_phone() {		
		return (ship_to_phone == null)?"":ship_to_phone.trim();	
	}
	public void setShip_to_phone(String shipToPhone) {
		ship_to_phone = shipToPhone;
	}
	public String getBill_to_fname() {		
		return (bill_to_fname == null)?"":bill_to_fname.trim();
	}
	public void setBill_to_fname(String billToFname) {
		bill_to_fname = billToFname;
	}
	public String getBill_to_lname() {		
		return (bill_to_lname == null)?"":bill_to_lname.trim();
	}
	public void setBill_to_lname(String billToLname) {
		bill_to_lname = billToLname;
	}
	public String getBill_to_company() {		
		return (bill_to_company == null)?"":bill_to_company.trim();
	}
	public void setBill_to_company(String billToCompany) {
		bill_to_company = billToCompany;
	}
	public String getBill_to_phone() {		
		return (bill_to_phone == null)?"":bill_to_phone.trim();
	}
	public void setBill_to_phone(String billToPhone) {
		bill_to_phone = billToPhone;
	}
				
	public int getLinkNumber() {
		return linkNumber;
	}
	public void setLinkNumber(int linkNumber) {
		this.linkNumber = linkNumber;
	}
	public String getShopper_id() {
		return shopper_id;
	}
	public void setShopper_id(String shopperId) {
		shopper_id = shopperId;
	}
	public String getShip_to_fname() {		
		return (ship_to_fname == null)?"":ship_to_fname.trim();
	}
	public void setShip_to_fname(String shipToFname) {
		ship_to_fname = shipToFname;
	}
	public String getShip_to_lname() {		
		return (ship_to_lname == null)?"":ship_to_lname.trim();
	}
	public void setShip_to_lname(String shipToLname) {
		ship_to_lname = shipToLname;
	}

}
